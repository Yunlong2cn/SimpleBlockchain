package com.dashilong.blockchain.web.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.json.JSONUtil;
import com.dashilong.blockchain.core.Block;
import com.dashilong.blockchain.core.BlockChain;
import com.dashilong.blockchain.web.config.BlockChainConfig;
import com.dashilong.blockchain.web.helper.BlockChainUtil;
import com.dashilong.blockchain.web.helper.NodeUtil;
import com.dashilong.blockchain.web.helper.RequestUtil;
import com.dashilong.blockchain.web.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {
    private BlockChain blockChain = BlockChainUtil.get();

    @Autowired
    AsyncService asyncService;

    @Autowired
    BlockChainConfig blockChainConfig;

    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping("/")
    public String index(String data, ModelMap map) {

        if (data != null) {
            // 上链前，先同步一下其它节点
            asyncService.execute();

            Block lastBlock = blockChain.getLastBlock();
            Block block = blockChain.createBlock(lastBlock.getIndex() + 1, blockChainConfig.getNode(), RequestUtil.getIpAddr(httpServletRequest), lastBlock.getHash(), DateUtil.now(), data);

            blockChain.addBlock(block);
        }



        map.put("blocks", blockChain.getBlocks());
        map.put("nodeCount", NodeUtil.get().size());

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
