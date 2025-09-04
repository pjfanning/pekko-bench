package example;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SWARUtil;
import org.apache.pekko.util.ByteString;

import java.nio.ByteOrder;

public class NettyByteBufUtil {
  /**
   * This is using a SWAR (SIMD Within A Register) batch read technique to minimize bound-checks and improve memory
   * usage while searching for {@code value}.
   */
  static int firstIndexOf(ByteString byteString, int fromIndex, byte value) {
    fromIndex = Math.max(fromIndex, 0);
    final int toIndex = byteString.length() - 1;
    if (fromIndex >= toIndex || byteString.isEmpty()) {
      return -1;
    }
    final int length = toIndex - fromIndex;
    byteString.checkIndex(fromIndex, length);
    if (!PlatformDependent.isUnaligned()) {
      return byteString.indexOf(value, fromIndex);
    }
    assert PlatformDependent.isUnaligned();
    int offset = fromIndex;
    final int byteCount = length & 7;
    if (byteCount > 0) {
      final int index = unrolledFirstIndexOf(byteString, fromIndex, byteCount, value);
      if (index != -1) {
        return index;
      }
      offset += byteCount;
      if (offset == toIndex) {
        return -1;
      }
    }
    final int longCount = length >>> 3;
    final ByteOrder nativeOrder = ByteOrder.nativeOrder();
    final boolean isNative = nativeOrder == byteString.order();
    final boolean useLE = nativeOrder == ByteOrder.LITTLE_ENDIAN;
    final long pattern = SWARUtil.compilePattern(value);
    for (int i = 0; i < longCount; i++) {
      // use the faster available getLong
      final long word = useLE ? byteString._getLongLE(offset) : byteString._getLong(offset);
      final long result = SWARUtil.applyPattern(word, pattern);
      if (result != 0) {
        return offset + SWARUtil.getIndex(result, isNative);
      }
      offset += Long.BYTES;
    }
    return -1;
  }

  private static int unrolledFirstIndexOf(ByteString byteString, int fromIndex, int byteCount, byte value) {
    assert byteCount > 0 && byteCount < 8;
    if (byteString.apply(fromIndex) == value) {
      return fromIndex;
    }
    if (byteCount == 1) {
      return -1;
    }
    if (byteString.apply(fromIndex + 1) == value) {
      return fromIndex + 1;
    }
    if (byteCount == 2) {
      return -1;
    }
    if (byteString.apply(fromIndex + 2) == value) {
      return fromIndex + 2;
    }
    if (byteCount == 3) {
      return -1;
    }
    if (byteString.apply(fromIndex + 3) == value) {
      return fromIndex + 3;
    }
    if (byteCount == 4) {
      return -1;
    }
    if (byteString.apply(fromIndex + 4) == value) {
      return fromIndex + 4;
    }
    if (byteCount == 5) {
      return -1;
    }
    if (byteString.apply(fromIndex + 5) == value) {
      return fromIndex + 5;
    }
    if (byteCount == 6) {
      return -1;
    }
    if (byteString.apply(fromIndex + 6) == value) {
      return fromIndex + 6;
    }
    return -1;
  }

}
