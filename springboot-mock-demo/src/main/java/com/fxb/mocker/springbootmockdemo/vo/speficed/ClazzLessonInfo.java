package com.fxb.mocker.springbootmockdemo.vo.speficed;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClazzLessonInfo {
    /**
     * 开课时间
     */
    private Long clazzBeginTime;
    /**
     * 结课时间
     */
    private Long clazzEndTime;
    /**
     * 上课总时长
     */
    private Long clazzTime;
    /**
     * 听课时长
     */
    private Long listenClazzTime;
    /**
     * 应出勤人数
     */
    private Integer needAttendCount;
    /**
     * 应提交人数
     */
    private Integer needSubmitCount;
    /**
     * 预习完成率
     */
    private BigDecimal prepareLessonRatio;
    /**
     * 预习完成人数
     */
    private Integer prepareLessonCount;
    /**
     * 辅导班number
     */
    private Long subclazzNumber;
}
