package cn.yj.market.module.common.constants;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

public final class RatioConstants {
	private RatioConstants(){}
	
	public static final BigDecimal DRF_DAI_RATE = new BigDecimal(0.55) ;
	public static final BigDecimal DRF_BAO_RATE = new BigDecimal(0.70) ;
	public static final BigDecimal DRF_DUN_RATE = new BigDecimal(0.85) ;

	public static BigDecimal getDrfRate(String punit) {
		if (StringUtils.isBlank(punit)) {
			return BigDecimal.ONE ;
		}
		if ("袋".equals(punit)) {
			return BigDecimal.ONE.subtract(DRF_DAI_RATE) ;
		}
		if ("包".equals(punit)) {
			return BigDecimal.ONE.subtract(DRF_BAO_RATE) ;
		}
		if ("吨".equals(punit)) {
			return BigDecimal.ONE.subtract(DRF_DUN_RATE) ;
		}
		return BigDecimal.ONE ;
	}
}
