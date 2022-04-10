package com.self;

import lombok.*;

/**
 * @author GTsung
 * @date 2022/3/20
 */
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Data
@AllArgsConstructor
public class AddressCopy {

    private String city;
    private String street;
    private String province;
}
