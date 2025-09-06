package example

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class UnsafeTest extends AnyWordSpec with Matchers {
  "Unsafe" should {
    "return the instance" in {
      UnsafeUtil.getUnsafeInstance should not be null
    }
  }
}
