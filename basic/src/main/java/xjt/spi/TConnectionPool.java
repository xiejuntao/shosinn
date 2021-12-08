package xjt.spi;

import lombok.extern.slf4j.Slf4j;
import xjt.concurrent.semaphore.Pool;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class TConnectionPool extends Pool<TConnection>{
    String url = "pool";
    String userName;
    String userPwd;
    public TConnectionPool(int MAX_AVAILABLE) {
        super(MAX_AVAILABLE);
        init();
    }
    @Override
    public void init() {
        try {
            for(int i=0;i<MAX_AVAILABLE;i++) {
                TConnection connection = (TConnection) DriverManager.getConnection(url, userName, userPwd);
                putItem(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final TConnectionPool pool = new TConnectionPool(2);
        for(int i=0;i<4;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info("try get");
                        xjt.spi.TConnection t = pool.getItem();
                        log.info("get:"+t);
                        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
                        pool.putItem(t);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
