package com.dubbo.gmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GTsung
 * @date 2021/10/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String userId;

    private String goodsName;
}
