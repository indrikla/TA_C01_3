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
import org.springframework.web.bind.annotation.*;

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
    private String addCabangSubmit(@ModelAttribute CabangModel cabang, Model model) {
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

    @GetMapping("/update/{idCabang}")
    public String updateCabangForm(
            @PathVariable Long idCabang,
            Model model
    ) {
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang);
        model.addAttribute("cabang", cabang);
        return "form-update-cabang";
    }

    @PostMapping("/update")
    public String updateCabangSubmit(
            @ModelAttribute CabangModel cabang,
            Model model
    ) {
        cabangService.updateCabang(cabang);
        model.addAttribute("nama", cabang.getNama());
        return "update-cabang";
    }

    @RequestMapping(value = "/delete/{idCabang}",
            method = RequestMethod.GET)
    public String deleteCabangByNoCabang(
            @PathVariable Long idCabang,
            @ModelAttribute CabangModel cabangModel,
            Model model
    ) {
        // Untuk constraint terkait dengan item dan status, belum bisa di handle pada progres 1.
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang);

        int res = 0;
        res = cabangService.deleteCabang(cabang);
        String msg = "";
        model.addAttribute("res", res);
        model.addAttribute("nama", cabang.getNama());
        return "remove-cabang";
    }

    @GetMapping("/{idCabang}")
    public String detailCabang(
            @PathVariable Long idCabang,
            Model model
    ) {
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang);
        model.addAttribute("cabang", cabang);
        return "view-cabang";
    }

    @GetMapping("/{idCabang}/add/item")
    public String addItemCabangFormPage(@PathVariable Long idCabang, Model model) {
        return "form-add-item-cabang";
    }

}
