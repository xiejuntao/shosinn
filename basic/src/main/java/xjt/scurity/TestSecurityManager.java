package xjt.scurity;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 开启 -Djava.security.manager
 * -Djava.security.manager=C:/Users/Lizhi/IdeaProjects/shosinn/basic/src/main/java/xjt/scurity/java.policy
 * 默认 C:/Program Files/Java/jre1.8.0_311/lib/security/java.policy
 * */
@Slf4j
public class TestSecurityManager {
    public static void main(String[] args) {
        SecurityManager appsm = System.getSecurityManager();

        if(appsm!=null){
            log.info("open security manager");

        }

        File file = new File("s.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
