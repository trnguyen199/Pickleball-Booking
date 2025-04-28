package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.repositories.CourtRepository;

import java.util.List;

@Service
public class CourtService {

    private final CourtRepository courtRepository;

    @Autowired
    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }

    public Court saveCourt(Court court) {
        return courtRepository.save(court);
    }

    public void deleteCourt(Long id) {
        courtRepository.deleteById(id);
    }
    
    public List<Court> getCourtsByOwnerId(Long ownerId) {
        return courtRepository.findByCourtOwner_Id(ownerId); // sửa theo quan hệ @ManyToOne
    }
}
