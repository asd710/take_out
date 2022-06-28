package com.hm.takeout.boot;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@MapperScan("com.hm.takeout.boot.mapper")
//开启servlet的注解
@ServletComponentScan
//开启事务注解的支持
@EnableTransactionManagement
public class HmApplication {
    public static void main(String[] args) {
        SpringApplication.run(HmApplication.class,args);
        log.info("项目启动成功！");
    }
}
