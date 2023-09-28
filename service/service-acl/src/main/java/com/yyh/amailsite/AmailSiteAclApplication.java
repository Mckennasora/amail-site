package com.yyh.amailsite;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@Slf4j
@SpringBootApplication
public class AmailSiteAclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AmailSiteAclApplication.class, args);

        log.info("\n----------------------------------------------------------\n\t" +
                "Application is running! Access URLs:\n\t" +
                "Doc文档: \thttp://" + "localhost" + ":" + 8080  + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
