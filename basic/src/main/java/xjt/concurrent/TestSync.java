package xjt.concurrent;
/**
 * 对代码进行反汇编 javap -c -v .\TestSync.class
 * */
public class TestSync {
    public static void main(String[] args){
        TestSync testSync = new TestSync();
        testSync.s();
    }
    public synchronized void s(){
        System.out.println("知行合一");
    }
    public void ss(){
        System.out.println("初");
        synchronized (this){//monitorenter
            System.out.println("致良知");
        }//monitorexit
        System.out.println("终");
    }
}
