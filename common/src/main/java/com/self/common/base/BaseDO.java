package com.self.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseDO {

    @Id
    private Long id;

    private Integer delFlag;
    private String createUser;
    private String updateUser;
    private Date createTime;
    private Date updateTime;

    private String remark;


}
