package java8.funC;

import java.util.function.Function;

public class TestFunctionofcompose {

    public static void main(String[] args) {

        System.out.println(testFunction(2,i -> i * 2 + 1,j -> j * j));  // 9  先执行func2  在执行func1

    }

    /**
     * 接收一个function类型的函数作为参数
     * @param i
     * @param function1
     * @param function2
     * @return
     */
    public static int testFunction(int i, Function<Integer,Integer> function1, Function<Integer,Integer> function2) {

        return function1.compose(function2).apply(i);
    }
}