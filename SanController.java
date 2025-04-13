package com.pickleball.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pickleball.model.San;
import com.pickleball.service.SanService;

@Controller
public class SanController {

    @Autowired
    private SanService sanService;
    
    @GetMapping("/tim-san")
    public String timSan(@RequestParam(required = false) String tuKhoa, Model model) {
        List<San> danhSachSan;
        
        if (tuKhoa != null && !tuKhoa.isEmpty()) {
            // Tìm sân theo từ khóa
            danhSachSan = sanService.timSanTheoTen(tuKhoa);
            model.addAttribute("tuKhoa", tuKhoa);
        } else {
            // Lấy tất cả sân đang hoạt động
            danhSachSan = sanService.timSanHoatDong();
        }
        
        model.addAttribute("danhSachSan", danhSachSan);
        return "tim-san";
    }
    
    @GetMapping("/chi-tiet-san/{id}")
    public String chiTietSan(@PathVariable Long id, Model model) {
        San san = sanService.timTheoId(id);
        
        if (san == null) {
            return "redirect:/tim-san";
        }
        
        model.addAttribute("san", san);
        return "chi-tiet-san";
    }
    
    // Các phương thức quản lý sân dành cho admin
    @GetMapping("/quan-ly-san")
    public String quanLySan(Model model) {
        model.addAttribute("danhSachSan", sanService.layTatCaSan());
        return "quan-ly-san";
    }
}