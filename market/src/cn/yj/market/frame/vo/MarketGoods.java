package cn.yj.market.frame.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_goods") 
public class MarketGoods extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2181048949775013632L;

	@Override
	public void initialize() {

	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long goodsId ;
	private String goodsName ;
	private String goodsNo ;
	private String goodsManufacturer ;
	private String goodsUsage ;
	private String goodsType ;
	private String goodsStatus ;
	private String goodsRemark ;
	private String common ;
	private Date createTime ;

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
	public String getGoodsManufacturer() {
		return goodsManufacturer;
	}
	public void setGoodsManufacturer(String goodsManufacturer) {
		this.goodsManufacturer = goodsManufacturer;
	}
	public String getGoodsUsage() {
		return goodsUsage;
	}
	public void setGoodsUsage(String goodsUsage) {
		this.goodsUsage = goodsUsage;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	public String getGoodsRemark() {
		return goodsRemark;
	}
	public void setGoodsRemark(String goodsRemark) {
		this.goodsRemark = goodsRemark;
	}
	public String getCommon() {
		return common;
	}
	public void setCommon(String common) {
		this.common = common;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
