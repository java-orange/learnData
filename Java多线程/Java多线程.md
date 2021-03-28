# Java多线程

## 基本概念: 程序, 进程, 线程

​	**程序(program)**: 是为了完成特定的任务, 用某种语言编写的一组指令的集合

​	**进程(process)**: 正在运行的程序

​	**线程(thread)**: 是程序内部的一条执行路径, 多个线程共享进程的堆和方法区, 但有各自的栈和程序计数器



## 创建多线程的方式

#### 方式一: 继承于Thread类

1. 创建一个继承于Thread类的子类
2. 重写Thread类的run()
3. 创建Thread类的子类的对象
4. 通过此对象调用start()

```java
class MyThread extends Thread{
    @Override
    public void run(){
        // 遍历100以内的偶数
        for(int i =0; i < 100; i++) {
            if (i % 2 == 0){
                System.out.println(i);
            }
        }
    }
}


public class ThreadTest{
    public static void main(String[] args){
        Mythrad t1 = new Mythread();
        t1.start();
    }
}
```



**小tip: Thread.currentThread().getName();   获取当前线程名称**



​	**创建Thread匿名子类的方式**

```java
public class ThreadDemo{
    public static void main(String[] args) {
        new Thread(){
        	@Override
            public void run(){
                System.out.println("你好");
            }
        }.start()
    }
}
```



##### Thread中常用的方法: 

1. start():启动当前线程；调用当前线程的run()，只有Thread类和他的子类才能调用start()方法
2. run(): 通常需要重写Thread类中的此方法，将创建的线程要执行的操作声明在此方法中
3. currentThread():静态方法，返回执行当前代码的线程
4. getName():获取当前线程的名字
5. setName():设置当前线程的名字
6. yield():释放当前cpu的执行权
7. join():在线程a中调用线程b的join(),此时线程a就进入阻塞状态，直到线程b完全执行完以后，线程a才结束阻塞状态。
8. stop():已过时。当执行此方法时，强制结束当前线程。
9. sleep(long millitime):让当前线程“睡眠”指定的millitime毫秒。在指定的millitime毫秒时间内，当前线程是阻塞状态。
10. isAlive():判断当前线程是否存活



##### 线程的优先级

 线程的优先级：

- MAX_PRIORITY：10
- MIN _PRIORITY：1
- NORM_PRIORITY：5 -->默认优先级

获取和设置当前线程的优先级：

- getPriority():获取线程的优先级
- setPriority(int p):设置线程的优先级

> 说明：高优先级的线程要抢占低优先级线程CPU的执行权。但是只是从概率上讲，高优先级的线程高概率的情况下被执行。并不意味着只当高优先级的线程执行完以后，低优先级的线程才执行。



#### 方式二: 实现Runnable接口

1. 创建一个实现了Runnable接口的类

2. 实现类去实现Runnable中的抽象方法：run()

3. 创建实现类的对象

4. 将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象

5. 通过Thread类的对象调用start()

   ```java
   //1. 创建一个实现了Runnable接口的类
   public class RunnableTest implements Runnable {
       // 2. 实现类去实现Runnable中的抽象方法：run()
       @Override
       public void run() {
           for (int i = 0; i < 100; i++) {
               System.out.println(i);
           }
       }
   }
   
   class test {
       public static void main(String[] args) {
           //3. 创建实现类的对象
           RunnableTest runnableTest = new RunnableTest();
           //4. 将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
           Thread thread = new Thread(runnableTest);
           //5. 通过Thread类的对象调用start()
           thread.start();
   
       }
   }
   ```

   

**两种方式的对比：**

开发中优先选择：实现Runnable接口的方式

**原因：**

\1. 实现的方式没类的单继承性的局限性

 2. 实现的方式更适合来处理多个线程共享数据的情况。

**联系：**public class Thread implements Runnable

**相同点：**两种方式都需要重写run(),将线程要执行的逻辑声明在run()中。 目前两种方式，要想启动线程，都是调用的Thread类中的start()。



### 线程的声明周期

- 新建
- 就绪
- 运行
- 阻塞
- 凋亡

 ![image-20210328190131842](C:\Users\j1781\Desktop\learnCode\Java多线程\img\life.png)





关于线程安全, 使用synchronized() , 同步代码块

注意其中传入的对象, 一定要唯一, 

慎传this. 认真观察是否为唯一对象

传入对象.class最棒,  class模子 唯一

