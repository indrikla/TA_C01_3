package apap.tk.si_retail.controller;

import apap.tk.si_retail.model.*;
import apap.tk.si_retail.rest.ItemDetailUpdate;
import apap.tk.si_retail.rest.ItemModel;
import apap.tk.si_retail.service.*;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cabang")
public class CabangController {

    @Autowired
    private CabangService cabangService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemCabangRestService itemCabangRestService;
    @Autowired
    private ItemCabangService itemCabangService;

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
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang);

        int res = cabangService.deleteCabang(cabang);
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

//         List<ItemModel> listItemCabangAPI = itemCabangRestService.retrieveListItemModel();
// //        TODO: Risa Verify ini sekali submit harus multiple atau ga
// //        List<ItemCabangModel> listItemCabangPending = cabangService.getCabangByIdCabang(idCabang).getListItemCabang();
// //        model.addAttribute("listItemCabangPending", listItemCabangPending);
//         model.addAttribute("listItemCabangAPI", listItemCabangAPI);
//         model.addAttribute("idCabang", idCabang);
//         return "form-add-item-cabang";
    // }


    @PostMapping("/{idCabang}/add/item")
    public String addItemCabangSubmit(
            @PathVariable Long idCabang,
            @ModelAttribute ItemCabangModel itemCabang,
            RedirectAttributes redirAttrs,
            Model model) {

        String message = "";

//        Ngambil Item yang dipilih user dari list di API
        ItemModel itemModelAPI = itemCabangRestService.getItemCabangModelByUuid(itemCabang.getUuid_item());

//        Cek apakah stok yang diminta user mencukupi sama yang di API
        if(itemCabang.getStok() < itemModelAPI.getStok()) {
//            Kalo iya, asosiasikan item dengan cabang yang dipilih
            CabangModel cabang = cabangService.getCabangByIdCabang(idCabang);

            if (cabang.getListItemCabang() == null) {
                cabang.setListItemCabang(new ArrayList<>());
            }

            ItemCabangModel itemCabangExist = cabangService.getItemCabangInCabangByIdItemCabang(cabang, itemCabang.getUuid_item());

            // handle kalo item dengan id yang sama udah ada; jadi cuman nambah stok + update harga
            if (itemCabangExist != null) {
                itemCabangExist.setStok(itemCabangExist.getStok() + itemCabang.getStok());
                itemCabangExist.setHarga(itemCabangExist.getStok() * itemModelAPI.getHarga());

            } else {
                cabang.getListItemCabang().add(itemCabang);

//            TODO: Ganti kalo promo udah ke implemen
                itemCabang.setId_promo(1);

                itemCabang.setNama(itemModelAPI.getNama());
                itemCabang.setCabang(cabang);
                itemCabang.setKategori(itemModelAPI.getKategori());
                itemCabang.setHarga(itemModelAPI.getHarga() * itemCabang.getStok());
                itemCabangService.addItemCabang(itemCabang);
            }

            // Update stok di API
            int stokUpdate = itemModelAPI.getStok() - itemCabang.getStok();
            ItemDetailUpdate idu = itemCabangRestService.updateStokItem(itemCabang.getUuid_item(), stokUpdate);

            message = "Berhasil menambahkan item";

        } else {
            message = "Stok tidak mencukupi!";
        }

        redirAttrs.addFlashAttribute("message", message);
        return "redirect:/cabang/" + idCabang;
    }
}
