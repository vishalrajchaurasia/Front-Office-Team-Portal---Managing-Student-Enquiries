package in.vishalit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.vishalit.binding.DashboardResponse;
import in.vishalit.binding.EnquiryForm;
import in.vishalit.binding.EnquirySearchCriteria;
import in.vishalit.constants.AppConstants;
import in.vishalit.entity.StudentEnqEntity;
import in.vishalit.service.EnquiryService;
@Controller
public class EnquiryController {
	
	@Autowired
	private EnquiryService enqService;
	
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		//TODO: logic to fetch data for dashboard
		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);
		
		DashboardResponse dashboardData = enqService.getDashboardData(userId);
		
		model.addAttribute("dashboardData", dashboardData);
		
		return "dashboard";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		//TODO: get courses for drop down
		List<String> courses = enqService.getCourses();
		
		//TODO: get enq status for drop down
		List<String> enqStatuses = enqService.getEnqStatuses();
		
		//TODO: get binding class obj //send form binding to UI 
		EnquiryForm formObj = new EnquiryForm();
		
		//TODO: set the data in model object
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);
		
		return "add-enquiry";
	}
	
	
	
	@PostMapping("/addEnq")
	public String addEnquiryForm(@ModelAttribute("formObj") EnquiryForm formObj,Model model) {
		
		boolean status = enqService.saveEnquiry(formObj);
		
		if(status) {
			model.addAttribute("succMsg", "Enquiry Added");
		}else {
			model.addAttribute("errMsg", "Problem Occured");
		}
		
		return "add-enquiry";
	}
	
	private void initForm(Model model) {
		//TODO: get courses for drop down
				List<String> courses = enqService.getCourses();
				
				//TODO: get enq status for drop down
				List<String> enqStatuses = enqService.getEnqStatuses();
				
				//TODO: get binding class obj //send form binding to UI 
				EnquiryForm formObj = new EnquiryForm();
				
				//TODO: set the data in model object
				model.addAttribute("courseNames", courses);
				model.addAttribute("statusNames", enqStatuses);
				model.addAttribute("formObj", formObj);
	}
	
	@GetMapping("/enquires")
	public String viewEnquiriesPage(EnquirySearchCriteria criteria, Model model) {
		initForm(model);
		model.addAttribute("searchForm", new EnquirySearchCriteria());
		List<StudentEnqEntity> enquiries =enqService.getEnquries();
		model.addAttribute("enquiries", enquiries);
		return "view-enquiries";
	}
	
	@GetMapping("/filter-enquiries")
	public String getFilterEnqs(@RequestParam String cname,
			@RequestParam String status,
			@RequestParam String mode,Model model) {
		
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setClassMode(mode); 
		criteria.setEnqStatus(status);
		
		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);
		
		List<StudentEnqEntity> filterEnqs =
				enqService.getFilterEnqs(criteria, userId);
		
		
		model.addAttribute("enquiries", filterEnqs);
		return "filter-enquiries-page";
	}
	
}
