package com.longshare.microservice.demo.crude.controller;

import com.longshare.microservice.demo.crude.ProblemCode;
import com.longshare.microservice.demo.crude.model.Menu;
import com.longshare.microservice.demo.crude.model.MenuDetail;
import com.longshare.microservice.demo.crude.model.MenuTree;
import com.longshare.microservice.demo.crude.model.param.MenuAddParam;
import com.longshare.microservice.demo.crude.model.param.MenuQueryParam;
import com.longshare.microservice.demo.crude.model.param.MenuUpdateParam;
import com.longshare.microservice.demo.crude.orm.entity.MenuEntity;
import com.longshare.microservice.demo.crude.service.MenuService;
import com.longshare.rest.core.problem.ProblemSolver;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by Generator on 2018-12-03 09:54:08.
 */

@Slf4j
@RestController
@RequestMapping("/v1/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;


/**
     * 查询菜单树形结构
     * @param menuQueryParam
     * @return
     */

    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(notes = "查询菜单树形结构", value = "查询菜单树形结构", httpMethod = "GET")
    public ResponseEntity<Resources<Resource<Menu>>> list(MenuQueryParam menuQueryParam) {
        List<Menu> list = menuService.queryMenu(menuQueryParam);
        List<Resource<Menu>> menus = StreamSupport.stream(list.spliterator(), false)
                .map(menu -> new Resource<>(menu,
                        getLinkList(menu)
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new Resources<>(menus));
    }


/**
     * 登录后页面渲染查询菜单树形结构
     * @return
     */
    @GetMapping(value = "/index_menu",produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(notes = "登录后页面渲染查询菜单树形结构", value = "登录后页面渲染查询菜单树形结构", httpMethod = "GET")
    public ResponseEntity<List<MenuTree>> menuTree() {
        MenuQueryParam menuQueryParam = new MenuQueryParam();
        List<Menu> list = menuService.queryMenu(menuQueryParam);
        List<MenuTree> menuTrees = new ArrayList<>();
        for (Menu menu:list){
            MenuTree menuTree = new MenuTree();
            BeanUtils.copyProperties(menu,menuTree);
            menuTrees.add(menuTree);
        }

        return ResponseEntity.ok(menuTrees);
    }

/**
     * 添加菜单
     * @param menu
     * @return
     */

    @PostMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(notes = "添加菜单", value = "添加菜单", httpMethod = "POST")
    ResponseEntity<?> create(@RequestBody MenuAddParam menu) {
        MenuEntity entity = new MenuEntity();
        BeanUtils.copyProperties(menu,entity);
        Optional<Menu> savedMenu = menuService.create(entity);

        if(!savedMenu.isPresent())
            throw ProblemSolver.client(ProblemCode.RESOURCE_CREATED_FAILED).withStatus(Status.UNPROCESSABLE_ENTITY).build();

        Resource<Menu> menuResource = new Resource<>(savedMenu.get(),
                linkTo(methodOn(MenuController.class).detail(savedMenu.get().getId())).withSelfRel());
        try {
            return ResponseEntity
                    .created(new URI(menuResource.getLink(Link.REL_SELF).getHref()))
                    .body(menuResource);
        } catch (URISyntaxException e) {
            throw ProblemSolver.server(ProblemCode.LINK_CREATED_FAILED).withStatus(Status.ACCEPTED).build();
        }
    }


/**
     * 删除菜单
     * @param id
     * @return
     */

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(notes = "删除菜单", value = "删除菜单", httpMethod = "DELETE")
    public ResponseEntity delete(@PathVariable String id) {
        menuService.deleteMenu(id);
        Optional<URI> uri = getUri(getCollectionLink());
        return uri.isPresent() ? ResponseEntity.noContent().location(uri.get())
                .build() : ResponseEntity.noContent()
                .build();
    }


/**
     * 批量删除菜单
     */

    @DeleteMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(notes = "批量删除菜单", value = "批量删除菜单", httpMethod = "DELETE")
    public ResponseEntity menuBatchDeletions(@RequestParam("idList") List<String> menuIdList){
        if(StringUtils.isEmpty(menuIdList)){
            throw ProblemSolver.client(ProblemCode.MENU_DELETIONS_PARAMS_NOT_EXIST).withStatus(Status.BAD_REQUEST).build();
        }
        menuService.batchDeleteMenus(menuIdList);
        Optional<URI> uri = getUri(getCollectionLink());
        return uri.isPresent() ? ResponseEntity.noContent().location(uri.get())
                .build() : ResponseEntity.noContent()
                .build();
    }


/**
     * 更新菜单信息
     * @param menuUpdateParam
     * @param id
     * @return
     */

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(notes = "更新菜单信息", value = "更新菜单信息", httpMethod = "PATCH")
    public ResponseEntity<?> patch(@RequestBody MenuUpdateParam menuUpdateParam, @PathVariable String id) {
        if(StringUtils.isEmpty(menuUpdateParam.getId()))
            menuUpdateParam.setId(id);
        MenuEntity entity = new MenuEntity();
        BeanUtils.copyProperties(menuUpdateParam,entity);
        Optional<Menu> updatedMenu = menuService.patch(entity);
        Link newlyCreatedLink = linkTo(methodOn(MenuController.class).detail(id)).withSelfRel();

        try {
            return updatedMenu.isPresent()?ResponseEntity.ok()
                    .location(new URI(newlyCreatedLink.getHref())).body(new Resource<>(menuUpdateParam, newlyCreatedLink)):ResponseEntity.noContent()
                    .location(new URI(newlyCreatedLink.getHref()))
                    .build();
        } catch (URISyntaxException e) {
            throw ProblemSolver.server(ProblemCode.LINK_CREATED_FAILED).withTitle("修改成功，但是创建资源["+menuUpdateParam.getId()+"]链接失败").withStatus(Status.ACCEPTED).build();
        }
    }


/**
     * 查询菜单详情
     * @param id
     * @return
     */

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(notes = "查询菜单详情", value = "查询菜单详情", httpMethod = "GET")
    public ResponseEntity<Resource<MenuDetail>> detail(@PathVariable String id) {
        MenuEntity menu = menuService.findById(id);
        MenuDetail menuDetail = new MenuDetail();
        BeanUtils.copyProperties(menu,menuDetail);
        if(null == menu)
            throw ProblemSolver.client(ProblemCode.RESOURCE_NOT_EXIST).build();
        return ResponseEntity.ok(new Resource<>(menuDetail,
                getLinkList(menuDetail)
        ));
    }

    private Optional<URI> getUri(Link link){
        URI uri = null;
        try {
            uri = new URI(link.getHref());
        } catch (URISyntaxException e) {
            log.debug("创建连接失败", e);
        }

        return Optional.ofNullable(uri);
    }

    private Link getCollectionLink(){
        MenuQueryParam menuQueryParam = new MenuQueryParam();
        return linkTo(methodOn(MenuController.class).list(menuQueryParam)).withRel("collection");
    }

    private List<Link> getLinkList(Menu menu){
        List<Link> links = new ArrayList<>();
        MenuUpdateParam menuUpdateParam = new MenuUpdateParam();
        BeanUtils.copyProperties(menu,menuUpdateParam);
        links.add(linkTo(methodOn(MenuController.class).detail(menu.getId())).withSelfRel());
        links.add(linkTo(methodOn(MenuController.class).patch(menuUpdateParam,menu.getId())).withRel("patch"));
        links.add(linkTo(methodOn(MenuController.class).detail(menu.getId())).withRel("delete"));
        links.add(getCollectionLink());
        return links;
    }

}

