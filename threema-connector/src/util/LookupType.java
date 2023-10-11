package util;


public enum LookupType {
  PHONE,
  EMAIL,
  INVALID;

  @Override
  public String toString() {
    return this.name().toLowerCase();
  }

  public static LookupType getByString(String id) {
    return switch(id) {
      case "phone" -> LookupType.PHONE;
      case "email" -> LookupType.EMAIL;
      default -> LookupType.INVALID;
    };
  }
}
