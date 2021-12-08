package xjt.ref;
/**
 * 人在一生应该这样度过：当回忆往事时，他不会因为虚度年华而后悔，也不会因为碌碌无为而羞愧。
 * */
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
