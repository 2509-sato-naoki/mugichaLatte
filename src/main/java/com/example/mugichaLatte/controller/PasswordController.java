package com.example.mugichaLatte.controller;

import com.example.mugichaLatte.controller.form.PasswordForm;
import com.example.mugichaLatte.repository.entity.User;
import com.example.mugichaLatte.service.PasswordService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PasswordController {

    @Autowired
    PasswordService passwordService;
    @GetMapping("/edit-password")
    public ModelAndView passwordContent(@ModelAttribute PasswordForm form,
                                        HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("password");
        User user = (User) session.getAttribute("loginUser");
        mav.addObject("loginUser", user);
        mav.addObject("passwordForm", form);
        return mav;
    }

    @PostMapping("/edit-password")
    public ModelAndView passwordEditContent(@ModelAttribute @Validated PasswordForm form,
                                            BindingResult result,
                                            HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        //バリデーションのエラーチェック
        if (result.hasErrors()) {
            for(FieldError error : result.getFieldErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
        }
        if (errorMessages.size() == 0) {
            passwordService.editPassword(form, user);
            return new ModelAndView("redirect:/home");
        } else {
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("loginUser", user);
            mav.setViewName("password");
            mav.addObject("passwordForm", form);
            return mav;
        }

    }
}
