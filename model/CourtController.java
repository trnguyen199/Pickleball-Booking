package com.pbs.controller;

import com.pbs.dto.CourtDto;
import com.pbs.model.Court;
import com.pbs.service.CourtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courts")
public class CourtController {

    private final CourtService courtService;

    @Autowired
    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping
    public ResponseEntity<List<Court>> getAllCourts() {
        return ResponseEntity.ok(courtService.getAllCourts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Court> getCourtById(@PathVariable UUID id) {
        return courtService.getCourtById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Court>> getCourtsByCity(@PathVariable String city) {
        return ResponseEntity.ok(courtService.getCourtsByCity(city));
    }

    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') and @courtSecurity.isCurrentManager(#managerId)")
    public ResponseEntity<List<Court>> getCourtsByManagerId(@PathVariable UUID managerId) {
        return ResponseEntity.ok(courtService.getCourtsByManagerId(managerId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<Court> createCourt(@Valid @RequestBody CourtDto courtDto) {
        Court createdCourt = courtService.createCourt(courtDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourt);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') and @courtSecurity.isCourtManager(#id)")
    public ResponseEntity<Court> updateCourt(@PathVariable UUID id, @Valid @RequestBody CourtDto courtDto) {
        return ResponseEntity.ok(courtService.updateCourt(id, courtDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') and @courtSecurity.isCourtManager(#id)")
    public ResponseEntity<Void> deleteCourt(@PathVariable UUID id) {
        courtService.deleteCourt(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/indoor/{indoor}")
    public ResponseEntity<List<Court>> getCourtsByIndoor(@PathVariable boolean indoor) {
        return ResponseEntity.ok(courtService.getCourtsByIndoor(indoor));
    }

    @GetMapping("/price")
    public ResponseEntity<List<Court>> getCourtsByPriceRange(
            @RequestParam double minPrice, @RequestParam double maxPrice) {
        return ResponseEntity.ok(courtService.getCourtsByPriceRange(minPrice, maxPrice));
    }

    @PostMapping("/{id}/review")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> addReview(@PathVariable UUID id, @RequestParam double rating) {
        courtService.addReviewToCourt(id, rating);
        return ResponseEntity.noContent().build();
    }
}
