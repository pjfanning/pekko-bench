package example;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class UnsafeUtil {
  public static Object getUnsafeInstance() throws ReflectiveOperationException {
    final Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    Object theUnsafe;
    try {
      MethodHandle fieldGetter = lookup.findStatic(unsafeClass,
          "getUnsafe",
          MethodType.methodType(unsafeClass));
      theUnsafe = fieldGetter.invoke();
    } catch (Throwable t) {
      final Field field = unsafeClass.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      theUnsafe = field.get(null);
    }
    return theUnsafe;
  }
}
