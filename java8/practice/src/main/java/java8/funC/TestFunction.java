package java8.funC;
/**
 * @author xhjing
 * @create 2021-09-23 11:36
 */

import org.junit.Test;

import java.util.function.Function;

/**
 * <p>Title: FunctionTest</p>
 * <p>Description:
 *
 *      Java8之function函数详解
 *
 * </p>
 *
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/9/23</p>
 *@author : xhjing
 *@version :1.0.0
 */
public class TestFunction {

    public static void main(String[] args) {

        System.out.println(testFunction(2,i -> i * 2 + 1));     // 5


    }

    /**
     * 关于如何理解 Function 函数，
     *      对于传统普通的调用来说， 是给定参数， 然后方法体在内部， 调用即可得出返回值
     * 而函数式编程，
     *      则是将函数的执行放入方法体中， 将具体的函数体由调用者提供。
     * 四大函数：
     *      主要是 Function() 函数
     *
     *  定义函数，传入Function 接口，
     *  在方法体中调用 func.apply(i);
     *  则 在调用时，即可。
     *
     * @param i
     * @param function
     * @return
     */
    public static int testFunction(int i, Function<Integer,Integer> function) {
        return function.apply(i);
    }
}