package ut.edu.pickleball_booking.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller

public class ThanhToanDatSanController {

    @GetMapping("danhsachsan/thanhtoan")
    public String getLienhe() {
        return "master/thanhtoan"; 
    }
}



