package com.thoughtworks.securityinourdna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class HomeController {

    private final UserRepo userRepo;

    @Autowired
    public HomeController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String home(Model model) throws SQLException {
        model.addAttribute("vendors", userRepo.allVendors());
        return "home";
    }

    @PostMapping("/login")
    public String login(Model model, @RequestParam("vendor") String vendor, @RequestParam("password") String password) throws Exception {
        final UserRepo.LoginResult loginResult = userRepo.login(vendor, password);

        if (loginResult.success) {
            model.addAttribute("vendor", loginResult.vendorName);
            return "login";
        } else {
            return "loginFail";
        }
    }
}
