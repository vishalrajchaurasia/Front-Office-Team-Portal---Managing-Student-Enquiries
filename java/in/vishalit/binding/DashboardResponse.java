package in.vishalit.binding;

import lombok.Data;

@Data
public class DashboardResponse {
	private Integer totalEnquriesCnt;
	private Integer enrolledCnt;
	private Integer lostCnt;
	public Integer getTotalEnquriesCnt() {
		return totalEnquriesCnt;
	}
	public void setTotalEnquriesCnt(Integer totalEnquriesCnt) {
		this.totalEnquriesCnt = totalEnquriesCnt;
	}
	public Integer getEnrolledCnt() {
		return enrolledCnt;
	}
	public void setEnrolledCnt(Integer enrolledCnt) {
		this.enrolledCnt = enrolledCnt;
	}
	public Integer getLostCnt() {
		return lostCnt;
	}
	public void setLostCnt(Integer lostCnt) {
		this.lostCnt = lostCnt;
	}
	@Override
	public String toString() {
		return "DashboardResponse [totalEnquriesCnt=" + totalEnquriesCnt + ", enrolledCnt=" + enrolledCnt + ", lostCnt="
				+ lostCnt + "]";
	}
	
	
	
}
