package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

@MapperScan("org.example.mapper")
@SpringBootApplication

public class DemoApplication {

	public static void main(String[] args){

		SpringApplication.run(DemoApplication.class, args);
//		InetAddress localHost = InetAddress.getLocalHost();
//		System.out.println(localHost);


	}
	//sout changes
}
