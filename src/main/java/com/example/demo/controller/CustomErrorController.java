package com.example.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("statusCode", statusCode);
            
            String errorMsg = switch(statusCode) {
                case 404 -> "ページが見つかりません";
                case 500 -> "内部サーバーエラーが発生しました";
                default -> "エラーが発生しました";
            };
            model.addAttribute("errorMsg", errorMsg);
        }
        return "error";  // error.htmlテンプレートを表示
    }
}