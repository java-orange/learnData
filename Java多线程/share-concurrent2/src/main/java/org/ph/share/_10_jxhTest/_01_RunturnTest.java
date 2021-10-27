package org.ph.share._10_jxhTest;
/**
 * @author xhjing
 * @create 2021-10-20 9:47
 */

import java.lang.reflect.Member;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>Title: _01_RunturnTest</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/10/20</p>
 *@author : xhjing
 *@version :1.0.0
 */
public class _01_RunturnTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String str = "asddsfb";
        _01_RunturnTest jxh = new _01_RunturnTest();

        CompletableFuture<String> stringCompletableFuture = jxh.method1(str);

        String s = stringCompletableFuture.get();
        System.out.println("s = " + s);


    }


    private CompletableFuture<String> method1(String msg) {
        long start = System.currentTimeMillis();
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            String s = msg.toUpperCase();
            try {
                Thread.sleep(3900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s;
        });

        CompletableFuture<Void> voidCompletableFuture = stringCompletableFuture.thenRun(() -> {
            long end = System.currentTimeMillis();
            System.out.println("System.currentTimeMillis() = " + (System.currentTimeMillis() - start));
        });

        return stringCompletableFuture;


    }

}