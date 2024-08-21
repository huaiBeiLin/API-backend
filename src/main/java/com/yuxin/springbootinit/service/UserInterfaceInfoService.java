package com.yuxin.springbootinit.service;

import com.yuxin.springbootinit.model.entity.InterfaceInfo;
import com.yuxin.springbootinit.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxin.springbootinit.model.entity.UserInterfaceInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author hp
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-08-18 17:00:44
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterface(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
