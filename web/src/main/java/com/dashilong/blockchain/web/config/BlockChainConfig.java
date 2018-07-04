package com.dashilong.blockchain.web.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;

@Configuration
@Data
public class BlockChainConfig {
    @Value("${blockchain.node-server}")
    private String nodeServer;

    @Value("${blockchain.node}")
    private String node;
}
