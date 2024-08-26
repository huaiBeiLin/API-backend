package com.yuxin.springbootinit.service.impl.inner;

import com.yuxin.yuapicommon.service.InnerUserInterfaceInfoService;

import com.yuxin.springbootinit.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * packageName com.yuxin.springbootinit.service.impl
 *
 * @author yuxin
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
