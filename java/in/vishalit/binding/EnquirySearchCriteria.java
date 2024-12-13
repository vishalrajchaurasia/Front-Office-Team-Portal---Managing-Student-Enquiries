package in.vishalit.binding;

public class EnquirySearchCriteria {
	private String  courseName;
	private String classMode;
	private String enqStatus;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getClassMode() {
		return classMode;
	}
	public void setClassMode(String classMode) {
		this.classMode = classMode;
	}
	public String getEnqStatus() {
		return enqStatus;
	}
	public void setEnqStatus(String enqStatus) {
		this.enqStatus = enqStatus;
	}
	@Override
	public String toString() {
		return "EnquirySearchCriteria [courseName=" + courseName + ", classMode=" + classMode + ", enqStatus="
				+ enqStatus + "]";
	}
	
	
}
