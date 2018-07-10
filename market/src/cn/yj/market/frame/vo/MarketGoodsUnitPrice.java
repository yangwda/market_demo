package cn.yj.market.frame.vo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_goods_unit_price") 
public class MarketGoodsUnitPrice extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 408980257197966883L;

	@Override
	public void initialize() {

	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long goodsUnitId ;
	private Long goodsId ;
	private String goodsUnitName ;
	private Integer goodsUnitRate ;
	private BigDecimal goodsUnitPrice ;

	public Long getGoodsUnitId() {
		return goodsUnitId;
	}
	public void setGoodsUnitId(Long goodsUnitId) {
		this.goodsUnitId = goodsUnitId;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsUnitName() {
		return goodsUnitName;
	}
	public void setGoodsUnitName(String goodsUnitName) {
		this.goodsUnitName = goodsUnitName;
	}
	public Integer getGoodsUnitRate() {
		return goodsUnitRate;
	}
	public void setGoodsUnitRate(Integer goodsUnitRate) {
		this.goodsUnitRate = goodsUnitRate;
	}
	public BigDecimal getGoodsUnitPrice() {
		return goodsUnitPrice;
	}
	public void setGoodsUnitPrice(BigDecimal goodsUnitPrice) {
		this.goodsUnitPrice = goodsUnitPrice;
	}
}
