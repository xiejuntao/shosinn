package xjt.sb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class SQLiteDataService implements DataService{
    @Override
    public void delete(long id) {
        log.info("sqlite delete`id={}",id);
    }
}
