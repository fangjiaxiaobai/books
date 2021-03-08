package com.fxb.mocker.springbootmockdemo.vo.speficed;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 练习相关指标数据
 *
 * @author xuqi
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PractiseInfo {
    /**
     * 练习订正率
     */
    private BigDecimal firstSubmitAndRectifyRatio;
    /**
     * 练习订正人数
     */
    private Integer firstSubmitAndRectifyCount;
    /**
     * 应提交人数
     */
    private Integer needSubmitCount;
    /**
     * 待订正人数
     */
    private Integer notCorrectCount;
    /**
     * 练习提交人数
     */
    private Integer currentSubmitCount;

}
