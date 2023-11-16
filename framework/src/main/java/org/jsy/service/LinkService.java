package org.jsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jsy.domain.Link;
import org.jsy.domain.ResponseResult;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();
}
