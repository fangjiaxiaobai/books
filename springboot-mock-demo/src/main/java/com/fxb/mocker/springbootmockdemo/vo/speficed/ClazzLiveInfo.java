package com.fxb.mocker.springbootmockdemo.vo.speficed;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuqi
 * 课程直播数据
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClazzLiveInfo {
    /**
     * 应出勤人数
     */
    private Integer needAttendCount;
    /**
     * 出勤人数
     */
    private Integer attendCount;
    /**
     * 出勤率
     */
    private BigDecimal attendRatio;
}
