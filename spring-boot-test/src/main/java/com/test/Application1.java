package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**  
 * @Description: spring boot v1.2  里面出现的注解 
 *     等同
 * <br>@Configuration
 * <br>@ComponentScan
 * <br>@EnableAutoConfiguration
 * @author yanlei
 * @date 2017年1月12日 上午1:59:54
 * @version V1.0
 */ 
@SpringBootApplication
public class Application1 {
	public static void main(String[] args) {
		SpringApplication.run(Application1.class);
	}
}
