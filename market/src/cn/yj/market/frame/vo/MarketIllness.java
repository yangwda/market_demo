package cn.yj.market.frame.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_illness") 
public class MarketIllness extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8246701355108115705L;

	@Override
	public void initialize() {

	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long illnessId ;
	private String illnessName ;
	private String callBack ;

	public Long getIllnessId() {
		return illnessId;
	}
	public void setIllnessId(Long illnessId) {
		this.illnessId = illnessId;
	}
	public String getIllnessName() {
		return illnessName;
	}
	public void setIllnessName(String illnessName) {
		this.illnessName = illnessName;
	}
	public String getCallBack() {
		return callBack;
	}
	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
}
