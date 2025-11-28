package com.example.mugichaLatte.service;

import com.example.mugichaLatte.controller.form.AccountEditForm;
import com.example.mugichaLatte.controller.form.AccountRegisterForm;
import com.example.mugichaLatte.repository.UserRepository;
import com.example.mugichaLatte.repository.entity.User;
import com.example.mugichaLatte.utils.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;
    public Page<User> findAllUser(Pageable pageable) {
        return userRepository.findAllUserOrderById(pageable);
    }

    //アカウントを登録するときのアカウント重複チェック
    public boolean checkAccount(AccountRegisterForm form) {
        User user = userRepository.findByAccount(form.getAccount());
        if (user == null) {
            return true;
        } else {
            return false;
        }
    }

    //アカウントを編集するときのアカウント重複チェック
    public boolean checkEditAccount(AccountEditForm form) {
        User user = userRepository.findByAccount(form.getAccount());
        //checkAccount(変更前のアカウント名)と変更後のアカウント名を比較して、等しい（アカウント名を変更していない場合）時にtrueを返す
        if (form.getCheckAccount().equals(form.getAccount())) {
            return true;
        }
        else if (user == null) {
            return true;
        } else {
            return false;
        }
    }

    //アカウント登録処理
    public void saveAccount(AccountRegisterForm form) {
        User user = new User();
        user.setAccount(form.getAccount());
        user.setName(form.getName());
        //パスワードはハッシュ化
        user.setPassword(CipherUtil.encrypt(form.getPassword()));
        user.setType(form.getType());
        user.setDepartmentId(form.getDepartmentId());
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        //登録する際は稼働状態にする
        user.setIsStopped(1);
        userRepository.save(user);
    }

    //アカウント編集画面を表示する際、IDを元にユーザーを取得
    public AccountEditForm findUserById(String id) {
        int userId = Integer.parseInt(id);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        AccountEditForm accountEditForm = new AccountEditForm();
        accountEditForm.setId(userId);
        accountEditForm.setAccount(user.getAccount());
        accountEditForm.setName(user.getName());
        accountEditForm.setPassword(user.getPassword());
        accountEditForm.setType(user.getType());
        accountEditForm.setDepartmentId(user.getDepartmentId());
        accountEditForm.setCreatedDate(user.getCreatedDate());
        accountEditForm.setIsStopped(user.getIsStopped());
        //アカウントを変更してるかしてないかを確認するためにcheckAccountを設定
        accountEditForm.setCheckAccount(user.getAccount());
        return accountEditForm;
    }

    public void editAccount(AccountEditForm form) {
        User user = userRepository.findById(form.getId()).orElse(null);
        user.setAccount(form.getAccount());
        user.setName(form.getName());
        //パスワードはハッシュ化
        if (form.getPassword() != null && !form.getPassword().isBlank()) {
            user.setPassword(CipherUtil.encrypt(form.getPassword()));
        }
        user.setType(form.getType());
        user.setDepartmentId(form.getDepartmentId());
        user.setCreatedDate(form.getCreatedDate());
        user.setUpdatedDate(LocalDateTime.now());
        //登録する際は稼働状態にする
        user.setIsStopped(form.getIsStopped());
        userRepository.save(user);
    }

    public void activeAccount(int id) {
        User user = userRepository.findById(id).orElse(null);
        user.setIsStopped(1);
        userRepository.save(user);
    }

    public void stopAccount(int id) {
        User user = userRepository.findById(id).orElse(null);
        user.setIsStopped(0);
        userRepository.save(user);
    }
}
