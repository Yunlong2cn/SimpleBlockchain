package com.dashilong.blockchain.web.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.dashilong.blockchain.core.Block;
import com.dashilong.blockchain.web.config.BlockChainConfig;
import com.dashilong.blockchain.web.helper.BlockChainUtil;
import com.dashilong.blockchain.web.helper.NodeUtil;
import com.dashilong.blockchain.web.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {
    @Autowired
    BlockChainConfig blockChainConfig;

    @Override
    public void execute() {
        try {
            // 获取节点
            String res = HttpUtil.get("http://" + blockChainConfig.getNodeServer() + "/node");
            JSONArray jsonArray = JSONUtil.parseArray(res);

            // 选出某个节点，用于同步区块
            int blockLen = 0;
            String node = "";

            // 找出拥有最长链的节点
            for (int i = 0; i < jsonArray.size(); i++) {
                String tmpNode = jsonArray.getStr(i);
                try {
                    String data = HttpUtil.get( "http://"+ tmpNode + "/read");
                    JSONArray jArray = JSONUtil.parseArray(data);
                    if (jArray.size() > blockLen) {
                        blockLen = jArray.size();
                        node = tmpNode;
                    }
                    NodeUtil.add(tmpNode);
                } catch (Exception e) {
                    StaticLog.info("节点故障，无法正常访问，请修复");
                    NodeUtil.remove(tmpNode);
                }

            }

            if (!node.isEmpty()) {
                String data = HttpUtil.get( "http://"+ node + "/read");
                JSONArray jArray = JSONUtil.parseArray(data);
                if(jArray.size() > BlockChainUtil.get().getBlocks().size()) {
                    StaticLog.info("同步区块数据");
                    for (int i = BlockChainUtil.get().getBlocks().size(); i < jArray.size(); i++) {
                        Block block = JSONUtil.toBean(JSONUtil.parseObj(jArray.get(i)), Block.class);
                        BlockChainUtil.get().addBlock(block);
                    }
                    StaticLog.info("同步完成");
                } else {
                    StaticLog.info("当前节点区块数据已是最新");
                }
            }
        } catch (Exception e) {
            StaticLog.info("节点注册服务器故障，请检查");
        }
    }
}
