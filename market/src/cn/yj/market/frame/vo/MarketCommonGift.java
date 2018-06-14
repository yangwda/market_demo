package cn.yj.market.frame.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_common_gift") 
public class MarketCommonGift extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8670588327700268921L;

	@Override
	public void initialize() {

	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long commonGiftId ;
	private String commonGiftName ;
	private String commonGiftUnit ;

	public Long getCommonGiftId() {
		return commonGiftId;
	}
	public void setCommonGiftId(Long commonGiftId) {
		this.commonGiftId = commonGiftId;
	}
	public String getCommonGiftName() {
		return commonGiftName;
	}
	public void setCommonGiftName(String commonGiftName) {
		this.commonGiftName = commonGiftName;
	}
	public String getCommonGiftUnit() {
		return commonGiftUnit;
	}
	public void setCommonGiftUnit(String commonGiftUnit) {
		this.commonGiftUnit = commonGiftUnit;
	}

}
