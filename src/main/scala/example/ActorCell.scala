package example

import org.apache.pekko.util.Unsafe

class ActorCell {

  @volatile private var _nextName = 0L
  private val _nextNameAtomic = new java.util.concurrent.atomic.AtomicLong()

  def nextNameUnsafe: Long = {
    Unsafe.instance.getAndAddLong(this, AbstractActorCell.nextNameOffset, 1)
  }

  def nextNameHandle: Long = {
    AbstractActorCell.nextNameHandle.getAndAdd(this, 1).asInstanceOf[Long]
  }

  def nextNameAtomic: Long = {
    _nextNameAtomic.getAndIncrement()
  }

  def printNextName(): Unit = {
    println(s"Next Name: ${_nextName}")
  }
}
