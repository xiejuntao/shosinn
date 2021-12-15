package xjt.xnetty;

import xjt.xnetty.loop.NioEventLoopGroup;

public abstract class Bootstrap {
    public NioEventLoopGroup master;
    public NioEventLoopGroup slave;
    public void group(NioEventLoopGroup master, NioEventLoopGroup slave){
        this.master = master;
        this.slave = slave;
    }
    public abstract void channel();
    public abstract Bootstrap bind(int port);
    public abstract void sync();
}
