package cn.yj.market.frame.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_member_no_seq") 
public class MarketMemberNoSeq extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1394758419442751176L;

	@Override
	public void initialize() {

	}
	
	@Id
	private Long memberNoSeqId ;
	private Long memberNoSeq ;

	public Long getMemberNoSeqId() {
		return memberNoSeqId;
	}

	public void setMemberNoSeqId(Long memberNoSeqId) {
		this.memberNoSeqId = memberNoSeqId;
	}

	public Long getMemberNoSeq() {
		return memberNoSeq;
	}

	public void setMemberNoSeq(Long memberNoSeq) {
		this.memberNoSeq = memberNoSeq;
	}

}
