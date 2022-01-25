package com.lime.mediscreenrecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class MediscreenRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediscreenRecordApplication.class, args);
    }

}
