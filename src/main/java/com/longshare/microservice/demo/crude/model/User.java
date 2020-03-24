package com.longshare.microservice.demo.crude.model;

import com.longshare.rest.core.hateoas.StringIdentifiable;
import com.longshare.rest.core.util.ConversionUtil;
import com.longshare.microservice.demo.crude.orm.entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
* Created by Generator on 2020-03-24 09:49:26.
*/

@Data
@ToString
public class User implements StringIdentifiable {
    @ApiModelProperty("用户id（对应邮箱）")
    private String id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("创建者")
    private String creator;
    @ApiModelProperty("创建时间")
    private String createdAt;
    @ApiModelProperty("修改者")
    private String updater;
    @ApiModelProperty("修改时间")
    private String updatedAt;

    public User(){
    }

    public User(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.creator = user.getCreator();
        this.createdAt = ConversionUtil.toString(user.getCreatedAt());
        this.updater = user.getUpdater();
        this.updatedAt = ConversionUtil.toString(user.getUpdatedAt());
    }

    public UserEntity toEntity(){
        UserEntity user = new UserEntity();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setCreator(this.creator);
        user.setCreatedAt(ConversionUtil.toLong(this.createdAt));
        user.setUpdater(this.updater);
        user.setUpdatedAt(ConversionUtil.toLong(this.updatedAt));
        return user;
    }
}
