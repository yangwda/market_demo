package cn.yj.market.frame.vo;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

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
	
	@OneToMany(mappedBy="goodsId",fetch=FetchType.LAZY)
	private List<MarketGoodsUnitPrice> unitPriceSet ;
	
	@Transient
	private String punit1 ;
	@Transient
	private String punit2 ;
	@Transient
	private String punit3 ;

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
	public String getPunit1() {
		if (StringUtils.isBlank(punit1)) {
			if (unitPriceSet != null) {
				Collections.sort(unitPriceSet, new Comparator<MarketGoodsUnitPrice>() {
					@Override
					public int compare(MarketGoodsUnitPrice o1,
							MarketGoodsUnitPrice o2) {
						if (o1 == null) {
							return 1 ;
						}
						if (o2 == null) {
							return -1 ;
						}
						return o1.getGoodsUnitRate().compareTo(o2.getGoodsUnitRate());
					}
				});
				boolean first = true ;
				StringBuilder pb = new StringBuilder();
				for (MarketGoodsUnitPrice up : unitPriceSet) {
					if (!first) {
						pb.append("*") ;
					}
					pb.append(up.getGoodsUnitRate()) ;
					first = false ;
				}
				punit1 = pb.toString() ;
			}
		}
		return punit1;
	}
	public void setPunit1(String punit1) {
		this.punit1 = punit1;
	}
	public String getPunit2() {
		if (StringUtils.isBlank(punit2)) {
			if (unitPriceSet != null) {
				Collections.sort(unitPriceSet, new Comparator<MarketGoodsUnitPrice>() {
					@Override
					public int compare(MarketGoodsUnitPrice o1,
							MarketGoodsUnitPrice o2) {
						if (o1 == null) {
							return 1 ;
						}
						if (o2 == null) {
							return -1 ;
						}
						return o1.getGoodsUnitRate().compareTo(o2.getGoodsUnitRate());
					}
				});
				boolean first = true ;
				StringBuilder pb = new StringBuilder();
				for (MarketGoodsUnitPrice up : unitPriceSet) {
					if (!first) {
						pb.append("*") ;
					}
					pb.append(up.getGoodsUnitName()) ;
					first = false ;
				}
				punit2 = pb.toString() ;
			}
		}
		return punit2;
	}
	public void setPunit2(String punit2) {
		this.punit2 = punit2;
	}
	public String getPunit3() {
		if (StringUtils.isBlank(punit3)) {
			if (unitPriceSet != null) {
				Collections.sort(unitPriceSet, new Comparator<MarketGoodsUnitPrice>() {
					@Override
					public int compare(MarketGoodsUnitPrice o1,
							MarketGoodsUnitPrice o2) {
						if (o1 == null) {
							return 1 ;
						}
						if (o2 == null) {
							return -1 ;
						}
						return o1.getGoodsUnitRate().compareTo(o2.getGoodsUnitRate());
					}
				});
				boolean first = true ;
				StringBuilder pb = new StringBuilder();
				for (MarketGoodsUnitPrice up : unitPriceSet) {
					if (!first) {
						pb.append("*") ;
					}
					pb.append(up.getGoodsUnitPrice()) ;
					first = false ;
				}
				punit3 = pb.toString() ;
			}
		}
		return punit3;
	}
	public void setPunit3(String punit3) {
		this.punit3 = punit3;
	}
	public List<MarketGoodsUnitPrice> getUnitPriceSet() {
		return unitPriceSet;
	}
	public void setUnitPriceSet(List<MarketGoodsUnitPrice> unitPriceSet) {
		this.unitPriceSet = unitPriceSet;
	}
	
}
