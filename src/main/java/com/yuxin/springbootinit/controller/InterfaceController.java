package com.yuxin.springbootinit.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuxin.springbootinit.annotation.AuthCheck;
import com.yuxin.springbootinit.common.BaseResponse;
import com.yuxin.springbootinit.common.DeleteRequest;
import com.yuxin.springbootinit.common.ErrorCode;
import com.yuxin.springbootinit.common.ResultUtils;
import com.yuxin.springbootinit.constant.UserConstant;
import com.yuxin.springbootinit.exception.BusinessException;
import com.yuxin.springbootinit.exception.ThrowUtils;

import com.yuxin.springbootinit.model.entity.User;
import com.yuxin.springbootinit.service.InterfaceInfoService;
import com.yuxin.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.yuxin.springbootinit.model.dto.Interface.InterfaceEditRequest;
import com.yuxin.springbootinit.model.dto.Interface.InterfaceQueryRequest;
import com.yuxin.springbootinit.model.dto.Interface.InterfaceUpdateRequest;
import com.yuxin.springbootinit.model.entity.InterfaceInfo;
import com.yuxin.springbootinit.service.InterfaceInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 接口接口
 * @author yuxin
 */
@RestController
@RequestMapping("/InterfaceInfo")
@Slf4j
public class InterfaceController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterface(@RequestBody com.yuxin.springbootinit.model.dto.Interface.InterfaceAddRequest interfaceAddRequest, HttpServletRequest request) {
        if (interfaceAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceAddRequest, interfaceInfo);
        String name = interfaceAddRequest.getName();
        if (name != null) {
            interfaceInfo.setName(name);
        }
        interfaceInfoService.validInterface(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newInterfaceId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterface(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (interfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param interfaceUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updatePost(@RequestBody InterfaceUpdateRequest interfaceUpdateRequest) {
        if (interfaceUpdateRequest == null || interfaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceUpdateRequest, interfaceInfo);
        String name = interfaceUpdateRequest.getName();
        if (name != null) {
            interfaceInfo.setName(name);
        }
        interfaceInfoService.validInterface(interfaceInfo, true);
        long id = interfaceUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public BaseResponse<InterfaceInfo> getInterfaceById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 分页获取列表（仅管理员）
     *
     * @param interfaceQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<InterfaceInfo>> listPostByPage(@RequestBody InterfaceQueryRequest interfaceQueryRequest) {
        long current = interfaceQueryRequest.getCurrent();
        long size = interfaceQueryRequest.getPageSize();
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size));
        return ResultUtils.success(interfaceInfoPage);
    }


    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param interfaceQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<InterfaceInfo>> listMyPostVOByPage(@RequestBody InterfaceQueryRequest interfaceQueryRequest,
            HttpServletRequest request) {
        if (interfaceQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        interfaceQueryRequest.setUserId(loginUser.getId());
        long current = interfaceQueryRequest.getCurrent();
        long size = interfaceQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size));
        return ResultUtils.success(interfaceInfoPage);
    }

    // endregion

//    /**
//     * 分页搜索（从 ES 查询，封装类）
//     *
//     * @param interfaceQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/search/page/vo")
//    public BaseResponse<Page<InterfaceInfo>> searchPostVOByPage(@RequestBody InterfaceQueryRequest interfaceQueryRequest,
//            HttpServletRequest request) {
//        long size = interfaceQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.searchFromEs(interfaceQueryRequest);
//        return ResultUtils.success(InterfaceInfoService.getPostVOPage(PostPage, request));
//    }

    /**
     * 编辑（用户）
     *
     * @param interfaceEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editPost(@RequestBody InterfaceEditRequest interfaceEditRequest, HttpServletRequest request) {
        if (interfaceEditRequest == null || interfaceEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceEditRequest, interfaceInfo);
        String name = interfaceEditRequest.getName();
        if (name != null) {
            interfaceInfo.setName(name);
        }
        interfaceInfoService.validInterface(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        long id = interfaceEditRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldInterfaceInfo.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

}
