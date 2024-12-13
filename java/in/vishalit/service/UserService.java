package in.vishalit.service;

import in.vishalit.binding.LoginForm;
import in.vishalit.binding.SignUpForm;
import in.vishalit.binding.UnlockForm;

public interface UserService {
	
	
	public boolean signUp(SignUpForm form);
	
	public String login(LoginForm form);
	
	public boolean unlockAccount(UnlockForm form);
	
	public boolean forgotPwd(String email);
	
}
