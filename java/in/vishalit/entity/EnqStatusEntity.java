package in.vishalit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "AIT_ENQUIRY_STATUS")
public class EnqStatusEntity {
	@Id
	@GeneratedValue
	private Integer statusId;
	private String statusName;
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	@Override
	public String toString() {
		return "EnqStatusEntity [statusId=" + statusId + ", statusName=" + statusName + "]";
	}
	
}
