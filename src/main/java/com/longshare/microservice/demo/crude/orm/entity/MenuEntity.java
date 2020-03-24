package com.longshare.microservice.demo.crude.orm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Table(name = "`menu`")
public class MenuEntity {
    /**
     * 菜单ID
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private String id;

    /**
     * 菜单ID
     */
    @Column(name = "`parent_id`")
    private String parentId;

    /**
     * 名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 类型
     */
    @Column(name = "`type`")
    private String type;

    /**
     * 代码
     */
    @Column(name = "`code`")
    private String code;

    /**
     * URL
     */
    @Column(name = "`url`")
    private String url;

    /**
     * 图标
     */
    @Column(name = "`icon`")
    private String icon;

    /**
     * 顺序
     */
    @Column(name = "`no`")
    private Integer no;

    /**
     * 描述
     */
    @Column(name = "`description`")
    private String description;

    /**
     * 是否删除：0-否，1-是
     */
    @Column(name = "`is_delete`")
    private String isDelete;

    /**
     * 创建者
     */
    @Column(name = "`creator`")
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "`created_at`")
    private Long createdAt;

    /**
     * 修改者
     */
    @Column(name = "`updater`")
    private String updater;

    /**
     * 修改时间
     */
    @Column(name = "`updated_at`")
    private Long updatedAt;

    private List<MenuEntity> children;
}