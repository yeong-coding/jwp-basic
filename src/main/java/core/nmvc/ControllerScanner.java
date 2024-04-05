package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {

    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflections;

    public ControllerScanner(Object... basePackage){
        reflections=new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers(){
        Set<Class<?>> preInitiatedControllers=reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(preInitiatedControllers);
    }

    Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers){
        Map<Class<?>, Object> controllers= Maps.newHashMap();

        try{
            for(Class<?> clazz: preInitiatedControllers){
                controllers.put(clazz, clazz.getConstructor().newInstance());
            }
        }catch (InstantiationException | IllegalAccessException e){
            logger.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
        }

        return controllers;
    }

    public Map<Class<?>, Object> findController() throws Exception {

        Reflections refs=new Reflections("my.project");
        Set<Class<?>> annotated=refs.getTypesAnnotatedWith(Controller.class);
        Map<Class<?>, Object> map=new HashMap<>();

        Iterator<Class<?>> controllers=annotated.iterator();

        logger.debug("get controller start");

        try {
            while (controllers.hasNext()) {
                Constructor constructor = controllers.getClass().getConstructor();
                Object instance = constructor.newInstance();
                logger.debug(instance.getClass().getName());
                map.put(controllers.next(), instance);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        logger.debug("get controller end");
        return map;
    }
}
