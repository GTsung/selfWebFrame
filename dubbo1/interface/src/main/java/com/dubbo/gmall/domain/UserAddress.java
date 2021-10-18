package com.dubbo.gmall.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author GTsung
 * @date 2021/10/11
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress implements Serializable {

    private Integer id;

    private String userAddress;

    private String userId;

    private String phoneNum;
}
