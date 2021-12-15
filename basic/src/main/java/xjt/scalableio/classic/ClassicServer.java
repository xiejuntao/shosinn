package xjt.scalableio.classic;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
@Slf4j
public class ClassicServer{
    int port = 2047;
    public void start(){
        try {
            ServerSocket ss = new ServerSocket(port);
            while (!Thread.interrupted()){
                Thread handler = new Thread(new ClassicHandler(ss.accept()));
                handler.start();
                log.info("start handler={}",handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClassicServer classicServer = new ClassicServer();
        classicServer.start();
    }
}
