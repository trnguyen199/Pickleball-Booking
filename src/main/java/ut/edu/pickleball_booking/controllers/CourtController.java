package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.pickleball_booking.dto.request.CourtCreationRequest;
import ut.edu.pickleball_booking.dto.request.CourtUpdateRequest;
import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.services.CourtService;

import java.util.List;

@RestController
@RequestMapping("/api/courts")
public class CourtController {

    @Autowired
    private CourtService courtService;

    // Lấy tất cả Courts
    @GetMapping
    public List<Court> getAllCourts() {
        return courtService.getAllCourts();
    }

    // Lấy Court theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Court> getCourtById(@PathVariable int id) {
        Court court = courtService.getCourtById(id);
        if (court != null) {
            return new ResponseEntity<>(court, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Tạo Court mới
    @PostMapping
    public ResponseEntity<Court> createCourt(@RequestBody CourtCreationRequest request) {
        Court createdCourt = courtService.createCourt(request);
        return new ResponseEntity<>(createdCourt, HttpStatus.CREATED);
    }

    // Cập nhật Court theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Court> updateCourt(@PathVariable int id, @RequestBody CourtUpdateRequest request) {
        Court updatedCourt = courtService.updateCourt(id, request);
        if (updatedCourt != null) {
            return new ResponseEntity<>(updatedCourt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Xóa Court theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourt(@PathVariable int id) {
        boolean isDeleted = courtService.deleteCourt(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
