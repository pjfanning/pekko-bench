package example

object LowerCase {
  // copied from pekko-http HttpHeaderParser
  def toLowerCase(c: Char): Char =
    if (c >= 'A' && c <= 'Z') (c + 0x20 /* - 'A' + 'a' */ ).toChar else c
}
