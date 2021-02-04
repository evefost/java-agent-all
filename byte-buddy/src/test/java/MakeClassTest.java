import com.xie.bytebuddy.Class2FileUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import org.junit.Test;

import java.lang.reflect.Modifier;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class MakeClassTest {

    @Test
    public void makeSimple(){
        DynamicType.Unloaded<Object> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make();
        Class2FileUtils.outputClazz(dynamicType.getBytes());
    }

    @Test
    public void makeMethod(){
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("org.itstack.demo.bytebuddy.HelloWorld2")
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
                .withParameter(String[].class, "args")
                .intercept(FixedValue.value("Hello World!"))
                .make();
        Class2FileUtils.outputClazz(dynamicType.getBytes());
    }
}
