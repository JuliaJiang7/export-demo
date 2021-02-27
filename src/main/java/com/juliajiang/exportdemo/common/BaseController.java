package com.juliajiang.exportdemo.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/18 4:00 下午
 */
public class BaseController {
    public Page getPage(BaseReq req) {
        Page page = new Page();
        page.setCurrent(req.getCurrent() == null ? 1 : req.getCurrent());
        page.setSize(req.getSize() == null ? 10 : req.getSize());
        return page;
    }
}
