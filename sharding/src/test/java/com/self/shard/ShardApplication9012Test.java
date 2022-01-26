package com.self.shard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.self.shard.domain.Course;
import com.self.shard.mapper.CourseMapper;
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
@ActiveProfiles("9012")
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardApplication9012Test {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void testAdd() {
        for (int i = 0; i < 2; i++) {
            // 主鍵使用雪花算法生成
            courseMapper.insert(Course.builder()
                    .userId(100L).status("normal")
                    .cname("Java").build());
        }
    }

    @Test
    public void findCourse() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", 693234656648101888L);
        System.out.println(courseMapper.selectOne(wrapper));
    }

}
