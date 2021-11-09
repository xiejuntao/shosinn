package xjt.javassist;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @see <a href="https://www.cnblogs.com/rickiyang/p/11336268.html">javassist demo</a>
 * */
public class TJ {
    public static final String CLASS_NAME = "xjt.javassist.Shosinn";
    public static void main(String[] args) {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = createShosinn(classPool);
        // 生成class文件,通过类加载器反射执行
        String classPath = "C:/Users/Lizhi/IdeaProjects/shosinn/basic/src/javassist/";
        createClassFile(ctClass,classPath);
        invokeClassLoader(classPool,classPath);
        // 反射执行
        //invokeMethod(ctClass);
        //接口执行
        //invokeInterface(classPool,ctClass);

    }
    private static CtClass createShosinn(ClassPool classPool){
        CtClass ctClass = classPool.makeClass(CLASS_NAME);
        try {
            CtField originField = new CtField(classPool.get("java.lang.String"), "origin", ctClass);
            originField.setModifiers(Modifier.PRIVATE);
            ctClass.addField(originField, CtField.Initializer.constant("致良知"));

            ctClass.addMethod(CtNewMethod.setter("setOrigin", originField));
            ctClass.addMethod(CtNewMethod.getter("getOrigin", originField));

            CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
            ctConstructor.setBody("{origin=\"无善无恶心之体，有善有恶意之动，知善知恶是良知，为善去恶是格物。\";}");
            ctClass.addConstructor(ctConstructor);

            ctConstructor = new CtConstructor(new CtClass[]{classPool.get("java.lang.String")}, ctClass);
            ctConstructor.setBody("{$0.origin = $1;}");//$0=this。$1 $2 $3..代表方法参数。
            ctClass.addConstructor(ctConstructor);

            CtMethod printMethod = new CtMethod(CtClass.voidType, "printOrigin", new CtClass[]{}, ctClass);
            printMethod.setModifiers(Modifier.PUBLIC);
            printMethod.setBody("{System.out.println(origin);}");
            ctClass.addMethod(printMethod);
        }catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
        return  ctClass;
    }
    private static void invokeClassLoader(ClassPool classPool,String classPath){
        try {
            classPool.appendClassPath(classPath);
            CtClass ctClass = classPool.get(CLASS_NAME);
            invokeMethod(ctClass);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void createClassFile(CtClass ctClass,String classPath){
        try {
            ctClass.writeFile(classPath);
        } catch (CannotCompileException | IOException e) {
            e.printStackTrace();
        }
    }
    private static void invokeMethod(CtClass ctClass){
        Object shosinnObject;
        try {
            shosinnObject = ctClass.toClass().newInstance();
            Method setOrigin = shosinnObject.getClass().getMethod("setOrigin",String.class);
            setOrigin.invoke(shosinnObject,"明明德");
            Method printOrigin = shosinnObject.getClass().getMethod("printOrigin");
            printOrigin.invoke(shosinnObject);
        } catch (InstantiationException | IllegalAccessException | CannotCompileException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    private static void invokeInterface(ClassPool classPool,CtClass ctClass){
        try {
            CtClass interfaceClass = classPool.get("xjt.javassist.IShosinn");
            ctClass.setInterfaces(new CtClass[]{interfaceClass});

            IShosinn iShosinn = (IShosinn) ctClass.toClass().newInstance();
            iShosinn.printOrigin();
        } catch (NotFoundException | CannotCompileException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
