package com.pickleball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pickleball.service.NguoiDungService;
import com.pickleball.service.SanService;

@Controller
public class HomeController {

    @Autowired
    private SanService sanService;
    
    @Autowired
    private NguoiDungService nguoiDungService;
    
    @GetMapping("/")
    public String trangChu(Model model, Authentication authentication) {
        // Thêm thông tin người dùng nếu đã đăng nhập
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("tenDangNhap", userDetails.getUsername());
        }
        
        // Thêm danh sách các sân nổi bật
        model.addAttribute("danhSachSanNoiBat", sanService.timSanHoatDong());
        
        return "index";
    }
    
    @GetMapping("/dang-nhap")
    public String hienThiTrangDangNhap() {
        return "dang-nhap";
    }
    
    @GetMapping("/lien-he")
    public String hienThiTrangLienHe() {
        return "lien-he";
    }
    
    @GetMapping("/gioi-thieu")
    public String hienThiTrangGioiThieu() {
        return "gioi-thieu";
    }
}