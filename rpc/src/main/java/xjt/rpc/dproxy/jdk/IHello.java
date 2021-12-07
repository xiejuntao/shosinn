package xjt.rpc.dproxy.jdk;

public interface IHello {
    String say();
    default void p(){
        System.out.println("人生");
    }
}
