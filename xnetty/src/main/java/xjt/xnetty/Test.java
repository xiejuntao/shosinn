package xjt.xnetty;

import xjt.xnetty.loop.NioEventLoopGroup;

public class Test {
    public static void main(String[] args) {
        Bootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup master = new NioEventLoopGroup(1);
        NioEventLoopGroup slave = new NioEventLoopGroup(100);
        bootstrap.group(master,slave);
        bootstrap.channel();
        bootstrap.bind(7777).sync();
    }
}
