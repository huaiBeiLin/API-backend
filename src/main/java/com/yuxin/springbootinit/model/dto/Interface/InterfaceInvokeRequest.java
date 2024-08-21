package com.yuxin.springbootinit.model.dto.Interface;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author yuxin
 */
@Data
public class InterfaceInvokeRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String requestParams;

    private static final long serialVersionUID = 1L;
}