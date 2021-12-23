package xjt.sb.basic;

import sun.reflect.ReflectionFactory;
import xjt.sb.service.LifeService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 反射创建实例的三种方式
 */
public class ReflectInstance {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        //1、java.lang.Class.newInsance()
        LifeService lifeService = LifeService.class.newInstance();
        lifeService.self.back();
        //2、java.lang.reflect.Constructor.newInstance()
        Constructor<?>[] constructors = LifeService.class.getConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                LifeService cLifeService = (LifeService) constructor.newInstance();
                cLifeService.self.back();
            }
        }
        //3、sun.reflect.ReflectionFactory.newConstructorForSerialization().newInstance()
        LifeService rLifeService = (LifeService) ReflectionFactory.getReflectionFactory().newConstructorForSerialization(LifeService.class).newInstance();
        rLifeService.self.back();
    }
}
