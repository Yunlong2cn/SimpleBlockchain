package com.dashilong.blockchain.web.command;

import cn.hutool.log.StaticLog;
import com.dashilong.blockchain.web.helper.BlockChainUtil;
import com.dashilong.blockchain.web.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class InitBlockChain implements CommandLineRunner {
    @Autowired
    AsyncService asyncService;

    @Override
    public void run(String... args) throws Exception {
        // 先同步一下其它节点
        asyncService.execute();

        if (BlockChainUtil.get().getBlocks().size() == 0) {
            StaticLog.info("初始化区块链");
            BlockChainUtil.get().genesisBlock();
        } else {
            StaticLog.info("已同步自其它节点的区块，无需创建创世块");
        }
    }
}
