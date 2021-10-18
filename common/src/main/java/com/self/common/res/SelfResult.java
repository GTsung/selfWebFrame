package com.self.common.res;

import com.self.common.enums.ResultEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Data
@NoArgsConstructor
public class SelfResult<T> {

    private int total;

    private String code;

    private T data;

    private SelfResult(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public SelfResult(int total, String code, T data) {
        this.total = total;
        this.code = code;
        this.data = data;
    }

    public static SelfResult<String> SUCCESS() {
        return new SelfResult<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc());
    }

    public static <T> SelfResult SUCCESS(T data) {
        return new SelfResult<>(ResultEnum.SUCCESS.getCode(), data);
    }

    public static <T> SelfResult SUCCESS(int total, T data) {
        return new SelfResult<>(total, ResultEnum.SUCCESS.getCode(), data);
    }

    public static SelfResult<String> ERROR() {
        return new SelfResult<>(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getDesc());
    }

}
