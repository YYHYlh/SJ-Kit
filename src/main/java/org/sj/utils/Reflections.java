package org.sj.utils;


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import sun.reflect.ReflectionFactory;

public class Reflections {
    public Reflections() {
    }

    public static void setAccessible(AccessibleObject member) {
        member.setAccessible(true);
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        Field field = null;

        try {
            field = clazz.getDeclaredField(fieldName);
            setAccessible(field);
        } catch (NoSuchFieldException var4) {
            if (clazz.getSuperclass() != null) {
                field = getField(clazz.getSuperclass(), fieldName);
            }
        }

        return field;
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }

    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Constructor<?> getFirstCtor(String name) throws Exception {
        Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        setAccessible(ctor);
        return ctor;
    }

}
