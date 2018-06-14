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
@Table(name="yj_market_member_gift") 
public class MarketMemberGift extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8670588327700268921L;

	@Override
	public void initialize() {

	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long memberGiftId ;
	private Long memberId ;
	private Date createTime ;
	private Long sourceOrderId ;
	private BigDecimal sourceGiftMoney ;
	private BigDecimal remainingMoney ;
	private String remarks ;

	public Long getMemberGiftId() {
		return memberGiftId;
	}
	public void setMemberGiftId(Long memberGiftId) {
		this.memberGiftId = memberGiftId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getSourceOrderId() {
		return sourceOrderId;
	}
	public void setSourceOrderId(Long sourceOrderId) {
		this.sourceOrderId = sourceOrderId;
	}
	public BigDecimal getSourceGiftMoney() {
		return sourceGiftMoney;
	}
	public void setSourceGiftMoney(BigDecimal sourceGiftMoney) {
		this.sourceGiftMoney = sourceGiftMoney;
	}
	public BigDecimal getRemainingMoney() {
		return remainingMoney;
	}
	public void setRemainingMoney(BigDecimal remainingMoney) {
		this.remainingMoney = remainingMoney;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
