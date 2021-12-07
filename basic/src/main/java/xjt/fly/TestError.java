package xjt.fly;

public class TestError {
    public static void main(String[] args) {
        try {
            e();
        }catch (Throwable throwable){
            System.out.println("catch");
        }
        System.out.println("next");
    }
    public static void e() {
        System.out.println("t");
        throw new XError();
    }
}
