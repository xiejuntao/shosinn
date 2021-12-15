package xjt.xnetty;

import xjt.xnetty.loop.NioEventLoop;
import xjt.xnetty.loop.NioEventLoopGroup;
import xjt.xnetty.utils.LogUtil;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class ServerBootstrap extends Bootstrap{
    public ServerSocketChannel serverSocketChannel;
    public Selector selector;
    public NioEventLoop masterEventLoop;
    public ServerBootstrap(){
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void group(NioEventLoopGroup master, NioEventLoopGroup slave) {
        super.group(master, slave);
        LogUtil.log("group");

    }
    @Override
    public void channel(){
        masterEventLoop = master.getNioEventLoop(0);
    }
    @Override
    public Bootstrap bind(int port){
        try {
            LogUtil.log("bind port=%d",port);
            serverSocketChannel.bind(new InetSocketAddress("localhost",port));

        }catch(Exception e){
            e.printStackTrace();
        }
        return this;
    }
    @Override
    public void sync(){
        //masterEventLoop.
        masterEventLoop.init(selector,serverSocketChannel);
        masterEventLoop.masterRun(slave);
    }
}
