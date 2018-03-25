package cn.yj.market.frame.vo;

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

@Entity
@Table(name="yj_market_gift_ct_config") 
public class MarketGiftCTConfig extends BasePojo {

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
	private Double giftAmount;
	
	@OneToMany(mappedBy="giftConfigId",fetch=FetchType.LAZY)
	private List<MarketGiftCTConfigLine> lineList ;
	/*
	 * for(var i=0;i<row.lineList.length ;i++){
		    			var line = row.lineList[i] ;
		    			var ct = line.checkType ;
		    			var act = "买" ;
		    			if(ct == "累积"){
		    				act = "累积" ;
		    			}
		    			var info = "<strong>" + ct + "</strong>[ "+act + " " + line.buyLimit + " " + line.buyLimitPunit + 
		    			                   " ，送 " + line.giftGoodsName+" " +line.giftGoodsCount + line.giftGoodsCountUnit+" ]<br>" ; 
		    			msg += info ;
		    		}
	 */
	
	public String giftCommonStr(){
		if (lineList == null || lineList.isEmpty()) {
			return "无" ;
		}
		StringBuilder cb = new StringBuilder() ;
		for (MarketGiftCTConfigLine line : lineList) {
			if (cb.length() > 0) {
				cb.append("<br>") ;
			}
			String ct = line.getCheckType() ;
			String act = "买" ;
			if ("累积".equals(ct)) {
				act = ct ;
			}
			cb.append(ct).append("[").append(act).append(" ")
					.append(line.getBuyLimit()).append(" ")
					.append(line.getBuyLimitPunit()).append(" ，送")
					.append(line.getGiftGoodsName()).append(" ")
					.append(line.getGiftGoodsCount())
					.append(line.getGiftGoodsCountUnit()).append("]");
		}
		return cb.toString() ;
	}
	
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
	public Double getGiftAmount() {
		return giftAmount;
	}
	public void setGiftAmount(Double giftAmount) {
		this.giftAmount = giftAmount;
	}
	public List<MarketGiftCTConfigLine> getLineList() {
		return lineList;
	}
	public void setLineList(List<MarketGiftCTConfigLine> lineList) {
		this.lineList = lineList;
	}
	public void loadLine() {
		if (lineList != null && !lineList.isEmpty()) {
			lineList.get(0) ;
		}
	}
}
