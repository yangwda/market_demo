package cn.yj.market.frame.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_callback") 
public class MarketCallBack extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8884836578563521945L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long callBackId;
	private Long orderId;
	private Long memberId;
	private String memberName ;
	private String memberTel ;
	private String memberPhone ;
	private Date createTime ;
	private  String callBackRemarks ;
	private String callBackOver ;
	private Date callBackOverTime ;
	
	
	@Override
	public void initialize() {
		
	};
	
	public Long getCallBackId() {
		return callBackId;
	}
	public void setCallBackId(Long callBackId) {
		this.callBackId = callBackId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberTel() {
		return memberTel;
	}
	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCallBackRemarks() {
		return callBackRemarks;
	}
	public void setCallBackRemarks(String callBackRemarks) {
		this.callBackRemarks = callBackRemarks;
	}
	public String getCallBackOver() {
		return callBackOver;
	}
	public void setCallBackOver(String callBackOver) {
		this.callBackOver = callBackOver;
	}
	public Date getCallBackOverTime() {
		return callBackOverTime;
	}
	public void setCallBackOverTime(Date callBackOverTime) {
		this.callBackOverTime = callBackOverTime;
	}

}
