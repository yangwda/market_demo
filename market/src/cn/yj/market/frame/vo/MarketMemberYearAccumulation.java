package cn.yj.market.frame.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_member_year_accumulation") 
public class MarketMemberYearAccumulation extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4267554883651849894L;

	@Override
	public void initialize() {

	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long yearAccumulationId ;
	private String accumulationYear ;
	private Long memberId ;
	private String memberName ;
	private String memberNo ;
	private BigDecimal accumulationMoney ;
	private String checkStatus ;
	private Long orderId ;
	private Date checkTime ;

	public Long getYearAccumulationId() {
		return yearAccumulationId;
	}
	public void setYearAccumulationId(Long yearAccumulationId) {
		this.yearAccumulationId = yearAccumulationId;
	}
	public String getAccumulationYear() {
		return accumulationYear;
	}
	public void setAccumulationYear(String accumulationYear) {
		this.accumulationYear = accumulationYear;
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
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public BigDecimal getAccumulationMoney() {
		return accumulationMoney;
	}
	public void setAccumulationMoney(BigDecimal accumulationMoney) {
		this.accumulationMoney = accumulationMoney;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
}
