package com.self.common.mq.boot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GTsung
 * @date 2021/8/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessageBO {

    private String routingKey;

    private String context;

}
