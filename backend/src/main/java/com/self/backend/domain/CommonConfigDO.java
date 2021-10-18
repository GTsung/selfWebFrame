package com.self.backend.domain;

import com.self.common.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author GTsung
 * @date 2021/8/19
 */
@Data
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "common_config")
public class CommonConfigDO extends BaseDO {

    @Column(name = "`group`")
    private String group;

    @Column(name = "`key`")
    private String key;

    @Column(name = "`value`")
    private String value;
}
