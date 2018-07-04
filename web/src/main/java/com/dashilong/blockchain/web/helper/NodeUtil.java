package com.dashilong.blockchain.web.helper;

import java.util.ArrayList;
import java.util.List;

public class NodeUtil {
    private static List<String> nodes;
    private static NodeUtil nodeUtil;

    public static List<String> get() {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        return nodes;
    }

    public static boolean add(String url) {
        if (!get().contains(url)) {
            nodes.add(url);
            return true;
        }
        return false;
    }
}
