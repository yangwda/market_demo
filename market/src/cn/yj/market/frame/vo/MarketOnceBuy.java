package cn.yj.market.frame.vo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_once_buy") 
public class MarketOnceBuy extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2247476805583963951L;

	@Override
	public void initialize() {

	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long onceById ;
	private BigDecimal beginAmount ;
	private BigDecimal endAmount ;
	private Long perRate ;

	public Long getOnceById() {
		return onceById;
	}
	public void setOnceById(Long onceById) {
		this.onceById = onceById;
	}
	public BigDecimal getBeginAmount() {
		return beginAmount;
	}
	public void setBeginAmount(BigDecimal beginAmount) {
		this.beginAmount = beginAmount;
	}
	public BigDecimal getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}
	public Long getPerRate() {
		return perRate;
	}
	public void setPerRate(Long perRate) {
		this.perRate = perRate;
	}
}
