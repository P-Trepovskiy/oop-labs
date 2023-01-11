package org.fpm.di.example;

import org.fpm.di.Container;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DummyContainer implements Container {
    private final DummyBinder binder;
    public DummyContainer(DummyBinder binder) {
        this.binder = binder;
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        if (binder.getList().contains(clazz)) {
            Constructor<?>[] constructor = clazz.getConstructors();
            for (Constructor<?> i : constructor) {
                if(i.getAnnotation(Inject.class)!=null) {
                    Object[] forCreate = new Object[i.getParameterCount()];
                    System.arraycopy(i.getParameterTypes(), 0, forCreate, 0, forCreate.length);
                    for (int j=0;j<forCreate.length;j++)
                        forCreate[j] = getComponent((Class<T>) forCreate[j]);
                    try {
                        return (T) i.newInstance(forCreate);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            try {
                return clazz.getConstructor().newInstance();
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
        if (binder.getMap_classes().containsKey(clazz)) {
            return (T) getComponent(binder.getMap_classes().get(clazz));
        }
        if (binder.getMap_instances().containsKey(clazz)) {
            return (T) binder.getMap_instances().get(clazz);
        }
        return null;
    }
}
