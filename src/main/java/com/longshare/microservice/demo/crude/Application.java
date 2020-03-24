package com.longshare.microservice.demo.crude;

import com.longshare.rest.core.EnableRestfulLevel3;
//import com.longshare.rest.core.problem.ProblemHandler;
//import com.longshare.rest.core.problem.ProblemConfiguration;
//import com.longshare.rest.core.security.SecurityConfiguration;
//import com.longshare.rest.core.swagger.Swagger2Configuration;

import com.longshare.rest.core.validator.ValidatorConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScan;

/**
* Created by Generator on 2020-03-24 09:49:26.
*/
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
//@EnableCaching
//@EnableMultipleDatabase
//@EnableDynamicDataSource
@EnableRestfulLevel3
@Import({
		ValidatorConfiguration.class //需要手工单独引入，不能spring factories，否则会初始化spring自带的Validator
})
@MapperScan("com.**.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
