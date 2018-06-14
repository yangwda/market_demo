package cn.yj.market.module.common.bean;


public class GiftConfigFormBean {
	// config
	private String giftConfigId;
	private String giftConfigDesc ;
	private String giftConfigType ;
	private String giftConfigBeginTime ;
	private String giftConfigEndTime ;
	private String giftConfigRemarks;
	private String goodsId;
	private String goodsName;
	private String goodsNo;
	private String buyLimit;
	private String giftAmount;
	
	// line
	private String giftConfigLineId ;
	private String giftGoodsId ;
	private String giftGoodsName ;
	private String giftGoodsNo ;
	private String giftGoodsCount ;
	private String giftGoodsCountUnit ;
	
	// line array
	String[] lineStrArr ;
	
	public String getGiftConfigId() {
		return giftConfigId;
	}
	public void setGiftConfigId(String giftConfigId) {
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
	public String getGiftConfigBeginTime() {
		return giftConfigBeginTime;
	}
	public void setGiftConfigBeginTime(String giftConfigBeginTime) {
		this.giftConfigBeginTime = giftConfigBeginTime;
	}
	public String getGiftConfigEndTime() {
		return giftConfigEndTime;
	}
	public void setGiftConfigEndTime(String giftConfigEndTime) {
		this.giftConfigEndTime = giftConfigEndTime;
	}
	public String getGiftConfigRemarks() {
		return giftConfigRemarks;
	}
	public void setGiftConfigRemarks(String giftConfigRemarks) {
		this.giftConfigRemarks = giftConfigRemarks;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
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
	public String getBuyLimit() {
		return buyLimit;
	}
	public void setBuyLimit(String buyLimit) {
		this.buyLimit = buyLimit;
	}
	public String getGiftAmount() {
		return giftAmount;
	}
	public void setGiftAmount(String giftAmount) {
		this.giftAmount = giftAmount;
	}
	public String getGiftConfigLineId() {
		return giftConfigLineId;
	}
	public void setGiftConfigLineId(String giftConfigLineId) {
		this.giftConfigLineId = giftConfigLineId;
	}
	public String getGiftGoodsId() {
		return giftGoodsId;
	}
	public void setGiftGoodsId(String giftGoodsId) {
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
	public String getGiftGoodsCount() {
		return giftGoodsCount;
	}
	public void setGiftGoodsCount(String giftGoodsCount) {
		this.giftGoodsCount = giftGoodsCount;
	}
	public String getGiftGoodsCountUnit() {
		return giftGoodsCountUnit;
	}
	public void setGiftGoodsCountUnit(String giftGoodsCountUnit) {
		this.giftGoodsCountUnit = giftGoodsCountUnit;
	}
	public String[] getLineStrArr() {
		return lineStrArr;
	}
	public void setLineStrArr(String[] lineStrArr) {
		this.lineStrArr = lineStrArr;
	}
}
