package com.oda.controller.login;

import com.oda.dto.login.LoginDto;
import com.oda.service.Impl.LoginServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/home")
    public String getLoginPage(Model model){
        model.addAttribute("loginDto",new LoginDto());
        return "loginpage/loginpage";
    }
    @PostMapping("/home")
    public String getLogin(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                           BindingResult bindingResult,Model model){
        if (!bindingResult.hasErrors()){
          String userAccess= loginService.isValidUser(loginDto.getUsername()
                  ,loginDto.getPassword());
          if (userAccess.equals("patient")){
              //go to patient page
          }
          else if(userAccess.equals("admin")){
              //go to admin page
          }else if(userAccess.equals("doctor")){
              //go to doctor page
          }
          else {
              model.addAttribute("message","username and password invalid");
              model.addAttribute("loginDto",loginDto);
              return "loginpage/loginpage";
          }
        }else {
            model.addAttribute("loginDto",loginDto);
            return "loginpage/loginpage";
        }
        return "loginpage/loginpage";
    }
}
