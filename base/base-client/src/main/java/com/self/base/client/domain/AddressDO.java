package com.self.base.client.domain;

import com.self.common.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Table;

/**
 * @author GTsung
 * @date 2021/9/8
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "base_address")
public class AddressDO extends BaseDO {

    private String province;
    private String city;
    private String street;
}
