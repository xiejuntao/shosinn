package xjt.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class TaoServer {
    public static void main(String[] args) {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            ObjectName name = new ObjectName("xjt.jmx:type=Tao");
            Tao mbean = new Tao();
            mBeanServer.registerMBean(mbean, name);
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        System.out.println("waiting forever....");
        while (true) {

        }
    }
}
