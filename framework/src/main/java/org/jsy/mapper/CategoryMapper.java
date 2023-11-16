package org.jsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jsy.domain.Category;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
