package com.self.common.test;

import com.self.common.mq.boot.MqServiceImpl;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.util.Hashtable;
import java.util.stream.IntStream;

/**
 * @author GTsung
 * @date 2021/9/27
 */
public class AllTest {

    public static void main(String[] args) throws Exception {
//        testThreadLocal();
//        String s = "12123232";
//        System.out.println(s.indexOf("3", 1));

//        CyclicBarrier barrier = new CyclicBarrier(1);
//        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put(null, 1);
    }

    private static void testThreadLocal() {
//        ThreadLocal<Integer> local = new ThreadLocal<>();
        InheritableThreadLocal<Integer> local = new InheritableThreadLocal<>();
        IntStream.range(0, 10).forEach(i -> {
            local.set(i);
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+ "-" +local.get());
            }).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void relect() throws ClassNotFoundException {
        //        char[] chars = {'a', 'e', 's'};
//        System.out.println(Character.codePointAt(chars, 0));
        Class clazz = Class.forName("java.lang.String");
//        System.out.println(clazz.getSimpleName());
        AnnotatedType[] as = clazz.getAnnotatedInterfaces();
        for (AnnotatedType a : as) {
            System.out.println(a.getType().getTypeName());
        }
        AnnotatedType bs = clazz.getAnnotatedSuperclass();
        System.out.println(bs.getType());
        Class clz = MqServiceImpl.class;
        Annotation[] annotations = clz.getAnnotations();
        for (Annotation a : annotations) {
            System.out.println(a.annotationType().getName());
        }
//        Annotation a = clz.getAnnotation(Service.class);
        Annotation[] ass = clz.getAnnotationsByType(Service.class);
        for (Annotation a : ass) {
            System.out.println(a.annotationType());
        }
//        Class cc = clz.getComponentType();
//            System.out.println(cc.getName());
        Constructor[] constructors = clz.getConstructors();
        for (Constructor c: constructors) {
            System.out.println(c.getName());
        }
    }
}
