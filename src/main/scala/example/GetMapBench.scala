package example

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.infra.Blackhole

class GetMapBench extends CommonParams {

  val cell = new ActorCell()

  @Benchmark
  def unsafe(blackhole: Blackhole): Unit = {
    blackhole.consume(cell.getMapUnsafe)
  }

  @Benchmark
  def atomic(blackhole: Blackhole): Unit = {
    blackhole.consume(cell.getMapAtomic)
  }
}
