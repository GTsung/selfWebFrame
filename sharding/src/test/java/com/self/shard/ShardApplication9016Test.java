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
 * @date 2022/1/27
 */
@ActiveProfiles("9016")
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardApplication9016Test {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void addUser(){
        // 主從分離的測試
        userMapper.insert(User.builder()
                .username("adams")
                .status("normal").build());
    }

    @Test
    public void findUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", 693506014758043649L);
        userMapper.selectOne(wrapper);
    }

}
