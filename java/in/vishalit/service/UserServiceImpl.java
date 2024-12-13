package in.vishalit.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vishalit.binding.LoginForm;
import in.vishalit.binding.SignUpForm;
import in.vishalit.binding.UnlockForm;
import in.vishalit.constants.AppConstants;
import in.vishalit.entity.UserDtlsEntity;
import in.vishalit.repo.UserDtlsRepo;
import in.vishalit.util.EmailUtils;
import in.vishalit.util.PwdUtils;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;

	@Override
	public boolean signUp(SignUpForm form) {
		
		UserDtlsEntity user = userDtlsRepo.findByEmail(form.getEmail());
		
		if(user!=null) {
			return false;
		}
		
		//TODO: copy data from binding obj to entity object
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);
		
		
		//TODO: generate random password and set to object
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);//setting that to entity object
		
		//TODO: set account status as locked
		entity.setAccStatus(AppConstants.STR_LOCKED);
		
		//TODO: insert the record into table
		userDtlsRepo.save(entity);
		
		//TODO: send email to user to unlock the account 
		String to = form.getEmail();
		String subject=AppConstants.UNLOCK_EMAIL_SUBJECT;
		//String body="<h1> Use below temporary pwd to unlock your account</h1>";
		
		StringBuffer body = new StringBuffer("");
		body.append("<h1> Use below temporary pwd to unlock your account</h1>");
		
		body.append("Temporary pwd: "+tempPwd);
		
		body.append("<br/>");
		
		body.append("<a href=\"http://localhost:8080/unlock?email="+ to +"\">Click Here To Unlock Your Account</a>");
		
		emailUtils.sendEmail(to, subject, body.toString());
		
		return true;
	}

	

	@Override
	public boolean unlockAccount(UnlockForm form) {
		
		UserDtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());
		
		if(entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus(AppConstants.STR_UNLOCKED);
			userDtlsRepo.save(entity);
			return true;
			
		}else {
			return false;
		}
		
	}
	
	@Override
	public String login(LoginForm form) {
		UserDtlsEntity entity = 
				userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		if(entity == null) {
			return AppConstants.INVALID_CREDENTIALS_MSG;
		}
		if(entity.getAccStatus().equals(AppConstants.STR_LOCKED)) {
			return AppConstants.STR_ACC_LOCKED_MSG;
		}
		//create session and store user data in session
		session.setAttribute(AppConstants.STR_USER_ID, entity.getUserId());//the purpose of the which user logged into our application.
		return AppConstants.STR_SUCCESS ;
	}
	
	
	@Override
	public boolean forgotPwd(String email) {
		//check record present in DB with given Email
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);
		
		// if record not available return false
		if(entity==null) {
			return false;
		}
		
		//if record available send pwd to email and return true
		String subject= AppConstants.RECOVER_PWD_EMAIL_SUBJECT;
		String body="Your Pwd ::"+ entity.getPwd();	
		
		emailUtils.sendEmail(email,subject,body);
		
		return true;
	}

}
