package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {

        logger.debug("class name=======");
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        // 모든 필드
        Field[] fields = clazz.getDeclaredFields();

        logger.debug("field==============");
        for(int i=0;i< fields.length;i++){
            logger.debug(fields[i].getName());
        }

        // 생성자
        logger.debug("constructor==============");
        Constructor[] consts=clazz.getDeclaredConstructors();
        for(int i=0;i< consts.length;i++){
            logger.debug(consts[i].getName());
        }

        // 메소드
        logger.debug("method==================");
        Method[] methods=clazz.getDeclaredMethods();
        for(int i=0;i< methods.length;i++){
            logger.debug(methods[i].getName());
        }
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> clazz = User.class;
//        String userId, String password, String name, String email
        Constructor constructor=clazz.getConstructor(String.class, String.class, String.class, String.class);
        Object instance=constructor.newInstance("1111", "password1234", "홍길동", "hong@gmail.com");
        logger.debug(clazz.getName());
    }
    
    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student=new Student();
        Field name=clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(student, "홍길동");
        Field age=clazz.getDeclaredField("age");
        age.setAccessible(true);
        age.set(student, 39);

        logger.debug(student.getName());
        logger.debug(student.getAge()+"");

    }
}
