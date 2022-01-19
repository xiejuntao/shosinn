package xjt.kafka.clients;

public class KConsumerConfig {
    public String BOOTSTRAP_SERVERS_CONFIG = "localhost:9093";
    public String GROUP_ID_CONFIG = "";
    public String AUTO_OFFSET_RESET_CONFIG = "earliest";//Default:	latest
    public boolean ENABLE_AUTO_COMMIT_CONFIG = false;
    public int POLL_INTERVAL_SECONDS = 1;
}
