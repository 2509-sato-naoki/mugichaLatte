package com.example.mugichaLatte.controller.form;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "アカウントを入力してください。")
    private String account;

    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "パスワードを入力してください。")
    private String password;
}