package example

import org.apache.pekko.util.ByteString

import scala.annotation.tailrec

object ByteStringUtil {
  final def startsWith(byteString: ByteString, bytes: Array[Byte]): Boolean = {
    if (byteString.length < bytes.length) false
    else {
      var i = 0
      while (i < bytes.length) {
        if (byteString(i) != bytes(i)) return false
        i += 1
      }
      true
    }
  }

  final def indexOfSlice0(byteString: ByteString, bytes: Array[Byte], from: Int): Int = {
    // this is only called if the first byte matches, so we can skip that check
    def check(startPos: Int): Boolean = {
      var i = startPos + 1
      var j = 1
      while (j < bytes.length) {
        if (byteString(i) != bytes(j)) return false
        i += 1
        j += 1
      }
      j == bytes.length
    }
    @tailrec def rec(from: Int): Int = {
      val startPos = byteString.indexOf(bytes.head, from, byteString.length - bytes.length + 1)
      if (startPos == -1) -1
      else if (check(startPos)) startPos
      else rec(startPos + 1)
    }
    if (bytes.isEmpty) 0 else rec(math.max(0, from))
  }

  final def indexOfSlice1(byteString: ByteString, bytes: Array[Byte], from: Int): Int = {
    if (bytes.isEmpty) 0
    else {
      val startFrom = math.max(0, from)
      val indexPos = byteString.indexOf(bytes.head, startFrom, byteString.length - bytes.length + 1)
      if (indexPos == -1) -1
      else {
        val newByteString = byteString.drop(indexPos + 1)
        if (startsWith(newByteString, bytes.tail)) indexPos
        else {
          val nextIndex = indexOfSlice1(newByteString, bytes, 1)
          if (nextIndex == -1) -1 else indexPos + 1 + nextIndex
        }
      }
    }
  }
}
