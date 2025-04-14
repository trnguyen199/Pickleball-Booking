package com.pickleball.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pickleball.model.DatSan;
import com.pickleball.model.NguoiDung;
import com.pickleball.model.San;
import com.pickleball.model.TrangThaiDatSan;
import com.pickleball.repository.DatSanRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DatSanService {

    @Autowired
    private DatSanRepository datSanRepository;
    
    @Autowired
    private SanService sanService;
    
    @Autowired
    private NguoiDungService nguoiDungService;

    public DatSan datSan(DatSan datSan) {
        // Kiểm tra tính hợp lệ của thời gian
        if (datSan.getThoiGianBatDau().isBefore(LocalDateTime.now())) {
            log.error("Không thể đặt sân trong quá khứ");
            return null; // Không thể đặt sân trong quá khứ
        }
        
        if (datSan.getThoiGianKetThuc().isBefore(datSan.getThoiGianBatDau())) {
            log.error("Thời gian kết thúc phải sau thời gian bắt đầu");
            return null; // Thời gian kết thúc phải sau thời gian bắt đầu
        }
        
        // Kiểm tra sân đã được đặt trong khoảng thời gian này chưa
        San san = datSan.getSan();
        if (san == null) {
            log.error("Không tìm thấy thông tin sân");
            return null; // Không tìm thấy sân
        }
        
        List<DatSan> trungLich = datSanRepository.timDatSanTrungThoiGian(
                san, datSan.getThoiGianBatDau(), datSan.getThoiGianKetThuc());
        
        if (!trungLich.isEmpty()) {
            log.error("Sân đã được đặt trong khoảng thời gian này");
            return null; // Sân đã được đặt trong khoảng thời gian này
        }
        
        // Tính tổng tiền
        long soGio = ChronoUnit.HOURS.between(datSan.getThoiGianBatDau(), datSan.getThoiGianKetThuc());
        if (soGio < 1) soGio = 1; // Tối thiểu 1 giờ
        
        double giaThue = san.getGiaThueMoiGio();
        datSan.setTongTien(giaThue * soGio);
        datSan.setThoiGianDatSan(LocalDateTime.now());
        
        if (datSan.getTrangThai() == null) {
            datSan.setTrangThai(TrangThaiDatSan.CHO_XAC_NHAN);
        }
        
        log.info("Đặt sân thành công: {}", datSan);
        return datSanRepository.save(datSan);
    }

    public DatSan timTheoId(Long id) {
        return datSanRepository.findById(id).orElse(null);
    }

    public List<DatSan> layTatCaDatSan() {
        return datSanRepository.findAll();
    }

    public List<DatSan> timDatSanTheoNguoiDung(NguoiDung nguoiDung) {
        return datSanRepository.findByNguoiDung(nguoiDung);
    }

    public List<DatSan> timDatSanTheoSan(San san) {
        return datSanRepository.findBySan(san);
    }

    public boolean capNhatTrangThaiDatSan(Long id, TrangThaiDatSan trangThai) {
        DatSan datSan = datSanRepository.findById(id).orElse(null);
        if (datSan == null) {
            return false;
        }
        
        datSan.setTrangThai(trangThai);
        datSanRepository.save(datSan);
        return true;
    }

    public boolean huyDatSan(Long id) {
        return capNhatTrangThaiDatSan(id, TrangThaiDatSan.DA_HUY);
    }

    public boolean xacNhanDatSan(Long id) {
        return capNhatTrangThaiDatSan(id, TrangThaiDatSan.DA_XAC_NHAN);
    }

    public boolean hoanThanhDatSan(Long id) {
        return capNhatTrangThaiDatSan(id, TrangThaiDatSan.DA_HOAN_THANH);
    }
    
    public List<DatSan> timDatSanTheoTrangThai(TrangThaiDatSan trangThai) {
        return datSanRepository.findByTrangThai(trangThai);
    }
}
