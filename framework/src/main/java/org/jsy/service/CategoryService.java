package org.jsy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jsy.domain.Category;
import org.jsy.domain.ResponseResult;

import java.util.List;

public interface CategoryService extends IService<Category> {
    //查询文章分类列表
    ResponseResult getCategoryList();
}
