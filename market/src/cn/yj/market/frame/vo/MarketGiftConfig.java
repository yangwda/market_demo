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
@Table(name="yj_market_gift_config") 
public class MarketGiftConfig extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1983092588317974337L;

	@Override
	public void initialize() {

	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long giftConfigId;
	private String giftConfigDesc ;
	private String giftConfigType ;
	private Date giftConfigBeginTime ;
	private Date giftConfigEndTime ;
	private String giftConfigRemarks;
	private Long goodsId;
	private String goodsName;
	private String goodsNo;
	private BigDecimal buyLimit;

	public Long getGiftConfigId() {
		return giftConfigId;
	}
	public void setGiftConfigId(Long giftConfigId) {
		this.giftConfigId = giftConfigId;
	}
	public String getGiftConfigDesc() {
		return giftConfigDesc;
	}
	public void setGiftConfigDesc(String giftConfigDesc) {
		this.giftConfigDesc = giftConfigDesc;
	}
	public String getGiftConfigType() {
		return giftConfigType;
	}
	public void setGiftConfigType(String giftConfigType) {
		this.giftConfigType = giftConfigType;
	}
	public Date getGiftConfigBeginTime() {
		return giftConfigBeginTime;
	}
	public void setGiftConfigBeginTime(Date giftConfigBeginTime) {
		this.giftConfigBeginTime = giftConfigBeginTime;
	}
	public Date getGiftConfigEndTime() {
		return giftConfigEndTime;
	}
	public void setGiftConfigEndTime(Date giftConfigEndTime) {
		this.giftConfigEndTime = giftConfigEndTime;
	}
	public String getGiftConfigRemarks() {
		return giftConfigRemarks;
	}
	public void setGiftConfigRemarks(String giftConfigRemarks) {
		this.giftConfigRemarks = giftConfigRemarks;
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
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	public BigDecimal getBuyLimit() {
		return buyLimit;
	}
	public void setBuyLimit(BigDecimal buyLimit) {
		this.buyLimit = buyLimit;
	}
}
