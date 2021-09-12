package com.example;

import com.example.configuration.AuditConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AuditConfig.class)
public class PaymentGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentGatewayApplication.class, args);
    }

}
