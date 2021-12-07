package xjt.proxy;

public class Test {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        IUserDao target = new UserDao();
        System.out.println(target.getClass());  //输出目标对象信息
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();
        IUserDao iUserDao = target.getClass().newInstance();
        //System.out.println(proxy.getClass());  //输出代理对象信息
        //proxy.save();  //执行代理方法
    }
    public static void proxy(){
        IUserDao target = new UserDao();
        System.out.println(target.getClass());  //输出目标对象信息
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();

        System.out.println(proxy.getClass());  //输出代理对象信息
        proxy.save();  //执行代理方法
    }
}
