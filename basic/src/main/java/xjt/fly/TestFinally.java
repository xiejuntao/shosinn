package xjt.fly;

public class TestFinally {
    public static void main(String[] args) {
        String f = fly();
        System.out.println(f);
    }
    public static String fly(){
        try{
            System.out.println("f");
            System.out.println("l");
            //int i = 1/0;
            System.exit(-1);
            System.out.println("y");
            return "fly";
        }catch (RuntimeException e){
            System.out.println("run error");
            //System.exit(-1);
        }finally {
            System.out.println("finally");
        }
        return null;
    }
}
