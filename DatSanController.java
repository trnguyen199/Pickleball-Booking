package com.pickleball.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pickleball.model.DatSan;
import com.pickleball.model.NguoiDung;
import com.pickleball.model.San;
import com.pickleball.service.DatSanService;
import com.pickleball.service.NguoiDungService;
import com.pickleball.service.SanService;

@Controller
public class DatSanController {

    @Autowired
    private DatSanService datSanService;
    
    @Autowired
    private SanService sanService;
    
    @Autowired
    private NguoiDungService nguoiDungService;
    
    @GetMapping("/dat-san/{sanId}")
    public String hienThiFormDatSan(@PathVariable Long sanId, Model model) {
        San san = sanService.timTheoId(sanId);
        
        if (san == null) {
            return "redirect:/tim-san";
        }
        
        model.addAttribute("san", san);
        
        // Định dạng thời gian hiện tại để hiển thị trong form
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("thoiGianHienTai", now.format(formatter));
        
        return "dat-san";
    }
    
    @PostMapping("/dat-san/{sanId}")
    public String xuLyDatSan(
            @PathVariable Long sanId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime thoiGianBatDau,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime thoiGianKetThuc,
            @RequestParam(required = false) String ghiChu,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        
        // Lấy thông tin sân
        San san = sanService.timTheoId(sanId);
        if (san == null) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Không tìm thấy sân với ID: " + sanId);
            return "redirect:/tim-san";
        }
        
        // Lấy thông tin người dùng hiện tại
        NguoiDung nguoiDung = nguoiDungService.timTheoTenDangNhap(principal.getName());
        
        // Tạo đối tượng đặt sân
        DatSan datSan = new DatSan();
        datSan.setSan(san);
        datSan.setNguoiDung(nguoiDung);
        datSan.setThoiGianBatDau(thoiGianBatDau);
        datSan.setThoiGianKetThuc(thoiGianKetThuc);
        datSan.setGhiChu(ghiChu);
        
        // Thực hiện đặt sân
        DatSan ketQua = datSanService.datSan(datSan);
        
        if (ketQua != null) {
            redirectAttributes.addFlashAttribute("thongBao", "Đặt sân thành công. Vui lòng đợi xác nhận từ quản lý sân.");
            return "redirect:/lich-su-dat-san";
        } else {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Đặt sân không thành công. Vui lòng kiểm tra lại thời gian.");
            return "redirect:/dat-san/" + sanId;
        }
    }
    
    @GetMapping("/lich-su-dat-san")
    public String lichSuDatSan(Model model, Principal principal) {
        NguoiDung nguoiDung = nguoiDungService.timTheoTenDangNhap(principal.getName());
        model.addAttribute("danhSachDatSan", datSanService.timDatSanTheoNguoiDung(nguoiDung));
        return "lich-su-dat-san";
    }
    
    @GetMapping("/huy-dat-san/{id}")
    public String huyDatSan(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal) {
        // Kiểm tra quyền hủy đặt sân
        DatSan datSan = datSanService.timTheoId(id);
        
        if (datSan == null) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Không tìm thấy thông tin đặt sân");
            return "redirect:/lich-su-dat-san";
        }
        
        NguoiDung nguoiDung = nguoiDungService.timTheoTenDangNhap(principal.getName());
        
        // Chỉ người đặt sân mới có quyền hủy
        if (!datSan.getNguoiDung().getId().equals(nguoiDung.getId())) {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Bạn không có quyền hủy đặt sân này");
            return "redirect:/lich-su-dat-san";
        }
        
        boolean ketQua = datSanService.huyDatSan(id);
        
        if (ketQua) {
            redirectAttributes.addFlashAttribute("thongBao", "Hủy đặt sân thành công");
        } else {
            redirectAttributes.addFlashAttribute("thongBaoLoi", "Hủy đặt sân không thành công");
        }
        
        return "redirect:/lich-su-dat-san";
    }
}