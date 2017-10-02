package demo2;

import java.lang.reflect.Method;
import java.util.Arrays;

// Use the following JVM option to suppress illegal access warning:
//   --add-opens=java.base/java.lang=ALL-UNNAMED
public class LiveStackTrace {

    private static Object invoke(String methodName, Object instance) {
        try {
            Class<?> liveStackFrame = Class.forName("java.lang.LiveStackFrame");
            Method m = liveStackFrame.getMethod(methodName);
            m.setAccessible(true);
            return m.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Should not happen", e);
        }
    }

    public static void dump() {
        StackWalker sw = (StackWalker) invoke("getStackWalker", null);
        sw.forEach(frame -> {
            Object[] locals = (Object[]) invoke("getLocals", frame);
            System.out.println(" at " + frame + "  " + Arrays.toString(locals));
        });
    }
}
