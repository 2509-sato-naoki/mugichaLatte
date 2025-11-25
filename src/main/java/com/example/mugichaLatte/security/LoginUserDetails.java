package com.example.mugichaLatte.security;

import com.example.mugichaLatte.repository.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// ★ ログイン中のユーザー情報を Spring Security に渡すためのクラス
public class LoginUserDetails implements UserDetails {

    private final User user;

    //コンストラクタ
    public LoginUserDetails(User user) {
        this.user = user;
    }

    //getter

    // 部署ID
    public Integer getDepartmentId() {
        return user.getDepartmentId();
    }

    // ユーザーID
    public Integer getUserId() {
        return user.getId();
    }

    // 区分（0〜3）
    public Integer getType() {
        return user.getType();
    }

    //UserDetailsの必須メソッド・Securityが使う情報

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //今回権限を使わないのでnull
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // アカウントの有効期限なし
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // ロックなし
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // パスワードの有効期限なし
    }

    @Override
    public boolean isEnabled() {
        return true; // 常に有効
    }
}
