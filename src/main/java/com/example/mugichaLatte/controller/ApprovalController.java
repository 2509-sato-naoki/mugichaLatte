package com.example.mugichaLatte.controller;

import com.example.mugichaLatte.repository.entity.Attendances;
import com.example.mugichaLatte.repository.entity.User;
import com.example.mugichaLatte.service.ApprovalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApprovalController {
    @Autowired
    ApprovalService approvalService;

    @GetMapping("/approve")
    public ModelAndView showApprovalList(HttpSession session,
                                         @RequestParam(defaultValue = "1") int page,
                                         @PageableDefault(size = 10) Pageable defaultPageable){
        ModelAndView mav = new ModelAndView("approve");

        // Spring Data JPA は 0始まりなので 1 を引いて PageRequest を作る
        Pageable pageable = PageRequest.of(page - 1, defaultPageable.getPageSize());
        Page<Attendances> list = approvalService.getApprovalList(pageable);

        User user = (User) session.getAttribute("loginUser");
        mav.addObject("loginUser", user);
        mav.addObject("attendances", list);
        return mav;
    }

    @PostMapping("/approve")
    public String permitStatus(@RequestParam(value="attendanceId", required = false) List<Integer>ids,
                                     @RequestParam("mode") String mode,
                                     RedirectAttributes redirectAttributes){

        List<String> errorMessages = new ArrayList<String>();
        if (ids == null) {
            // エラーを一度だけ表示するためにFlash属性を使う
            errorMessages.add("1つ以上選択してください");
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            return "redirect:/approve";
        }

        if(mode.equals("permit")) {
            approvalService.permit(ids);
        } else if (mode.equals("reject")) {
            approvalService.reject(ids);
        }
        return "redirect:/approve";
    }
}
