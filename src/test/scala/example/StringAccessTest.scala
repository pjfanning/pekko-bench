package example

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StringAccessTest extends AnyWordSpec with Matchers {

  "StringAccess" should {
    "support getBytes" in {
      val str = "testString"
      StringAccess.getBytes(str) shouldEqual str.getBytes(java.nio.charset.StandardCharsets.UTF_8)
    }
  }
}
