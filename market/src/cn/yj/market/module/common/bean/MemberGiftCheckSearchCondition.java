package cn.yj.market.module.common.bean;

import java.util.Date;

import cn.yj.market.frame.util.CoreUtils;

public class MemberGiftCheckSearchCondition {

	private String memberId ;
	private String memberName ;
	private String memberNo ;
	private String memberTel ;
	private String memberPhone ;
	private String memberQQ ;
	private String memberWeixin ;
	private String memberAddress ;
	private String memberBusiRemark ;
	private String common ;
	private String createTime ;
	private String likeStr ;
	
	public String getMemberId() {
		return memberId;
	}
	public Long getMemberIdLong() {
		return CoreUtils.parseLong(memberId);
	}
	public void setMemberId(String memberId) {
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
	public String getCreateTime() {
		return createTime;
	}
	public Date getCreateTimeDate() {
		return CoreUtils.parseDate(createTime);
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLikeStr() {
		return likeStr;
	}
	public void setLikeStr(String likeStr) {
		this.likeStr = likeStr;
	}
	
}
