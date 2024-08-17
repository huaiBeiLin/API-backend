package com.yuxin.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxin.springbootinit.common.ErrorCode;
import com.yuxin.springbootinit.exception.BusinessException;
import com.yuxin.springbootinit.exception.ThrowUtils;
import com.yuxin.springbootinit.model.entity.InterfaceInfo;
import com.yuxin.springbootinit.service.InterfaceInfoService;
import com.yuxin.springbootinit.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author hp
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-08-15 16:04:21
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
implements InterfaceInfoService {

    @Override
    public void validInterface(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        String method = interfaceInfo.getMethod();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, url, requestHeader, responseHeader, method), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
    }

}
