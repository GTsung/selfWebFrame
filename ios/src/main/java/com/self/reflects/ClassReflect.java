package com.self.reflects;

import com.self.reflects.domain.ReflectSample;

import java.lang.reflect.*;

/**
 * @author GTsung
 * @date 2021/10/12
 */
public class ClassReflect {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<ReflectSample> clazz1 = ReflectSample.class;
        Type[] types = clazz1.getGenericInterfaces();
        for (Type type:types) {
            System.out.println(type.getTypeName());
        }
        AnnotatedType[] annotatedTypes = clazz1.getAnnotatedInterfaces();
        for (AnnotatedType type:annotatedTypes) {
            System.out.println(type.getType());
        }
        TypeVariable<Class<ReflectSample>>[] typeVariables = clazz1.getTypeParameters();
        for (int i=0;i<typeVariables.length;i++) {
            TypeVariable<Class<ReflectSample>> typeVariable = typeVariables[i];
//            typeVariable.
        }

        Constructor[] constructors = clazz1.getConstructors();
        for (Constructor c: constructors
             ) {
            System.out.println(c.getClass());
            ReflectSample sample = (ReflectSample) c.newInstance();
        }
    }
}
