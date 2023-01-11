package org.fpm.di.example;

import org.fpm.di.Binder;
import org.fpm.di.Configuration;

import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class DummyBinder implements Binder {
    public DummyBinder(Configuration configuration) {
        configuration.configure(this);
    }

    private final ArrayList<Class<?>> list = new ArrayList<>();
    private final HashMap<Class<?>, Class<?>> map_classes = new HashMap<>();
    private final HashMap<Class<?>, Object> map_instances = new HashMap<>();
    @Override
    public <T> void bind(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Singleton.class)) {
            try {
                bind(clazz, clazz.getConstructor().newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            list.add(clazz);
        }

    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        map_classes.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        map_instances.put(clazz, instance);
    }

    public ArrayList<Class<?>> getList() {
        return list;
    }

    public HashMap<Class<?>, Class<?>> getMap_classes() {
        return map_classes;
    }

    public HashMap<Class<?>, Object> getMap_instances() {
        return map_instances;
    }
}
