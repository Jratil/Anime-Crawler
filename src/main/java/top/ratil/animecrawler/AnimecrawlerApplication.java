package top.ratil.animecrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class AnimecrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimecrawlerApplication.class, args);
    }

}

