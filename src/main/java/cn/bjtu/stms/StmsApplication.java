package cn.bjtu.stms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@tk.mybatis.spring.annotation.MapperScan(basePackages = "cn.bjtu.stms.mapper")
@MapperScan("cn.bjtu.stms.mapper")
@SpringBootApplication
public class StmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StmsApplication.class, args);
    }

}
