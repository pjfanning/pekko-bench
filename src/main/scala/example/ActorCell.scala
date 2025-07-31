package example

import org.apache.pekko.util.Unsafe

import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicLongFieldUpdater

class ActorCell {

  @volatile private var _nextName = 0L
  private val _nextNameAtomic = new java.util.concurrent.atomic.AtomicLong()
  private val nextNameUpdater = AtomicLongFieldUpdater.newUpdater(classOf[ActorCell], "_nextName")

  @volatile private var _functionRefs = Map.empty[String, Any]
  private val _functionRefsAtomic = new java.util.concurrent.atomic.AtomicReference(Map.empty[String, Any])

  def nextNameUnsafe: Long = {
    Unsafe.instance.getAndAddLong(this, AbstractActorCell.nextNameOffset, 1)
  }

  def nextNameHandle: Long = {
    AbstractActorCell.nextNameHandle.getAndAdd(this, 1L)
  }

  def nextNameAtomic: Long = {
    _nextNameAtomic.getAndIncrement()
  }

  def nextNameAtomicUpdater: Long = {
    nextNameUpdater.getAndIncrement(this)
  }

  def getMapUnsafe: Map[String, Any] = {
    Unsafe.instance.getObjectVolatile(this, AbstractActorCell.functionRefOffset)
      .asInstanceOf[Map[String, Any]]
  }

  def getMapAtomic: Map[String, Any] = {
    _functionRefsAtomic.get()
  }

  def print(): Unit = {
    println(s"Next Name: ${_nextName}")
    println(s"Function Refs: ${_functionRefs}")
  }

  private def genMap(): Map[String, Any] =
    Map("key" -> ThreadLocalRandom.current().nextInt())
}
