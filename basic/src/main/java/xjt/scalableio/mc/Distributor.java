package xjt.scalableio.mc;

public class Distributor {
    private int size = 0;
    private int index = 0;
    public Distributor(int size){
        this.size = size;
    }
    public int roundrobin(){
        int tmp = index;
        if(++index==size){
            index=0;
        }
        return tmp;
    }
}
