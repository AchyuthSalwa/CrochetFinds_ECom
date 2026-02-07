package com.crochetfinds.web;

import com.ecommerceapp.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

/**
 * Simple controller to make a user admin.
 * In production, this should be secured or removed.
 */
@Controller
@RequestMapping("/admin/setup")
@RequiredArgsConstructor
public class AdminSetupController {

    private final UserService userService;

    @GetMapping
    public String setupPage(Model model) {
        return "admin/setup";
    }

    @PostMapping("/make-admin")
    public String makeAdmin(
            @RequestParam String email,
            RedirectAttributes redirectAttributes) {
        try {
            userService.makeAdmin(email);
            redirectAttributes.addFlashAttribute("success", "User " + email + " is now an admin");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/setup";
    }
}
