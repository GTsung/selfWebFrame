package com.self.shard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.self.shard.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
