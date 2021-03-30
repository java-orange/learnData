# Java多线程

## 基本概念: 程序, 进程, 线程

​	**程序(program)**: 是为了完成特定的任务, 用某种语言编写的一组指令的集合

​	**进程(process)**: 正在运行的程序

​	**线程(thread)**: 是程序内部的一条执行路径, 多个线程共享进程的堆和方法区, 但有各自的栈和程序计数器



```xml
进程可以细化为多个线程。 
每个线程，拥有自己独立的：栈、程序计数器 
多个线程，共享同一个进程中的结构：方法区、堆。
```



### 并行与并发的理解

并行：多个CPU同时执行多个任务。比如：多个人同时做不同的事。

并发：一个CPU(采用时间片)同时执行多个任务。比如：秒杀、多个人做同一件事



### 多线程程序的优点：

1. 提高应用程序的响应。对图形化界面更有意义，可增强用户体验。
2. 提高计算机系统CPU的利用率。
3. 改善程序结构。将既长又复杂的进程分为多个线程，独立运行，利于理解和修改。







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
9. sleep(long millitime):让当前线程“睡眠”指定的millitime毫秒。在指定的millitime毫秒时间内，当前线程是阻塞状态。
10. isAlive():判断当前线程是否存活



**sleep(), 与 wait()**   

sleep属于线程方法, 是Thread类的, 是静态方法

wait() 是final 非静态方法, 若是非静态方法, 则可被其他线程调用其sleep, 造成线程之间不必要的问题



**sleep() 和 wait()的异同？**

相同点：一旦执行方法，都可以使得当前的线程进入阻塞状态。

不同点：

1）两个方法声明的位置不同：Thread类中声明sleep() , Object类中声明wait()

2）调用的要求不同：sleep()可以在任何需要的场景下调用。 wait()必须使用在同步代码块或同步方法中

3）关于是否释放同步监视器：如果两个方法都使用在同步代码块或同步方法中，sleep()不会释放锁，wait()会释放锁。





**两种方式的对比：**

开发中优先选择：实现Runnable接口的方式

**原因：**

1. 实现的方式没类的单继承性的局限性

2. 实现的方式更适合来处理多个线程共享数据的情况。

**联系：**public class Thread implements Runnable

**相同点：**两种方式都需要重写run(),将线程要执行的逻辑声明在run()中。 目前两种方式，要想启动线程，都是调用的Thread类中的start()



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



**Lock锁**

- ReentrantLock类实现了Lock，它拥有与 synchronized相同的并发性和内存语义，在实现线程安全的控制中，比较常用的是 Reentrantlock，可以显式加锁、释放锁。

```java
class A {
    //1.实例化ReentrantLock对象
    private final ReenTrantLock lock = new ReenTrantLook();
    public void m (){
        lock.lock//2.先加锁
        try{
            //保证线程同步的代码
        }finally{
            lock.unlock();//3.后解锁
        }
    }
}

//注意：如果同步代码块有异常，要将unlock()写入finally语句块中
```



```java
class Window implements Runnable{
    private Integer ticket = 100;
    private ReentrantLock lock = new ReentrantLock();
    @Override
    public void run(){
            while(true) {
                 // 上锁
        		try{
                    lock.lock();
                    if (ticket > 0) {
                        System.out.println(Thread.currentThread.getName + "号窗口开始卖第" + ticket-- +" 张票");
                    }else{
                        break;
                    }
            	}finally{
            		lock.unlock()
        		}
            }
        }
    }
}

public class Test{
    public static void main(String[] args){
        Window w = new Window();
        
        new Thread(w).start();
        new Thread(w).start();
        new Thread(w).start();
    }
}
```



**synchronized 与 Lock的异同？**

1. 相同：二者都可以解决线程安全问题

2. 不同：synchronized机制在执行完相应的同步代码以后，自动的释放同步监视器

3. Lock需要手动的启动同步（lock()，同时结束同步也需要手动的实现（unlock()）

4. 使用的优先顺序：

   Lock---> 同步代码块（已经进入了方法体，分配了相应资源 ) --->同步方法（在方法体之外)

5. 利弊： 同步的方式，解决了线程的安全问题。---好处 操作同步代码时，只能一个线程参与，其他线程等待。相当于是一个单线程的过程，效率低。



**Java是如何解决线程安全问题的，有几种方式？并对比几种方式的不同**

利用同步锁的方式，有三种方式同步代码块、同步方法和用lock方法



**synchronized和Lock方式解决线程安全问题的对比**

1. 相同：二者都可以解决线程安全问题
2. 不同：synchronized机制在执行完相应的同步代码以后，自动的释放同步监视器
3. Lock需要手动的启动同步（lock()，同时结束同步也需要手动的实现（unlock()）

###  死锁问题

1. 死锁的理解： 不同的线程分别占用对方需要的同步资源不放弃，都在等待对方放弃自己需要的同步资源，就形成了线程的死锁
2. 说明：

- 出现死锁后，不会出现异常，不会出现提示，只是所的线程都处于阻塞状态，无法继续
- 我们使用同步时，要避免出现死锁。



**死锁举例：**

```java
public static void main(String[] args) {

    StringBuffer s1 = new StringBuffer();
    StringBuffer s2 = new StringBuffer();


    // 匿名线程, 使用继承Thread方式
    new Thread(){
        @Override
        public void run() {

            synchronized (s1){

                s1.append("a");
                s2.append("1");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (s2){
                    s1.append("b");
                    s2.append("2");

                    System.out.println(s1);
                    System.out.println(s2);
                }


            }

        }
    }.start();


    // 匿名内部类, 采用实现Runnable接口方式
    new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (s2){

                s1.append("c");
                s2.append("3");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (s1){
                    s1.append("d");
                    s2.append("4");

                    System.out.println(s1);
                    System.out.println(s2);
                }
            }
        }
    }).start();


}
```







###  同步的范围：

**如何找问题，即代码是否存在线程安全？（非常重要**）

（1）明确哪些代码是多线程运行的代码

（2）明确多个线程是否有共享数据

（3）明确多线程运行代码中是否有多条语句操作共享数据

**如何解决呢？（非常重要）**

对多条操作共享数据的语句，只能让一个线程都执行完，在执行过程中，其他线程不可以参与执行。 即所有操作共享数据的这些语句都要放在同步范围中

**注意点：**

范围太小：没锁住所有有安全问题的代码 范围太大：没发挥多线程的功能



## 线程通讯

为了解决线程的死锁问题，引入线程通讯



### 1. 线程通信涉及到的三个方法：

- wait():一旦执行此方法，当前线程就进入阻塞状态，并释放同步监视器。
- notify():一旦执行此方法，就会唤醒被wait的一个线程。如果有多个线程被wait，就唤醒优先级高的那个。
- notifyAll():一旦执行此方法，就会唤醒所有被wait的线程。



### 2. 说明：

- wait()，notify()，notifyAll()三个方法**必须使用在同步代码块或同步方法中**。

- wait()，notify()，notifyAll()三个方法的调用者必须是同步代码块或同步方法中的同步监视器。

  否则，会出现IllegalMonitorStateException异常

- wait()，notify()，notifyAll()三个方法是定义在java.lang.Object类中。





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



#### 	方式三: 实现Callable接口 

​	重写call()方法, 可以有返回值

**实现方法：**

1. 创建一个实现Callable的实现类
2. 实现call方法，将此线程需要执行的操作声明在call()中
3. 创建Callable接口实现类的对象
4. 将此Callable接口实现类的对象作为传递到FutureTask构造器中，创建FutureTask的对象
5. 将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start()
6. 获取Callable中call方法的返回值

```java

//1.创建一个实现Callable的实现类
class NumThread implements Callable{
    //2.实现call方法，将此线程需要执行的操作声明在call()中
    @Override
    public Object call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            if(i % 2 == 0){
                System.out.println(i);
                sum += i;
            }
        }
        return sum;
    }
}


public class ThreadNew {
    public static void main(String[] args) {
        //3.创建Callable接口实现类的对象
        NumThread numThread = new NumThread();
        //4.将此Callable接口实现类的对象作为传递到FutureTask构造器中，创建FutureTask的对象
        FutureTask futureTask = new FutureTask(numThread);
        //5.将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start()
        new Thread(futureTask).start();

        try {
            //6.获取Callable中call方法的返回值
            //get()返回值即为FutureTask构造器参数Callable实现类重写的call()的返回值。
            Object sum = futureTask.get();
            System.out.println("总和为：" + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
```



**如何理解实现Callable接口的方式创建多线程比实现Runnable接口创建多线程方式强大？**

1. call()可以返回值的。
2. call()可以抛出异常，被外面的操作捕获，获取异常的信息
3. Callable是支持泛型的

​	

#### 	方式四: 使用线程池

​	背景：经常创建和销毁、使用量特别大的资源，比如并发情况下的线程对性能影响很大。



**应用线程池的好处：**

1. 提高响应速度（减少了创建新线程的时间）

2. 降低资源消耗（重复利用线程池中线程，不需要每次都创建）

3. 便于线程管理

   

   

   ```java
   class NumberThread implements Runnable{
   
       @Override
       public void run() {
           for(int i = 0;i <= 100;i++){
               if(i % 2 == 0){
                   System.out.println(Thread.currentThread().getName() + ": " + i);
               }
           }
       }
   }
   
   class NumberThread1 implements Runnable{
   
       @Override
       public void run() {
           for(int i = 0;i <= 100;i++){
               if(i % 2 != 0){
                   System.out.println(Thread.currentThread().getName() + ": " + i);
               }
           }
       }
   }
   
   public class ThreadPool {
   
       public static void main(String[] args) {
           //1. 提供指定线程数量的线程池
           ExecutorService service = Executors.newFixedThreadPool(10);
           ThreadPoolExecutor service1 = (ThreadPoolExecutor) service;
           //设置线程池的属性
   //        System.out.println(service.getClass());
   //        service1.setCorePoolSize(15);
   //        service1.setKeepAliveTime();
   
   
           //2.执行指定的线程的操作。需要提供实现Runnable接口或Callable接口实现类的对象
           service.execute(new NumberThread());//适合适用于Runnable
           service.execute(new NumberThread1());//适合适用于Runnable
   
   //        service.submit(Callable callable);//适合使用于Callable
           //3.关闭连接池
           service.shutdown();
       }
   
   }
   ```

   

   **线程不安全的懒汉式**

   ```java
public class Singleton4 {
       /**
     * 1、构造器私有化
        * 2、用一个静态变量保存这个唯一的实例
        * 3、提供一个静态方法，获取这个实例对象
        */
       private static Singleton4 instance;
       private Singleton4() {}
   
       public static Singleton4 getInstance() {
               if (instance == null) {
                   try{
                       Thread.sleep(100);
                   } catch(Exception e){
                       e.printStackTrace();
                   }
                   instance = new Singleton4();
               }
               return instance;
   
       }
   }
   
   
   // 此执行方式线程不安全, 
   public Class TestSingleton4{
       public static void main(String[] args) {
           // Callable 方式实现多线程
           Callable<Singleton4> c = new Callable<>() {
               @Override
               public Singleton4 call() throws Exception{
                   return Singleton4.getInstance();
               }
           };
           // 创建一个线程池,里面放俩
           ExecutorService es = Executors.newFixedThreadPool(2);
           Future<Singleton4> f1 = es.submit(c);
           Future<Singleton4> f2 = es.submit(c);
   
           Singleton4 s1 = f1.get();
           Singleton4 s2 = f2.get();
           
           System.out.println(s1 == s2);    // 多次验证, 会出现false情况
           
           
       }
   }
   ```
   
   
   
   
   
   
   
   
   
   corePoolSize：核心池的大小
   
   maximumPoolSize：最大线程数
   
   keepAliveTime：线程没任务时最多保持多长时间后会终止

​	Executors: 创建线程池

​	ExecutorService service = Executors.newFixedThreadPool (5);



​	多态

​	ThreadPoolExecutor pool = (ThreadPoolExecutor) serivce;



​	pool.setCorePoolSize(); // 设置相应参数



​	serivce.sumbit() 适合Callable

​	service.execute() 适合Runnable

​	service.shutdown() 关闭连接池











### 小测试

1. 创建个窗口卖票，总票数为100张.

**使用Runnable方式**

```java
public class Ticket implements Runnable {
    private int tick = 100;

    @Override
    public void run() {

        while (true) {
            synchronized (this) {
                if (tick > 0) {
                    System.out.println(Thread.currentThread().getName() + "号窗口买票，票号为：" + tick--);
                } else {
                    break;
                }
            }
        }
    }
}

class TicketTest {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();			// 创建一个对象, 

        Thread thread1 = new Thread(ticket);	// 多个线程调用
        Thread thread2 = new Thread(ticket);
        Thread thread3 = new Thread(ticket);

        thread1.setName("窗口1");
        thread2.setName("窗口2");
        thread3.setName("窗口3");

        thread1.start();
        thread2.start();
        thread3.start();

    }
}
```

**使用Thread方式**

```java
public class Ticket extends Thread{
    // 使用静态变量进行约束单独的票,否则非静态成员变量会各自产生对应的票
    private static Integer ticket = 100;
    @Override
    public void run(){
        while(ture){
            synchronized(Ticket.class) {
                if (ticket > 0) {
                    System.out.println(Thread.currnetThread.getName + "号窗口卖第" + ticket-- + "张票");
                }else {
                    break;
                }
            }
        }
    }
}

public class Test{
    public static void main(String[] args) {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        ticket1.start();
        ticket2.start();
        ticket3.start();
    }
}
```



**使用同步代码块方式**

```java

public class Ticket implements Runnable{
    private Integer ticket = 100;
    private boolean flag = true;
    
    @Override
    public void run(){
        while(flag){
            show();
        }
    }
    private static synchronized void show(){
        if (ticket  > 0) {
            System.out.println(Thread.currentThread.getName + "号窗口开始卖第" + ticket-- + "张票");
        }else {
            flag = false;
        }
    } 
}


public class Test{
    public static void main(String[] args) {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();
        ticket1.start();
        ticket2.start();
        ticket3.start();
    }
}
```





**使用两个线程打印 1-100，线程1, 线程2 交替打印。**

// 使用object 对象, 是因为调度时, wait() notify() synchronized() 需绑定相同的对象, 才可以

线程通信不适用lock锁

```java

class MyThread implements Runnable{
    private int number = 1;
    private Object obj = new Object();

    @Override
    public void run(){
        while(true){
            synchronized (obj) {
                obj.notifyAll();
                if (number <= 100) {
                    System.out.println(Thread.currentThread().getName() + "---" + number);
                    number++;
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }
    }
}

public class Test3{
    public static void main(String[] args){
        MyThread th = new MyThread();
        new Thread(th,"线程1").start();
        new Thread(th,"线程2").start();
    }
}
```



