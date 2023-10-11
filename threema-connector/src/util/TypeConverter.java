package util;

import javax.xml.bind.DatatypeConverter;

public class TypeConverter {
  public static String encode(byte[] data) {
    return DatatypeConverter.printHexBinary(data);
  }

  public static byte[] decode(String data) {
    return DatatypeConverter.parseHexBinary(data);
  }
}
