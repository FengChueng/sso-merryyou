package cn.merryyou.sso.client;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by zhangyinglong on 2019/1/15.
 */
public class Test {

    public static void main(String[] args) {
//        testConsumer();
    }

    public static void testConsumer() {
        Consumer<String> consumer1 = (x) -> System.out.println(x);
        Consumer<String> consumer2 = (x) -> System.out.println(" after consumer 1" + x);
        consumer1.andThen(consumer2).accept("test consumer1");
        System.out.println("----------------------");
        consumer1.andThen(consumer2);

        List<Integer> list = Arrays.asList(1,2,3,3,4,4,5,6);

        list.stream()
            .peek(System.out::print)
            .filter(distinctKey(Integer::intValue))
            .collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctKey(Function<? super T, Object> fun) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return (T t) -> map.putIfAbsent(fun.apply(t), Boolean.TRUE) == null;
    }
}
