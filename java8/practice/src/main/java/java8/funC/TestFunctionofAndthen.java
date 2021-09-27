package java8.funC;

import java.util.function.Function;

public class TestFunctionofAndthen {

    public static void main(String[] args) {

        System.out.println(testFunction(2,i -> i * 2 + 1,j -> j * j));  // 25
    }

    public static int testFunction(int i, Function<Integer,Integer> function1, Function<Integer,Integer> function2) {

        return function1.andThen(function2).apply(i);
    }
}