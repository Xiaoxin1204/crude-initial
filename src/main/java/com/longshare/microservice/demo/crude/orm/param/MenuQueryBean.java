package com.longshare.microservice.demo.crude.orm.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "`menu`")
public class MenuQueryBean {
    /**
     * 菜单ID
     */
    private String id;

    /**
     * 菜单ID
     */
    private String parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;

    /**
     * 代码
     */
    private String code;

    /**
     * URL
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 顺序
     */
    private Integer no;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String updater;

    private String roleId;

    private String orderByStr;

}