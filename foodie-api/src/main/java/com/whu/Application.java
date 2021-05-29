package com.whu;

/*
* 用于启动项目
* */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//扫描mybatis通用的mapper所在的包
@MapperScan(basePackages = {"com.whu.mapper"})
//扫描所有包，以及相关组件包
@ComponentScan(basePackages={"com.whu","org.n3r.idworker"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
