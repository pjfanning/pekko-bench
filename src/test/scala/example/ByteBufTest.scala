package example

import org.apache.pekko.util.ByteString
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ByteStringWrapper(byteString: ByteString) {
  def getLong(index: Int): Long = {
    (byteString.apply(index).toLong & 0xFF) << 56 |
      (byteString.apply(index + 1).toLong & 0xFF) << 48 |
      (byteString.apply(index + 2).toLong & 0xFF) << 40 |
      (byteString.apply(index + 3).toLong & 0xFF) << 32 |
      (byteString.apply(index + 4).toLong & 0xFF) << 24 |
      (byteString.apply(index + 5).toLong & 0xFF) << 16 |
      (byteString.apply(index + 6).toLong & 0xFF) << 8 |
      (byteString.apply(index + 7).toLong & 0xFF)
  }

  // big endian only
  private def getIndex(word: Long): Int = {
    java.lang.Long.numberOfLeadingZeros(word) >>> 3
  }

  private def compilePattern(byteToFind: Byte): Long =
    (byteToFind.toLong & 0xFFL) * 0x101010101010101L

  private def applyPattern(word: Long, pattern: Long): Long = {
    val input = word ^ pattern
    val tmp = (input & 0x7F7F7F7F7F7F7F7FL) + 0x7F7F7F7F7F7F7F7FL
    ~(tmp | input | 0x7F7F7F7F7F7F7F7FL)
  }

  /**
   * This is using a SWAR (SIMD Within A Register) batch read technique to minimize bound-checks and improve memory
   * usage while searching for {@code value}.
   */
  def indexOf(value: Byte): Int = {
    if (byteString.isEmpty) return -1
    val searchLength = byteString.length
    var offset = 0
    val byteCount = searchLength & 7
    if (byteCount > 0) {
      val index = unrolledFirstIndexOf(0, byteCount, value)
      if (index != -1) return index
      offset += byteCount
      if (offset == searchLength) return -1
    }
    val longCount = searchLength >>> 3
    val pattern = compilePattern(value)
    for (i <- 0 until longCount) {
      // use the faster available getLong
      val word = getLong(offset)
      val result = applyPattern(word, pattern)
      if (result != 0) return offset + getIndex(result)
      offset += java.lang.Long.BYTES
    }
    -1
  }

  /**
   * This is using a SWAR (SIMD Within A Register) batch read technique to minimize bound-checks and improve memory
   * usage while searching for {@code value}.
   */
  def indexOf(value: Byte, from: Int): Int = {
    val fromIndex = Math.max(0, from)
    val toIndex = byteString.length
    if (fromIndex >= toIndex) return -1
    val searchLength = toIndex - fromIndex
    var offset = fromIndex
    val byteCount = searchLength & 7
    if (byteCount > 0) {
      val index = unrolledFirstIndexOf(fromIndex, byteCount, value)
      if (index != -1) return index
      offset += byteCount
      if (offset == toIndex) return -1
    }
    val longCount = searchLength >>> 3
    val pattern = compilePattern(value)
    for (i <- 0 until longCount) {
      val word = getLong(offset)
      val result = applyPattern(word, pattern)
      if (result != 0) return offset + getIndex(result)
      offset += java.lang.Long.BYTES
    }
    -1
  }

  private def unrolledFirstIndexOf(fromIndex: Int, byteCount: Int, value: Byte): Int = {
    if (byteString.apply(fromIndex) == value) fromIndex
    else if (byteCount == 1) -1
    else if (byteString.apply(fromIndex + 1) == value) fromIndex + 1
    else if (byteCount == 2) -1
    else if (byteString.apply(fromIndex + 2) == value) fromIndex + 2
    else if (byteCount == 3) -1
    else if (byteString.apply(fromIndex + 3) == value) fromIndex + 3
    else if (byteCount == 4) -1
    else if (byteString.apply(fromIndex + 4) == value) fromIndex + 4
    else if (byteCount == 5) -1
    else if (byteString.apply(fromIndex + 5) == value) fromIndex + 5
    else if (byteCount == 6) -1
    else if (byteString.apply(fromIndex + 6) == value) fromIndex + 6
    else -1
  }
}

class ByteBufTest extends AnyWordSpec with Matchers {
  "Netty ByteBuf" should {
    "return getLong" in {
      val buf = io.netty.buffer.Unpooled.buffer(8)
      buf.writeBytes(Array[Byte](0, 0, 0, 0, 7, 91, -51, 21))
      buf.writeBytes(Array[Byte](0, 0, 0, 0, 7, 91, -51, 21))
      buf.getLong(0) shouldEqual 123456789L
      buf.getLong(1) shouldEqual 31604937984L
      buf.getLong(8) shouldEqual 123456789L
    }
    "return indexOf" in {
      val buf = io.netty.buffer.Unpooled.buffer(8)
      buf.writeBytes(Array[Byte](0, 0, 0, 0, 7, 91, -51, 21))
      buf.writeBytes(Array[Byte](0, 0, 0, 0, 7, 91, -51, 21))
      buf.indexOf(0, buf.capacity(), 21.toByte) shouldEqual 7
      buf.indexOf(0, buf.capacity(), 99.toByte) shouldEqual -1
      buf.indexOf(7, buf.capacity(), 21.toByte) shouldEqual 7
      buf.indexOf(8, buf.capacity(), 21.toByte) shouldEqual 15
      buf.indexOf(8, buf.capacity(), 99.toByte) shouldEqual -1
    }
  }
  "ByteStringWrapper" should {
    "return getLong" in {
      val byteString1 = ByteString.fromArrayUnsafe(Array[Byte](0, 0, 0, 0, 7, 91, -51, 21))
      val byteStringConcat = byteString1 ++ byteString1
      val buf = new ByteStringWrapper(byteStringConcat)
      buf.getLong(0) shouldEqual 123456789L
      buf.getLong(1) shouldEqual 31604937984L
      buf.getLong(8) shouldEqual 123456789L
    }
    "return indexOf" in {
      val byteString1 = ByteString.fromArrayUnsafe(Array[Byte](0, 0, 0, 0, 7, 91, -51, 21))
      val byteStringConcat = byteString1 ++ byteString1
      val buf = new ByteStringWrapper(byteStringConcat)
      buf.indexOf(21.toByte) shouldEqual 7
      buf.indexOf(99.toByte) shouldEqual -1

      buf.indexOf(21.toByte, 0) shouldEqual 7
      buf.indexOf(99.toByte, 0) shouldEqual -1
      buf.indexOf(21.toByte, 7) shouldEqual 7
      buf.indexOf(21.toByte, 8) shouldEqual 15
      buf.indexOf(99.toByte, 8) shouldEqual -1

    }
  }
}
