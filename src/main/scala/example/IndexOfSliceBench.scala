package example

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.infra.Blackhole

import org.apache.pekko
import pekko.util.ByteString

class IndexOfSliceBench extends CommonParams {

  val byteStringLong = ByteString.fromString("abcdefghijklmnopqrstuvwxyz")
  val byteStrings = byteStringLong ++ byteStringLong
  val slice0 = ByteString.fromString("xyz")
  val bytes0 = slice0.toArray
  val slice1 = ByteString.fromString("xyzabc")
  val bytes1 = slice1.toArray

  @Benchmark
  def indexOfSliceByteStringCompact(blackhole: Blackhole): Unit = {
    blackhole.consume(byteStringLong.indexOfSlice(slice0))
  }

  @Benchmark
  def indexOfSliceBytes0Compact(blackhole: Blackhole): Unit = {
    blackhole.consume(ByteStringUtil.indexOfSlice0(byteStringLong, bytes0, 0))
  }

  @Benchmark
  def indexOfSliceBytes1Compact(blackhole: Blackhole): Unit = {
    blackhole.consume(ByteStringUtil.indexOfSlice1(byteStringLong, bytes0, 0))
  }

  @Benchmark
  def indexOfSliceByteStringConcat(blackhole: Blackhole): Unit = {
    blackhole.consume(byteStrings.indexOfSlice(slice1))
  }

  @Benchmark
  def indexOfSliceBytes0Concat(blackhole: Blackhole): Unit = {
    blackhole.consume(ByteStringUtil.indexOfSlice0(byteStrings, bytes1, 0))
  }

  @Benchmark
  def indexOfSliceBytes1Concat(blackhole: Blackhole): Unit = {
    blackhole.consume(ByteStringUtil.indexOfSlice1(byteStrings, bytes1, 0))
  }
}
