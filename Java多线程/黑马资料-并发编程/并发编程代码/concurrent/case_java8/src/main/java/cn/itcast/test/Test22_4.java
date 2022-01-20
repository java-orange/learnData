package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Title: Test22_4</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2022/1/13</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */
public class Test22_4 {
    public static void main(String[] args) {
        ChopStick2 c1 = new ChopStick2("1");
        ChopStick2 c2 = new ChopStick2("2");
        ChopStick2 c3 = new ChopStick2("3");
        ChopStick2 c4 = new ChopStick2("4");
        ChopStick2 c5 = new ChopStick2("5");

        new Philosopher2("苏格拉底", c1, c2).start();
        new Philosopher2("柏拉图", c2, c3).start();
        new Philosopher2("亚里士多德", c3, c4).start();
        new Philosopher2("赫拉克利特", c4, c5).start();
        new Philosopher2("阿基米德", c5, c1).start();
    }


}

@Slf4j(topic = "c.Philosopher2")
class Philosopher2 extends Thread{


    final ChopStick2 left;
    final ChopStick2 right;

    public Philosopher2(String name, ChopStick2 left, ChopStick2 right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    public void eat() {
        log.info("吃饭");
    }

    @Override
    public void run() {
        while (true) {
            if (left.tryLock()) {
                try {
                    if (right.tryLock()) {
                        try {
                            eat();
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }
}


class ChopStick2 extends ReentrantLock {
    final String name;


    ChopStick2(String name) {
        this.name = name;
    }

}