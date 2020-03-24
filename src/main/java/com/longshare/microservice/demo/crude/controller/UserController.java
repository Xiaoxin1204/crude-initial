package com.longshare.microservice.demo.crude.controller;

import com.longshare.microservice.demo.crude.model.User;
import com.longshare.microservice.demo.crude.ProblemCode;

import com.longshare.microservice.demo.crude.model.param.UserParam;
import com.longshare.microservice.demo.crude.service.UserService;
import com.longshare.rest.core.problem.ProblemSolver;
import com.longshare.rest.core.hateoas.ResourceFactory;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.PagedResources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Created by Generator on 2020-03-24 09:49:26.
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(notes = "新增User", value = "新增User", httpMethod = "POST")
    ResponseEntity<?> create(@Valid @RequestBody User user) {
        Optional<User> savedUser = userService.create(user);

        if(!savedUser.isPresent())
            throw ProblemSolver.client(ProblemCode.RESOURCE_CREATED_FAILED).withStatus(Status.UNPROCESSABLE_ENTITY).build();

        Resource<User> userResource = new Resource<>(savedUser.get(),
                linkTo(methodOn(UserController.class).detail(savedUser.get().getId())).withSelfRel());
        return ResponseEntity
            .created(ResourceFactory.getUri(userResource.getLink(Link.REL_SELF)).get())
            .body(userResource);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(notes = "根据id删除User", value = "根据id删除User", httpMethod = "DELETE")
    public ResponseEntity delete(@PathVariable String id) {
        userService.deleteById(id);

        Optional<URI> collectionUri = ResourceFactory.getUri(ResourceFactory.getCollectionLink(this.getClass()));

        return collectionUri.map(uri -> ResponseEntity.noContent().location(uri).build())
            .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(notes = "根据id更新User", value = "根据id更新User", httpMethod = "PATCH")
    public ResponseEntity<?> patch(@RequestBody User user,@PathVariable String id) {
        if(StringUtils.isEmpty(user.getId()))
            user.setId(id);
        Optional<User> updatedUser = userService.patch(user);
        Link newlyCreatedLink = linkTo(methodOn(UserController.class).detail(id)).withSelfRel();
        Optional<URI> newlyUri = ResourceFactory.getUri(newlyCreatedLink);
        return updatedUser.isPresent() ?
            (newlyUri.map(uri -> ResponseEntity.ok().location(uri)).orElseGet(ResponseEntity::ok)).body(new Resource<>(user, newlyCreatedLink))
            : ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(notes = "根据id查询User", value = "根据id查询User", httpMethod = "GET")
    public ResponseEntity<Resource<User>> detail(@PathVariable String id) {
        Optional<User> user = userService.get(id);
        if(!user.isPresent())
            throw ProblemSolver.client(ProblemCode.RESOURCE_NOT_EXIST).build();
        return ResponseEntity.ok(new Resource<>(user.get(),
            ResourceFactory.getResourceLinkList(this.getClass(),id)
        ));
    }

    @GetMapping
    @ApiOperation(notes = "User列表", value = "User列表", httpMethod = "GET")
    public ResponseEntity<PagedResources<User>> list(UserParam userParam) {
        List users = userService.findAll(userParam);
        return ResponseEntity.ok(ResourceFactory.getPagedResources(this.getClass(), users));
    }

}
