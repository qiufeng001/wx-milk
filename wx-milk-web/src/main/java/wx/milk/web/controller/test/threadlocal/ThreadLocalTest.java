package wx.milk.web.controller.test.threadlocal;

/**
 * auther: kiven on 2018/9/7/007 14:52
 * try it bast!
 */
public class ThreadLocalTest {


    private static ThreadLocal<Long> longThreadLocal = new ThreadLocal<Long>();
    private static ThreadLocal<String> StringThreadLocal = new ThreadLocal<String>();

    public static void setLong(Long v) {
        longThreadLocal.set(v);
    }

    public static void setString(String v) {
        StringThreadLocal.set(v);
    }

    public static Long getLong() {
        return longThreadLocal.get();
    }

    public static String getString() {
        return StringThreadLocal.get();
    }

    public static void threadLong() {
        new Thread() {
            @Override
            public void run() {

                setLong(Thread.currentThread().getId());
                System.out.println(getLong());
                setString(Thread.currentThread().getName());

                System.out.println(getString());
            }
        }.start();
    }

    public static void threadString() {
        new Thread() {
            @Override
            public void run() {
                setLong(Thread.currentThread().getId());
                System.out.println(getLong());
                setString(Thread.currentThread().getName());
                System.out.println(getString());
            }
        }.start();
    }


    public static void main(String args[]) {
        setLong(Thread.currentThread().getId());
        setString(Thread.currentThread().getName());
        System.out.println(getString());
        System.out.println("userId 前:" + getLong());
        System.out.println("name 前:" + getString());
        threadLong();
        threadString();
        System.out.println("userId 前:" + getLong());
        System.out.println("name 前:" + getString());
    }
}
