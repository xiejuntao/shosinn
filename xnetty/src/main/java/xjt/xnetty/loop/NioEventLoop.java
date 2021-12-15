package xjt.xnetty.loop;

import xjt.xnetty.utils.LogUtil;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioEventLoop implements EventLoop {
    public Selector selector;
    public ServerSocketChannel serverSocketChannel;
    public Executor executor;
    public void init(Selector selector, ServerSocketChannel serverSocketChannel){
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }
    public void masterRun(final NioEventLoopGroup nioEventLoopGroup){
        executor = new Executor() {
            @Override
            public void execute() {
                while (true) {
                    try {
                        int n = selector.select();
                        if (n > 0) {
                            Set<SelectionKey> selectionKeys = selector.selectedKeys();
                            Iterator<SelectionKey> iterator = selectionKeys.iterator();
                            while (iterator.hasNext()) {
                                SelectionKey selectionKey = iterator.next();
                                if (selectionKey.isAcceptable()) {
                                    SocketChannel socketChannel = serverSocketChannel.accept();
                                    socketChannel.configureBlocking(false);
                                    socketChannel.register(selector, SelectionKey.OP_READ);
                                    LogUtil.log("ready");
                                } else {
                                    nioEventLoopGroup.slavesRun(selectionKey);
                                }
                                iterator.remove();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executor.doExecute();
    }
    public void slaveRun(final SelectionKey selectionKey){
        executor = new Executor() {
            @Override
            public void execute() {
                try {
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                        int i = socketChannel.read(byteBuffer);
                        if (i != -1) {
                            String msg = new String(byteBuffer.array()).trim();
                            LogUtil.log("server received message: %s", msg);
                            String replyMsg = msg.toUpperCase();
                            socketChannel.write(ByteBuffer.wrap(replyMsg.getBytes()));
                            LogUtil.log("server reply upper message: %s", replyMsg);
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        executor.doExecute();
    }
}
