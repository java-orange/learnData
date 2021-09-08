package org.ph.share.answer;

import org.ph.share.SmallTool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 如果没发生异常
 *  A 和 B 并发执行
 *  B 执行完后执行 C
 *
 *  如果发生异常，立刻返回异常
 *  代码中有3段注释，模拟 发生异常的情况
 */
public class ExceptionImmediatelyReturn {
    public static void main(String[] args) {

        try {
            String result = getABCResult();
            SmallTool.printTimeAndThread(result);
        } catch (Exception e) {
            SmallTool.printTimeAndThread("发生异常 " + e.getMessage());
            e.printStackTrace();
        }


    }

    private static String getABCResult() {
        CompletableFuture<String> a = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("start A");
            SmallTool.sleepMillis(1000);
//            if (true) {
//                throw new RuntimeException("a error");
//            }
            SmallTool.printTimeAndThread("end A");
            return "A";
        });

        CompletableFuture<String> bAndC = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("start B");
            SmallTool.sleepMillis(2000);
//            if (true) {
//                throw new RuntimeException("b error");
//            }
            SmallTool.printTimeAndThread("end B");
            return "B";
        }).thenCompose(resultB -> CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("start C");
            SmallTool.sleepMillis(1000);
//            if (true) {
//                throw new RuntimeException("c error");
//            }
            SmallTool.printTimeAndThread("end C");
            return resultB + " C";
        }));


        List<CompletableFuture> cfList = new LinkedList<>();
        cfList.add(a);
        cfList.add(bAndC);

        while (cfList.size() > 0) {
            CompletableFuture.anyOf(cfList.toArray(new CompletableFuture[cfList.size()])).join();
            cfList.removeIf(completableFuture -> completableFuture.isDone());
        }

        /**
         * 拼装结果
         */
        return "正常执行完毕 -->" + a.join() + " " + bAndC.join();
    }


}
