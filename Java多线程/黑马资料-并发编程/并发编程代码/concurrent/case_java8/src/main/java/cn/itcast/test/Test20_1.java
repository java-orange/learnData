package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title: Test20_1</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/12/23</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */
@Slf4j(topic = "c.Test20_1")
public class Test20_1 {
    public static void main(String[] args) {

        final GuardeObject guardeObject = new GuardeObject();

        new Thread(() -> {
            Object o = guardeObject.get(1000);
            log.debug("info {}", o);
        },"t1").start();

        new Thread(() -> {
            log.debug("complete");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guardeObject.complete("123");
        },"t2").start();


    }
}

class GuardeObject{
    private Object response;

    public Object get(long timeOut) {
        synchronized (this) {
            long start = System.currentTimeMillis();
            long passTime = 0;
            while (response == null) {
                // 防止被虚假唤醒。
                long waitTime = timeOut - passTime;
                if (waitTime <= 0) {
                    break;
                }
                try {
                    wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passTime = System.currentTimeMillis() - start;
            }
            return response;
        }
    }

    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            notifyAll();
        }
    }


}