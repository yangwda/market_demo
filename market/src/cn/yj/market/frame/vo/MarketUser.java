package cn.yj.market.frame.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.yj.market.frame.hibernate.BasePojo;

@Entity
@Table(name="yj_market_user") 
public class MarketUser extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8884836578563521945L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
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
