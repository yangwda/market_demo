package cn.yj.market.frame.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	private String goodsCountUnit ;
	private String goodsPrice;
	private BigDecimal goodsOrderPrice;
	private BigDecimal goodsDrfDiffAmount;
	private String goodsGift ;
	private String goodsGiftCheck ;
	private Long goodsGiftConfigId ;
	private BigDecimal giftAmount ;
	
	@OneToMany(mappedBy="orderLineId",fetch=FetchType.LAZY)
	private List<MarketOrderGiftLine> orderGiftLineSet ;
	
	@OneToMany(mappedBy="orderLineId",fetch=FetchType.LAZY)
	private List<MarketMemberGiftAccumulation> orderGiftAccLineSet ;

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
	public String getGoodsCountUnit() {
		return goodsCountUnit;
	}
	public void setGoodsCountUnit(String goodsCountUnit) {
		this.goodsCountUnit = goodsCountUnit;
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
	public BigDecimal getGoodsDrfDiffAmount() {
		return goodsDrfDiffAmount;
	}
	public void setGoodsDrfDiffAmount(BigDecimal goodsDrfDiffAmount) {
		this.goodsDrfDiffAmount = goodsDrfDiffAmount;
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
	public List<MarketOrderGiftLine> getOrderGiftLineSet() {
		return orderGiftLineSet;
	}
	public void setOrderGiftLineSet(List<MarketOrderGiftLine> orderGiftLineSet) {
		this.orderGiftLineSet = orderGiftLineSet;
	}
	public List<MarketMemberGiftAccumulation> getOrderGiftAccLineSet() {
		return orderGiftAccLineSet;
	}
	public void setOrderGiftAccLineSet(
			List<MarketMemberGiftAccumulation> orderGiftAccLineSet) {
		this.orderGiftAccLineSet = orderGiftAccLineSet;
	}
	public Long getGoodsGiftConfigId() {
		return goodsGiftConfigId;
	}
	public void setGoodsGiftConfigId(Long goodsGiftConfigId) {
		this.goodsGiftConfigId = goodsGiftConfigId;
	}
	public BigDecimal getGiftAmount() {
		return giftAmount;
	}
	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}
	
}
