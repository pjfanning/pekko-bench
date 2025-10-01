package example

object LowerCase {
  // copied from pekko-http HttpHeaderParser
  final def toLowerCase(c: Char): Char =
    if (c >= 'A' && c <= 'Z') (c + 0x20 /* - 'A' + 'a' */ ).toChar else c

  private final val Byte_A = 'A'.toByte
  private final val Byte_Z = 'Z'.toByte

  final def toLowerCaseByte(b: Byte): Byte =
    if (b >= Byte_A && b <= Byte_Z) (b + 0x20).toByte /* - 'A' + 'a' */ else b

  final val majorVersion: Int = Runtime.version().feature()

  final val versionedToLower = if (majorVersion >= 21)
    Character.toLowerCase _
  else
    toLowerCase _
}
