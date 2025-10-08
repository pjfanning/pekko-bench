package example

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.infra.Blackhole

private object EndsWithBench {
  implicit class StringOps(val s: String) extends AnyVal {
    def endsWithChar(c: Char): Boolean = s.nonEmpty && s.charAt(s.length - 1) == c
  }
}

class EndsWithBench extends CommonParams {

  private final val testString =
    "This is a simple test string to check whether it ends with something"

  @Benchmark
  def endsWithString(blackhole: Blackhole): Unit = {
    blackhole.consume(testString.endsWith("g"))
  }

  @Benchmark
  def endsWithChar(blackhole: Blackhole): Unit = {
    import EndsWithBench._
    blackhole.consume(testString.endsWithChar('g'))
  }

}
