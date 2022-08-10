package org.example.config;

import org.example.service.LoadService;
import org.example.service.LoadServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
//
      @Bean
//    @Scope("prototype")
    public RestTemplate getTemplate(){
        return new RestTemplate();
    }



//    @Bean
//    @Scope("prototype")
//    public LoadService getLoadService(){
//        return new LoadServiceImpl();
//    }


}
