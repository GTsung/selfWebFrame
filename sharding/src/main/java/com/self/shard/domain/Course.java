package com.self.shard.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author GTsung
 * @date 2022/1/26
 */
@Data
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private Long cid;
    private String cname;
    private Long userId;
    private String status;

}
