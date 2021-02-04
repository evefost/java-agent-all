import bean.Source;
import bean.Target;
import com.xie.bytebuddy.Class2FileUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.Test;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class ProxyMethod {



    @Test
    public void main() throws IllegalAccessException, InstantiationException {

        String helloWorld = new ByteBuddy()
                .subclass(Source.class)
                .method(named("hello"))
                .intercept(MethodDelegation.to(Target.class))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .hello("World");


        System.out.println(helloWorld);

    }

}
