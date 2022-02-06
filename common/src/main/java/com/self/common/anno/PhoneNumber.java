package com.self.common.anno;


import com.self.common.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PhoneNumberValidator.class})
public @interface PhoneNumber {

    String message() default "手機號碼校驗不通過";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
