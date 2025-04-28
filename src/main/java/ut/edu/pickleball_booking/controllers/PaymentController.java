package ut.edu.pickleball_booking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ut.edu.pickleball_booking.configurations.Config;
import ut.edu.pickleball_booking.dto.request.PaymentResDTO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")

public class PaymentController {

    @GetMapping("create_payment")
    public ResponseEntity<?> createPayment(@RequestParam("amount") long amount) throws UnsupportedEncodingException {
        if (amount <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số tiền không hợp lệ.");
        }

        String vnp_TxnRef = Config.getRandomNumber(8); // Mã giao dịch duy nhất
        String orderType = "other";
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount)); // Số tiền
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                // Không mã hóa trong hashData
                hashData.append(fieldName).append('=').append(fieldValue).append('&');

                // Mã hóa khi tạo query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                     .append('=')
                     .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()))
                     .append('&');
            }
        }

        // Xóa ký tự '&' cuối cùng
        if (hashData.length() > 0) {
            hashData.setLength(hashData.length() - 1);
        }
        if (query.length() > 0) {
            query.setLength(query.length() - 1);
        }

        // Tạo chữ ký
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        // Tạo URL thanh toán
        String paymentUrl = Config.vnp_PayUrl + "?" + query.toString();

        // Log để kiểm tra
        System.out.println("Hash Data: " + hashData.toString());
        System.out.println("Generated Secure Hash: " + vnp_SecureHash);
        System.out.println("Generated Payment URL: " + paymentUrl);
        System.out.println("Secret Key: " + Config.secretKey); // Chỉ in trong môi trường phát triển
        // Trả về URL thanh toán dưới dạng JSON
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("URL", paymentUrl);
        return ResponseEntity.ok(response);
    }
}