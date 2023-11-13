package org.example.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionInfoUtils {
    public static <T> void printDeclaredFieldsInfo(Class<? extends T> clazz, T instance) throws IllegalAccessException {
        for (Field field : clazz.getDeclaredFields()) {
            System.out.printf("Field name :%s type %s , is Synthetic %s \n", field.getType(), field.getName(), field.isSynthetic());
            System.out.printf("Field value is : %s \n", field.get(instance));
        }
    }

    private static Constructor<?> getFirstDeclaredConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new IllegalStateException(String.format("No constructor has been found for class %s", clazz.getSimpleName()));
        }
        return constructors[0];
    }

    public static <T> T createObjectRecursively(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = getFirstDeclaredConstructor(clazz);
        List<Object> constructorArguments = new ArrayList<>();

        for (Class<?> argumentType : constructor.getParameterTypes()) {
            Object argumentValue = createObjectRecursively(argumentType);
            constructorArguments.add(argumentValue);
        }

        constructor.setAccessible(true);

        return (T) constructor.newInstance(constructorArguments.toArray());
    }

    public static void printClassInfo(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            System.out.printf("class name: %s,class package name %s \n", clazz.getSimpleName(), clazz.getPackageName());

            Class<?>[] implementedInterfaces = clazz.getInterfaces();
            for (Class<?> implementedInterface : implementedInterfaces) {
                System.out.printf("class %s implements : %s \n", clazz.getSimpleName(), implementedInterface.getSimpleName());
            }

            System.out.printf("Is Interface %s \n", clazz.isInterface());
            System.out.printf("Is Array %s \n", clazz.isArray());
            System.out.printf("Is isEnum %s \n", clazz.isEnum());
            System.out.printf("Is isPrimitive %s \n", clazz.isPrimitive());
            System.out.printf("Is Anon %s \n", clazz.isAnonymousClass());

            System.out.println();
        }
    }

    public static void printConstructorData(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        System.out.printf("For the class %s has %d declared constructors", clazz.getSimpleName(), constructors.length);


        for (int i = 0; i < constructors.length; i++) {
            Class<?>[] parameterTypes = constructors[i].getParameterTypes();

            List<String> parameterTypesNames = Arrays.stream(parameterTypes).map(Class::getSimpleName).toList();
            System.out.println(parameterTypesNames);
        }
    }

    public static <T> T createInstanceWithArguments(Class<T> clazz, Object... args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == args.length) {
                return (T) constructor.newInstance(args);
            }
        }
        System.out.println("No constructor with appropriate constructor found");
        return null;
    }

    public static void inspectArrayObject(Object arrayObject){
        Class<?> clazz = arrayObject.getClass();
        System.out.printf("Is Array %s",clazz.isArray());
        Class<?> arrayComponentType =clazz.getComponentType();

        System.out.printf("This is amy array of type %s",arrayComponentType.getTypeName());
    }

    private static void inspectArrayValues(Object arrayObject){
        int arrayLength = Array.getLength(arrayObject);
        System.out.println("[");
        for (int i=0;i<arrayLength;i++){
            Object element = Array.get(arrayObject,i);

            if (element.getClass().isArray()){
                inspectArrayValues(element);
                continue;
            }

            System.out.print(element);
            if(i!=arrayLength-1){
                System.out.print(",");
            }
        }
        System.out.println("]");
    }
}


