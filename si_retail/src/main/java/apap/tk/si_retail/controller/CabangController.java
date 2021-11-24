package apap.tk.si_retail.controller;

import apap.tk.si_retail.model.CabangModel;
import apap.tk.si_retail.model.UserModel;
import apap.tk.si_retail.service.CabangService;
import apap.tk.si_retail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cabang")
public class CabangController {
    @Autowired
    private CabangService cabangService;
    @Autowired
    private UserService userService;

    @GetMapping("/add")
    private String addCabangFormPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserModel currentUser = userService.findUserByUsername(currentUsername);
        if(currentUser.getRole().getId()==1 || currentUser.getRole().getId()==2) {
            CabangModel cabang = new CabangModel();
            model.addAttribute("cabang", cabang);
            return "form-add-cabang";
        } else {
            return "not-authorized";
        }
    }

    @PostMapping(value="/add")
    private String addUserSubmit(@ModelAttribute CabangModel cabang, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserModel currentUser = userService.findUserByUsername(currentUsername);
        cabang.setPenanggungJawab(currentUser);
        model.addAttribute("nama", cabang.getNama());
        System.out.println(cabang.getPenanggungJawab());
        cabangService.addCabang(cabang);
        return "add-cabang-success";
    }

    @GetMapping("/viewall")
    private String viewAllCabang(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserModel currentUser = userService.findUserByUsername(currentUsername);
        Long idRole = currentUser.getRole().getId();
        List<CabangModel> listCabang;
        if(idRole == 2) {
            listCabang = cabangService.getListCabangManager(currentUser);
        } else {
            listCabang = cabangService.getListCabang();
        }
        List<Integer> listJmlItem = new ArrayList<>();
        for(CabangModel cabang: listCabang) {
            listJmlItem.add(cabang.getListItemCabang().size());
        }

        model.addAttribute("listCabang", listCabang);
        model.addAttribute("listJmlItem", listJmlItem);
        model.addAttribute("idRole", idRole);
        return "viewall-cabang";
    }
}
