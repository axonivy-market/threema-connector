package apitool;

import static ch.threema.apitool.CryptTool.*;
import ch.threema.apitool.results.EncryptResult;

public class MsgHandler {

  private static String tempId = "9UVR49KD"; // Threema ID Fabian Heuberger

  /**
   * Function to encrypt TextMessage
   * @param msg Message to encrypt
   * @param privateKey Private key of the sender
   * @param publicKey Public key of the recipient
   * @return
   */
  public static String encryptMessage(String msg, byte[] privateKey, byte[] publicKey) {
    EncryptResult result = encryptTextMessage(msg, privateKey, publicKey);
    return result.getResult().toString();
  }

  /**
   * Function to generate keyPair
   * @return private key and public key wrappend in byte[]
   */
  public static byte[][] genKeyPair() {
    byte[] privByte = new byte[32];
    byte[] pubByte = new byte[32];
    generateKeyPair(privByte, pubByte);
    return new byte[][] {privByte, pubByte};
  }
}
