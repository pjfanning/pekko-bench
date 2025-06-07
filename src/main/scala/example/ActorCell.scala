package example

import org.apache.pekko.util.Unsafe

import java.util.concurrent.atomic.AtomicLongFieldUpdater

class ActorCell {

  @volatile private var _nextName = 0L
  private val _nextNameAtomic = new java.util.concurrent.atomic.AtomicLong()
  private val nextNameUpdater = AtomicLongFieldUpdater.newUpdater(classOf[ActorCell], "_nextName")

  def nextNameUnsafe: Long = {
    Unsafe.instance.getAndAddLong(this, AbstractActorCell.nextNameOffset, 1)
  }

  def nextNameHandle: Long = {
    AbstractActorCell.nextNameHandle.getAndAdd(this, 1).asInstanceOf[Long]
  }

  def nextNameAtomic: Long = {
    _nextNameAtomic.getAndIncrement()
  }

  def nextNameAtomicUpdater: Long = {
    nextNameUpdater.getAndIncrement(this)
  }

  def printNextName(): Unit = {
    println(s"Next Name: ${_nextName}")
  }
}
