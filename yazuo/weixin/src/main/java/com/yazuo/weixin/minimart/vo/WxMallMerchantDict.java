package com.yazuo.weixin.minimart.vo;

import java.util.Date;

/**
* @ClassName WxMallMerchantDict
* @Description  微信商户参数
* @author sundongfeng@yazuo.com
* @date 2014-8-6 下午2:35:21
* @version 1.0
*/
public class WxMallMerchantDict {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_merchant_dict.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_merchant_dict.app_id
     *
     * @mbggenerated
     */
    private String appId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_merchant_dict.app_secret
     *
     * @mbggenerated
     */
    private String appSecret;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_merchant_dict.app_key
     *
     * @mbggenerated
     */
    private String appKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_merchant_dict.partner_id
     *
     * @mbggenerated
     */
    private String partnerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_merchant_dict.partner_key
     *
     * @mbggenerated
     */
    private String partnerKey;
    
    private Integer brandId;
    private Date createTime;
    
    private String mails;
    private String subject;
    private String contents;
    private String partnerPass;//密码
    private String certificateUrl;//证书路径
    private String certificateName;//证书名称
    private String pagePicurl;//主页图url
    private String pageColor;//主要color
    //2015-1-20 sundongfeng
    private String loginName;
    private String loginPwd;
    private Boolean isCertification;
    private Boolean isOpenPayment;
    private Integer v2_v3;
    
    public String getPartnerPass() {
		return partnerPass;
	}

	public void setPartnerPass(String partnerPass) {
		this.partnerPass = partnerPass;
	}

	public String getCertificateUrl() {
		return certificateUrl;
	}

	public void setCertificateUrl(String certificateUrl) {
		this.certificateUrl = certificateUrl;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getPagePicurl() {
		return pagePicurl;
	}

	public void setPagePicurl(String pagePicurl) {
		this.pagePicurl = pagePicurl;
	}

	public String getPageColor() {
		return pageColor;
	}

	public void setPageColor(String pageColor) {
		this.pageColor = pageColor;
	}

	public String getMails() {
		return mails;
	}

	public void setMails(String mails) {
		this.mails = mails;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_merchant_dict.id
     *
     * @return the value of weixin_mall_merchant_dict.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_merchant_dict.id
     *
     * @param id the value for weixin_mall_merchant_dict.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_merchant_dict.app_id
     *
     * @return the value of weixin_mall_merchant_dict.app_id
     *
     * @mbggenerated
     */
    public String getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_merchant_dict.app_id
     *
     * @param appId the value for weixin_mall_merchant_dict.app_id
     *
     * @mbggenerated
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_merchant_dict.app_secret
     *
     * @return the value of weixin_mall_merchant_dict.app_secret
     *
     * @mbggenerated
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_merchant_dict.app_secret
     *
     * @param appSecret the value for weixin_mall_merchant_dict.app_secret
     *
     * @mbggenerated
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_merchant_dict.app_key
     *
     * @return the value of weixin_mall_merchant_dict.app_key
     *
     * @mbggenerated
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_merchant_dict.app_key
     *
     * @param appKey the value for weixin_mall_merchant_dict.app_key
     *
     * @mbggenerated
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_merchant_dict.partner_id
     *
     * @return the value of weixin_mall_merchant_dict.partner_id
     *
     * @mbggenerated
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_merchant_dict.partner_id
     *
     * @param partnerId the value for weixin_mall_merchant_dict.partner_id
     *
     * @mbggenerated
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId == null ? null : partnerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_merchant_dict.partner_key
     *
     * @return the value of weixin_mall_merchant_dict.partner_key
     *
     * @mbggenerated
     */
    public String getPartnerKey() {
        return partnerKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_merchant_dict.partner_key
     *
     * @param partnerKey the value for weixin_mall_merchant_dict.partner_key
     *
     * @mbggenerated
     */
    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey == null ? null : partnerKey.trim();
    }

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public Boolean getIsCertification() {
		return isCertification;
	}

	public void setIsCertification(Boolean isCertification) {
		this.isCertification = isCertification;
	}

	public Boolean getIsOpenPayment() {
		return isOpenPayment;
	}

	public void setIsOpenPayment(Boolean isOpenPayment) {
		this.isOpenPayment = isOpenPayment;
	}

	public Integer getV2_v3() {
		return v2_v3;
	}

	public void setV2_v3(Integer v2_v3) {
		this.v2_v3 = v2_v3;
	}

}