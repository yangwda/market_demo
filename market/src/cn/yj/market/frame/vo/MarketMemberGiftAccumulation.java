package cn.yj.market.frame.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_member_gift_accumulation") 
public class MarketMemberGiftAccumulation extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7432822381819572014L;

	@Override
	public void initialize() {

	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long giftAccumulationId ;
	private Long orderId ;
	private Long orderLineId ;
	private Long goodsId ;
	private String goodsName ;
	private Date buyTime ;
	private Long buyCount ;
	private Date checkTime ;
	private Long checkCount ;
	
	public String giftInfo() {
		return new StringBuilder("消费累积：").append(getGoodsName()).append(" ").append(buyCount).toString();
	}

	public Long getGiftAccumulationId() {
		return giftAccumulationId;
	}
	public void setGiftAccumulationId(Long giftAccumulationId) {
		this.giftAccumulationId = giftAccumulationId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getOrderLineId() {
		return orderLineId;
	}
	public void setOrderLineId(Long orderLineId) {
		this.orderLineId = orderLineId;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Date getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}
	public Long getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(Long buyCount) {
		this.buyCount = buyCount;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Long getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(Long checkCount) {
		this.checkCount = checkCount;
	}
}
