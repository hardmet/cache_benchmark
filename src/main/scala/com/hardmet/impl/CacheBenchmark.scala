package com.hardmet.impl

import java.util.concurrent.TimeUnit

import com.hardmet.api.Cache
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.collection.parallel.ParSeq
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Array(Mode.All))
@Warmup(iterations = 10)
class CacheBenchmark {

  val immutableMapBases = new ImmutableMapBased
  val mutableMapBases = new MutableMapBased
  val trieMapBased = new TrieMapBased
  val concurrentHashMapBased = new ConcurrentHashMapBased

  @Benchmark
  def testImmutableMap(blackhole: Blackhole): Unit = {
    testGetOrElseUpdate(immutableMapBases, blackhole)
  }

  @Benchmark
  def testMutableMapBased(blackhole: Blackhole): Unit = {
    testGetOrElseUpdate(mutableMapBases, blackhole)
  }

  @Benchmark
  def testTrieMapBased(blackhole: Blackhole): Unit = {
    testGetOrElseUpdate(trieMapBased, blackhole)
  }

  @Benchmark
  def testConcurrentHashMapBased(blackhole: Blackhole): Unit = {
    testGetOrElseUpdate(concurrentHashMapBased, blackhole)
  }


  import ExecutionContext.Implicits._

  def testGetOrElseUpdate(cache: Cache[Int, String], blackhole: Blackhole): Unit = {
    val n = 1000
    val tasks = (1 to n * 1000).par.map {
      i =>
        Future {
          val value = i % n
          blackhole.consume(cache.getOrElseUpdate(value, s"select $value from table"))
        }
    }
    await(tasks)
  }

  def await(ts: ParSeq[Future[_]]): Boolean = {
    ts.filter(t => !t.isCompleted).forall {
      f =>
        Await.result(f, 10.seconds)
        true
    }
  }
}
