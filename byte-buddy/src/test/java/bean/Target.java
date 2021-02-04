package bean;

public class Target {

    /**
     * 为什么要静态方法？
     * @param name
     * @return
     */
    public static String hello(String name) {
            return "Hello " + name + "!";
        }
    }