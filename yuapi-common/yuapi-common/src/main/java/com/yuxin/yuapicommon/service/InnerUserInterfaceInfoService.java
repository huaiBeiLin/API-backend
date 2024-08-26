package com.yuxin.yuapicommon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxin.yuapicommon.model.entity.InterfaceInfo;
import com.yuxin.yuapicommon.model.entity.User;
import com.yuxin.yuapicommon.model.entity.UserInterfaceInfo;

/**
* @author hp
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-08-18 17:00:44
*/
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}