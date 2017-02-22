package cn.yj.market.frame.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_member") 
public class MarketMember extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2551318511404074472L;

	@Override
	public void initialize() {

	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long memberId ;
	private String memberName ;
	private String memberNo ;
	private String memberTel ;
	private String memberPhone ;
	private String memberQQ ;
	private String memberWeixin ;
	private String memberAddress ;
	private String memberBusiRemark ;
	private String common ;
	private Date createTime ;

	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberTel() {
		return memberTel;
	}
	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getMemberQQ() {
		return memberQQ;
	}
	public void setMemberQQ(String memberQQ) {
		this.memberQQ = memberQQ;
	}
	public String getMemberWeixin() {
		return memberWeixin;
	}
	public void setMemberWeixin(String memberWeixin) {
		this.memberWeixin = memberWeixin;
	}
	public String getMemberAddress() {
		return memberAddress;
	}
	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}
	public String getMemberBusiRemark() {
		return memberBusiRemark;
	}
	public void setMemberBusiRemark(String memberBusiRemark) {
		this.memberBusiRemark = memberBusiRemark;
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
