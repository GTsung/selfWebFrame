package com.self.shard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.self.shard.domain.User;
import com.self.shard.mapper.UserMapper;
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
@ActiveProfiles("9014")
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardApplication9014Test {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testAdd() {
        // 垂直分庫
        User user = User.builder().username("Jack")
                .status("normal").build();
        userMapper.insert(user);
    }

    @Test
    public void findUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", 693245029849759745L);
        userMapper.selectOne(wrapper);
    }

}
