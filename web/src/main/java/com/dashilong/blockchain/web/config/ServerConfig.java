package com.dashilong.blockchain.web.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ServerConfig {
    @Value("${server.port}")
    private String port;
}
