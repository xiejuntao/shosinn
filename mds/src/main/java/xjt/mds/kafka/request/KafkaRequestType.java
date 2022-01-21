package xjt.mds.kafka.request;

public enum KafkaRequestType {
    PRODUCE(KafkaRequestCls.Data)
    ,FETCH(KafkaRequestCls.Data)
    ,LeaderAndIsr(KafkaRequestCls.Controller)
    ,StopReplica(KafkaRequestCls.Controller);

    KafkaRequestCls cls;
    KafkaRequestType(KafkaRequestCls kafkaRequestCls){
        this.cls = kafkaRequestCls;
    }
    public KafkaRequestCls getCls(){
        return this.cls;
    }
}
