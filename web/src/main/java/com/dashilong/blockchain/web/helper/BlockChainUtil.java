package com.dashilong.blockchain.web.helper;

import com.dashilong.blockchain.core.BlockChain;

public class BlockChainUtil {
    private static BlockChain blockChain;

    public static BlockChain get() {
        if(blockChain == null) {
            blockChain = new BlockChain();
        }
        return blockChain;
    }
}
