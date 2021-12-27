package xjt.autumn.context;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import xjt.autumn.annotation.Harvest;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AutumnContext {
    public Map<Class,Object> clsMap = new ConcurrentHashMap<>();
    public Map<String,Object> nameMap = new ConcurrentHashMap<>();
    public Object getHarvest(Class cls){
        return clsMap.get(cls);
    }
    public Object getHarvest(String name){
        return nameMap.get(name);
    }
    public void putHarvest(Class cls,Object object){
        clsMap.put(cls,object);
    }
    public void putHarvest(String name,Object object){
        nameMap.put(name,object);
    }
    public void start(){
        Reflections reflections = new Reflections("xjt.autumn");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Harvest.class);
        for(Class<?> cls : classes){
            Harvest fruit = cls.getAnnotation(Harvest.class);
            Object object = null;
            try {
                object = cls.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
            clsMap.put(cls,object);
            if(StringUtils.isNotEmpty(fruit.name())){
                nameMap.put(fruit.name(), object);
            }
        }
    }
}
