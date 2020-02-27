package com.hardmet.api

trait Consistency {

  def consistentWhileUpdating[K, V](cache: Cache[K, V], key: K, value: V, updateOp: (V, V) => V): Unit = {
    cache.synchronized {
      require(!cache.contains(key))
      cache.getOrElseUpdate(key, updateOp(cache.get(key), value))
      require(cache.contains(key))
    }
  }

}
