package com.dashilong.blockchain.core;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Block {
    int index;

    String preHash;
    String hash;

    String timeStamp;
    String data;
}
