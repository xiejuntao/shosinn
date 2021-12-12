package xjt.jvm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloseResource implements AutoCloseable{
    public static void main(String[] args) {
        try(CloseResource closeResource=new CloseResource()){

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            log.info("finally");
        }
    }

    @Override
    public void close() throws Exception {
        log.info("close");
    }
}
