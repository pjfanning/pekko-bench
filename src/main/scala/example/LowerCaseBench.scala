package example

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.infra.Blackhole

class LowerCaseBench extends CommonParams {

  @Benchmark
  def versioned(blackhole: Blackhole): Unit = {
    blackhole.consume(LowerCase.versionedToLower('A'))
  }

  @Benchmark
  def versionedNoAction(blackhole: Blackhole): Unit = {
    blackhole.consume(LowerCase.versionedToLower('a'))
  }

  @Benchmark
  def pekkoHttp(blackhole: Blackhole): Unit = {
    blackhole.consume(LowerCase.toLowerCase('A'))
  }

  @Benchmark
  def pekkoHttpNoAction(blackhole: Blackhole): Unit = {
    blackhole.consume(LowerCase.toLowerCase('a'))
  }

  @Benchmark
  def javaLang(blackhole: Blackhole): Unit = {
    blackhole.consume(Character.toLowerCase('A'))
  }

  @Benchmark
  def javaLangNoAction(blackhole: Blackhole): Unit = {
    blackhole.consume(Character.toLowerCase('a'))
  }

  @Benchmark
  def parboiled(blackhole: Blackhole): Unit = {
    blackhole.consume(org.parboiled2.CharUtils.toLowerCase('A'))
  }

  @Benchmark
  def parboiledNoAction(blackhole: Blackhole): Unit = {
    blackhole.consume(org.parboiled2.CharUtils.toLowerCase('a'))
  }
}
