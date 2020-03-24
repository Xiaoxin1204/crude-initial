package com.longshare.microservice.demo.crude.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuUpdateParam {
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
     * 类型
     */
    @ApiModelProperty("类型")
    private String type;
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
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

    /**
     * 顺序
     */
    @ApiModelProperty("顺序")
    private Integer no;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
}