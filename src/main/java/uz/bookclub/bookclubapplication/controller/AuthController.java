package uz.bookclub.bookclubapplication.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.bookclub.bookclubapplication.model.Result;
import uz.bookclub.bookclubapplication.payload.ActivationRequest;
import uz.bookclub.bookclubapplication.payload.RegisterReq;
import uz.bookclub.bookclubapplication.repository.UserRepository;
import uz.bookclub.bookclubapplication.service.AuthService;
import uz.bookclub.bookclubapplication.service.UserService;


import javax.validation.Valid;
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    private UserRepository userRepository;
    private UserDetailsService userDetailsService;

    @GetMapping(value = {"/login"})
    public String getHomePage() {
        return "login";
    }


     @GetMapping(value = {"/activation"})
        public String getActivationPage() {
            return "activation";
        }

    @GetMapping(value = {"/sign/up"})
    public String getRegisterPage(Model model, RegisterReq registerReq) {
        model.addAttribute("registerReq", registerReq);
        return "register";
    }


    @PostMapping(value = {"/sign/up"})
    public String registerUser(@Valid RegisterReq registerReq,
                               BindingResult bindingResult,
                               Model model) {

        Result result = new Result();

        if (bindingResult.hasErrors()) {
//            result = userService.saveUser(registerReq);
            model.addAttribute("result",result.getMessage());
            model.addAttribute("registerReq", registerReq);
            return "register";
        } else if (userService.saveUser(registerReq).isSuccess()){
            result = userService.saveUser(registerReq);
            model.addAttribute("result",result.getMessage());
            return "activation";
        }else {
            model.addAttribute("result",userService.saveUser(registerReq).getMessage());
            model.addAttribute("registerReq", registerReq);
            return "register";
        }
    }


    @GetMapping(value = {"/activate/user"})
    public String getActivePage(Model model,ActivationRequest activationRequest) {
            model.addAttribute("activationReq",activationRequest);
            return "activation";

    }

    @PostMapping(value = {"/wActivate/user"})
    public String getActiveUser(@Valid ActivationRequest activationRequest,BindingResult bindingResult,
                Model model) {
        Result result = authService.activateUser(activationRequest.getActiveCode());
        model.addAttribute("activationReq",activationRequest);
        if (!bindingResult.hasErrors() && result.isSuccess()) {
            return "redirect:/cabinet";
        } else {
            return "activation";
        }
    }

//    public String getActivePage(@RequestParam(name = "activeCode") String activeCode) {
//        Result result = authService.activateUser(activeCode);
//        if (result.isSuccess()) {
//            return "redirect:/cabinet";
//        } else {
//            return "activation";
//        }
//
//    }

   }

