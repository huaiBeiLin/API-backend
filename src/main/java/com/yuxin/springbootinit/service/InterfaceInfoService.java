package com.yuxin.springbootinit.service;

import com.yuxin.springbootinit.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author hp
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-08-15 16:04:21
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add
     */
    void validInterface(InterfaceInfo interfaceInfo, boolean add);
}
