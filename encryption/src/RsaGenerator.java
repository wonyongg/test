import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RsaGenerator {
    public static String encryptWithHexHash(String data, String privateKeyStr) {
        try {
            // Base64로 인코딩된 개인 키를 PrivateKey 객체로 변환합니다.
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // JSON 데이터로부터 SHA-256 해시 값을 생성합니다.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedData = md.digest(data.getBytes(StandardCharsets.UTF_8));
            String sha256hex = bytesToHex(hashedData);

            // RSA 개인 키를 사용하여 해시 값을 암호화합니다.
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] encryptedHashValue = cipher.doFinal(sha256hex.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedHashValue);
        } catch (Exception e) {
            throw new RuntimeException(e);  // 필요에 따라 예외 처리를 합니다
        }
    }

    public static String encryptWithBase64(String data, String privateKeyStr) {
        try {
            // Base64로 인코딩된 개인 키를 PrivateKey 객체로 변환합니다.
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // JSON 데이터로부터 SHA-256 해시 값을 생성합니다.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedData = md.digest(data.getBytes(StandardCharsets.UTF_8));
            String sha256Base64 = Base64.getEncoder().encodeToString(hashedData);

            // RSA 개인 키를 사용하여 해시 값을 암호화합니다.
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] encryptedHashValue = cipher.doFinal(sha256Base64.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedHashValue);
        } catch (Exception e) {
            throw new RuntimeException(e);  // 필요에 따라 예외 처리를 합니다
        }
    }

    public static String encryptWithNormalHash(String data, String privateKeyStr) {
        try {
            // Base64로 인코딩된 개인 키를 PrivateKey 객체로 변환합니다.
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // JSON 데이터로부터 SHA-256 해시 값을 생성합니다.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedData = md.digest(data.getBytes(StandardCharsets.UTF_8));

            // RSA 개인 키를 사용하여 해시 값을 암호화합니다.
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] encryptedHashValue = cipher.doFinal(hashedData);
            return Base64.getEncoder().encodeToString(encryptedHashValue);
        } catch (Exception e) {
            throw new RuntimeException(e);  // 필요에 따라 예외 처리를 합니다
        }
    }

    public static String encryptNoHash(String data, String privateKeyStr) {
        try {
            // Base64로 인코딩된 개인 키를 PrivateKey 객체로 변환합니다.
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // RSA 개인 키를 사용하여 해시 값을 암호화합니다.
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);  // 필요에 따라 예외 처리를 합니다
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
