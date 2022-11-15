package cn.itcast.n3;

import ch.qos.logback.core.util.FileUtil;
import cn.itcast.Constants;
import cn.itcast.n2.util.FileReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.TestStart")
public class TestStart {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @SneakyThrows
            @Override
            public void run() {
                log.debug(Thread.currentThread().getName());
//                FileReader.read(Constants.MP4_FULL_PATH);
                for (int i = 0; i < 10; i++) {
                    log.debug("count {}" , i);
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        };

        t1.start();
        log.debug("do other things ...");
    }
}
