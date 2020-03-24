package com.longshare.microservice.demo.crude.orm.entity;

import com.longshare.rest.core.hateoas.StringIdentifiable;
import javax.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
* Created by Generator on 2020-03-24 09:49:26.
*/

@Data
@ToString
@Table(name = "user")
public class UserEntity implements StringIdentifiable {

    /**
     * 用户id（对应邮箱） 
     */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 用户名 
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码 
     */
    @Column(name = "password")
    private String password;

    /**
     * 姓名 
     */
    @Column(name = "name")
    private String name;

    /**
     * 创建者 
     */
    @Column(name = "creator")
    private String creator;

    /**
     * 创建时间 
     */
    @Column(name = "created_at")
    private Long createdAt;

    /**
     * 修改者 
     */
    @Column(name = "updater")
    private String updater;

    /**
     * 修改时间 
     */
    @Column(name = "updated_at")
    private Long updatedAt;

}
