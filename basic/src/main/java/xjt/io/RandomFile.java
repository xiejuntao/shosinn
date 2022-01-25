package xjt.io;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@Slf4j
public class RandomFile {
    public static void main(String[] args) {
        RandomAccessFile randomAccessFile = null;
        try {
            long t = System.currentTimeMillis();
            //randomAccessFile = new RandomAccessFile("D:\\xjt\\growth_git\\growth\\README.md","r");
            randomAccessFile = new RandomAccessFile("D:\\xjt\\growth_git\\growth\\Interview\\BAT\\1.pdf","r");
            log.info("open file`cost={}",System.currentTimeMillis() - t);
            String line = null;
/*            while((line=randomAccessFile.readLine())!=null){
                log.info(StringUtils.toEncodedString(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();;
        }finally {
            if(randomAccessFile!=null){
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
