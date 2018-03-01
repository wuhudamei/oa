package cn.damei.shiro;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;
public class PasswordUtil {
    private static final int SALT_SIZE = 8;
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_ITERATIONS = 1024;
    public static String entryptUserPassword(String password, String salt) {
        return hashPassword(password, salt);
    }
    public static String generateSalt() {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        return Encodes.encodeHex(salt);
    }
    public static String hashPassword(String plainPassword, String salt) {
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_ITERATIONS);
        return Encodes.encodeHex(hashPassword);
    }
//    public static boolean isPasswordCorrect(String loginPassword, User user) {
//        return PasswordUtil.hashPassword(loginPassword, user.getSalt()).equals(user.getPassWord());
//    }
}
