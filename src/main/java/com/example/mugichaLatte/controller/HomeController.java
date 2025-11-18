package com.example.mugichaLatte.controller;

import com.example.mugichaLatte.controller.form.AttendancesSearchForm;
import com.example.mugichaLatte.repository.entity.Attendances;
import com.example.mugichaLatte.service.AttendancesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    AttendancesService attendancesService;
    @GetMapping("home")
    public ModelAndView homeContent(@ModelAttribute AttendancesSearchForm form,
                                    HttpSession session) {
        ModelAndView mav = new ModelAndView();
        LocalDate now = LocalDate.now();
        Map<String, Attendances> attendancesList = attendancesService.findAllAttendances(form);

        LocalDate prevMonth = null;
        LocalDate nextMonth = null;
        List<LocalDate> allDates = new ArrayList<>();
        if (form.getDate() == null) {
            // 今月の最初の日
            LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
            // 今月の最後の日
            LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
            prevMonth = LocalDate.now().minusMonths(1);
            nextMonth = LocalDate.now().plusMonths(1);
            //月の日にちをすべて格納する
            for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
                allDates.add(date);
            }
        } else {
            prevMonth = form.getDate().minusMonths(1);
            nextMonth = form.getDate().plusMonths(1);
            // 検索月の最初の日
            LocalDate firstDay = form.getDate().with(TemporalAdjusters.firstDayOfMonth());
            // 検索月の最後の日
            LocalDate lastDay = form.getDate().with(TemporalAdjusters.lastDayOfMonth());
            //月の日にちをすべて格納する
            for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
                allDates.add(date);
            }
        }
        mav.addObject("prevMonth", prevMonth);
        mav.addObject("nextMonth", nextMonth);
        mav.addObject("attendancesList", attendancesList);
        mav.addObject("allDates", allDates);
        mav.setViewName("home");
        return mav;
    }
}
