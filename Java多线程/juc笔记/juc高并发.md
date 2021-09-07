# JUC笔记



## 什么是JUC



### 进程和线程

`进程`： 是系统进行资源分配和调度的基本单位

`线程`：是操作系统能够进行运算调度的最小单位

>  总结来说:
> 进程：指在系统中正在运行的一个应用程序；程序一旦运行就是进程；进程-----资源分配的最小单位。
> 线程：系统分配处理器时间资源的基本单元，或者说进程之内独立执行的一个单元执行流。线程——程序执行的最小单位。



### 线程的状态

`Thread.State`  枚举类

- NEW,(新建)
- 'RUNNABLE,（准备就绪）
- BLOCKED,（阻塞）
- WAITING,（不见不散）
- TIMED_WAITING,（过时不候）
- TERMINATED;(终结)



###   wait/sleep 的区别

> （1）sleep 是 Thread 的静态方法，wait 是 Object 的方法，任何对象实例都能调用。
> （2）sleep 不会释放锁，它也不需要占用锁。wait 会释放锁，但调用它的前提是当前线程占有锁(即代码要在 synchronized 中)。
> （3）它们都可以被 interrupted 方法中断。
>
> (4 )  两个方法都是在哪里睡觉在哪里起来。会造成虚假唤醒，后面会提到



### 并发与并行

这里说的并发和并行是语言上的，程序上的。

> 并发：同一时刻多个线程在访问同一个资源，多个线程对一个点
> 		例子：春运抢票 电商秒杀...
> 		并行：多项工作一起执行，之后再汇总
> 		例子：泡方便面，电水壶烧水，一边撕调料倒入桶中

在操作系统中

并发指的是 多个线程交替执行，实际一个时间只有一个线程在执行，。

并行指的是 多核CPU同时执行多个线程。

### 管程

通俗易懂的话来说， 就是  锁， 操作系统中的专业词汇。

> 执行线程首先要持有管程对象，然后才能执行方法，当方法完成之后会释放管程，方法在执行时候会持有管程，其他线程无法再获取同一个管程

进入拿到锁， 用完释放锁。



### 用户线程和守护线程

`用户线程`: 平时用到的普通线程,自定义线程
        `守护线程`: 运行在后台,是一种特殊的线程,比如垃圾回收

设置守护线程方式： setDaemon(true)

查看线程性质： isDaemon()



## LOCK接口



### Synchronized关键字

==synchronized 是 Java 中的关键字，是一种同步锁。==

> 1. 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象；
> 2. 修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；

### **经典卖票**

`必须先加锁，后判断，不然锁不住。`

有多少个线程，想怎么样执行就写完整的代码，然后在资源类中进行限制通知等等。

例如： 3个售货员卖30张票，则每个线程的代码都一致，都是30个循环，然后在 资源票 上进行控制，。

```java
 
/**
 * 卖票
 */

class Ticket{
    private int num = 30;

    public void sale() {
        synchronized (this) {			// 必须先加锁再判断，不然控制不住。
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出" + num-- + "剩余" + num);
            }
        }
    }
}

public class SaleTicket {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }
        },"售货员A   ").start();

        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }
        },"售货员B   ").start();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }
        },"售货员C   ").start();
    }

}

```



### 多线程编程步骤：（特别重要）

`都是套路，看准了`

- **编写资源类，封装属性与对应方法。**
- **在资源操作方法中**
  - **加锁**
  - **判断**
  - **干活**
  - **通知**
  - **释放锁**
- **创建多线程进行操作**
- **防止虚假唤醒**

![image-20210907222855184](juc高并发.assets/image-20210907222855184.png)



### 什么是 Lock

> Lock 锁实现提供了比使用同步方法和语句可以获得的更广泛的锁操作。它们允许更灵活的结构，可能具有非常不同的属性，并且可能支持多个关联的条件对象。Lock 提供了比 synchronized 更多的功能。



**Lock 与的 Synchronized 区别**

> Lock 不是 Java 语言内置的，synchronized 是 Java 语言的关键字，因此是内置特性。Lock 是一个类，通过这个类可以实现同步访问；
> 		Lock 和 synchronized 有一点非常大的不同，采用 synchronized 不需要用户去手动释放锁，当 synchronized 方法或者 synchronized 代码块执行完之后，系统会自动让线程释放对锁的占用；而 Lock 则必须要用户去手动释放锁，如果没有主动释放锁，就有可能导致出现死锁现象。'
>
> 少量的线程资源争抢没有区别，大量的线程争抢 LOCK效率更高

### Lock 接口



```java
Lock lock = ...;
lock.lock();
try{
//处理任务
}catch(Exception ex){
}finally{
```

lock.lock()  用来上锁

lock.unLock() 用来解锁

==因为lock 并不会主动释放锁， 所以一定要使用规范的开发，try {} finally { }==



比较常用的是： **ReentrantLock** （可重入锁） 后面详细讲述。





### 经典的生产者消费者模式

**防止虚假唤醒， 使用while 进行判断**

#### **使用synchronized 实现**

```java

/**
 * 线程通信
 */
class Sour{
    private int num = 0;

    public synchronized void incr() throws InterruptedException {
        while ( num != 0) {
            this.wait();
        }
        num ++;
        System.out.println(Thread.currentThread().getName() + "输出 " + num);
        this.notifyAll();

    }

    public synchronized void decr() throws InterruptedException {
        while ( num != 1) {
            this.wait();
        }
        num --;
        System.out.println(Thread.currentThread().getName() + "输出 " + num);
        this.notifyAll();
    }

}


public class ChangeNum {

    public static void main(String[] args) {
        Sour sour = new Sour();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sour.incr();
                } catch (InterruptedException e) {
                }
            }
        },"AA").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sour.decr();
                } catch (InterruptedException e) {
                }
            }
        },"BB").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sour.incr();
                } catch (InterruptedException e) {
                }
            }
        },"CC").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sour.decr();
                } catch (InterruptedException e) {
                }
            }
        },"DD").start();

    }


}

```



#### **使用 lock 实现** ( 一定要记得释放锁！！！！！)



lock.newCondition()  获取Condition 对象。

其中的await() 与 Object 中的wait() 方法类似

其中的signalAll() 与 Thread 中的 notifyAll()  类似   

暂时不懂两者区别。

```java

class N{
    private int num = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void incr() throws InterruptedException {
        lock.lock();
        try {
            while (num != 0) {
                condition.await();
            }
            num ++;
            System.out.println(Thread.currentThread().getName() + "输出： " + num);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void decr() throws InterruptedException {
        lock.lock();
        try {
            while (num != 1) {
                condition.await();
            }
            num --;
            System.out.println(Thread.currentThread().getName() + "输出： " + num);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

}
public class ChangeNumLock {

    public static void main(String[] args) {

        N n = new N();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    n.incr();
                } catch (InterruptedException e) {
                }
            }
        },"AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    n.decr();
                } catch (InterruptedException e) {
                }
            }
        },"BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    n.incr();
                } catch (InterruptedException e) {
                }
            }
        },"CC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    n.decr();
                } catch (InterruptedException e) {
                }
            }
        },"DD").start();
    }
}

```

