package com.longshare.microservice.demo.crude.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MenuQueryParam {
    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private String id;

    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
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
     * 顺序
     */
    @ApiModelProperty("顺序")
    private Integer no;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("排序")
    private String orderBy;

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("排序字段")
    private List<String> sortList;

    @ApiModelProperty("排序")
    private List<String> orderList;


}