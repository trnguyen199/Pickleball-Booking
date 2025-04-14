package com.pickleball.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pickleball.model.San;
import com.pickleball.repository.SanRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SanService {

    @Autowired
    private SanRepository sanRepository;

    public San themSan(San san) {
        log.info("Thêm sân mới: {}", san.getTenSan());
        return sanRepository.save(san);
    }

    public San timTheoId(Long id) {
        return sanRepository.findById(id).orElse(null);
    }

    public List<San> layTatCaSan() {
        return sanRepository.findAll();
    }

    public List<San> timSanHoatDong() {
        return sanRepository.findByTrangThaiTrue();
    }

    public boolean capNhatSan(San san) {
        if (!sanRepository.existsById(san.getId())) {
            return false;
        }
        sanRepository.save(san);
        return true;
    }

    public boolean xoaSan(Long id) {
        if (!sanRepository.existsById(id)) {
            return false;
        }
        sanRepository.deleteById(id);
        return true;
    }

    public List<San> timSanTheoTen(String tenSan) {
        if (tenSan == null || tenSan.isEmpty()) {
            return layTatCaSan();
        }
        
        return sanRepository.findByTenSanContaining(tenSan);
    }
    
    // Phương thức khởi tạo dữ liệu mẫu nếu cần
    public void taoSanMau() {
        if (sanRepository.count() == 0) {
            log.info("Tạo dữ liệu sân mẫu");
            
            San san1 = new San();
            san1.setTenSan("Sân Pickleball Quận 1");
            san1.setDiaChi("123 Nguyễn Huệ, Quận 1, TP.HCM");
            san1.setMoTa("Sân trong nhà, 2 sân thi đấu tiêu chuẩn");
            san1.setGiaThueMoiGio(200000);
            san1.setUrlHinhAnh("/images/san1.jpg");
            san1.setTrangThai(true);
            san1.setSoNguoiChoiToiThieu(2);
            san1.setSoNguoiChoiToiDa(4);
            sanRepository.save(san1);
            
            San san2 = new San();
            san2.setTenSan("Sân Pickleball Quận 7");
            san2.setDiaChi("456 Nguyễn Lương Bằng, Quận 7, TP.HCM");
            san2.setMoTa("Sân ngoài trời, 4 sân thi đấu tiêu chuẩn");
            san2.setGiaThueMoiGio(150000);
            san2.setUrlHinhAnh("/images/san2.jpg");
            san2.setTrangThai(true);
            san2.setSoNguoiChoiToiThieu(2);
            san2.setSoNguoiChoiToiDa(6);
            sanRepository.save(san2);
            
            San san3 = new San();
            san3.setTenSan("Sân Pickleball Thủ Đức");
            san3.setDiaChi("789 Võ Văn Ngân, TP. Thủ Đức, TP.HCM");
            san3.setMoTa("Sân ngoài trời có mái che, 3 sân thi đấu tiêu chuẩn");
            san3.setGiaThueMoiGio(180000);
            san3.setUrlHinhAnh("/images/san3.jpg");
            san3.setTrangThai(true);
            san3.setSoNguoiChoiToiThieu(2);
            san3.setSoNguoiChoiToiDa(4);
            sanRepository.save(san3);
        }
    }
}
