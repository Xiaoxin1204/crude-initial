package com.longshare.microservice.demo.crude.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Menu {
    @ApiModelProperty("菜单ID")
    private String id;
    @ApiModelProperty("菜单父ID")
    private String parentId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("代码")
    private String code;
    @ApiModelProperty("URL")
    private String url;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("顺序")
    private Integer no;
    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("子菜单")
    private List<Menu> children;

    private Boolean checked;
}