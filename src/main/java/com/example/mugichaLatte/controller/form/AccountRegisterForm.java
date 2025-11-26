package com.example.mugichaLatte.controller.form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountRegisterForm {

    @NotBlank(message = "アカウントを入力してください。")
    @Pattern(
            regexp = "^$|^[a-zA-Z0-9]{6,20}$",
            message = "アカウントは半角英数字かつ6文字以上20文字以内で入力してください。"
    )
    private String account;

    @NotNull(message = "部署を選択してください")
    private Integer departmentId;

    @Pattern(regexp = "^(?![\\s　]*$).+$", message = "名前を入力してください。")
    @Size(max = 10, message="名前は10文字以内で入力してください")
    private String name;

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

    @NotNull(message = "区分を選択してください")
    private Integer type;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Integer isStopped;
}
