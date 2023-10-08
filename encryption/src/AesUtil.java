import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AesUtil {

    /**
     * 128비트 AES 키
     * 128비트 AES 키는 충분한 보안 수준을 제공하면서도 빠른 암호화 및 복호화 속도를 가집니다.
     * 많은 애플리케이션에서 기본적으로 사용되는 키 크기입니다.
     * 대부분의 보안 요구사항을 충족시킬 수 있으며, 일반적으로 권장되는 기본 옵션입니다.
     * 192비트 AES 키
     * 192비트 AES 키는 추가적인 보안 강도를 제공합니다.
     * 일부 규정 준수 요구사항이나 민감한 데이터 처리에 사용될 수 있습니다.
     * 암호화 및 복호화 속도가 128비트보다 느릴 수 있으므로 성능에 조금 영향을 줄 수 있습니다.
     * 256비트 AES 키
     * 256비트 AES 키는 가장 높은 보안 강도를 제공합니다.
     * 매우 민감한 데이터나 고도로 보안이 필요한 시스템에서 사용됩니다.
     * 암호화 및 복호화 속도가 상대적으로 느리고, 메모리 사용량이 증가할 수 있습니다.
     * @param keySize
     * @return
     */
    public static SecretKey generator(int keySize) throws NoSuchAlgorithmException {

        // "AES"라고만 지정한 경우, Java에서는 Cipher 클래스의 기본값으로 CBC (Cipher Block Chaining) 모드와 PKCS5Padding 패딩이 사용됨
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keySize); // 키 크기 설정 (128, 192, 256 등)
        return keyGenerator.generateKey();
    }

    public static String encrypt(String data, String secretKeyStr) {

        try {
            byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyStr);

            byte[] initVector = new byte[16];
            System.arraycopy(secretKeyBytes, 0, initVector, 0, initVector.length);

            IvParameterSpec iv = new IvParameterSpec(initVector);

            SecretKeySpec keySpec = new SecretKeySpec(secretKeyBytes, "AES");


            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);  // 필요에 따라 예외 처리를 합니다
        }
    }

    public static EncryptedData encryptWithRandomIv(String data, String secretKeyStr) {
        try {
            byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyStr);

            // [2-2] 16바이트의 랜덤한 초기화 벡터 값을 생성
            SecureRandom random = new SecureRandom();
            byte[] initVector = new byte[16];
            random.nextBytes(initVector);

            IvParameterSpec iv = new IvParameterSpec(initVector);

            SecretKeySpec keySpec = new SecretKeySpec(secretKeyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            String encryptedDataStr = Base64.getEncoder().encodeToString(encryptedBytes);

            return new EncryptedData(encryptedDataStr, initVector);
        } catch (Exception e) {
            throw new RuntimeException(e); // 필요에 따라 예외 처리를 합니다
        }
    }

    public static String decrypt(String encryptedData, String secretKeyStr) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyStr);

        byte[] initVector = new byte[16];
        System.arraycopy(secretKeyBytes, 0, initVector, 0, initVector.length);

        IvParameterSpec iv = new IvParameterSpec(initVector);

        SecretKeySpec keySpec = new SecretKeySpec(secretKeyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static String decryptWithRandomIv(String encryptedData, String secretKeyStr, byte[] initVector) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyStr);

        IvParameterSpec iv = new IvParameterSpec(initVector);

        SecretKeySpec keySpec = new SecretKeySpec(secretKeyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static class EncryptedData {
        private String encryptedDataStr;
        private byte[] initVector;

        public EncryptedData(String encryptedDataStr, byte[] initVector) {
            this.encryptedDataStr = encryptedDataStr;
            this.initVector = initVector;
        }

        public String getEncryptedDataStr() {
            return encryptedDataStr;
        }

        public byte[] getInitVector() {
            return initVector;
        }
    }
}