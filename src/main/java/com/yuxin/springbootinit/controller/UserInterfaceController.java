package com.yuxin.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yuxin.springbootinit.annotation.AuthCheck;
import com.yuxin.springbootinit.common.*;
import com.yuxin.springbootinit.constant.UserConstant;
import com.yuxin.springbootinit.exception.BusinessException;
import com.yuxin.springbootinit.exception.ThrowUtils;
import com.yuxin.springbootinit.model.dto.Interface.InterfaceEditRequest;
import com.yuxin.springbootinit.model.dto.Interface.InterfaceInvokeRequest;
import com.yuxin.springbootinit.model.dto.Interface.InterfaceQueryRequest;
import com.yuxin.springbootinit.model.dto.Interface.InterfaceUpdateRequest;
import com.yuxin.springbootinit.model.dto.UserInterface.UserInterfaceAddRequest;
import com.yuxin.springbootinit.model.entity.UserInterfaceInfo;
import com.yuxin.springbootinit.model.entity.User;
import com.yuxin.springbootinit.service.UserInterfaceInfoService;
import com.yuxin.springbootinit.service.UserService;
import com.yuxin.yuapiclientsdk.client.YuApiClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口关系管理
 * @author yuxin
 */
@RestController
@RequestMapping("/UserInterfaceInfo")
@Slf4j
public class UserInterfaceController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param userInterfaceAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addInterface(@RequestBody UserInterfaceAddRequest userInterfaceAddRequest, HttpServletRequest request) {
        if (userInterfaceAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceAddRequest, userInterfaceInfo);

        userInterfaceInfoService.validUserInterface(userInterfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        userInterfaceInfo.setUserId(loginUser.getId());
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newInterfaceId = userInterfaceInfo.getId();
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
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteInterface(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(userInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (userInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = userInterfaceInfoService.removeById(id);
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
    public BaseResponse<Boolean> updateInterface(@RequestBody InterfaceUpdateRequest interfaceUpdateRequest) {
        if (interfaceUpdateRequest == null || interfaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(interfaceUpdateRequest, userInterfaceInfo);
        String name = interfaceUpdateRequest.getName();

        userInterfaceInfoService.validUserInterface(userInterfaceInfo, true);
        long id = interfaceUpdateRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldUserInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
        return ResultUtils.success(result);
    }


    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<UserInterfaceInfo> getInterfaceById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(userInterfaceInfo);
    }

    /**
     * 分页获取列表（仅管理员）
     *
     * @param interfaceQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserInterfaceInfo>> listPostByPage(@RequestBody InterfaceQueryRequest interfaceQueryRequest) {
        long current = interfaceQueryRequest.getCurrent();
        long size = interfaceQueryRequest.getPageSize();
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size));
        return ResultUtils.success(userInterfaceInfoPage);
    }


    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param interfaceQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserInterfaceInfo>> listMyPostVOByPage(@RequestBody InterfaceQueryRequest interfaceQueryRequest,
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
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size));
        return ResultUtils.success(userInterfaceInfoPage);
    }
    // endregion
}