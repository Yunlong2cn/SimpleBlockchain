package com.dashilong.blockchain.web.schedule;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.dashilong.blockchain.core.Block;
import com.dashilong.blockchain.web.config.BlockChainConfig;
import com.dashilong.blockchain.web.helper.BlockChainUtil;
import com.dashilong.blockchain.web.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AsyncBlockChain {

    @Autowired
    AsyncService asyncService;

    @Scheduled(fixedRate = 5000)
    public void execute() {
        asyncService.execute();
    }
}
