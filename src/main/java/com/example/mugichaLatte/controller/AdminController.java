package com.example.mugichaLatte.controller;

import com.example.mugichaLatte.controller.form.AccountEditForm;
import com.example.mugichaLatte.controller.form.AccountRegisterForm;
import com.example.mugichaLatte.repository.entity.User;
import com.example.mugichaLatte.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;
    @GetMapping("admin")
    public ModelAndView adminContents(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin");

        List<User> userList = new ArrayList<>();
        userList = adminService.findAllUser();
        mav.addObject("loginUser", user);
        mav.addObject("userList", userList);
        return mav;
    }

    //アカウント登録画面表示
    @GetMapping("account-register")
    public ModelAndView accountRegisterContent(AccountRegisterForm form,
                                               HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute("loginUser");
        mav.addObject("loginUser", user);
        mav.addObject("accountRegisterForm", form);
        mav.setViewName("accountRegister");
        return mav;
    }

    //アカウント登録処理
    @PostMapping("account-register")
    public ModelAndView accountRegister(@ModelAttribute @Validated AccountRegisterForm form,
                                        BindingResult result, HttpSession session
                                        ) {
        User user = (User) session.getAttribute("loginUser");
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            for(FieldError error : result.getFieldErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
        }
        //アカウント重複処理
        if (!adminService.checkAccount(form)) {
            errorMessages.add("アカウントが重複しています");
        }
        if (errorMessages.size() > 0) {
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("loginUser", user);
            mav.addObject("accountRegisterForm", form);
            mav.setViewName("accountRegister");
            return mav;
        } else {
            //エラーがなかった場合、アカウントの登録処理
            adminService.saveAccount(form);
            return new ModelAndView("redirect:admin");
        }
    }

    //アカウント編集画面表示処理
    @GetMapping("account-edit/{id}")
    public ModelAndView accountEditContent(@PathVariable("id") String id,
                                           RedirectAttributes redirectAttributes,
                                           HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("accountEdit");

        //IDが数字かどうか判断する処理
        if(!id.matches("\\d+")){
            redirectAttributes.addFlashAttribute("errorMessages", "不正なパラメータが入力されました");
            mav.setViewName("redirect:/admin");
            return mav;
        }

        AccountEditForm form = adminService.findUserById(id);
        if (form == null) {
            redirectAttributes.addFlashAttribute("errorMessages", "指定されたアカウント情報は存在しません");
            mav.setViewName("redirect:/admin");
            return mav;
        }

        mav.addObject("loginUser", user);
        mav.addObject("accountEditForm", form);
        return mav;
    }

    @GetMapping("account-edit/")
    public ModelAndView redirectEmptyId(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessages", "不正なパラメータが入力されました");
        return new ModelAndView("redirect:/admin");
    }

    //アカウント編集処理
    @PostMapping("account-edit")
    public ModelAndView accountEdit(@ModelAttribute @Validated AccountEditForm form,
                                    BindingResult result,
                                    HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            for(FieldError error : result.getFieldErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
        }
        //アカウント重複処理、この際に、アカウントが入力前と変わっているかどうかも確認
        if (!adminService.checkEditAccount(form)) {
            errorMessages.add("アカウントが重複しています");
        }
        if (errorMessages.size() > 0) {
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("loginUser", user);
            mav.addObject("accountEditForm", form);
            mav.setViewName("accountEdit");
            return mav;
        } else {
            //エラーがなかった場合、アカウントの編集処理
            adminService.editAccount(form);
            return new ModelAndView("redirect:admin");
        }
    }

    //ユーザー復活処理
    @PostMapping("isActived/{id}")
    public ModelAndView activeContent(@PathVariable("id") int id) {
        adminService.activeAccount(id);
        return new ModelAndView("redirect:/admin");
    }

    //ユーザー停止処理
    @PostMapping("isStopped/{id}")
    public ModelAndView stopContent(@PathVariable("id") int id) {
        adminService.stopAccount(id);
        return new ModelAndView("redirect:/admin");
    }
}
