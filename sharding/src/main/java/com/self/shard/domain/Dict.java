package com.self.shard.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author GTsung
 * @date 2022/1/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@TableName("t_dict")
public class Dict {

    private Long dictId;
    private String status;
    private String value;
}
