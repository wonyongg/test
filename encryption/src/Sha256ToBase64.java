import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Sha256ToBase64 {

    /**
     * 입력 받은 문자열을 SHA-256으로 해싱하여 해시 값을 반환함
     * @param text
     * @return 해시값
     * @throws NoSuchAlgorithmException
     */
    public String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        return Base64.getEncoder().encodeToString(md.digest());
    }
}
