package xjt.ref;

public class Life {
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("曲终人散");
    }
}
