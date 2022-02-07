package xjt.io.buffer;

import lombok.extern.slf4j.Slf4j;

import java.nio.CharBuffer;
@Slf4j
public class BufferFillDrain {
    private static String[] lines = {
      "滚滚长江东逝水", "浪花淘尽英雄","是非成败转头空","青山依旧在","几度夕阳红"
    };
    private static int index = 0;
    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate(100);//position=0,limit=capacity（第一个不能读写的位置）
        while (fillBuffer(buffer)){//position=position+1。
            buffer.flip();//准备读。limit=position,position=0。
            drainBuffer(buffer);//position=position+1。
            buffer.clear();//准备写。position=0,limit=capacity。
        }
    }
    public static void drainBuffer(CharBuffer buffer){
        while(buffer.hasRemaining()){//position < limit
            System.out.println("drain:"+buffer.get());
        }
    }
    public static boolean fillBuffer(CharBuffer buffer){
        if(index>= lines.length){
            return false;
        }
        String l = lines[index++];
        for(int i=0;i<l.length();i++){
            buffer.put(l.charAt(i));
        }
        return true;
    }
}
