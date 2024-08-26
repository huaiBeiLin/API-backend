package com.yuxin.springbootinit.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxin.springbootinit.common.ErrorCode;
import com.yuxin.springbootinit.exception.BusinessException;
import com.yuxin.springbootinit.mapper.UserMapper;
import com.yuxin.yuapicommon.model.entity.User;
import com.yuxin.yuapicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * packageName com.yuxin.springbootinit.service.impl
 *
 * @author yuxin
 */
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 实现接口中的getInvokeUser方法，用于根据密钥获取内部用户信息
     * @param accessKey
     * @return 内部用户信息，如果找不到匹配的用户则返回null
     * @throws com.yuxin.springbootinit.exception.BusinessException 参数错误时抛出业务异常
     */
    @Override
    public User getInvokeUser(String accessKey) {
        // 参数校验
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建查询条件包装器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);

        // 使用 UserMapper 的 selectOne 方法查询用户信息
        return userMapper.selectOne(queryWrapper);
    }
}
