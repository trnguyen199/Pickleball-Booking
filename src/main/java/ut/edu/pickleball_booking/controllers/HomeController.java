
package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ut.edu.pickleball_booking.services.HomeService;

@Controller
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        String welcomeMessage = homeService.getWelcomeMessage();
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "home"; 
    }
}
