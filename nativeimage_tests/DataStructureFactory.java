package my.testpackage;

import java.util.Collection;
import java.util.ArrayList;
import java.util.AbstractList;
import java.util.ArrayListInteger;
import java.util.ArrayListPoint;

public class DataStructureFactory {

    public static <T> AbstractList<T> ArrayList(Class<T> parameterT) {
        System.out.println("HELLO!");
        if (parameterT.equals(Integer.class)) {
           return (AbstractList<T>) new ArrayListInteger(); 
        } else if (parameterT.equals(Point.class)) {
           return (AbstractList<T>) new ArrayListPoint(); 
        } else {
            return new ArrayList<T>();
        }
    }

    public static <T> AbstractList<T> ArrayList(Class<T> parameterT, int initialCapacity) {
        if (parameterT.equals(Integer.class)) {
           return (AbstractList<T>) new ArrayListInteger(initialCapacity); 
        } else if (parameterT.equals(Point.class)) {
           return (AbstractList<T>) new ArrayListPoint(initialCapacity); 
        } else {
            return new ArrayList<T>(initialCapacity);
        }
    }

    public static <T> AbstractList<T> ArrayList(Class<T> parameterT, Collection<? extends T> c) {
        if (parameterT.equals(Integer.class)) {
           return (AbstractList<T>) new ArrayListInteger((Collection<Integer>)c); 
        } else if (parameterT.equals(Point.class)) {
           return (AbstractList<T>) new ArrayListPoint((Collection<Point>)c); 
        } else {
            return new ArrayList<T>(c);
        }
    }
}
