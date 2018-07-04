package com.dashilong.blockchain.web.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.dashilong.blockchain.core.Block;
import com.dashilong.blockchain.core.BlockChain;
import com.dashilong.blockchain.web.helper.BlockChainUtil;
import com.dashilong.blockchain.web.helper.NodeUtil;
import com.dashilong.blockchain.web.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {
    private BlockChain blockChain = BlockChainUtil.get();

    @Autowired
    AsyncService asyncService;

    @GetMapping("/")
    public String index(String data, ModelMap map) {

        if (data != null) {
            // 上链前，先同步一下其它节点
            asyncService.execute();

            Block lastBlock = blockChain.getLastBlock();
            Block block = blockChain.createBlock(lastBlock.getIndex() + 1, lastBlock.getHash(), DateUtil.now(), data);

            blockChain.addBlock(block);
        }



        map.put("blocks", blockChain.getBlocks());

        return "home/index";
    }

    @ResponseBody
    @GetMapping("/read")
    public String read() {
        return JSONUtil.toJsonStr(blockChain.getBlocks());
    }

    @ResponseBody
    @GetMapping("/node")
    public String node() {
        return JSONUtil.toJsonStr(NodeUtil.get());
    }

    @ResponseBody
    @GetMapping("/register")
    public String register(String url) {
        if (NodeUtil.add(url)) {
            return "success";
        }
        return "fail";
    }


}
