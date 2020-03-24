package com.longshare.microservice.demo.crude.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class MenuAddParam {

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
    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty("名称")
    private String name;
    /**
     * 代码
     */
    @ApiModelProperty("代码")
    @NotBlank(message = "菜单code不能为空")
    private String code;

    /**
     * URL
     */
    @ApiModelProperty("URL")
    private String url;

    /**
     * 图标
     */ @ApiModelProperty("图标")
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