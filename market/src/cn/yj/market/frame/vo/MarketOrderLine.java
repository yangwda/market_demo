package cn.yj.market.frame.vo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_order_line") 
public class MarketOrderLine extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -160519698366734902L;

	@Override
	public void initialize() {

	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long orderLineId ;
	private Long orderId ;
	private Long goodsId ;
	private String goodsName ;
	private Long goodsCount ;
	private String goodsPrice;
	private BigDecimal goodsOrderPrice;
	private String goodsGift ;
	private String goodsGiftCheck ;

	public Long getOrderLineId() {
		return orderLineId;
	}
	public void setOrderLineId(Long orderLineId) {
		this.orderLineId = orderLineId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public Long getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}
	public String getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public BigDecimal getGoodsOrderPrice() {
		return goodsOrderPrice;
	}
	public void setGoodsOrderPrice(BigDecimal goodsOrderPrice) {
		this.goodsOrderPrice = goodsOrderPrice;
	}
	public String getGoodsGift() {
		return goodsGift;
	}
	public void setGoodsGift(String goodsGift) {
		this.goodsGift = goodsGift;
	}
	public String getGoodsGiftCheck() {
		return goodsGiftCheck;
	}
	public void setGoodsGiftCheck(String goodsGiftCheck) {
		this.goodsGiftCheck = goodsGiftCheck;
	}
}
