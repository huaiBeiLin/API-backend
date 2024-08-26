package com.yuxin.yuapicommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxin.yuapicommon.model.entity.InterfaceInfo;

/**
* @author hp
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-08-15 16:04:21
*/
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     * @param path
     * @param method
     * @return
     */

    InterfaceInfo getInterfaceInfo(String path, String method);
}
