package cn.yj.market.frame.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_gift_ct_config_line") 
public class MarketGiftCTConfigLine extends BasePojo {

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
	private String checkType ;
	private Long buyLimit ;
	private String buyLimitPunit ;
	private Long giftGoodsId ;
	private String giftGoodsName ;
	private String giftGoodsNo ;
	private Long giftGoodsCount ;
	private String giftGoodsCountUnit ;
	
	public String giftCommonStr(){
		StringBuilder cb = new StringBuilder() ;
		String ct = this.getCheckType() ;
		String act = "买" ;
		if ("累积".equals(ct)) {
			act = ct ;
		}
		cb.append(ct).append("[").append(act).append(" ")
				.append(this.getBuyLimit()).append(" ")
				.append(this.getBuyLimitPunit()).append(" ，送")
				.append(this.getGiftGoodsName()).append(" ")
				.append(this.getGiftGoodsCount())
				.append(this.getGiftGoodsCountUnit()).append("]");
		return cb.toString() ;
	}

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
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public Long getBuyLimit() {
		return buyLimit;
	}
	public void setBuyLimit(Long buyLimit) {
		this.buyLimit = buyLimit;
	}
	public String getBuyLimitPunit() {
		return buyLimitPunit;
	}
	public void setBuyLimitPunit(String buyLimitPunit) {
		this.buyLimitPunit = buyLimitPunit;
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
