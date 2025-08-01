package example;

import org.apache.pekko.util.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class StringAccess {
    private static final VarHandle bytesHandle;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            bytesHandle =
                    MethodHandles.privateLookupIn(String.class, lookup)
                            .findVarHandle(
                                    String.class,
                                    "value",
                                    byte[].class);

        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    public static byte[] getBytes(String str) {
        return (byte[]) bytesHandle.get(str);
    }
}
