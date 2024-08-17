package com.yuxin.springbootinit.mapper;

import com.yuxin.springbootinit.model.entity.Post;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 帖子数据库操作测试
 *
 * @author <a href="https://github.com/liyuxin">程序员yuxin</a>
 * @from <a href="https://yuxin.icu">编程导航知识星球</a>
 */
@SpringBootTest
class PostMapperTest {

    @Resource
    private PostMapper PostMapper;

    @Test
    void listPostWithDelete() {
        List<Post> PostList = PostMapper.listPostWithDelete(new Date());
        Assertions.assertNotNull(PostList);
    }
}