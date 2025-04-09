package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.dto.request.CourtCreationRequest;
import ut.edu.pickleball_booking.dto.request.CourtUpdateRequest;
import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.repositories.CourtRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourtService {

    @Autowired
    private CourtRepository courtRepository;

    // Tạo mới Court
    public Court createCourt(CourtCreationRequest request) {
        Court court = new Court();
        court.setName(request.getName());
        court.setLocation(request.getLocation());
        court.setStatus(request.getStatus());
        return courtRepository.save(court);
    }

    // Cập nhật Court
    public Court updateCourt(int id, CourtUpdateRequest request) {
        Optional<Court> existingCourt = courtRepository.findById(id);
        if (existingCourt.isPresent()) {
            Court court = existingCourt.get();
            court.setName(request.getName());
            court.setLocation(request.getLocation());
            court.setStatus(request.getStatus());
            return courtRepository.save(court);
        }
        return null; // Nếu không tìm thấy Court với id này
    }

    // Lấy danh sách Court
    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }

    // Lấy Court theo ID
    public Court getCourtById(int id) {
        return courtRepository.findById(id).orElse(null);
    }

    // Xóa Court theo ID
    public boolean deleteCourt(int id) {
        Optional<Court> existingCourt = courtRepository.findById(id);
        if (existingCourt.isPresent()) {
            courtRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
