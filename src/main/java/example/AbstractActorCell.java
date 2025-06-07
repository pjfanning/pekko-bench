package example;

import org.apache.pekko.util.Unsafe;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

final class AbstractActorCell {
    static final long nextNameOffset;
    static final VarHandle nextNameHandle;
    static final AtomicLongFieldUpdater<ActorCell> nextNameUpdater;

    static {
        final String fieldName = "_nextName";
        try {
            nextNameUpdater =
                    AtomicLongFieldUpdater.newUpdater(ActorCell.class, fieldName);
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
