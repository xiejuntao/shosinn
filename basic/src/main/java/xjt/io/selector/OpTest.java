package xjt.io.selector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpTest {
    static final int OP_READ = 1 << 0;//   0000 0001
    static final int OP_WRITE = 1 << 2; // 0000 0100
    static final int OP_CONNECT = 1 << 3;//0000 1000
    static final int OP_ACCEPT = 1 << 4;// 0001 0000
    public static void main(String[] args) {
/*        log.info("isReadable={}",(readyOps()&OP_READ)!=0);
        log.info("isWritable={}",(readyOps()&OP_WRITE)!=0);
        log.info("isConnectable={}",(readyOps()&OP_CONNECT)!=0);
        log.info("isAcceptable={}",(readyOps()&OP_ACCEPT)!=0);*/
        interestOps(OP_ACCEPT);
        interestOps(OP_WRITE);
    }
    public static int readyOps(){
        return OP_READ|OP_WRITE;
    }
    public static int interestOps(){
        return OP_READ|OP_WRITE|OP_CONNECT|OP_ACCEPT;
    }
    public static void interestOps(int ops){
        if ((ops & ~validOps()) != 0) {
            //0001 0000
            //1110 1111
            //accept & ==0(与自己的非做与运算，一定为非)
            throw new IllegalArgumentException();
        }
        log.info("interest`ops={}",ops);
    }
    public static int validOps(){
        return OP_ACCEPT;
    }
}
