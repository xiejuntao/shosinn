package xjt.future;

import xjt.ref.Life;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public class TestCompleableFuture {
    public static void main(String[] args) {
/*        CompletableFuture<Life> completableFuture = CompletableFuture.supplyAsync(new Supplier<Life>() {
            @Override
            public Life get() {
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(3));
                return new Life();
            }
        });*/
        CompletableFuture<Life> completableFuture = new CompletableFuture<>();

        completableFuture.thenAccept(new Consumer<Life>() {
            @Override
            public void accept(Life life) {
                life.go();
            }
        });
        System.out.println("go gogo");
        completableFuture.complete(new Life());
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(12));
    }
}
