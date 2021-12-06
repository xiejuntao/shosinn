package xjt.rpc.serial.protostuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import xjt.rpc.serial.protostuff.bean.Dream;

/**
 * 官网示例
 * https://github.com/protostuff/protostuff
 * */
public class OfficeCase {
    public static void main(String[] args) {
        Dream dream = new Dream(0,"情人知己");;
        long t = System.currentTimeMillis();
        Schema<Dream> schema = RuntimeSchema.getSchema(Dream.class);
        LinkedBuffer buffer = LinkedBuffer.allocate(512);
        final byte[] protostuff;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(dream, schema, buffer);
        }finally {
            buffer.clear();
        }
        System.out.println("serial cost time="+(System.currentTimeMillis()-t));
        t = System.currentTimeMillis();
        Dream tmpDream = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(protostuff,tmpDream,schema);
        System.out.println("deserial cost time="+(System.currentTimeMillis()-t));
        System.out.println(tmpDream.item);
    }
}

