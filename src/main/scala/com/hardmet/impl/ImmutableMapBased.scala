package com.hardmet.impl

import java.util.concurrent.atomic.AtomicReference
import java.util.function.UnaryOperator

import com.hardmet.api.{Cache, Consistency}

class ImmutableMapBased extends Cache[Int, String] with Consistency {

  private val map = new AtomicReference[Map[Int, String]](Map.empty[Int, String])

  override def put(k: Int, v: String): (Int, String) = {
    map.updateAndGet(new UnaryOperator[Map[Int, String]] {
      override def apply(t: Map[Int, String]): Map[Int, String] = t + (k -> v)
    })
    k -> v
  }

  override def get(k: Int): String = map.get()(k)

  override def getOrElseUpdate(k: Int, op: => String): String = {
    if (!contains(k)) {
      map.synchronized {
        if (!contains(k)) {
          put(k, op)
        }
      }
    }
    map.get()(k)
  }

  override def contains(k: Int): Boolean = map.get().contains(k)

}
