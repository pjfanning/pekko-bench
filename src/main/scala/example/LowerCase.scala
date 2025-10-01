package example

object LowerCase {
  // copied from pekko-http HttpHeaderParser
  def toLowerCase(c: Char): Char =
    if (c >= 'A' && c <= 'Z') (c + 0x20 /* - 'A' + 'a' */ ).toChar else c

  val majorVersion: Int = Runtime.version().feature()

  lazy val versionedToLower = if (majorVersion >= 21)
    Character.toLowerCase _
  else
    toLowerCase _
}
