package in.vishalit.binding;

public class UnlockForm {
	
	private String email;
	private String tempPwd;
	private String newPwd;
	private String confirmPwd;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTempPwd() {
		return tempPwd;
	}
	public void setTempPwd(String tempPwd) {
		this.tempPwd = tempPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	@Override
	public String toString() {
		return "UnlockForm [email=" + email + ", tempPwd=" + tempPwd + ", newPwd=" + newPwd + ", confirmPwd="
				+ confirmPwd + "]";
	}
	
	
}
