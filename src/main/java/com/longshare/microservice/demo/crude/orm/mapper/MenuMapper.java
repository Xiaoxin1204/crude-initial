package com.longshare.microservice.demo.crude.orm.mapper;

import com.longshare.microservice.demo.crude.orm.entity.MenuEntity;
import com.longshare.microservice.demo.crude.orm.param.MenuQueryBean;
import com.longshare.rest.core.dao.Mapper;

import java.util.List;

public interface MenuMapper extends Mapper<MenuEntity> {
    List<MenuEntity> queryMenu(MenuQueryBean menuQueryBean);

    List<MenuEntity> findMenuByParentId();
}