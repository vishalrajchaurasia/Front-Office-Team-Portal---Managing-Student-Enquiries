package in.vishalit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.vishalit.binding.LoginForm;
import in.vishalit.binding.SignUpForm;
import in.vishalit.binding.UnlockForm;
import in.vishalit.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {
		boolean status = userService.signUp(form);
		
		if(status) {
			model.addAttribute("succMsg", "Account Created, check Your Email");
		}else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}
		return "signUp";
	}
	
	
	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}
	
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email,Model model) {
		
		UnlockForm unlockFormObj= new UnlockForm();//i created a object for binding class
		unlockFormObj.setEmail(email);//sending the data to binding object
		
		model.addAttribute("unlock", unlockFormObj);//sending the binding object to UI
		return "unlock";
	}
	
	  
	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock,Model model) {
		
		
		
		if(unlock.getNewPwd().equals(unlock.getConfirmPwd())){
			boolean status = userService.unlockAccount(unlock);
			
			if(status) {
				model.addAttribute("succMsg", "Your Account unlocked successfully");
			}else {
				model.addAttribute("errMsg", "Given Temporary Pwd is incorrect, check your email");
			}
		}else {
			model.addAttribute("errMsg", "New Pwd and confirm pwd should be same");
		}
		
		return "unlock";
	}
	
	@GetMapping("/login")  
	public String loginPage(Model model) {
		
		model.addAttribute("loginForm", new LoginForm()); 
		
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
		
		String status= userService.login(loginForm);
		
		if(status.contains("success")) {
			//redirect request to  dashboard method
			//return "dashboard";
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", status);
		return "login";
	}
	
	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email,Model model) {
		
		
		
		boolean status = userService.forgotPwd(email);
		
		if(status) {
			//send success msg
			model.addAttribute("succMsg", "Pwd sent to your email");
		}else {
			//send error msg
			model.addAttribute("errMsg", "Invalid email");
		}
		
		return "forgotPwd";
	}
}
