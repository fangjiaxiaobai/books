package com.fxb.mocker.springbootmockdemo.vo.speficed;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 听课相关指标信息
 * @author xuqi
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListenClazzInfo {
    /**
     * 有效听课率
     */
    private BigDecimal effectiveListenRatio;
    /**
     * 有效听课人次
     */
    private Integer effectiveListenCount;
    /**
     * 听课人数
     */
    private Integer listenClazzCount;


}
