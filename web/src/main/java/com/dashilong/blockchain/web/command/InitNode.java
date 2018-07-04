package com.dashilong.blockchain.web.command;

import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.log.StaticLog;
import com.dashilong.blockchain.web.config.BlockChainConfig;
import com.dashilong.blockchain.web.config.ServerConfig;
import com.dashilong.blockchain.web.helper.NodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 自动注册当前节点到节点服务器，用于被其它节点发现
 *
 */
@Component
@Order(1)
public class InitNode implements CommandLineRunner {
    @Autowired
    BlockChainConfig blockChainConfig;
    @Autowired
    ServerConfig serverConfig;
    @Override
    public void run(String... args) throws Exception {
//        String node = NetUtil.getLocalhostStr() + ":" + serverConfig.getPort();
        String node = blockChainConfig.getNode();
        try {
            String res = HttpUtil.get("http://"+ blockChainConfig.getNodeServer() +"/register?url=" + URLUtil.encode(node));
            StaticLog.info("节点注册成功");
        } catch (Exception e) {
            StaticLog.info("请确保某个节点做为信令服务器，用于节点发现");
        }

    }
}
