package com.longshare.microservice.demo.crude.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MenuTree {
    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private String id;

    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单父ID")
    private String parentId;

    /**
     * 代码
     */
    @ApiModelProperty("代码")
    private String code;

    /**
     * URL
     */
    @ApiModelProperty("URL")
    private String url;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("子菜单")
    private List<MenuTree> children;
}