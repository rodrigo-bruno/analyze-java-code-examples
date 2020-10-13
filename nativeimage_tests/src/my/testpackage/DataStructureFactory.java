package my.testpackage;

import java.util.Collection;
import java.util.ArrayList;
import java.util.AbstractList;

public class DataStructureFactory {

    public static <T> AbstractList<T> ArrayList(Class<T> parameterT) {
        return new ArrayList<T>();
    }

    public static <T> AbstractList<T> ArrayList(Class<T> parameterT, int initialCapacity) {
        return new ArrayList<T>(initialCapacity);
    }

    public static <T> AbstractList<T> ArrayList(Class<T> parameterT, Collection<? extends T> c) {
        return new ArrayList<T>(c);
    }
}
