package com.pbs.controller;

import com.pbs.dto.PaymentDto;
import com.pbs.model.Payment;
import com.pbs.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or @paymentSecurity.isPaymentUser(#id)")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userSecurity.isCurrentUser(#userId)")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserId(userId));
    }

    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or @bookingSecurity.isBookingUser(#bookingId)")
    public ResponseEntity<List<Payment>> getPaymentsByBookingId(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentsByBookingId(bookingId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable Payment.PaymentStatus status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentDto paymentDto) {
        Payment createdPayment = paymentService.createPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable UUID id, @RequestParam Payment.PaymentStatus status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(id, status));
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Payment> completePayment(
            @PathVariable UUID id, @RequestParam String transactionId) {
        return ResponseEntity.ok(paymentService.completePayment(id, transactionId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<Double> getTotalRevenueByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(paymentService.getTotalRevenueByDateRange(startDate, endDate));
    }
}
