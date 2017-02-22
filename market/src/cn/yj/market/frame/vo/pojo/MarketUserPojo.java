package cn.yj.market.frame.vo.pojo;

import java.io.Serializable;
import java.util.Date;

import cn.yj.market.frame.hibernate.BasePojo;

@Deprecated
public abstract class MarketUserPojo extends BasePojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5462191855684300937L;
	
	private Long userId;
	
	private String loginName ;
	
	private String passwd ;
	
	private String defaultpwd ;
	
	private  String userName ;
	
	private String sex ;
	
	private String comm ;
	
	private String phone ;
	
	private String role ;
	
	private Date createTime ;
	
	@Override
	public void initialize() {
		
	};

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
 
	public String getDefaultpwd() {
		return defaultpwd;
	}

	public void setDefaultpwd(String defaultpwd) {
		this.defaultpwd = defaultpwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
