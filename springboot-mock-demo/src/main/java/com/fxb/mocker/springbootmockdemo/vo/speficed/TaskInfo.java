package com.fxb.mocker.springbootmockdemo.vo.speficed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lvjiaying01
 * @date 2020-09-12 12:01
 * @since 1.0
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TaskInfo {

    /**
     * 待订正人数
     */
    private Integer shouldModifyCount;


}
