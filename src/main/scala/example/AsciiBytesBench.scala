package example

import org.apache.pekko.util.Unsafe
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.infra.Blackhole

import java.nio.charset.StandardCharsets

class AsciiBytesBench extends CommonParams {

  val stringSize = 1024
  val array = new Array[Byte](2048)
  val str = {
    val sb = new StringBuilder(stringSize)
    (0 until stringSize).map { i =>
      sb.append('a')
    }
    sb.toString()
  }

  @Benchmark
  def unsafe(blackhole: Blackhole): Unit = {

    blackhole.consume {
      val a = new Array[Byte](1024)
      Unsafe.copyUSAsciiStrToBytes(str, a)
    }
  }

  @Benchmark
  def getBytes(blackhole: Blackhole): Unit = {
    blackhole.consume(str.getBytes(StandardCharsets.US_ASCII))
  }

}
