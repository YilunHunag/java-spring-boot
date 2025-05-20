package com.example.wizaccountsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wizaccountsystem.security.JwtFilter;

@SpringBootApplication
public class WizaccountsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WizaccountsystemApplication.class, args);
	}

	@RestController
	public class HelloController {

		@GetMapping("/")
		public String hello() {
			return "Spring Boot 啟動成功！Hello from wizaccountsystem 👋";
		}
	}
	// 加入jwtfilter
	 @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtFilter filter) {
        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(filter);
        bean.addUrlPatterns("/*");
        return bean;
    }
	
}
