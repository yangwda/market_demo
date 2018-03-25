package cn.yj.market.frame.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="yj_market_order") 
public class MarketOrder extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6697562719475538732L;

	@Override
	public void initialize() {

	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long orderId ;
	private String orderNo ;
	private String orderType ;
	private Long memberId ;
	private String memberName ;
	private String memberNo ;
	private Long callBackId ;
	private String orderStatus ;
	private String orderRemark ;
	private Date createTime ;
	private BigDecimal orderTotalMoney ;
	private BigDecimal orderChargeMoney ;
	private BigDecimal orderCutMoney ;
	private BigDecimal yearAccumulationMoney ;
	private BigDecimal payOffVoucherTotalMoney ;
	private BigDecimal payOffCashTotalMoney ;
	private String payOffStatus ;
	private String salesReturn ;
	private BigDecimal orderTotalGiftAmount ;

	@JSONField(serialize=false)  
	@OneToMany(mappedBy="orderId",fetch=FetchType.LAZY)
	private List<MarketOrderLine> orderLineSet ;
	
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	public Long getCallBackId() {
		return callBackId;
	}
	public void setCallBackId(Long callBackId) {
		this.callBackId = callBackId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getOrderTotalMoney() {
		return orderTotalMoney;
	}
	public void setOrderTotalMoney(BigDecimal orderTotalMoney) {
		this.orderTotalMoney = orderTotalMoney;
	}
	public BigDecimal getOrderChargeMoney() {
		return orderChargeMoney;
	}
	public void setOrderChargeMoney(BigDecimal orderChargeMoney) {
		this.orderChargeMoney = orderChargeMoney;
	}
	public BigDecimal getOrderCutMoney() {
		return orderCutMoney;
	}
	public void setOrderCutMoney(BigDecimal orderCutMoney) {
		this.orderCutMoney = orderCutMoney;
	}
	public BigDecimal getYearAccumulationMoney() {
		return yearAccumulationMoney;
	}
	public void setYearAccumulationMoney(BigDecimal yearAccumulationMoney) {
		this.yearAccumulationMoney = yearAccumulationMoney;
	}
	public BigDecimal getPayOffVoucherTotalMoney() {
		return payOffVoucherTotalMoney;
	}
	public void setPayOffVoucherTotalMoney(BigDecimal payOffVoucherTotalMoney) {
		this.payOffVoucherTotalMoney = payOffVoucherTotalMoney;
	}
	public BigDecimal getPayOffCashTotalMoney() {
		return payOffCashTotalMoney;
	}
	public void setPayOffCashTotalMoney(BigDecimal payOffCashTotalMoney) {
		this.payOffCashTotalMoney = payOffCashTotalMoney;
	}
	public String getPayOffStatus() {
		return payOffStatus;
	}
	public void setPayOffStatus(String payOffStatus) {
		this.payOffStatus = payOffStatus;
	}
	public String getSalesReturn() {
		return salesReturn;
	}
	public void setSalesReturn(String salesReturn) {
		this.salesReturn = salesReturn;
	}
	public List<MarketOrderLine> getOrderLineSet() {
		return orderLineSet;
	}
	public void setOrderLineSet(List<MarketOrderLine> orderLineSet) {
		this.orderLineSet = orderLineSet;
	}
	public BigDecimal getOrderTotalGiftAmount() {
		return orderTotalGiftAmount;
	}
	public void setOrderTotalGiftAmount(BigDecimal orderTotalGiftAmount) {
		this.orderTotalGiftAmount = orderTotalGiftAmount;
	}
	
}
