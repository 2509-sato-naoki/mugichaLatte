package com.example.mugichaLatte.service;

import com.example.mugichaLatte.controller.form.PasswordForm;
import com.example.mugichaLatte.repository.UserRepository;
import com.example.mugichaLatte.repository.entity.User;
import com.example.mugichaLatte.utils.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordService {

    @Autowired
    UserRepository userRepository;
    public void editPassword(PasswordForm form, User user) {
        String encryptedPassword = CipherUtil.encrypt(form.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }
}
