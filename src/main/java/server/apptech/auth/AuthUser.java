package server.apptech.auth;

import lombok.Getter;

@Getter
public class AuthUser {

    private Long userId;
    private Authority userAuthority;

    private AuthUser(Long userId, Authority userAuthority){
        this.userId = userId;
        this.userAuthority = userAuthority;
    }

    public static AuthUser user(Long userId){
        return new AuthUser(userId, Authority.ROLE_USER);
    }

    public static AuthUser visitor(){
        return new AuthUser(null, Authority.ROLE_VISITOR);
    }

    public boolean isVisitor() {
        return this.userAuthority == Authority.ROLE_VISITOR;
    }
}
