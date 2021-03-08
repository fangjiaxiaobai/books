package com.fxb.mocker.springbootmockdemo.vo.speficed;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author xuqi
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubclazzLessonDataResponse {
    /**
     * 辅导班编号
//     */
    private Long subclazzNumber;
    /**
     * 班级编号
     */
    private Long clazzNumber;
    /**
     * 班级课节编号
     */
    private Long clazzLessonNumber;
    /**
     * 课节类型
     */
    private String clazzLessonType;
//    /**
//     * 课程直播数据
//     */
    private ClazzLiveInfo clazzLiveInfo;
//    /**
//     * 课程听课数据
//     */
    private ListenClazzInfo listenClazzInfo;
//    /**
//     * 课程练习数据
//     */
    private PractiseInfo practiseInfo;
//    /**
//     * 课节信息
//     */
    private ClazzLessonInfo clazzLessonInfo;
//
    private TaskInfo taskInfo;

}
