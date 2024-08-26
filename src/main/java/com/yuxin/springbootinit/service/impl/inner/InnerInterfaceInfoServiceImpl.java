package com.yuxin.springbootinit.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxin.springbootinit.common.ErrorCode;
import com.yuxin.springbootinit.exception.BusinessException;
import com.yuxin.springbootinit.mapper.InterfaceInfoMapper;
import com.yuxin.yuapicommon.model.entity.InterfaceInfo;
import com.yuxin.yuapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * packageName com.yuxin.springbootinit.service.impl
 *
 * @author yuxin
 */

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 实现接口中的getInterfaceInfo方法，用于根据URL和请求方法获取内部接口信息
     * @param url 请求URL
     * @param method 请求方法
     * @return 内部接口信息，如果找不到匹配的接口则返回null
     * @throws com.yuxin.springbootinit.exception.BusinessException 参数错误时抛出业务异常
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        // 参数校验
        if (StringUtils.isAnyBlank(url, method)) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建查询条件包装器
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);

        // 使用 InterfaceInfoMapper 的 selectOne方法查询接口信息
        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
