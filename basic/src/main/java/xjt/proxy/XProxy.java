package xjt.proxy;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

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
    int V1_8=18;
    int ACC_PUBLIC=0;
    public void genCode(){
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        cw.visit(V1_8,                      // 指定Java版本
                ACC_PUBLIC,               // 说明是public类型
                "xjt/proxy/UserDaoProxy",  // 指定包和类的名称
                null,                     // 签名，null表示不是泛型
                "java/lang/Object",               // 指定父类
                new String[]{ "xjt/proxy/IUserDao" }); // 指定需要实现的接口


        MethodVisitor mv = cw.visitMethod(
                ACC_PUBLIC,               // 声明公共方法
                "save",               // 方法名称
                "()Ljava/lang/Object;",   // 描述符
                null,                     // 签名，null表示不是泛型
                null);                      // 可能抛出的异常，如果有，则指定字符串数组

        mv.visitCode();
// 省略代码逻辑实现细节
        cw.visitEnd();
        // 结束类字节码生成
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
