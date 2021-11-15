package xjt.jmx;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.concurrent.atomic.AtomicLong;

public class Tao extends NotificationBroadcasterSupport implements TaoMBean{
    private int process = 7;
    private final AtomicLong sequenceNumber = new AtomicLong(0);
    @Override
    public void setProcess(int process) {
        int oldProcess = this.process;
        System.out.println("set process:"+process);
        this.process = process;
        Notification notification = new AttributeChangeNotification(
                this, sequenceNumber.getAndIncrement(), System.currentTimeMillis(),
                "process changed", "process", "int",
                oldProcess, this.process);
        sendNotification(notification);
    }

    @Override
    public int getProcess() {
        return this.process;
    }

    @Override
    public void find() {
        System.out.println("Tao");
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] {
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };
        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[]{info};
    }
}
