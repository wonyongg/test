import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaVerifier {

    public static String decryptReturnNewString(String encryptedValue, String publicKeyStr) {
        try {

            // Base64로 인코딩된 공개 키를 PublicKey 객체로 변환합니다.
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            // 암호화된 해시 값을 복호화합니다.
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            byte[] encryptedValueBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedValueBytes = cipher.doFinal(encryptedValueBytes);

            return new String(decryptedValueBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);  // 필요에 따라 예외 처리를 합니다
        }
    }

    public static String decryptReturnBase64(String encryptedValue, String publicKeyStr) {
        try {

            // Base64로 인코딩된 공개 키를 PublicKey 객체로 변환합니다.
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            // 암호화된 해시 값을 복호화합니다.
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            byte[] encryptedValueBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedValueBytes = cipher.doFinal(encryptedValueBytes);

            return Base64.getEncoder().encodeToString(decryptedValueBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);  // 필요에 따라 예외 처리를 합니다
        }
    }
}
