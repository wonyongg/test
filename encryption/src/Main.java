import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws Exception {

        String text = "안녕하세요.";

//        sha256Test(text);
//        rsaEncryptDecryptTest(text);
//        rsaTest(text);
//        swiftTest();
        aesTest(text);
    }

    public static void sha256Test(String text) throws NoSuchAlgorithmException {
        Sha256ToBase64 sha256ToBase64 = new Sha256ToBase64();
        Sha256ToHex sha256ToHex = new Sha256ToHex();

        // SHA-256 Return Base64
        System.out.println(sha256ToBase64.encrypt(text)); //ixGNZ0H3z6Gn7iRtDdo58vAL+f0ge05sf62HoVQ0pRM=
        System.out.println(sha256ToHex.encrypt(text)); //8b118d6741f7cfa1a7ee246d0dda39f2f00bf9fd207b4e6c7fad87a15434a513
    }

    public static void rsaEncryptDecryptTest(String text) {
        String publicKeyByJava   = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlK5ioVvjb1CHE+wl0Od9tekm7tMLB06ApYKuwEVEFQNRRdcVToNnHMf80Hy17LHAv4R83UTf6ZOy3IeLWbjFMp43s3K4SCS1e94T9bojztWLuwrSDIrGMXfKOsnaEdJyAEAqocL+HSZTOGpM20UGKIHzKK+VDwFYsP1d2qoRBhy6bLE/hbFYodc5iIFeB+U8vpHwcSROYXdZ+hceo9oBxMZM0Kv+SqOFIcguVEoIteMH7VjSXR1vtfN5yCDRrLKwj6FOPTO9EXHsgbc1+PwOntSLGxm7AbgS90BvEmHDSXlUt7hA2WHeP7PCsN1cYYpuca+nRAP3jwfVMLSxsziq8wIDAQAB";
        String privateKeyByJava  = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCUrmKhW+NvUIcT7CXQ53216Sbu0wsHToClgq7ARUQVA1FF1xVOg2ccx/zQfLXsscC/hHzdRN/pk7Lch4tZuMUynjezcrhIJLV73hP1uiPO1Yu7CtIMisYxd8o6ydoR0nIAQCqhwv4dJlM4akzbRQYogfMor5UPAViw/V3aqhEGHLpssT+FsVih1zmIgV4H5Ty+kfBxJE5hd1n6Fx6j2gHExkzQq/5Ko4UhyC5USgi14wftWNJdHW+183nIINGssrCPoU49M70RceyBtzX4/A6e1IsbGbsBuBL3QG8SYcNJeVS3uEDZYd4/s8Kw3Vxhim5xr6dEA/ePB9UwtLGzOKrzAgMBAAECggEBAI2REYZ0WLTHpyYYBtXHZRINYq2psumNHL77FGssSKv9Uag/KCFuyoJEBXt6EzrA8Tcp7j488zqTEI4cXVnbraAJuB2cEM1Ybpj3lme1iZzKBdbdcazRge7eEWcyhPnNOBD3B+CoKo8Uy85+oJlzOm7ddOp7bGEyNgGn7XJj1fvLcLogm2DAUmrq3d4VUrOs5QrKIuUScYaFN1w8QbIEXQ+vYuPht3AcxCCex6m82FdIQTsPv53DrcIRda2tqwtcmMKlcDreDMVg3NZkVlpZS3FWGFlPPjQ6wrBpYMoGgO3/R58hxnTydr5VU4dtdfN7jueEUX8ZQOquCYGqYjfO+rECgYEAzi+ti1n9Uz1whzg64238yTWKqxknSMfYaXgnk/PmU4AY0+dW91SDKh6bjzHS5W1QS9o3ZifDXtk/CaEF3x6CrmxjhIX98fsB+8OgKhJk0GGR6UAYWv2y/T2p6h/BUyKEreL6axh9Guw1Vy2qi4QxLA+otLOlw/zFJrvJDfl2M8sCgYEAuJoaVtytkjz5mHfRib9k6gAT/F4bn8KC8tkl7+L5ctKnsZHo1bUhMg9G7Pkq5H+Sq8hMBjwO5dLoPL5oeCx1k/jnG1Vnya/bFZ95+aVatBKhByYLvGGq8OVoDsYNRw4uCO6wLvzyQNa8j6LRBef0pNnMdUjDlRNf3rczbPCokHkCgYAuGMK38PqN7aKG2K9xwVlOssW50vnRb7yWd+KBVXodGPp0BbiPuzlH5Wfp772yxWm6S6MvF5y6S9+oC3QAmhWlYV4udrZ7rx6WZvob7djULbwtZcMMNMQbkfm2+jqCl6kKJ0DNSMBPkJesUe1bG8bSjv2syK06z/xT2I/uq+MdWQKBgQCkPg+zOtJ/axrTKb/Xo98gRwOBJSXExaYGdWdIgJRaMcybs2sKQQaQ6IC9NI+SD2MqzaG+Zk8G8dhIu0xtp3jRN8I/UoRuMLVnDqXSlOkXj/+PQa1tZSRXAnTLuDB2CMqyjdNZaxoK0EOiNEVu0MqcpV9A9oV1LHC+0hbCFXiUWQKBgQChzJf2BPsocR63KBHfDJB2MvgcijyOjZtlQA00W9SdumD4T27imi0IxqZTWronQHVyIT6X8uWpvRTepBTlZTxelFgKEJRaXl9KRRmwTbMLMmyn7ZaG/IUihISP6dIblWguAywll27tdGzxAbMznVxX2j2pA2Obwd3FFu5YiBSvDQ==";

        String encryptNoHash = RsaGenerator.encryptNoHash(text, privateKeyByJava);

        String decryptReturnBase64 = RsaVerifier.decryptReturnBase64(encryptNoHash, publicKeyByJava);
        String decryptNewString = RsaVerifier.decryptReturnNewString(encryptNoHash, publicKeyByJava);

        System.out.println("### decryptReturnBase64 : " + decryptReturnBase64);
        // ### decryptReturnBase64 : 7JWI64WV7ZWY7IS47JqULg==

        System.out.println("### decryptNewString : " + decryptNewString);
        // ### decryptNewString : 안녕하세요.
    }

    public static void rsaTest(String text) throws NoSuchAlgorithmException {

        Sha256ToBase64 sha256ToBase64 = new Sha256ToBase64();
        Sha256ToHex sha256ToHex = new Sha256ToHex();

        // text를 SHA-256으로 해시하여 두가지 방식(Base64, Hex)으로 리턴
        System.out.println("SHA-256 ENCRYPT");
        System.out.println("### 원본 text : " + text);
        System.out.println("### sha256ToHex : " + sha256ToHex.encrypt(text));
        System.out.println("### sha256ToBase64 : " + sha256ToBase64.encrypt(text));

        // 자바에서 만든 공개키와 개인키
        String publicKeyByJava   = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlK5ioVvjb1CHE+wl0Od9tekm7tMLB06ApYKuwEVEFQNRRdcVToNnHMf80Hy17LHAv4R83UTf6ZOy3IeLWbjFMp43s3K4SCS1e94T9bojztWLuwrSDIrGMXfKOsnaEdJyAEAqocL+HSZTOGpM20UGKIHzKK+VDwFYsP1d2qoRBhy6bLE/hbFYodc5iIFeB+U8vpHwcSROYXdZ+hceo9oBxMZM0Kv+SqOFIcguVEoIteMH7VjSXR1vtfN5yCDRrLKwj6FOPTO9EXHsgbc1+PwOntSLGxm7AbgS90BvEmHDSXlUt7hA2WHeP7PCsN1cYYpuca+nRAP3jwfVMLSxsziq8wIDAQAB";
        String privateKeyByJava  = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCUrmKhW+NvUIcT7CXQ53216Sbu0wsHToClgq7ARUQVA1FF1xVOg2ccx/zQfLXsscC/hHzdRN/pk7Lch4tZuMUynjezcrhIJLV73hP1uiPO1Yu7CtIMisYxd8o6ydoR0nIAQCqhwv4dJlM4akzbRQYogfMor5UPAViw/V3aqhEGHLpssT+FsVih1zmIgV4H5Ty+kfBxJE5hd1n6Fx6j2gHExkzQq/5Ko4UhyC5USgi14wftWNJdHW+183nIINGssrCPoU49M70RceyBtzX4/A6e1IsbGbsBuBL3QG8SYcNJeVS3uEDZYd4/s8Kw3Vxhim5xr6dEA/ePB9UwtLGzOKrzAgMBAAECggEBAI2REYZ0WLTHpyYYBtXHZRINYq2psumNHL77FGssSKv9Uag/KCFuyoJEBXt6EzrA8Tcp7j488zqTEI4cXVnbraAJuB2cEM1Ybpj3lme1iZzKBdbdcazRge7eEWcyhPnNOBD3B+CoKo8Uy85+oJlzOm7ddOp7bGEyNgGn7XJj1fvLcLogm2DAUmrq3d4VUrOs5QrKIuUScYaFN1w8QbIEXQ+vYuPht3AcxCCex6m82FdIQTsPv53DrcIRda2tqwtcmMKlcDreDMVg3NZkVlpZS3FWGFlPPjQ6wrBpYMoGgO3/R58hxnTydr5VU4dtdfN7jueEUX8ZQOquCYGqYjfO+rECgYEAzi+ti1n9Uz1whzg64238yTWKqxknSMfYaXgnk/PmU4AY0+dW91SDKh6bjzHS5W1QS9o3ZifDXtk/CaEF3x6CrmxjhIX98fsB+8OgKhJk0GGR6UAYWv2y/T2p6h/BUyKEreL6axh9Guw1Vy2qi4QxLA+otLOlw/zFJrvJDfl2M8sCgYEAuJoaVtytkjz5mHfRib9k6gAT/F4bn8KC8tkl7+L5ctKnsZHo1bUhMg9G7Pkq5H+Sq8hMBjwO5dLoPL5oeCx1k/jnG1Vnya/bFZ95+aVatBKhByYLvGGq8OVoDsYNRw4uCO6wLvzyQNa8j6LRBef0pNnMdUjDlRNf3rczbPCokHkCgYAuGMK38PqN7aKG2K9xwVlOssW50vnRb7yWd+KBVXodGPp0BbiPuzlH5Wfp772yxWm6S6MvF5y6S9+oC3QAmhWlYV4udrZ7rx6WZvob7djULbwtZcMMNMQbkfm2+jqCl6kKJ0DNSMBPkJesUe1bG8bSjv2syK06z/xT2I/uq+MdWQKBgQCkPg+zOtJ/axrTKb/Xo98gRwOBJSXExaYGdWdIgJRaMcybs2sKQQaQ6IC9NI+SD2MqzaG+Zk8G8dhIu0xtp3jRN8I/UoRuMLVnDqXSlOkXj/+PQa1tZSRXAnTLuDB2CMqyjdNZaxoK0EOiNEVu0MqcpV9A9oV1LHC+0hbCFXiUWQKBgQChzJf2BPsocR63KBHfDJB2MvgcijyOjZtlQA00W9SdumD4T27imi0IxqZTWronQHVyIT6X8uWpvRTepBTlZTxelFgKEJRaXl9KRRmwTbMLMmyn7ZaG/IUihISP6dIblWguAywll27tdGzxAbMznVxX2j2pA2Obwd3FFu5YiBSvDQ==";

        String encryptNoHash = RsaGenerator.encryptNoHash(text, privateKeyByJava);
        String encryptedTextWithHex = RsaGenerator.encryptWithHexHash(text, privateKeyByJava);
        String encryptedTextWithBase64 = RsaGenerator.encryptWithBase64(text, privateKeyByJava);

        String decryptedNoHash = RsaVerifier.decryptReturnNewString(encryptNoHash, publicKeyByJava);
        String decryptedTextWithHex = RsaVerifier.decryptReturnNewString(encryptedTextWithHex, publicKeyByJava);
        String decryptedTextWithBase64 = RsaVerifier.decryptReturnNewString(encryptedTextWithBase64, publicKeyByJava);
        System.out.println("RSA DECRYPT");
        System.out.println("### decryptedNoHash : " + decryptedNoHash);
        System.out.println("### decryptedTextWithHex : " + decryptedTextWithHex);
        System.out.println("### decryptedTextWithBase64 : " + decryptedTextWithBase64);
    }

    public static void aesTest(String text) throws Exception {
        SecretKey secretKey = AesUtil.generator(256);
//        String secretKeyStr = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        String secretKeyStr = "o7H+tnx0rvExGwSviWMRhcMjO1RzUVU5+T4GbJUyFO0=";
        String encryptedStr = AesUtil.encrypt(text, secretKeyStr);

        System.out.println("### secretKeyStr : " + secretKeyStr);
        System.out.println("### encryptedStr : " + encryptedStr);

        String decryptedStr = AesUtil.decrypt(encryptedStr, secretKeyStr);

        System.out.println("### decryptedStr : " + decryptedStr);

        System.out.println("##################################################");

        AesUtil.EncryptedData encryptedData = AesUtil.encryptWithRandomIv(text, secretKeyStr);

        System.out.println("### encryptedStrWithRandomIv : " + encryptedData.getEncryptedDataStr());

        String decryptedStrWithRandomIv = AesUtil.decryptWithRandomIv(encryptedData.getEncryptedDataStr(), secretKeyStr,
                                                       encryptedData.getInitVector());

        System.out.println("### decryptedStrWithRandomIv : " + decryptedStrWithRandomIv);

        System.out.println("##################################################");
    }
}