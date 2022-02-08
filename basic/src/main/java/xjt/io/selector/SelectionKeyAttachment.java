package xjt.io.selector;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
@Slf4j
public class SelectionKeyAttachment {
    public volatile Object attachment;
    public AtomicReferenceFieldUpdater<SelectionKeyAttachment,Object> updater
            = AtomicReferenceFieldUpdater.newUpdater(SelectionKeyAttachment.class,Object.class,"attachment");
    public Object attachment(Object attachment){
        return updater.getAndSet(this,attachment);
    }
    public Object attachment(){
        return attachment;
    }

    public static void main(String[] args) {
        SelectionKeyAttachment selectionKeyAttachment = new SelectionKeyAttachment();
        List<Integer> list = new LinkedList<>();
        list.add(2046);
        selectionKeyAttachment.attachment(list);
        log.info("attachment={}",((List)selectionKeyAttachment.attachment()).get(0));
    }
}
