package com.hardmet.impl

import com.hardmet.api.Cache

class TrieMapBased extends Cache[Int, String] {

  override def put(k: Int, v: String): (Int, String) = ???

  override def get(k: Int): String = ???

  override def getOrElseUpdate(k: Int, op: => String): String = ???

  override def contains(k: Int): Boolean = ???
}
