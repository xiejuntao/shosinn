package xjt.proxy;

import java.lang.reflect.*;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class XProxy {
    private static final Class<?>[] constructorParams =
            { InvocationHandler.class };
    public static Object newProxyInstance(Class cl,
                                          InvocationHandler h){
        try {
            final Constructor<?> cons = cl.getConstructor(constructorParams);
            final InvocationHandler ih = h;
            if (!Modifier.isPublic(cl.getModifiers())) {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    public Void run() {
                        cons.setAccessible(true);
                        return null;
                    }
                });
            }
            return cons.newInstance(new Object[]{h});
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        IUserDao target = new UserDao();
        IUserDao iUserDao = (IUserDao) newProxyInstance(target.getClass(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("开启事务");

                // 执行目标对象方法
                Object returnValue = method.invoke(target, args);

                System.out.println("提交事务");
                return null;
            }
        });
        iUserDao.save();
    }
}
