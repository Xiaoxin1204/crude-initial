package com.longshare.microservice.demo.crude.service.impl;

import com.longshare.microservice.demo.crude.ProblemCode;
import com.longshare.microservice.demo.crude.model.Menu;
import com.longshare.microservice.demo.crude.model.param.MenuQueryParam;
import com.longshare.microservice.demo.crude.orm.entity.MenuEntity;
import com.longshare.microservice.demo.crude.orm.mapper.MenuMapper;
import com.longshare.microservice.demo.crude.orm.param.MenuQueryBean;
import com.longshare.microservice.demo.crude.service.MenuService;
import com.longshare.microservice.demo.crude.utils.SortUtils;
import com.longshare.rest.core.problem.ProblemSolver;
import com.longshare.rest.core.service.AbstractServiceImpl;
import com.longshare.rest.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Generator on 2018-12-03 09:54:08.
 */
@Service
@Slf4j
public class MenuServiceImpl extends AbstractServiceImpl<MenuEntity> implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    //private RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional
    public Optional<Menu> create(MenuEntity menu) {
        menu.setId(menu.getCode());
        MenuQueryBean menuQueryBean = new MenuQueryBean();
        menuQueryBean.setCode(menu.getCode());
        List<MenuEntity> menuEntities = menuMapper.queryMenu(menuQueryBean);
        if(!CollectionUtils.isEmpty(menuEntities)){
            throw ProblemSolver.client(ProblemCode.MENU_CODE_HAS_EXIST).build();
        }
        long currentTime = DateUtil.getCurrentTime();
        menu.setCreatedAt(currentTime);
        Menu menuVO = new Menu();
        BeanUtils.copyProperties(menu,menuVO);
        return this.save(menu)?Optional.of(menuVO):Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Menu> patch(MenuEntity menu) {
        if(StringUtils.isEmpty(menu.getId()))
            throw ProblemSolver.client(ProblemCode.ID_MISS).build();
        long currentTime = DateUtil.getCurrentTime();
        menu.setUpdatedAt(currentTime);
        Menu menuVO = new Menu();
        BeanUtils.copyProperties(menu,menuVO);
        return this.update(menu)?Optional.of(menuVO):Optional.empty();
    }

    @Override
    public List<Menu> queryMenu(MenuQueryParam menuQueryParam) {
        MenuQueryBean menuQueryBean = new MenuQueryBean();
        BeanUtils.copyProperties(menuQueryParam,menuQueryBean);
        if(!CollectionUtils.isEmpty(menuQueryParam.getSortList())){
            menuQueryBean.setOrderByStr(SortUtils.formatOrderBy(SortUtils.decompile(menuQueryParam.getSortList()),menuQueryParam.getOrderList()));
        }else {
            menuQueryBean.setOrderByStr("no asc");
        }
        List<MenuEntity> menuEntities = menuMapper.queryMenu(menuQueryBean);
        List<String> menuIds = new ArrayList<>();
        if(!StringUtils.isEmpty(menuQueryParam.getRoleId())){
            //menuIds = roleMenuMapper.queryMenuByRoleId(menuQueryParam.getRoleId());
        }

        List<Menu> menus = new ArrayList<>();
        if(!CollectionUtils.isEmpty(menuEntities)){
            for(MenuEntity menuEntity:menuEntities){
                Menu menu = new Menu();
                BeanUtils.copyProperties(menuEntity,menu);
                if(!CollectionUtils.isEmpty(menuIds)){
                    if(menuIds.contains(menu.getId())){
                        menu.setChecked(true);
                    }else {
                        menu.setChecked(false);
                    }
                }
                if(!CollectionUtils.isEmpty(menuEntity.getChildren())){
                    List<Menu> menuList = new ArrayList<>();
                    for(MenuEntity entity:menuEntity.getChildren()){
                        Menu me = new Menu();
                        BeanUtils.copyProperties(entity,me);
                        if(!CollectionUtils.isEmpty(menuIds)){
                            if(menuIds.contains(me.getId())){
                                me.setChecked(true);
                            }else {
                                me.setChecked(false);
                            }
                        }
                        menuList.add(me);
                    }
                    menu.setChildren(menuList);
                }
                if(StringUtils.isEmpty(menu.getParentId())){
                    menus.add(menu);
                }
            }
        }
        return menus;
    }

    @Override
    @Transactional
    public void deleteMenu(String menuId) {
        MenuQueryBean menuQueryBean = new MenuQueryBean();
        menuQueryBean.setId(menuId);
        List<MenuEntity> menuEntities = menuMapper.queryMenu(menuQueryBean);
        if(!CollectionUtils.isEmpty(menuEntities)){
            //根据menuId查询到值进行删除
            MenuEntity menuEntity = menuEntities.get(0);
            if(CollectionUtils.isEmpty(menuEntity.getChildren())){
                mapper.deleteByPrimaryKey(menuId);
            }else {
                this.deleteMenuTree(menuEntity);
            }
        }
    }

    @Override
    @Transactional
    public void batchDeleteMenus(List<String> menuIdList){
        for(String menuId: menuIdList){
            if(!StringUtils.isEmpty(menuId)){
                MenuQueryBean menuQueryBean = new MenuQueryBean();
                menuQueryBean.setId(menuId);
                List<MenuEntity> menuEntities = menuMapper.queryMenu(menuQueryBean);
                if(!CollectionUtils.isEmpty(menuEntities)){
                    //根据menuId查询到值进行删除
                    MenuEntity menuEntity = menuEntities.get(0);
                    if(CollectionUtils.isEmpty(menuEntity.getChildren())){
                        mapper.deleteByPrimaryKey(menuId);
                    }else {
                        this.deleteMenuTree(menuEntity);
                    }
                }
            }
        }
    }

    /**
     * 递归删除菜单
     */
    private void deleteMenuTree(MenuEntity menuEntity){
        if(menuEntity != null){
            if(!CollectionUtils.isEmpty(menuEntity.getChildren())){
                for(MenuEntity entity:menuEntity.getChildren()){
                    deleteMenuTree(entity);
                }
            }
            this.deleteById(menuEntity.getId());
        }

    }


}
