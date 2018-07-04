package com.dashilong.blockchain.web.helper;

import java.util.ArrayList;
import java.util.List;

public class NodeUtil {
    private static List<String> nodes;

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

    public static boolean remove(String url) {
        if (!get().contains(url)) {
            nodes.remove(url);
            return true;
        }
        return false;
    }
}
