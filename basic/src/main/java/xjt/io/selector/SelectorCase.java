package xjt.io.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class SelectorCase {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(2047));
        serverSocketChannel.configureBlocking(false);
        Selector selector0 = Selector.open();
        //Selector selector1 = Selector.open();
        //log.info("multi selector`assert={}",selector0!=selector1);
        SelectionKey selectionKey = serverSocketChannel.register(selector0, SelectionKey.OP_ACCEPT);//|SelectionKey.OP_WRITE//.interestOps(SelectionKey.OP_WRITE);
        //selectionKey.interestOps(SelectionKey.OP_WRITE);
        log.info("selection key multi interest`ops={}",selectionKey.interestOps());
        //Pipe pipe = Pipe.open();
        //selectionKey.attach(new Worker(pipe.sink()));
        while (true) {
            log.info("waiting for connection.");
            Set<SelectionKey> selectionKeys = selector0.selectedKeys();
            log.info("before select()`selection keys`size={}",selectionKeys.size());
            SelectionKey alreadySelectionKey = null;
            if(selectionKeys.size()>0){
                alreadySelectionKey = selectionKeys.stream().findFirst().get();
                log.info("interest`isSame={}`readyOps={}`interestOps={}",alreadySelectionKey==selectionKey,alreadySelectionKey.readyOps(),alreadySelectionKey.interestOps());
            }
            int count = selector0.select();
            selectionKeys = selector0.selectedKeys();
            log.info("selection keys`count={}`keys={}", count,selectionKeys.size());
            if (count > 0) {//count判断条件
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey tmpSelectionKey = iterator.next();
                    if (tmpSelectionKey.isAcceptable()) {
                        //如果不调用accept处理，客户端一直处理等待连接状态，select会立即返回准备连接的选择键，即使在遍历后清空了selector的选择键集合
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.write(ByteBuffer.wrap("hi".getBytes()));
                        //socketChannel.
                        log.info("so interest`tmpSame={}`selectedSame={}",tmpSelectionKey==alreadySelectionKey,tmpSelectionKey==selectionKey);
                        log.info("server say hi`interestOps={}`channel={}", tmpSelectionKey.interestOps(), tmpSelectionKey.channel());

                    }
                }
                //selectionKeys.clear();/
                /** 问题：如果不调用clear，第二次telnet,selector.select()立即返回0，未有预期的阻塞。
                 *  1、第一次telnet，count=1,accept处理了。
                 *  2、select按预期处于阻塞中。
                 *  3、第二次telnet，select一直快速返回且count=0.
                 *  原因：因为serversocketchannel与selector注册的selectionKey在第一次telnet的accept事件处理后没有被清理。
                 *  第二次telnet的accept事件触发后并没有更新selectionKey而返回count=0，导致第二次telnet的accept事件没有被处理，系统一直循环触发accept事件而表象就是select()一直快速返回。
                 *
                 *
                 *  select立即返回的情况：
                 *  1、It returns only after at least one channel is selected,
                 *  1、至少一个channel是就绪状态（因为count=0,所以不触发此条件？）
                 *  2、this selector's {@link #wakeup wakeup} method is invoked, or the current
                 *  thread is interrupted, whichever comes first
                 *  2、调用wakeup方法或当前线程被中断。
                 *
                 *  问题分析
                 *  1、因为第一次telnet后没有清理就绪的selectionKey（此selectionKey是serversocketchannel与selector的注册关系，只会有一个）
                 *  符合select()立即返回的第一个条件？，关联的是selector的selectedKeys集合。
                 *  ==>
                 *  1、select()的返回count如何计算？
                 *  答：返回值不是已准备好的通道的总数，而是从上一个 select( )调用之后进入就绪状态的通道的数量。（新增）
                 *  interest`isSame=true`readyOps=16`interestOps=16。通道serversocketchannel在第一次telnet后就一直就绪acceptable状态。所以之后的返回的count一直是0。
                 *
                 *  2、第一次telnet后，再select()，selectionKey虽没清理，并且即使readyOps=16`interestOps=16。也要等系统变更，否则一直阻塞了？
                 *  理论上也应该立即返回？==>selectedKeys的更新机制？==>第二次telnet，系统触发了变更？
                 *
                 *  再分析：
                 *  1、select什么时候返回?
                 *  a、无参调用在没有通道就绪时将无限阻塞。一旦至少有一个已注册的通道就绪，选择器的选择键就会被更新，并且每个就绪的通道的 ready 集合也将被更新。
                 * */
            }
        }
    }
}
