package com.longshare.microservice.demo.crude.service;

import com.longshare.microservice.demo.crude.model.param.UserParam;
import com.longshare.microservice.demo.crude.orm.entity.UserEntity;
import com.longshare.microservice.demo.crude.model.User;
import com.longshare.rest.core.service.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Generator on 2020-03-24 09:49:26.
 */
public interface UserService extends Service<UserEntity> {
    /**
     * 创建对象，会自动生成主键并返回对象，如果创建失败，则返回Null
     * @param user
     * @return
     */
    Optional<User> create(User user);

    /**
     * 可选修改对象，只会修改给定的字段和值
     * @param user
     * @return
     */
    Optional<User> patch(User user);

    /**
    * 根据ID获取对象
    * @param id
    * @return
    */
    Optional<User> get(String id);

    /**
    * 分页查询
    * @param page 第几页
    * @param size 每页条数
    * @return
    */
    List<User> findAll(int page, int size);

    /**
    * 高级查询
    * @param userParam 查询条件
    * @return
    */
    List<User> findAll(UserParam userParam);
}
