package xjt.javassist;

import javassist.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TJModify {
    public static void main(String[] args) {
        ClassPool classPool = ClassPool.getDefault();
        try {
            CtClass originCls = classPool.get("xjt.javassist.OriginCls");
            CtMethod printMethod = originCls.getDeclaredMethod("printOrigin");
            printMethod.insertAfter("System.out.println(\"秋日胜春朝\");");

            Object originObj = originCls.toClass().newInstance();
            Method printRefMethod = originObj.getClass().getMethod("printOrigin");
            printRefMethod.invoke(originObj);
        } catch (NotFoundException | CannotCompileException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
