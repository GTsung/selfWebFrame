package com.self.common.vo;

import lombok.Data;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Data
public class PageParamVO {

    public static final int DEFAULT_PAGE_SIZE = 1000;

    protected Integer pageIndex = 0;
    protected Integer pageSize = DEFAULT_PAGE_SIZE;
}
