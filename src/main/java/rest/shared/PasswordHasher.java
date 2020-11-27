package rest.shared;

import rest.shared.interfaces.IPasswordHasher;

public class PasswordHasher implements IPasswordHasher {

    @Override
    public String getPasswordHash(String password) {
        return hashPassword(password);
    }

    @Override
    public Boolean getPasswordCheckStatus(String password, String hashedPwd){
        return checkPwd(password, hashedPwd);
    }
}
