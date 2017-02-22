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
@Table(name="yj_market_member_year_accumulation_line") 
public class MarketMemberYearAccumulationLine extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1011725959162285499L;

	@Override
	public void initialize() {

	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long yearAccumulationLineId ;
	private Long yearAccumulationId ;
	private Long orderId ;
	private String orderNo ;
	private BigDecimal yearAccumulationMoney ;
	private Date createTime ;

	public Long getYearAccumulationLineId() {
		return yearAccumulationLineId;
	}
	public void setYearAccumulationLineId(Long yearAccumulationLineId) {
		this.yearAccumulationLineId = yearAccumulationLineId;
	}
	public Long getYearAccumulationId() {
		return yearAccumulationId;
	}
	public void setYearAccumulationId(Long yearAccumulationId) {
		this.yearAccumulationId = yearAccumulationId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public BigDecimal getYearAccumulationMoney() {
		return yearAccumulationMoney;
	}
	public void setYearAccumulationMoney(BigDecimal yearAccumulationMoney) {
		this.yearAccumulationMoney = yearAccumulationMoney;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
