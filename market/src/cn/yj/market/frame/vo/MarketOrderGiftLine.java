package cn.yj.market.frame.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_order_gift_line") 
public class MarketOrderGiftLine extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3260274047642504171L;

	@Override
	public void initialize() {

	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long giftLineId ;
	private Long orderId ;
	private Long orderLineId ;
	private Long goodsId ;
	private String goodsName ;
	private Long goodsCount ;
	private String goodsCountUnit ;
	private String giftLogic ;
	
	public String giftInfo() {
		return new StringBuilder("赠：").append(getGoodsName()).append(" ").append(getGoodsCount()).append(" ").append(getGoodsCountUnit()).toString();
	}

	public Long getGiftLineId() {
		return giftLineId;
	}
	public void setGiftLineId(Long giftLineId) {
		this.giftLineId = giftLineId;
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
	public String getGiftLogic() {
		return giftLogic;
	}
	public void setGiftLogic(String giftLogic) {
		this.giftLogic = giftLogic;
	}
}
