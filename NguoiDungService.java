package com.pickleball.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pickleball.model.NguoiDung;
import com.pickleball.model.VaiTro;
import com.pickleball.repository.NguoiDungRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NguoiDungService implements UserDetailsService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String tenDangNhap) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByTenDangNhap(tenDangNhap);
        
        if (nguoiDung == null) {
            log.error("Không tìm thấy tài khoản với tên đăng nhập: {}", tenDangNhap);
            throw new UsernameNotFoundException("Không tìm thấy tài khoản với tên đăng nhập: " + tenDangNhap);
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // Chuyển đổi VaiTro enum thành ROLE_XXX
        if (nguoiDung.getVaiTro() != null) {
            String role = "ROLE_" + nguoiDung.getVaiTro().name();
            authorities.add(new SimpleGrantedAuthority(role));
        }
        
        log.info("Đăng nhập thành công với tài khoản: {}, vai trò: {}", tenDangNhap, nguoiDung.getVaiTro());
        
        return new User(
                nguoiDung.getTenDangNhap(), 
                nguoiDung.getMatKhau(), 
                true, true, true, true, 
                authorities);
    }
    
    public NguoiDung dangKy(NguoiDung nguoiDung) {
        // Kiểm tra trùng lặp tên đăng nhập
        if (nguoiDungRepository.existsByTenDangNhap(nguoiDung.getTenDangNhap())) {
            log.error("Tên đăng nhập đã tồn tại: {}", nguoiDung.getTenDangNhap());
            return null;
        }
        
        // Kiểm tra trùng lặp email
        if (nguoiDungRepository.existsByEmail(nguoiDung.getEmail())) {
            log.error("Email đã tồn tại: {}", nguoiDung.getEmail());
            return null;
        }
        
        // Mã hóa mật khẩu
        nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDung.getMatKhau()));
        
        // Đặt vai trò mặc định là NGUOI_DUNG
        if (nguoiDung.getVaiTro() == null) {
            nguoiDung.setVaiTro(VaiTro.NGUOI_DUNG);
        }
        
        log.info("Đăng ký thành công tài khoản mới: {}", nguoiDung.getTenDangNhap());
        
        return nguoiDungRepository.save(nguoiDung);
    }
    
    public NguoiDung timTheoTenDangNhap(String tenDangNhap) {
        return nguoiDungRepository.findByTenDangNhap(tenDangNhap);
    }
    
    public NguoiDung timTheoId(Long id) {
        return nguoiDungRepository.findById(id).orElse(null);
    }
    
    public List<NguoiDung> layTatCaNguoiDung() {
        return nguoiDungRepository.findAll();
    }
    
    public boolean doiMatKhau(String tenDangNhap, String matKhauCu, String matKhauMoi) {
        NguoiDung nguoiDung = nguoiDungRepository.findByTenDangNhap(tenDangNhap);
        
        if (nguoiDung == null) {
            return false;
        }
        
        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(matKhauCu, nguoiDung.getMatKhau())) {
            return false;
        }
        
        // Cập nhật mật khẩu mới
        nguoiDung.setMatKhau(passwordEncoder.encode(matKhauMoi));
        nguoiDungRepository.save(nguoiDung);
        
        return true;
    }
    
    public boolean capNhatThongTin(NguoiDung nguoiDung) {
        NguoiDung nguoiDungHienTai = nguoiDungRepository.findById(nguoiDung.getId()).orElse(null);
        
        if (nguoiDungHienTai == null) {
            return false;
        }
        
        // Chỉ cập nhật các thông tin được phép thay đổi
        nguoiDungHienTai.setHoTen(nguoiDung.getHoTen());
        nguoiDungHienTai.setSoDienThoai(nguoiDung.getSoDienThoai());
        
        // Nếu email thay đổi, kiểm tra trùng lặp
        if (!nguoiDungHienTai.getEmail().equals(nguoiDung.getEmail())) {
            if (nguoiDungRepository.existsByEmail(nguoiDung.getEmail())) {
                return false;
            }
            nguoiDungHienTai.setEmail(nguoiDung.getEmail());
        }
        
        nguoiDungRepository.save(nguoiDungHienTai);
        return true;
    }
}