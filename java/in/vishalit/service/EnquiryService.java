package in.vishalit.service;

import java.util.List;

import in.vishalit.binding.DashboardResponse;
import in.vishalit.binding.EnquiryForm;
import in.vishalit.binding.EnquirySearchCriteria;
import in.vishalit.entity.StudentEnqEntity;


public interface EnquiryService {
	
	public DashboardResponse getDashboardData(Integer userId); //input taking userId and output giving dashboard reponse 
	
	public List<String> getCourses();
	
	public List<String> getEnqStatuses();
	
	
	
	public boolean saveEnquiry(EnquiryForm form);
	
	public List<StudentEnqEntity> getEnquries();
	
	public List<StudentEnqEntity> getFilterEnqs(EnquirySearchCriteria criteria,Integer userId);
	
	
	
}
