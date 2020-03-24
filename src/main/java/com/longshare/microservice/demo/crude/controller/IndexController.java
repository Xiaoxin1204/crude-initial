package com.longshare.microservice.demo.crude.controller;

import com.longshare.microservice.demo.crude.ProblemCode;
import com.longshare.rest.core.problem.ProblemSolver;
import com.longshare.rest.core.util.DateUtil;
import com.longshare.microservice.demo.crude.controller.UserController;
import com.longshare.microservice.demo.crude.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.HttpStatusAdapter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Generator on 2020-03-24 09:49:26.
 */
@Slf4j
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping(value = "/index", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResourceSupport index() {
        ResourceSupport index = new ResourceSupport();

        index.add(linkTo(UserController.class).withRel("users"));
        index.add(linkTo(UserController.class).withRel("users"));
        index.add(linkTo(IndexController.class).withSelfRel());

        return index;
    }

    @GetMapping(value = "/problem", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResourceSupport problem(HttpServletRequest request) {
        String requestUri = (String)request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Optional<URI> uri = Optional.empty();
        Integer status = null;
        try{
            if(request.getParameter("status") != null){
                status = Integer.valueOf(request.getParameter("status"));
            }
            if(status == null) {
                status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
            }
            if(requestUri == null)
                requestUri = request.getRequestURL().toString();
            uri = Optional.of(URI.create(requestUri));
        }catch (Exception e){
            log.info("获取具体错误信息失败{}", e.getMessage());
        }
        String detail = (String)request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String title = request.getParameter("title");
        HttpStatus httpStatus;
        if(status == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            title = title == null ? "internal_server_error" : title;
        } else {
            try {
                httpStatus = HttpStatus.valueOf(status.intValue());
                title = title == null ? httpStatus.getReasonPhrase().toLowerCase().replace(" ","_") : title;
            } catch (Exception var5) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                title = title == null ? "internal_server_error_with" + status : title;
            }
        }

        if(title != null)
            throw Problem.builder().withTitle(title).withDetail(detail).withInstance(uri.get())
                    .withStatus(new HttpStatusAdapter(httpStatus))
                    .withType(URI.create(format(ProblemCode.UNDEFINED.getTypeFormat(),title)))
                    .with("timestamp", DateUtil.getCurrentTime())
                    .build();
        else
            throw ProblemSolver.client(ProblemCode.UNDEFINED).withDetail(detail).withInstance(uri.get()).withStatus(new HttpStatusAdapter(httpStatus)).build();
    }

}
