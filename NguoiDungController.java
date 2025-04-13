package com.pickleball.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pickleball.model.NguoiDung;
import com.pickleball.service.NguoiDungService;

@Controller
public class NguoiDungController {

    private final NguoiDungService nguoiDungService;

    public NguoiDungController(NguoiDungService nguoiDungService) {
        this.nguoiDungService = nguoiDungService;
    }

    @GetMapping("/dang-ky")
    public String hienThiTrangDangKy(Model model) {
        model.addAttribute("nguoiDung", new NguoiDung());
        return "dang-ky";
    }

    @PostMapping("/dang-ky")
    public String xuLyDangKy(@Valid @ModelAttribute("nguoiDung") NguoiDung nguoiDung,
                             BindingResult result, HttpServletRequest request) {
        
        if (result.hasErrors()) {
            return "dang-ky";
        }
        
        // Kiểm tra tên đăng nhập đã tồn tại
        if (nguoiDungService.timTheoTenDangNhap(nguoiDung.getTenDangNhap()) != null) {
            result.rejectValue("tenDangNhap", "error.nguoiDung", "Tên đăng nhập đã tồn tại");
            return "dang-ky";
        }
        
        // Đăng ký người dùng mới
        NguoiDung nguoiDungMoi = nguoiDungService.dangKy(nguoiDung);
        
        if (nguoiDungMoi != null) {
            // Tự động đăng nhập người dùng
            try {
                request.login(nguoiDung.getTenDangNhap(), nguoiDung.getMatKhau());
            } catch (ServletException e) {
                // Xử lý lỗi đăng nhập
                return "redirect:/dang-nhap";
            }
            return "redirect:/";
        } else {
            result.reject("error.nguoiDung", "Đã xảy ra lỗi khi đăng ký tài khoản");
            return "dang-ky";
        }
    }

    @GetMapping("/ho-so")
    public String hienThiHoSo(Model model, Authentication authentication) {
        NguoiDung nguoiDung = (NguoiDung) authentication.getPrincipal();
        model.addAttribute("nguoiDung", nguoiDung);
        return "ho-so";
    }

    @PostMapping("/cap-nhat-ho-so")
    public String capNhatHoSo(@ModelAttribute("nguoiDung") NguoiDung nguoiDung,
                             Authentication authentication, Model model) {
        
        NguoiDung nguoiDungHienTai = (NguoiDung) authentication.getPrincipal();
        
        // Cập nhật thông tin cơ bản
        nguoiDungHienTai.setHoTen(nguoiDung.getHoTen());
        nguoiDungHienTai.setSoDienThoai(nguoiDung.getSoDienThoai());
        nguoiDungHienTai.setEmail(nguoiDung.getEmail());
        
        // Không cập nhật mật khẩu nếu trống
        if (nguoiDung.getMatKhau() != null && !nguoiDung.getMatKhau().isEmpty()) {
            nguoiDungHienTai.setMatKhau(nguoiDung.getMatKhau());
        }
        
        boolean ketQua = nguoiDungService.capNhatThongTin(nguoiDungHienTai);
        
        if (ketQua) {
            model.addAttribute("thongBaoThanhCong", "Cập nhật hồ sơ thành công");
        } else {
            model.addAttribute("thongBaoLoi", "Đã xảy ra lỗi khi cập nhật hồ sơ");
        }
        
        return "ho-so";
    }
}
