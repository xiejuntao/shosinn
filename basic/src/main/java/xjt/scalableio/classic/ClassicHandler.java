package xjt.scalableio.classic;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
@Slf4j
public class ClassicHandler implements Runnable{
    private Socket socket;
    ClassicHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            long t = System.currentTimeMillis();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String req = bufferedReader.readLine();
            log.info("read`req={}",req);
            String res = process(req);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(res);
            bufferedWriter.flush();
            log.info("write`res={}`cost={}",res,System.currentTimeMillis()-t);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                bufferedWriter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public String process(String req){
        return req;
    }
}
