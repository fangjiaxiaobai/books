package com.fxb.mocker.springbootmockdemo.vo.speficed;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fangjiaxiaobai
 * @date 2021-03-01 11:46
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FirstPageDataResponse {
    private List<SubclazzLessonDataResponse> subclazzClazzLessonList;
}
