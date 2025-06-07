package example

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.infra.Blackhole

class GetAndAddBench extends CommonParams {

  val cell = new ActorCell()

  @Benchmark
  def unsafe(blackhole: Blackhole): Unit = {
    blackhole.consume(cell.nextNameUnsafe)
  }

  @Benchmark
  def handle(blackhole: Blackhole): Unit = {
    blackhole.consume(cell.nextNameHandle)
  }

  @Benchmark
  def atomic(blackhole: Blackhole): Unit = {
    blackhole.consume(cell.nextNameAtomic)
  }

}
