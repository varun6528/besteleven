package com.verteil.besteleven.controller;

import com.verteil.besteleven.exception.CustomException;
import com.verteil.besteleven.model.ChangePass;
import com.verteil.besteleven.model.User;
import com.verteil.besteleven.service.PasswordChangeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Controller
@AllArgsConstructor
@Validated
public class PasswordChangeController {

    @Autowired
    private PasswordChangeService passwordChangeService;

    @GetMapping("/changePassword")
    public String changePassword(final Model model){
        model.addAttribute("changePass", new ChangePass());
        return "PasswordChange";
    }

    @PostMapping("/changePassword")
    public String changePassword(final HttpServletRequest request, final @Valid ChangePass changePass,
            final BindingResult bindingResult, final Model model) {
        User user = (User)request
                .getSession()
                .getAttribute("user");
        boolean isChanged = false;
        try {
            isChanged = passwordChangeService.changePassword(user, changePass.getOldPassword(), changePass.getNewPassword());
        } catch (CustomException ex) {
            if (ex.getMessage().matches("(.*)registered(.*)|(.*)login(.*)")) {
                model.addAttribute("user", new User());
                return "redirect:/login";
            } else {
                bindingResult.rejectValue("oldPassword", "changePass.oldPassword", ex.getMessage());
            }
        }
        if (isChanged) {
            request.getSession().removeAttribute("user");
            model.addAttribute("user", user);
            return "redirect:/login";
        } else {
            return "PasswordChange";
        }
    }
}
