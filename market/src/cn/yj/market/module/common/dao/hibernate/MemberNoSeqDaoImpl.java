package cn.yj.market.module.common.dao.hibernate;

import org.springframework.stereotype.Component;

import cn.yj.market.frame.hibernate.GenericDao;
import cn.yj.market.frame.vo.MarketMemberNoSeq;
import cn.yj.market.module.common.dao.MemberNoSeqDao;

@Component
public class MemberNoSeqDaoImpl extends GenericDao<MarketMemberNoSeq> implements MemberNoSeqDao {}
