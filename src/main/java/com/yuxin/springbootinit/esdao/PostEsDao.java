package com.yuxin.springbootinit.esdao;

import com.yuxin.springbootinit.model.dto.Post.PostEsDTO;
import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 *
 * @author <a href="https://github.com/liyuxin">程序员yuxin</a>
 * @from <a href="https://yuxin.icu">编程导航知识星球</a>
 */
public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {

    List<PostEsDTO> findByUserId(Long userId);
}