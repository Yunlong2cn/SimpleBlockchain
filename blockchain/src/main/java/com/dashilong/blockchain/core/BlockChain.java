package com.dashilong.blockchain.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.log.StaticLog;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {

    @Getter
    private List<Block> blocks = new ArrayList<>();


    public void addBlock(Block block) {
        if (valid(block)) {
            blocks.add(block);
            StaticLog.info("\n>> 区块上链成功\n区块高度：{}\n时间：{}\n内容：{}", block.getIndex(), block.getTimeStamp(), block.getData());
        } else {
            StaticLog.info("区块验证失败，无法添加上链");
        }
    }

    public Block createBlock(int index, String nodeName, String from, String hash, String time, String data) {
        Block block = new Block();
        block.setIndex(index);
        block.setNodeName(nodeName);
        block.setFrom(from);
        block.setPreHash(hash);
        block.setTimeStamp(time);
        block.setData(data);
        block.setHash(hash(block));
        return block;
    }

    public String hash(Block block) {
        return DigestUtil.sha256(block.getIndex() + block.getNodeName() + block.getFrom() + block.getPreHash() + block.getTimeStamp() + block.getData()).toString();
    }

    public boolean valid(Block block) {
        if (getLastBlock() != null && getLastBlock().getIndex() >= block.getIndex()) {
            return false;
        }
        return true;
    }

    public void genesisBlock(String nodeName) {
        Block block = createBlock(0, nodeName, "", "", DateUtil.now(), "Welcome to blockchain");
        addBlock(block);
    }

    public Block getLastBlock() {
        return blocks.size() == 0 ? null : blocks.get(blocks.size() - 1);
    }
}
