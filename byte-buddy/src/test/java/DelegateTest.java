import bean.Hi;
import com.xie.bytebuddy.Class2FileUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.Test;

import java.lang.reflect.Modifier;

public class DelegateTest {

    @Test
    public void testDelegate(){
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("org.itstack.demo.bytebuddy.HelloWorld")
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
                .withParameter(String[].class, "args")
                .intercept(MethodDelegation.to(Hi.class))
                .make();
        Class2FileUtils.outputClazz(dynamicType.getBytes());
    }
}
