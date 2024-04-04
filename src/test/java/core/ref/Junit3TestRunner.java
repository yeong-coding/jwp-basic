package core.ref;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
//        Class<Junit3Test> instance=clazz.newInstance();
        Constructor constructor= clazz.getConstructor();
        Object instance=constructor.newInstance();
        Method[] methods=clazz.getDeclaredMethods();

        for(Method method: methods){
            String name=method.getName();

            if(name.startsWith("test")){
                method.invoke(instance);
            }

        }
    }
}
