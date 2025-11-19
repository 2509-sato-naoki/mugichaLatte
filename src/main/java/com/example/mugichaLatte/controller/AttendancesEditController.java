package com.example.mugichaLatte.controller;

import com.example.mugichaLatte.repository.entity.Attendances;
import com.example.mugichaLatte.service.AttendancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            mav.setViewName("redirect:/attendance-edit/id");
            return mav;
        }

        //idに対応する勤怠記録をもっていってセットする
        Attendances attendances = attendancesService.findAttendances(id);
        mav.addObject("attendancesEditForm", attendances);
        return mav;
    }
}
