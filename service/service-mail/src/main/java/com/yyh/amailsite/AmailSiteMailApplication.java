package com.yyh.amailsite;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class AmailSiteMailApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(AmailSiteMailApplication.class, args);
        Environment env = application.getEnvironment();
        String port = env.getProperty("server.port");

        log.info("\n----------------------------------------------------------\n\t" +
                "Application is running! Access URLs:\n\t" +
                "Doc文档: \thttp://" + "localhost" + ":" + port + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
