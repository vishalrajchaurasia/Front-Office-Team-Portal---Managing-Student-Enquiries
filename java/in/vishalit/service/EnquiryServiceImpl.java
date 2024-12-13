package in.vishalit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vishalit.binding.DashboardResponse;
import in.vishalit.binding.EnquiryForm;
import in.vishalit.binding.EnquirySearchCriteria;
import in.vishalit.constants.AppConstants;
import in.vishalit.entity.CourseEntity;
import in.vishalit.entity.EnqStatusEntity;
import in.vishalit.entity.StudentEnqEntity;
import in.vishalit.entity.UserDtlsEntity;
import in.vishalit.repo.CourseRepo;
import in.vishalit.repo.EnqStatusRepo;
import in.vishalit.repo.StudentEnqRepo;
import in.vishalit.repo.UserDtlsRepo;
@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashboardData(Integer userId) {//based on the userID(Integer userId)
		//i want the send data in the form of one object that is called DashboardResponse
		DashboardResponse response = new DashboardResponse();
		
		
		Optional<UserDtlsEntity> findById = 
				userDtlsRepo.findById(userId);//based on the useId i am getting the user record
		
		if(findById.isPresent()) {
			UserDtlsEntity userEntity = findById.get();
			
			List<StudentEnqEntity> enquiries = 
					userEntity.getEnquiries();
			
			Integer totalCnt = enquiries.size();
			
			Integer enrolledCnt = enquiries.stream()
			             .filter(e->e.getEnqStatus().equals("Enrolled"))
			             .collect(Collectors.toList()).size();
			
			int lostCnt = enquiries.stream()
			    .filter(e->e.getEnqStatus().equals("Lost"))
			    .collect(Collectors.toList()).size();
			
			//now creating response object
			response.setTotalEnquriesCnt(totalCnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
			
		}
		return response;
	}

	@Override
	public List<String> getCourses() {
		List<CourseEntity> findAll = courseRepo.findAll();
		
		List<String> names = new ArrayList<>();
		
		for(CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnqStatuses() {
		List<EnqStatusEntity> findAll = statusRepo.findAll();
		
		List<String> statusList = new ArrayList<>();
		for(EnqStatusEntity entity :findAll) {
			statusList.add(entity.getStatusName());
		}
		return statusList;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {
		//copy data form object to entity object
		StudentEnqEntity enqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);
		
		//userId is available in the session
		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);
		
		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();
		enqEntity.setUser(userEntity);
		
		enqRepo.save(enqEntity);//this enq is belong to particular users
		
		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquries() {
		Integer userId  =(Integer) session.getAttribute(AppConstants.STR_USER_ID);
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			return enquiries;
		}
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilterEnqs(EnquirySearchCriteria criteria, Integer userId) {
		
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			
			//filter logic
			if(null!=criteria.getCourseName() 
					& !"".equals(criteria.getCourseName())) {
			
			enquiries =	enquiries.stream()
				         .filter(e->e.getCourseName().equals(criteria.getCourseName()))
				         .collect(Collectors.toList());
		}
		
		if(null!=criteria.getEnqStatus() 
				& !"".equals(criteria.getEnqStatus())) {
		
			enquiries = enquiries.stream()
			         .filter(e->e.getEnqStatus().equals(criteria.getEnqStatus()))
			         .collect(Collectors.toList());
		}
		
		if(null!=criteria.getClassMode() 
				& !"".equals(criteria.getClassMode())) {
		
			enquiries = enquiries.stream()
			         .filter(e->e.getClassMode().equals(criteria.getClassMode()))
			         .collect(Collectors.toList());
		}
			return enquiries;
		}
		return null;
	}

	

}
