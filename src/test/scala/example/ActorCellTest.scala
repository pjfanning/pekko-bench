package example

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ActorCellTest extends AnyWordSpec with Matchers {
  "ActorCell" should {
    "return the next name using unsafe method" in {
      val cell = new ActorCell()
      val nextName = cell.nextNameUnsafe
      nextName shouldEqual 0L
    }

    "return the next name using handle method" in {
      val cell = new ActorCell()
      val nextName = cell.nextNameHandle
      nextName shouldEqual 0L
    }

    "return the next name using atomic method" in {
      val cell = new ActorCell()
      val nextName = cell.nextNameAtomic
      nextName shouldEqual 0L
    }

    "return the next name using atomicUpdater method" in {
      val cell = new ActorCell()
      val nextName = cell.nextNameAtomicUpdater
      nextName shouldEqual 0L
    }
  }
}
