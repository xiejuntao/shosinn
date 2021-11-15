package xjt.jmx;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

public class TaoClient {
    public static void main(String[] args) throws IOException, MalformedObjectNameException, InstanceNotFoundException {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        //获取MBeanServer的连接
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
/*        waitForEnterPressed();

        echo("\nMBeanServer default domain = " + mbsc.getDefaultDomain());
        echo("\nMBean count = " + mbsc.getMBeanCount());
        echo("\nQuery MBeanServer MBeans:");

        Set<ObjectName> objectNames = mbsc.queryNames(null, null);
        for (ObjectName objectName : objectNames) {
            echo("\tObjectName = " + objectName);
        }
        waitForEnterPressed();*/
        //管理 Hello MBean
        ObjectName mbeanName = new ObjectName("xjt.jmx:type=Tao");
 /*       TaoMBean mbeanProxy = JMX.newMBeanProxy(mbsc, mbeanName, TaoMBean.class, true);
        mbeanProxy.find();
        mbeanProxy.setProcess(97);*/
        mbsc.addNotificationListener(mbeanName, new NotificationListener() {
            @Override
            public void handleNotification(Notification notification, Object handback) {
                echo("\nReceived notification:");
                echo("\tClassName: " + notification.getClass().getName());
                echo("\tSource: " + notification.getSource());
                echo("\tType: " + notification.getType());
                echo("\tMessage: " + notification.getMessage());
                //如果通知类型是AttributeChangeNotification，那么就获取一些和属性有关的信息
                if (notification instanceof AttributeChangeNotification) {
                    AttributeChangeNotification acn = (AttributeChangeNotification) notification;
                    echo("\tAttributeName: " + acn.getAttributeName());
                    echo("\tAttributeType: " + acn.getAttributeType());
                    echo("\tNewValue: " + acn.getNewValue());
                    echo("\tOldValue: " + acn.getOldValue());
                }
            }
        }, null, null);
        waitForEnterPressed();
    }
    private static void echo(String msg) {
        System.out.println(msg);
    }

    private static void waitForEnterPressed() {
        try {
            echo("\nPress <Enter> to continue...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
