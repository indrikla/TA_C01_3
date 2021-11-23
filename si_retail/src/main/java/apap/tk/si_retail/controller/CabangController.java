package apap.tk.si_retail.controller;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.service.CabangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cabang")
public class CabangController {
    @Autowired
    private CabangService cabangService;

    @GetMapping("/add")
    private String addCabangFormPage(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentUsername = authentication.getName();
//        UserModel currentUser = userService.getUserByUsername(currentUsername);
//        if(currentUser.getRole().getId()==1) {
            CabangModel cabang = new CabangModel();
            model.addAttribute("cabang", cabang);
            return "form-add-cabang";
//        } else {
//            return "not-authorized";
//        }
    }

    @PostMapping(value="/add")
    private String addUserSubmit(@ModelAttribute CabangModel cabang, Model model) {
        model.addAttribute("nama", cabang.getNama());
        cabangService.addCabang(cabang);
        return "add-cabang-success";
    }
}
