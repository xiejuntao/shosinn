package xjt.codec;

import org.apache.commons.codec.digest.MurmurHash3;

public class TestMurmurHash3 {
    public static void main(String[] args) {
        byte[] data = "自由".getBytes();
        System.out.println(MurmurHash3.hash32(data));
    }
}
