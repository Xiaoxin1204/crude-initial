package com.longshare.microservice.demo.crude.service.impl;

import com.github.pagehelper.PageHelper;
import com.longshare.microservice.demo.crude.model.param.UserParam;
import com.longshare.microservice.demo.crude.orm.entity.UserEntity;
import com.longshare.microservice.demo.crude.model.User;
import com.longshare.microservice.demo.crude.orm.param.UserQueryBean;
import com.longshare.rest.core.problem.DefaultProblemCode;
import com.longshare.microservice.demo.crude.orm.mapper.UserMapper;
import com.longshare.microservice.demo.crude.service.UserService;
import com.longshare.rest.core.problem.ProblemSolver;
import com.longshare.rest.core.service.AbstractServiceImpl;
import com.longshare.rest.core.util.DateUtil;
import com.longshare.rest.core.util.UUIDUtil;
import com.longshare.rest.core.util.ConversionUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

/**
 * Created by Generator on 2020-03-24 09:49:26.
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl<UserEntity> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    //@Transactional
    public Optional<User> create(User user) {
        UserEntity userEntity = user.toEntity();
        if(StringUtils.isEmpty(userEntity.getId()))
            userEntity.setId(UUIDUtil.generateShortUuid());
        long currentTime = DateUtil.getCurrentTime();
        userEntity.setCreatedAt(currentTime);
        userEntity.setUpdatedAt(currentTime);
        return this.save(userEntity)?Optional.of(new User(userEntity)):Optional.empty();
    }

    @Override
    //@Transactional
    public Optional<User> patch(User user) {
        UserEntity userEntity = user.toEntity();
        if(StringUtils.isEmpty(userEntity.getId()))
            throw ProblemSolver.client(DefaultProblemCode.ID_MISS).build();
        long currentTime = DateUtil.getCurrentTime();
        userEntity.setUpdatedAt(currentTime);
        return this.update(userEntity)?Optional.of(new User(userEntity)):Optional.empty();
    }

    @Override
    public Optional<User> get(String id) {
        UserEntity userEntity = mapper.selectByPrimaryKey(id);
        return null == userEntity?Optional.empty():Optional.of(new User(userEntity));
    }

    @Override
    public List<User> findAll(int page, int size) {
        PageHelper.startPage(page, size);
        List<UserEntity> userEntities = mapper.selectAll();
        return ConversionUtil.toVO(userEntities,
            userEntities.stream().map(userEntity -> new User(userEntity)).collect(Collectors.toList())
        );
        //return userEntities.stream().map(userEntity -> new User(userEntity)).collect(Collectors.toList());
    }

    @Override
    public List<User> findAll(UserParam userParam){
        PageHelper.startPage(userParam.getPage(), userParam.getRows());
        UserQueryBean userQueryBean = new UserQueryBean();
        List<UserEntity> userEntities = userMapper.queryByQueryBean(userQueryBean);
        return ConversionUtil.toVO(userEntities,
            userEntities.stream().map(userEntity -> new User(userEntity)).collect(Collectors.toList())
        );
    }
}
