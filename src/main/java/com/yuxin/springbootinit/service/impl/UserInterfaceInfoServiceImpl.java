package com.yuxin.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxin.springbootinit.common.ErrorCode;
import com.yuxin.springbootinit.exception.BusinessException;
import com.yuxin.springbootinit.exception.ThrowUtils;
import com.yuxin.yuapicommon.model.entity.InterfaceInfo;
import com.yuxin.yuapicommon.model.entity.UserInterfaceInfo;
import com.yuxin.springbootinit.service.UserInterfaceInfoService;
import com.yuxin.springbootinit.mapper.UserInterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author hp
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-08-18 17:00:44
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Override
    public void validUserInterface(UserInterfaceInfo userinterfaceInfo, boolean add) {
        if (userinterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = userinterfaceInfo.getId();
        long userId = userinterfaceInfo.getUserId();
        long leftNum = userinterfaceInfo.getLeftNum();
        long totalNum = userinterfaceInfo.getTotalNum();
        // id和userId不能小于0
        if (add) {
            ThrowUtils.throwIf(id < 0 || userId < 0, ErrorCode.PARAMS_ERROR);
        }
        // 校验剩余次数
        if (leftNum < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数小于0");
        }
        // 校验总次数
        if (totalNum < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "总次数小于0");
        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }
}




