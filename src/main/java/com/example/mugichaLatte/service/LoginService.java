package com.example.mugichaLatte.service;

import com.example.mugichaLatte.controller.form.LoginForm;
import com.example.mugichaLatte.repository.UserRepository;
import com.example.mugichaLatte.repository.entity.User;
import com.example.mugichaLatte.utils.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UserRepository userRepository;

    //ユーザー情報が存在するのか・停止中のアカウントではないのか確認
    public String checkLogin(LoginForm form){
        String encryptedPassword = CipherUtil.encrypt(form.getPassword());
        User user = userRepository.findByAccountAndPassword(form.getAccount(), encryptedPassword);

        if(user == null){
            return "ログインに失敗しました";
        }

        if(user.getIsStopped() == 0){
            return "ログインに失敗しました";
        }

        return null;
    }

    public User findLoginUser(LoginForm form){
        String encryptedPassword = CipherUtil.encrypt(form.getPassword());
        User user = userRepository.findByAccountAndPassword(form.getAccount(), encryptedPassword);
        return user;
    }
}
