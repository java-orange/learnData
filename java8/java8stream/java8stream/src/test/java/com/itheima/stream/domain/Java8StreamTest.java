package com.itheima.stream.domain;

import org.junit.Test;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8StreamTest {

    /**
     * 功能描述:  过滤集合中不符合条件的元素
     * @return : void
     */
    @Test
    public void filter(){
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(str->str.contains("f")).collect(Collectors.toList());
        System.out.println(filtered);
    }
    /**
     * 功能描述: distinct去重
     * @return :
     */
    @Test
    public void distinct(){
        List<String> strings = Arrays.asList("abc", "bc", "bc", "efg", "abcd","", "jkl", "jkl");
        List<String> distincted = strings.stream().distinct().collect(Collectors.toList());
        System.out.println(distincted);
        //---------------------------------------------------------
        List<User> users = new ArrayList<>();
        users.add(new User(1,"z"));
        users.add(new User(1,"z"));
        users.add(new User(2,"s"));
        List<User> distinctedUser = users.stream().distinct().collect(Collectors.toList());
        System.out.println(distinctedUser);
    }
    /**
     * 功能描述: limit获取流中前n个元素
     * @return : void
     */
    @Test
    public void limit(){
        List<String> strings = Arrays.asList("abc", "bc", "bc", "efg", "abcd","", "jkl", "jkl");
        List<String> limited = strings.stream().limit(2).collect(Collectors.toList());
        System.out.println(limited);
    }
    /**
     * 功能描述: skip获取流中除去前n个元素的其他所有元素
     * @return : void
     */
    @Test
    public void skip(){
        List<String> strings = Arrays.asList("abc", "bc", "bc", "efg", "abcd","", "jkl", "jkl");
        List<String> skipped = strings.stream().skip(2).collect(Collectors.toList());
        System.out.println(skipped);
    }
    /**
     * 功能描述:  对流中所有元素做统一处理
     * @return : void
     */
    @Test
    public void map(){
        List<String> strings = Arrays.asList("abc", "bc", "bc", "efg", "abcd","", "jkl", "jkl");
        List<String> mapped = strings.stream().map(str -> "itheima_"+str).collect(Collectors.toList());
        System.out.println(mapped);
    }
    /**
     * 功能描述 :
     * @return : void
     */
    @Test
    public void flatMap(){
        List<String> strings = Arrays.asList("abc", "bc", "bc", "efg", "abcd","", "jkl", "jkl");
        List<Character> flatmaped = strings.stream().flatMap(str -> Java8StreamTest.getCharacterByString(str)).collect(Collectors.toList());
        System.out.println(flatmaped);
    }
    @Test
    public void map2flatmap(){
        List<String> string1 = Arrays.asList("abc", "bc", "bc", "efg", "abcd","", "jkl", "jkl");
        Stream<Character> flatMap = string1.stream().flatMap(str -> Java8StreamTest.getCharacterByString(str));
        //解析：flatmap
        //(1)[a,b,c] ,[b,c]...[j,k,l]
        //(2)[a,b,c,b,c...j,k,l] 扁平化操作
        flatMap.forEach(s-> System.out.print(s));
        List<String> string2 = Arrays.asList("abc", "bc", "bc", "efg", "abcd","", "jkl", "jkl");
        Stream<Stream<Character>> mapped = string2.stream().map(str -> Java8StreamTest.getCharacterByString(str));
        //[a,b,c] ,[b,c]...[j,k,l]
        mapped.forEach(stream-> {stream.forEach(character->{System.out.println(character);});});
    }
    /**
     * 功能描述:字符串转换为字符流
     * @param str
     * @return : java.util.stream.Stream<java.lang.Character>
     */
    public static Stream<Character> getCharacterByString(String str) {
        List<Character> characterList = new ArrayList<>();
        for (Character character : str.toCharArray()) {
            characterList.add(character);
        }
        return characterList.stream();
    }
    /**
     * 功能描述:
     * @return : void
     */
    @Test
    public void sorted(){
        //-----------------------字母排序--------------------------------------
        List<String> string1 = Arrays.asList("abc", "bc", "bcf", "efg", "abcd","bca", "jkl", "jkl");
        List<String> sorted = string1.stream().sorted().collect(Collectors.toList());
        System.out.println(sorted);
        //------------------------数字排序--------------------------------------
        List<Integer> ints = Arrays.asList(50,10,2,40,-9,8);
        List<Integer> collect = ints.stream().sorted().collect(Collectors.toList());
        System.out.println(collect);
        //-------------------------汉字排序-------------------------------------------
        List<String> strings2 = Arrays.asList("张三", "李四", "王五", "赵柳", "张哥","李哥", "王哥");
        List<String> collect1 = strings2.stream().sorted(Collections.reverseOrder(Collator.getInstance(Locale.CHINA))).collect(Collectors.toList());
        System.out.println(collect1);
    }
    //-----------------------------------终止操作符---------------------------------------------
    /**
     * 功能描述:  集合中是否有一个元素满足条件
     * @return : void
     */
    @Test
    public void anyMatch(){
        List<String> string = Arrays.asList("abc", "bc", "bcf", "efg", "abcd","bca", "jkl", "jkl");
        boolean bc = string.stream().anyMatch(s -> s.contains("q"));
        System.out.println(bc);
    }
    /**
     * 功能描述:  集合中元素是否都满足条件
     * @return : void
     */
    @Test
    public void allMatch(){
        List<String> string = Arrays.asList("abc", "bc", "bcf", "efg", "abcd","bca", "jkl", "jkl");
        boolean bc = string.stream().allMatch(s -> s.length()>3);
        System.out.println(bc);
    }
    /**
     * 功能描述:  集合中所有元素都不满足条件
     * @return : void
     */
    @Test
    public void noneMatch(){
        List<String> string = Arrays.asList("abc", "bc", "bcf", "efg", "abcd","bca", "jkl", "jkl");
        boolean bc = string.stream().noneMatch(s -> s.length()>5);
        System.out.println(bc);
    }
    /**
     * 功能描述:  返回集合中任意元素
     * @return : void
     */
    @Test
    public void findAny(){
        List<String> string = Arrays.asList("abc", "bc", "bcf", "efg", "abcd","bca", "jkl", "jkl");
        Optional<String> any = string.stream().findAny();
        if(any.isPresent()) System.out.println(any.get());
    }
    /**
     * 功能描述:  返回集合中第一个元素
     * @return : void
     */
    @Test
    public void findFirst(){
        List<String> string = Arrays.asList("abc", "bc", "bcf", "efg", "abcd","bca", "jkl", "jkl");
        Optional<String> any = string.parallelStream().findFirst();
        if(any.isPresent()) System.out.println(any.get());
    }
    /**
     * 功能描述: 循环
     * @return : void
     */
    @Test
    public void foreach(){
        List<String> string = Arrays.asList("abc", "bc", "bcf", "efg", "abcd","bca", "jkl", "jkl");
        string.stream().forEach(s-> System.out.println(s));
    }
    /**
     * 功能描述 : collect 将流转换为其他形式：list set map
     * @return : void
     */
    @Test
    public void collect(){
        List<String> string = Arrays.asList("abc", "bc", "bcf", "efg", "abcd","bca", "jkl", "jkl");
        Set<String> collect = string.stream().collect(Collectors.toSet());
        System.out.println(collect);
        //-----------------------------------------
        Map<String, String> collect1 = string.stream().collect(Collectors.toMap(v -> "prod_"+v, v -> v, (oldvalue, newvalue) -> newvalue));
        System.out.println(collect1);
    }
    /**
     * 功能描述:  将流中元素反复结合起来得到一个结果
     * @return : void
     */
    @Test
    public void reduce(){
        List<String> string = Arrays.asList("abc", "bc", "bcf", "efg", "abcd","bca", "jkl", "jkl");
        Optional<String> reduce = string.stream().reduce((acc,item) -> {return (acc+item).replace("a","");});
        if(reduce.isPresent()) System.out.println(reduce.get());
    }
    /**
     * 功能描述 :  获取集合中元素数量
     * @return : void
     */
    @Test
    public void count(){
        List<String> string = Arrays.asList("abc", "bc", "bcf", "efg", "abcd","bca", "jkl", "jkl");
        long count = string.stream().count();
        System.out.println(count);
    }
}
