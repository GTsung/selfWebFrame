package com.self.reflects.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author GTsung
 * @date 2021/10/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReflectSample implements Serializable {

    private String name;
    private Integer version;

    private String desc;
}
