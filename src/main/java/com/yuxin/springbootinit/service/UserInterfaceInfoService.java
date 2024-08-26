package com.yuxin.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxin.yuapicommon.model.entity.UserInterfaceInfo;
import org.apache.poi.ss.formula.functions.T;

public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterface(UserInterfaceInfo userinterfaceInfo, boolean add);

    boolean invokeCount(long interfaceInfoId, long userId) ;


}
