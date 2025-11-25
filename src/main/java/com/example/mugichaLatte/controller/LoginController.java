package com.example.mugichaLatte.controller;

import com.example.mugichaLatte.controller.form.LoginForm;
import com.example.mugichaLatte.repository.entity.User;
import com.example.mugichaLatte.security.LoginUserDetails;
import com.example.mugichaLatte.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private HttpSession session;

    //★ログイン画面表示
    @GetMapping("/login")
    public ModelAndView showLogin(){
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("loginForm", new LoginForm());
        return mav;
    }

    //★ログイン機能
    @PostMapping("/login")
    public ModelAndView doLogin(@ModelAttribute @Validated LoginForm loginForm,
                                BindingResult result,
                                HttpSession session){

        //FORMの中にエラーがあった場合抽出➡それを画面に渡す
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()){
            for(FieldError error : result.getFieldErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("loginForm", loginForm);
            return mav;
        }

        //停止中のアカウントではないか・既に存在していないかの確認
        String errorMessage = loginService.checkLogin(loginForm);
        if(errorMessage != null){
            ModelAndView mav = new ModelAndView("login");
            errorMessages.add(errorMessage);
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("loginForm", loginForm);
            return mav;
        }

        User user = loginService.findLoginUser(loginForm);
        session.setAttribute("loginUser",user);


        //Authenticationはログイン情報の塊
        LoginUserDetails details = new LoginUserDetails(user);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // ★SecurityContext に保存
        SecurityContextHolder.getContext().setAuthentication(auth);

        //★セッションにも保存
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return new ModelAndView("redirect:/home");

    }


}
