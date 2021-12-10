package apap.tk.si_retail.controller;

import apap.tk.si_retail.model.*;
import apap.tk.si_retail.repository.ItemCabangDB;
import apap.tk.si_retail.rest.ItemDetailUpdate;
import apap.tk.si_retail.rest.ItemModel;
import apap.tk.si_retail.rest.KuponModel;
import apap.tk.si_retail.service.*;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;
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
    @Autowired
    private KuponRestService kuponRestService;

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

    // @PostMapping(value="/add")
    // private String addCabangSubmit(@ModelAttribute CabangModel cabang, Model model) {
    //     cabang.setStatus(0);
    //     model.addAttribute("nama", cabang.getNama());
    //     cabangService.addCabang(cabang);
    //     return "add-cabang-success";
    // }

    @GetMapping("/viewall")
    private String viewAllCabang(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserModel currentUser = userService.findUserByUsername(currentUsername);
        Long idRole = currentUser.getRole().getId();

        List<CabangModel> listCabang;
        List<CabangModel> listCabangRequest = cabangService.getListCabangStatus(0);
        if(idRole == 2) {
            listCabang = cabangService.getListCabangManager(currentUser);
        } else {
            listCabang = cabangService.getListCabangStatus(2);
        }
        List<Integer> listJmlItem = new ArrayList<>();
        for(CabangModel cabang: listCabang) {
            listJmlItem.add(cabang.getListItemCabang().size());
        }

        model.addAttribute("listCabangReq", listCabangRequest);
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
        try{
            model.addAttribute("penanggungJawab", cabang.getPenanggungJawab().getName());
        } catch(NullPointerException e){
            model.addAttribute("penanggungJawab", "-");
        }
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

    @RequestMapping(value = "item/delete/{idItem}",
            method = RequestMethod.GET)
    public String removeItemFromCabang(
            @PathVariable Long idItem,
            @ModelAttribute ItemCabangModel itemCabangModel,
            Model model
    ) {
        ItemCabangModel itemCabang = itemCabangService.getItemCabangByIdItemCabang(idItem);

        itemCabangService.deleteItemCabang(itemCabang);

        model.addAttribute("namaItem", itemCabang.getNama());
        model.addAttribute("namaCabang", itemCabang.getCabang().getNama());
        model.addAttribute("idCabang", itemCabang.getCabang().getId());

        return "remove-item";
    }

    @GetMapping("/viewall/request")
    private String viewAllCabangRequested(Model model) {

        List<CabangModel> listCabang = cabangService.getListCabangStatus(0);
        List<CabangModel> listCabangRej = cabangService.getListCabangStatus(1);

        List<Integer> listJmlItem = new ArrayList<>();
        for(CabangModel cabang: listCabang) {
            listJmlItem.add(cabang.getListItemCabang().size());
        }

        model.addAttribute("listCabangRej", listCabangRej);
        model.addAttribute("listCabang", listCabang);
        model.addAttribute("listJmlItem", listJmlItem);
        return "viewall-cabang-requested";
    }

    @GetMapping("/viewall/rejected")
    private String viewAllCabangRejected(Model model) {

        List<CabangModel> listCabang = cabangService.getListCabangStatus(1);

        List<Integer> listJmlItem = new ArrayList<>();
        for(CabangModel cabang: listCabang) {
            listJmlItem.add(cabang.getListItemCabang().size());
        }

        model.addAttribute("listCabang", listCabang);
        model.addAttribute("listJmlItem", listJmlItem);
        return "viewall-cabang-rejected";
    }

    @RequestMapping(value="/accept/{idCabang}", method = { RequestMethod.GET, RequestMethod.POST })
    private String acceptCabang(@PathVariable Long idCabang, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserModel currentUser = userService.findUserByUsername(currentUsername);

        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang);

        cabang.setStatus(2);
        cabang.setPenanggungJawab(currentUser);

        cabangService.addCabang(cabang);
        model.addAttribute("nama", cabang.getNama());
        model.addAttribute("namaUser", currentUser.getName());
        model.addAttribute("status","acc");
        return "request-status";
    }

    @RequestMapping(value="/decline/{idCabang}", method = { RequestMethod.GET, RequestMethod.POST })
    private String declineCabang(@PathVariable Long idCabang, Model model){
        CabangModel cabang = cabangService.getCabangByIdCabang(idCabang);

        cabang.setStatus(1);
        cabangService.addCabang(cabang);

        // cabangService.deleteCabang(cabang);

        model.addAttribute("nama", cabang.getNama());
        model.addAttribute("status","dec");
        return "request-status";
    }

    @GetMapping(value = "/tambahstok/{idCabang}/{idItem}")
    private String tambahStok(@PathVariable Long idCabang, @PathVariable Long idItem, Model model){
        ItemCabangModel item = itemCabangService.getItemCabangByIdItemCabang(idItem);

        model.addAttribute("idCabang", idCabang);
        model.addAttribute("idCabang", idItem);
        model.addAttribute("namaItem",item.getNama());
        return "tambah-stok-item";
    }

    @RequestMapping(value = "/tambahstok/{idCabang}/{idItem}", method = { RequestMethod.POST })
    private String tambahStokSubmit(@PathVariable Long idCabang, @PathVariable Long idItem, @RequestParam Integer stok, Model model){
        String uuid = itemCabangService.getItemCabangByIdItemCabang(idItem).getUuid_item();

        itemCabangService.tambahanStok(uuid, stok, idCabang);

        System.out.println(idCabang.toString() + " " + uuid.toString() + " " + stok.toString());
        return "tambah-stok-success";
    }

    @GetMapping(value = "/list-coupon")
    private List<KuponModel> retrieveListCoupon() { return kuponRestService.retrieveListKuponModel(); }
}
