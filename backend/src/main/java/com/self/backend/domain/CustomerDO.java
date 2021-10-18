package com.self.backend.domain;

import com.self.common.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_customer")
public class CustomerDO extends BaseDO {

    private String name;

    private Integer age;

    private String cardNum;

    private String phone;
}
