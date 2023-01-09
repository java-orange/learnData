package cn.itcast.server;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ClassName: MainTest
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author xhjing
 * @Create 2023/1/9 11:47
 * @Version 1.0
 */
public class MainTest {
    public static void main(String[] args) {
        Map<String, Integer> sizeMaps = new HashMap();
        Optional<Map.Entry<String, Integer>> minEntry = sizeMaps.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue));
        String nodeId = null;
        if (minEntry.isPresent()) {
            nodeId = (String)((Map.Entry)minEntry.get()).getKey();
        }
        System.out.println("nodeId = " + nodeId);

    }
}
