package cc.dt.mch.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cc.dt.mch.demo")
@SpringBootApplication
public class OpenClassApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenClassApplication.class, args);
    }
}