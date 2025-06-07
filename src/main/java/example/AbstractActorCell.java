package example;

import org.apache.pekko.util.Unsafe;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

final class AbstractActorCell {
    static final long nextNameOffset;
    static final VarHandle nextNameHandle;

    static {
        final String fieldName = "_nextName";
        try {
            nextNameOffset =
                    Unsafe.instance.objectFieldOffset(
                            ActorCell.class.getDeclaredField(fieldName));
            nextNameHandle =
                    MethodHandles.privateLookupIn(ActorCell.class, MethodHandles.lookup())
                            .findVarHandle(
                                    ActorCell.class,
                                    fieldName,
                                    long.class);

        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }
}
