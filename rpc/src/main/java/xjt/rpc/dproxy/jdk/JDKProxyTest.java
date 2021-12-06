package xjt.rpc.dproxy.jdk;

import java.lang.reflect.Proxy;

public class JDKProxyTest {
    public static void main(String[] args) {
        // 构建代理器
        HelloImpl target = new HelloImpl();
        JDKProxy proxy = new JDKProxy(target);
        ClassLoader classLoader = target.getClass().getClassLoader();
        // 把生成的代理类保存到文件
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        // 生成代理类
        IHello test = (IHello) Proxy.newProxyInstance(classLoader,target.getClass().getInterfaces(), proxy);
        // 方法调用
        System.out.println(test.say());
    }
}
