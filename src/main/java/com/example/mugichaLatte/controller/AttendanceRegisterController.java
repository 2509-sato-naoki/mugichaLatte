package com.example.mugichaLatte.controller;

import com.example.mugichaLatte.controller.form.AttendanceRegisterForm;
import com.example.mugichaLatte.service.AttendanceRegisterService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AttendanceRegisterController {

    @Autowired
    AttendanceRegisterService attendanceRegisterService;

    @GetMapping("/attendance/register")
    public ModelAndView showRegisterForm(){
        ModelAndView mav = new ModelAndView("attendanceRegister");
        return mav;
    }

//    @PostMapping("/attendance/register")
//    public ModelAndView register(@ModelAttribute @Validated AttendanceRegisterForm attendanceRegisterForm,
//                                 BindingResult result,
//                                 RedirectAttributes redirectAttributes,
//                                 HttpSession session){
//
//        //FORMの中にエラーがあった場合抽出➡それを画面に渡す
//        List<String> errorMessages = new ArrayList<>();
//        if (result.hasErrors()){
//            for(FieldError error : result.getFieldErrors()){
//                errorMessages.add(error.getDefaultMessage());
//            }
//            ModelAndView mav = new ModelAndView("attendanceRegister");
//            mav.addObject("errorMessages", errorMessages);
//            mav.addObject("attendanceRegisterForm", attendanceRegisterForm);
//            return mav;
//        }
//
//        Integer userId = (Integer) session.getAttribute("loginUserId");
//        if(attendanceRegisterService.isDuplicate(userId, attendanceRegisterForm.getDate())){
//            errorMessages.add("日付が重複しています。編集したい場合は勤怠編集画面から操作してください。");
//            ModelAndView mav = new ModelAndView("attendanceRegister");
//            mav.addObject("errorMessages", errorMessages);
//            return mav;
//        }
//    }
}
