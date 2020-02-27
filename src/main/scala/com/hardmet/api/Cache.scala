package com.hardmet.api

trait Cache[K, V] {

  def put(k: K, v: V): (K, V)

  def get(k: K): V

  def getOrElseUpdate(k: K, op: => V): V

  def contains(k: K): Boolean

}
