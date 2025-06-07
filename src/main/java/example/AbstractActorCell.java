package example;

import org.apache.pekko.util.Unsafe;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

final class AbstractActorCell {
    static final long nextNameOffset;
    static final VarHandle nextNameHandle;
    static final long functionRefOffset;

    static {
        final String nextNameField = "_nextName";
        final String functionRefField = "_functionRefs";
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            nextNameOffset =
                    Unsafe.instance.objectFieldOffset(
                            ActorCell.class.getDeclaredField(nextNameField));
            nextNameHandle =
                    MethodHandles.privateLookupIn(ActorCell.class, lookup)
                            .findVarHandle(
                                    ActorCell.class,
                                    nextNameField,
                                    long.class);
            functionRefOffset =
                    Unsafe.instance.objectFieldOffset(
                            ActorCell.class.getDeclaredField(functionRefField));
        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }
}
