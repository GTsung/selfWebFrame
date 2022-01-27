package com.self.shard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.self.shard.domain.Dict;
import com.self.shard.mapper.DictMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author GTsung
 * @date 2022/1/26
 */
@ActiveProfiles("9015")
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardApplication9015Test {

    @Autowired
    private DictMapper dictMapper;

    @Test
    public void testAdd() {
        // 公共表數據的添加,所有庫都需要添加這個表，當插入時每個庫都會插入數據
        dictMapper.insert(Dict.builder().status("normal").value("start").build());
    }

    @Test
    public void testDelete() {
        // 刪除時每個數據庫的公共表的數據都會被刪除
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_id", 693467670351183873L);
        dictMapper.delete(wrapper);
    }

}
