package com.self.shard;

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
@ActiveProfiles("9013")
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardApplication9013Test {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void testAdd() {
        // 根據userId分到不同的庫中，同時sharding使用雪花算法每次插入庫中時將id寫入
        Course course = Course.builder().cname("JAVA").userId(100L).status("NORMAL").build();
        courseMapper.insert(course);
        course.setUserId(101L);
        course.setCname("PYTHON");
        courseMapper.insert(course);
    }

}
