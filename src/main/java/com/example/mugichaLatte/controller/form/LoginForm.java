package com.example.mugichaLatte.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

    @NotBlank(message = "アカウントを入力してください。")
    private String account;

    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "パスワードを入力してください。")
    private String password;
}