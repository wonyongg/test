import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256ToHex {

    /**
     * 입력 받은 문자열을 SHA-256으로 해싱하여 해시 값을 반환함
     * @param text
     * @return 해시값
     * @throws NoSuchAlgorithmException
     */
    public String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }

    /**
     * 바이트 배열을 16진수 문자열로 반환함
     * @param bytes
     * @return 16진수 문자열
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}