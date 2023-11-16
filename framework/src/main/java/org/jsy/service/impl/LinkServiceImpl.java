package org.jsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsy.constants.SystemConstants;
import org.jsy.domain.Link;
import org.jsy.domain.ResponseResult;
import org.jsy.mapper.LinkMapper;
import org.jsy.service.LinkService;
import org.jsy.utils.BeanCopyUtils;
import org.jsy.vo.LinkVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        //要求查的是友链表status字段的值为0，注意SystemConstants是我们写的一个常量类，用来解决字面值的书写问题
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //把最后的查询结果封装成LinkListVo(我们写的实体类)。BeanCopyUtils是我们写的工具类
        List<LinkVO> linkVos = BeanCopyUtils.copyBeanList(links, LinkVO.class);

        //封装响应返回。
        return ResponseResult.okResult(linkVos);
    }
}
