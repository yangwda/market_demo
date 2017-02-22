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
@Table(name="yj_market_member_voucher") 
public class MarketMemberVoucher extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8670588327700268921L;

	@Override
	public void initialize() {

	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long memberVoucherId ;
	private Long memberId ;
	private Date createTime ;
	private Long sourceOrderId ;
	private BigDecimal sourceVoucherMoney ;
	private BigDecimal remainingMoney ;

	public Long getMemberVoucherId() {
		return memberVoucherId;
	}
	public void setMemberVoucherId(Long memberVoucherId) {
		this.memberVoucherId = memberVoucherId;
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
	public BigDecimal getSourceVoucherMoney() {
		return sourceVoucherMoney;
	}
	public void setSourceVoucherMoney(BigDecimal sourceVoucherMoney) {
		this.sourceVoucherMoney = sourceVoucherMoney;
	}
	public BigDecimal getRemainingMoney() {
		return remainingMoney;
	}
	public void setRemainingMoney(BigDecimal remainingMoney) {
		this.remainingMoney = remainingMoney;
	}
}
