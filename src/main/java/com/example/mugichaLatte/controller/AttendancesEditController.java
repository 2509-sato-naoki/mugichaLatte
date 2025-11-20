package com.example.mugichaLatte.controller;

import com.example.mugichaLatte.controller.form.AttendancesEditForm;
import com.example.mugichaLatte.controller.form.PasswordForm;
import com.example.mugichaLatte.repository.entity.Attendances;
import com.example.mugichaLatte.service.AttendancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AttendancesEditController {
    @Autowired
    AttendancesService attendancesService;

    @GetMapping("attendance-edit/{id}")
    public ModelAndView attendancesEditContent(@PathVariable("id") String id,
                                               RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("attendanceEdit");
        //IDが数字かどうか判断する処理
        if(!id.matches("\\d+")){
            redirectAttributes.addFlashAttribute("errorMessages", "不正なパラメータが入力されました");
            mav.setViewName("redirect:/home");
            return mav;
        }

        //idに対応する勤怠記録をもっていってセットする
        AttendancesEditForm attendances = attendancesService.findAttendances(id);
        mav.addObject("attendancesEditForm", attendances);
        return mav;
    }

    @PostMapping("attendance-edit/")
    public ModelAndView attendancesEdit(@ModelAttribute @Validated AttendancesEditForm form,
                                        BindingResult result,
                                        RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
            for(FieldError error : result.getFieldErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
        }
        if (errorMessages.size() > 0) {
            //バリデーションエラーに引っかかった時の処理
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            redirectAttributes.addFlashAttribute("attendancesEditForm", form);
            return new ModelAndView("redirect:/attendance-edit/" + form.getId());
        } else {
            //編集処理
            attendancesService.saveAttendances(form);
            return new ModelAndView("redirect:/home");
        }
    }

    @PostMapping("/attendance-delete")
    public ModelAndView attendanceDeleteContent(@ModelAttribute AttendancesEditForm form) {
        int userId = form.getId();
        attendancesService.deleteAttendances(userId);
        return new ModelAndView("redirect:/home");
    }

}
