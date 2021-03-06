package cn.yj.market.frame.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_gift_config_line") 
public class MarketGiftConfigLine extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6827578322146831506L;

	@Override
	public void initialize() {
		
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long giftConfigLineId ;
	private Long giftConfigId ;
	private Long giftGoodsId ;
	private String giftGoodsName ;
	private String giftGoodsNo ;
	private Long giftGoodsCount ;
	private String giftGoodsCountUnit ;

	public Long getGiftConfigLineId() {
		return giftConfigLineId;
	}
	public void setGiftConfigLineId(Long giftConfigLineId) {
		this.giftConfigLineId = giftConfigLineId;
	}
	public Long getGiftConfigId() {
		return giftConfigId;
	}
	public void setGiftConfigId(Long giftConfigId) {
		this.giftConfigId = giftConfigId;
	}
	public Long getGiftGoodsId() {
		return giftGoodsId;
	}
	public void setGiftGoodsId(Long giftGoodsId) {
		this.giftGoodsId = giftGoodsId;
	}
	public String getGiftGoodsName() {
		return giftGoodsName;
	}
	public void setGiftGoodsName(String giftGoodsName) {
		this.giftGoodsName = giftGoodsName;
	}
	public String getGiftGoodsNo() {
		return giftGoodsNo;
	}
	public void setGiftGoodsNo(String giftGoodsNo) {
		this.giftGoodsNo = giftGoodsNo;
	}
	public Long getGiftGoodsCount() {
		return giftGoodsCount;
	}
	public void setGiftGoodsCount(Long giftGoodsCount) {
		this.giftGoodsCount = giftGoodsCount;
	}
	public String getGiftGoodsCountUnit() {
		return giftGoodsCountUnit;
	}
	public void setGiftGoodsCountUnit(String giftGoodsCountUnit) {
		this.giftGoodsCountUnit = giftGoodsCountUnit;
	}
}
