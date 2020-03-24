package com.longshare.microservice.demo.crude.service;

import com.longshare.microservice.demo.crude.model.Menu;
import com.longshare.microservice.demo.crude.model.param.MenuQueryParam;
import com.longshare.microservice.demo.crude.orm.entity.MenuEntity;
import com.longshare.rest.core.service.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Generator on 2018-12-03 09:54:08.
 */
public interface MenuService extends Service<MenuEntity> {
    /**
     * 创建对象，会自动生成主键并返回对象，如果创建失败，则返回Null
     * @param menu
     * @return
     */
    Optional<Menu> create(MenuEntity menu);

    /**
     * 可选修改对象，只会修改给定的字段和值
     * @param menu
     * @return
     */
    Optional<Menu> patch(MenuEntity menu);

    List<Menu> queryMenu(MenuQueryParam menuQueryParam);

    void deleteMenu(String menuId);

    void batchDeleteMenus(List<String> menuIdList);
}
