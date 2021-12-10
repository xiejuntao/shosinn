package xjt.jvm;

import org.openjdk.jmh.annotations.Benchmark;
import xjt.proxy.TestProxy;

public class BenchmarkRunner {
    @Benchmark
    public void test() {
        TestProxy testProxy = new TestProxy();
        testProxy.testProxy();
    }
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
