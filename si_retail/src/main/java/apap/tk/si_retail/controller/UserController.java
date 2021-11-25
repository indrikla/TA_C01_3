package apap.tk.si_retail.controller;

import apap.tk.si_retail.model.RoleModel;
import apap.tk.si_retail.model.UserModel;
import apap.tk.si_retail.service.*;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/add")
    private String addUserFormPage(Model model) {
        UserModel user = new UserModel();
        List<RoleModel> listRole = roleService.getListRole();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);
        return "form-add-user";
    }

    @PostMapping(value= "/add")
    private String addUserSubmit(@ModelAttribute UserModel user, Model model,
                                 RedirectAttributes redirAttrs) {
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8,30));
        rules.add(new WhitespaceRule());
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Special, 1));

        PasswordValidator validator = new PasswordValidator(rules);
        PasswordData password = new PasswordData(user.getPassword());
        RuleResult result = validator.validate(password);

        if(result.isValid()){
            userService.addUser(user);
            model.addAttribute("user", user);
            return "redirect:/";
        } else {
            redirAttrs.addFlashAttribute("message", validator.getMessages(result));
            return "redirect:/user/add";
        }
    }

    @GetMapping("/viewall")
    private String listUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserModel currentUser = userService.findUserByUsername(currentUsername);

        List<UserModel> listUser = userService.getListUser();

        model.addAttribute("currentUserRole", currentUser.getRole().getId());
        model.addAttribute("listUser", listUser);
        return "viewall-user";
    }

    @GetMapping("/update/{username}")
    private String formUpdate(@PathVariable String username, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserModel currentUser = userService.findUserByUsername(currentUsername);

        UserModel user = userService.findUserByUsername(username);
        List<RoleModel> listRole = roleService.getListRole();

        model.addAttribute("listRole", listRole);
        
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        return "form-update-user";
    }

    @PostMapping("/update/{username}")
    private String formUpdateSubmit(@ModelAttribute UserModel user, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserModel currentUser = userService.findUserByUsername(currentUsername);
        userService.updateUser(user);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userId", user.getId());
        return "update-user";
    }
}
