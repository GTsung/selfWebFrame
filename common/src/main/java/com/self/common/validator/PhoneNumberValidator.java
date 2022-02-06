package com.self.common.validator;

import com.self.common.anno.PhoneNumber;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** 電話號碼校驗
 *  ConstraintValidator<A extends Annotation, T> A為注解，T為校驗字段的類型
 * @author GTsung
 * @date 2022/2/6
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private static final String MOBILE_REGEX = "^[1][3,4,5,6,7,8,9][0-9]{9}$";

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        // 初始化操作
    }

    // 判斷
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isNoneBlank(value) && value.matches(MOBILE_REGEX);
    }
}
