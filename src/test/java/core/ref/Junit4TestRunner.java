package core.ref;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Constructor constructor=clazz.getConstructor();
        Object instance=constructor.newInstance();
        Method[] methods=clazz.getDeclaredMethods();

        for(Method method: methods){
            for(Annotation annotation: method.getAnnotations()){
                if(annotation.annotationType().equals(MyTest.class)){
                    method.invoke(instance);
                }
            }
        }

    }
}
