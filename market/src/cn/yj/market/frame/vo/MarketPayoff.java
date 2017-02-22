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
@Table(name="yj_market_payoff") 
public class MarketPayoff extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8412559368408758520L;

	@Override
	public void initialize() {

	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long payOffId ;
	private Long orderId ;
	private Long memberId ;
	private String payOffWay ;
	private Date payOffTime ;
	private BigDecimal payOffMoney;
	private String payOffType;

	public Long getPayOffId() {
		return payOffId;
	}
	public void setPayOffId(Long payOffId) {
		this.payOffId = payOffId;
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
	public String getPayOffWay() {
		return payOffWay;
	}
	public void setPayOffWay(String payOffWay) {
		this.payOffWay = payOffWay;
	}
	public Date getPayOffTime() {
		return payOffTime;
	}
	public void setPayOffTime(Date payOffTime) {
		this.payOffTime = payOffTime;
	}
	public BigDecimal getPayOffMoney() {
		return payOffMoney;
	}
	public void setPayOffMoney(BigDecimal payOffMoney) {
		this.payOffMoney = payOffMoney;
	}
	public String getPayOffType() {
		return payOffType;
	}
	public void setPayOffType(String payOffType) {
		this.payOffType = payOffType;
	}
}
