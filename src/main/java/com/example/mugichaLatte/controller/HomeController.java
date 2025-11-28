package com.example.mugichaLatte.controller;

import com.example.mugichaLatte.controller.form.AttendancesSearchForm;
import com.example.mugichaLatte.repository.entity.Attendances;
import com.example.mugichaLatte.repository.entity.User;
import com.example.mugichaLatte.service.AttendancesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDate;
import java.time.YearMonth;
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
        User user = (User) session.getAttribute("loginUser");
        Map<String, Attendances> attendancesList = attendancesService.findAllAttendances(form, user.getId());

        YearMonth prevMonth = null;
        YearMonth nextMonth = null;
        List<LocalDate> allDates = new ArrayList<>();
        if (form.getDate() == null) {
            // 今月の最初の日
            LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
            // 今月の最後の日
            LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
            prevMonth = YearMonth.now().minusMonths(1);
            nextMonth = YearMonth.now().plusMonths(1);

            //月の日にちをすべて格納する
            for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
                allDates.add(date);
            }
        } else {
            prevMonth = form.getDate().minusMonths(1);
            nextMonth = form.getDate().plusMonths(1);

            for (int day = 1; day <= form.getDate().lengthOfMonth(); day++) {
                allDates.add(form.getDate().atDay(day));
            }
        }
        mav.addObject("prevMonth", prevMonth);
        mav.addObject("nextMonth", nextMonth);
        mav.addObject("attendancesList", attendancesList);
        mav.addObject("allDates", allDates);
        mav.addObject("loginUser", user);
        mav.setViewName("home");
        return mav;
    }
}
