package xjt.ref;

public class Life {
    public void go(String d){
        System.out.println(d!=null?d:"人世繁华");
    }
    public void go(){
        go(null);
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("曲终人散");
    }
}
