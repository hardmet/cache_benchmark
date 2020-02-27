package com.hardmet.impl

import com.hardmet.api.Cache

class ConcurrentHashMapBased extends Cache[Int, String] {
  //  private val map = collection.mutable.Map[String, CachedValue]()
  //  private val map = new ConcurrentHashMap[String, CachedValue]()

  override def put(k: Int, v: String): (Int, String) = ???

  override def get(k: Int): String = ???

  override def getOrElseUpdate(k: Int, op: => String): String = ???

  override def contains(k: Int): Boolean = ???
}
