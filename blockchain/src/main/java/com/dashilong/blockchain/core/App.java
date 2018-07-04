package com.dashilong.blockchain.core;

import cn.hutool.core.date.DateUtil;

public class App {
    public static void main(String[] args) {
        BlockChain blockChain = new BlockChain();
        blockChain.genesisBlock();

        Block lastBlock = blockChain.getBlocks().get(blockChain.getBlocks().size() - 1);
        blockChain.addBlock(blockChain.createBlock( lastBlock.getIndex() + 1, lastBlock.getHash(), DateUtil.now(), "I am first data"));
    }
}
