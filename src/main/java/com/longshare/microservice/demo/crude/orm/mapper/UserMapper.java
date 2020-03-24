package com.longshare.microservice.demo.crude.orm.mapper;

import com.longshare.microservice.demo.crude.orm.entity.UserEntity;
import com.longshare.microservice.demo.crude.orm.param.UserQueryBean;
import com.longshare.rest.core.dao.Mapper;
import java.util.List;

/**
 * Created by Generator on 2020-03-24 09:49:26.
 */
public interface UserMapper extends Mapper<UserEntity> {
    List<UserEntity> queryByQueryBean(UserQueryBean queryBean);
}
