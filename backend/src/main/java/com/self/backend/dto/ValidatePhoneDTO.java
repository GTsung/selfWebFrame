package com.self.backend.dto;

import com.self.common.anno.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author GTsung
 * @date 2022/2/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ValidatePhoneDTO {

    @PhoneNumber
    private String phoneNumber;
}
