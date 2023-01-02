package cn.itcast.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>Title: TestByteBuffer</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2023/1/2</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */
@Slf4j
public class TestByteBuffer {

    public static void main(String[] args) {

        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(10);


            while (true) {
                final int read = channel.read(buffer);
                log.debug("读取到的字节数{}", read);
                if (read == -1) {
                    break;
                }
                buffer.flip();
                while (buffer.hasRemaining()) {
                    final byte b = buffer.get();
                    log.debug("字符为: {}",(char) b);
                }
                buffer.clear();
            }


        } catch (IOException e) {
        }

    }

}