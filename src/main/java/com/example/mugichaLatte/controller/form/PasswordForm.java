package com.example.mugichaLatte.controller.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForm {

    @Pattern.List({
            @Pattern(regexp = "^$|^[\\x21-\\x7E]{8,20}$",message = "パスワードは半角文字かつ８文字以上20文字以下で入力してください。"),
            @Pattern(regexp = "^(?![\\s　]*$).+$", message = "パスワードを入力してください。")
    })
    private String password;

    private String passwordConfirm;

    //パスワード確認
    @AssertTrue(message="パスワードと確認用パスワードが一致しません")
    public boolean isPasswordMatching() {
        if (password == null || passwordConfirm == null) {
            return true; // 未入力時のバリデーションは他で行う
        }
        return password.equals(passwordConfirm);
    }
}
