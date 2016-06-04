package com.hesyun.framework.tenant.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lsxy.framework.core.persistence.IdEntity;
import com.lsxy.framework.core.utils.DateUtils;
import com.lsxy.framework.core.utils.JSONUtil;

/**
 * 
 * 租户
 * 
 * @author WangYun
 * 
 */
@Entity
@Table(schema="hsy_service_tenant",name = "tb_tenant")
@Where(clause="deleted=0")
@XmlRootElement
public class Tenant extends IdEntity{
	private static final long serialVersionUID = 1L;
	public static int TENANT_STATE_WAITFOR_CHECK = 0;// 待审批
	public static int TENANT_STATE_OK = 1;// 通过
	public static int TENANT_STATE_NOTOK = 2;// 异常，审核不通过等
	public static int TENANT_STATE_WAITFO_ACTIVE = 3;// 新注册，等待邮件激活

	public static Integer TENANT_SNS_OPEN_CHANNEL_YES = 1; // 开通渠道
	public static Integer TENANT_SNS_OPEN_CHANNEL_NO = 0; // 开通渠道
	
	public static Integer TENANT_SCENE_SITE = 1;		// 网站注册
	public static Integer TENANT_SCENE_MOBILE = 2;		// 开放微网页注册
	public static Integer TENANT_SCENE_WEIXIN = 3;		// 微信公众号入驻注册
	public static Integer TENANT_SCENE_OTHER = 4;		// 其它
	public static Integer TENANT_SCENE_B2B2C = 10;		// B2B2C接口	
	
	public static final int TENANT_TYPE_HESYUN = 1; // 租户类别:和声云运营中心
	public static final int TENANT_TYPE_AGENT = 2; // 租户类别：代理商
	public static final int TENANT_TYPE_COMMON = 3; // 租户类别：普通租户
	public static final int TENANT_TYPE_SUBAGENT = 4; // 租户类别：代理商下租户

	// 租户唯一名称
	private String tenantUn;

	// 租户序列号 1-100000
	private int tenantUid;

	// 租户名称
	private String tenantName;

	// 状态 :
	private int tenantStatus;

	// 备注
	private String tenantRemark;

	// 租户类型： 1-公司;2-个体
	private String tenantKind="1";
	
	// 租户类型：1：代理运营商；2：普通租户
	private int tenantType;
	
//	@JsonIgnore
		// 上级代理运营租户
	private Tenant agentTenant;

	// LOGO图片地址
	private String logoUrl;

	// 创建时间
	private Date createDt = new Date();

	// 公司信息
	private TenantCompany tenantCompany;


	// 租户管理员在和声云微信中的openid
//	private String hsyWeixinOpenid;

	// 租户管理员在和声云中的unionId
//	private String hsyWeixinUnionId;

	// 客服二维码5个,逗号分隔，可用来获取二维码图片
	// https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=token
//	private String kfEwmTokens;

	// 审核人
//	private String checker;

	// 审核时间
//	private Date checkTime;

	// 审核意见回复
//	private String checkReply;
	
//	// 快捷回复配置
//	private Set<TenantQuickReply> quickReplys;
//	
//	// 是否开通微信渠道：0：否；1：开通
//	private Integer useWeixin = 1;
//
//	// 是否开通微博渠道：0：否；1：开通
//	private Integer useWeibo = 1;
//
//	// 是否开通淘宝渠道：0：否；1：开通
//	private Integer useTaobao = 0;

	// 是否开通京东渠道：0：否；1：开通
//	private Integer useJd = 0;
	
	// 是否开通座席-PC端：0：否；1：开通
//	private Integer useZxPC = 1;
//	
//	// 是否开通座席-微信服务号端：0：否；1：开通
//	private Integer useZxWX = 0;
//	
//	// 是否开通座席-微博端：0：否；1：开通
//	private Integer useZxWeibo = 0;
	
	// 注册场景（来源）
//	private Integer sceneId;
	
//	// 租户座席
//	private Set<TenantZX> tenantZx;
	
//	@Column(name = "checker")
//	public String getChecker() {
//		return checker;
//	}
//
//	public void setChecker(String checker) {
//		this.checker = checker;
//	}
//
//	@Column(name = "check_time")
//	public Date getCheckTime() {
//		return checkTime;
//	}
//
//	public void setCheckTime(Date checkTime) {
//		this.checkTime = checkTime;
//	}
//
//	@Column(name = "check_reply")
//	public String getCheckReply() {
//		return checkReply;
//	}
//
//	public void setCheckReply(String checkReply) {
//		this.checkReply = checkReply;
//	}
//
//	@Column(name = "tenant_kf_ewm_tokens")
//	public String getKfEwmTokens() {
//		return kfEwmTokens;
//	}
//
//	public void setKfEwmTokens(String kfEwmTokens) {
//		this.kfEwmTokens = kfEwmTokens;
//	}
//
//	@Column(name = "tenant_wx_hsy_openid")
//	public String getHsyWeixinOpenid() {
//		return hsyWeixinOpenid;
//	}
//
//	public void setHsyWeixinOpenid(String hsyWeixinOpenid) {
//		this.hsyWeixinOpenid = hsyWeixinOpenid;
//	}
//	
//	@Column(name="tenant_wx_hsy_unionid")
//	public String getHsyWeixinUnionId() {
//		return hsyWeixinUnionId;
//	}
//
//	public void setHsyWeixinUnionId(String hsyWeixinUnionId) {
//		this.hsyWeixinUnionId = hsyWeixinUnionId;
//	}

	public static String[] toJsonProperties = new String[] { "id", "tenantUn",
			"tenantName", "tenantStatus", "tenantRemark", "tenantKind",
			"logoUrl", "createDt", "checkTime", "time", "useWeibo", "useTaobao", "useJd", "useZxPC", "useZxWX", "useZxWeibo"};

	@Column(name = "tenant_uid")
	public int getTenantUid() {
		return tenantUid;
	}

	public void setTenantUid(int tenantUid) {
		this.tenantUid = tenantUid;
	}

	@Column(name = "tenant_un")
	public String getTenantUn() {
		return tenantUn;
	}

	public void setTenantUn(String tenantUn) {
		this.tenantUn = tenantUn;
	}

	@Column(name = "tenant_name")
	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	@Column(name = "tenant_status")
	public int getTenantStatus() {
		return tenantStatus;
	}

	public void setTenantStatus(int tenantStatus) {
		this.tenantStatus = tenantStatus;
	}

	@Column(name = "tenant_remark")
	public String getTenantRemark() {
		return tenantRemark;
	}

	public void setTenantRemark(String tenantRemark) {
		this.tenantRemark = tenantRemark;
	}

	@Column(name = "tenant_kind")
	public String getTenantKind() {
		return tenantKind;
	}

	public void setTenantKind(String tenantKind) {
		this.tenantKind = tenantKind;
	}

	@Column(name = "logo_url")
	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	@Column(name = "createdt")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	@OneToOne(mappedBy = "tenant", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@NotFound(action=NotFoundAction.IGNORE)
	public TenantCompany getTenantCompany() {
		return tenantCompany;
	}

	public void setTenantCompany(TenantCompany tenantCompany) {
		this.tenantCompany = tenantCompany;
	}
//
//	@OneToMany(mappedBy = "tenant", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonIgnore
//	public List<TenantContact> getContacts() {
//		return contacts;
//	}
//
//	public void setContacts(List<TenantContact> contacts) {
//		this.contacts = contacts;
//	}

	public String toJson() {
		return JSONUtil.objectToJson(this, toJsonProperties);
	}

//	@Column(name="use_wb")
//	public Integer getUseWeibo() {
//		return useWeibo;
//	}
//
//	public void setUseWeibo(Integer useWeibo) {
//		this.useWeibo = useWeibo;
//	}
//
//	@Column(name="use_taobao")
//	public Integer getUseTaobao() {
//		return useTaobao;
//	}
//
//	public void setUseTaobao(Integer useTaobao) {
//		this.useTaobao = useTaobao;
//	}
//
//	@Column(name="use_jd")
//	public Integer getUseJd() {
//		return useJd;
//	}
//
//	public void setUseJd(Integer useJd) {
//		this.useJd = useJd;
//	}

//	@Column(name="use_wx")
//	public Integer getUseWeixin() {
//		return useWeixin;
//	}
//
//	public void setUseWeixin(Integer useWeixin) {
//		this.useWeixin = useWeixin;
//	}

//	@Column(name="use_zx_pc")
//	public Integer getUseZxPC() {
//		return useZxPC;
//	}
//
//	public void setUseZxPC(Integer useZxPC) {
//		this.useZxPC = useZxPC;
//	}
//
//	@Column(name="use_zx_wx")
//	public Integer getUseZxWX() {
//		return useZxWX;
//	}
//
//	public void setUseZxWX(Integer useZxWX) {
//		this.useZxWX = useZxWX;
//	}
//
//	@Column(name="use_zx_weibo")
//	public Integer getUseZxWeibo() {
//		return useZxWeibo;
//	}
//
//	public void setUseZxWeibo(Integer useZxWeibo) {
//		this.useZxWeibo = useZxWeibo;
//	}

//	@OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonIgnore
//	public Set<TenantZX> getTenantZx() {
//		return tenantZx;
//	}
//
//	public void setTenantZx(Set<TenantZX> tenantZx) {
//		this.tenantZx = tenantZx;
//	}

//	@Column(name="scene_id")
//	public Integer getSceneId() {
//		return sceneId;
//	}
//
//	public void setSceneId(Integer sceneId) {
//		this.sceneId = sceneId;
//	}

	@Column(name="tenant_type")
	public int getTenantType() {
		return tenantType;
	}

	public void setTenantType(int tenantType) {
		this.tenantType = tenantType;
	}

	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="agent_tenant_id")
	public Tenant getAgentTenant() {
		return agentTenant;
	}

	public void setAgentTenant(Tenant agentTenant) {
		this.agentTenant = agentTenant;
	}

	@Transient @JsonIgnore
	public String getCreateDtFmt(){
		return DateUtils.formatDate(this.getCreateDt(), "yyyy-MM-dd HH:mm:ss");
	}
}