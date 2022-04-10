package com.self;

import com.self.base.client.domain.AddressDO;
import com.self.common.utils.BeanUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test() {
        AddressDO addressDO = AddressDO.builder()
                .province("1").street("s").id(1L).remark("as")
                .build();
        AddressCopy addressCopy = AddressCopy.builder().city("11").build();
        BeanUtil.copy(addressDO, addressCopy);
        System.out.println(addressCopy.toString());
    }
}
