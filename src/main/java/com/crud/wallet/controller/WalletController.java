package com.crud.wallet.controller;

import com.crud.wallet.dao.WalletDao;
import com.crud.wallet.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WalletController {

    @Autowired
    private WalletDao walletDao;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String newRegistration(ModelMap model){
        Wallet wallet = new Wallet();
        wallet.setMoneyAdd(0L);
        wallet.setPrice(0L);

        model.addAttribute("wallet", wallet);
        return "add";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveRegistration(@Valid Wallet wallet, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            System.out.println("HAS ERRORS!");
            return "add";
        }

        walletDao.save(wallet);
        wallet.setMoneyLeft(howMuchLeft());
        walletDao.save(wallet);
        return "redirect:/viewProducts";
    }

    @RequestMapping(value = "/viewProducts")
    public ModelAndView getAll(){
        List<Wallet> list = walletDao.findAll();
        return new ModelAndView("viewProducts","list",list);
    }

    @RequestMapping(value = "/editProduct/{id}")
    public String edit(@PathVariable Long id, ModelMap model){
        Wallet wallet = walletDao.findOne(id);
        model.addAttribute("wallet",wallet);
        return "editProduct";
    }

    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public ModelAndView editsave(@ModelAttribute("wallet") Wallet p){
        Wallet wallet = walletDao.findOne(p.getId());


        wallet.setName(p.getName());
        wallet.setPrice(p.getPrice());
        wallet.setMoneyAdd(p.getMoneyAdd());
        wallet.setMoneyLeft(howMuchLeft());

        walletDao.save(wallet);
        return new ModelAndView("redirect:/viewProducts");
    }

    @RequestMapping(value = "deleteProduct/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Long id){
        Wallet wallet = walletDao.findOne(id);
        walletDao.delete(wallet);
        return new ModelAndView("redirect:/viewProducts");
    }

    @RequestMapping(value = "/deleteAll")
    public ModelAndView delAll(){
        List<Wallet> list = walletDao.deleteAll();
        return new ModelAndView("redirect:/viewProducts","list",list);
    }


    @ModelAttribute(name = "money")
    public Long howMuchLeft(){
               Long left = walletDao.findAll().stream()
                       .collect(Collectors.summingLong(s -> s.getMoneyAdd() - s.getPrice())).longValue();
            return left;
    }

}
