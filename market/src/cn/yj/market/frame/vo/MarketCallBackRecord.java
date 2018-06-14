package cn.yj.market.frame.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_callback_record") 
public class MarketCallBackRecord extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8095999289692544271L;

	@Override
	public void initialize() {
		
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long callBackRecordId;
	private Long callBackId;
	private String callBackTalkAbout ;
	private Date callBackTime ;

	public Long getCallBackRecordId() {
		return callBackRecordId;
	}
	public void setCallBackRecordId(Long callBackRecordId) {
		this.callBackRecordId = callBackRecordId;
	}
	public Long getCallBackId() {
		return callBackId;
	}
	public void setCallBackId(Long callBackId) {
		this.callBackId = callBackId;
	}
	public String getCallBackTalkAbout() {
		return callBackTalkAbout;
	}
	public void setCallBackTalkAbout(String callBackTalkAbout) {
		this.callBackTalkAbout = callBackTalkAbout;
	}
	public Date getCallBackTime() {
		return callBackTime;
	}
	public void setCallBackTime(Date callBackTime) {
		this.callBackTime = callBackTime;
	}
	
}
