package xjt.rpc.dproxy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyTest {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("君子不器"))
                .make()
                .load(ByteBuddyTest.class.getClass().getClassLoader())
                .getLoaded();
        System.out.println(dynamicType.newInstance().toString());
    }
}
