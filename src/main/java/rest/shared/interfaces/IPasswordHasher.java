package rest.shared.interfaces;

import org.mindrot.jbcrypt.BCrypt;

public interface IPasswordHasher {
    default String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    default Boolean checkPwd(String password, String hashedPwd) {
        return BCrypt.checkpw(password, hashedPwd);
    }

    String getPasswordHash(String password);

    Boolean getPasswordCheckStatus(String password, String hashedPwd);
}
