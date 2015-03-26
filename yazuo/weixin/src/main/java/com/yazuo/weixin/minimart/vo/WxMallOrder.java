package com.yazuo.weixin.minimart.vo;

import java.util.Date;

/**
* @ClassName WxMallOrder
* @Description 微信商城订单表
* @author sundongfeng@yazuo.com
* @date 2014-8-6 下午2:35:32
* @version 1.0
*/
public class WxMallOrder {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.open_id
     *
     * @mbggenerated
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.app_id
     *
     * @mbggenerated
     */
    private String appId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.notify_id
     *
     * @mbggenerated
     */
    private String notify_id; //改成这是，是方便beanUtil.copy

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.partner
     *
     * @mbggenerated
     */
    private String partner;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.bank_billno
     *
     * @mbggenerated
     */
    private String bank_billno;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.transaction_id
     *
     * @mbggenerated
     */
    private String transaction_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.sign
     *
     * @mbggenerated
     */
    private String sign;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.product_fee
     *
     * @mbggenerated
     */
    private Long product_fee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.transport_fee
     *
     * @mbggenerated
     */
    private Long transport_fee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.discount
     *
     * @mbggenerated
     */
    private Long discount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.integral
     *
     * @mbggenerated
     */
    private Long integral;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.total_fee
     *
     * @mbggenerated
     */
    private Double total_fee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.time_begin
     *
     * @mbggenerated
     */
    private Date timeBegin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.time_end
     *
     * @mbggenerated
     */
    private String time_end;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.trade_state
     *
     * @mbggenerated
     */
    private String trade_state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.out_trade_no
     *
     * @mbggenerated
     */
    private String out_trade_no;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.nonce_tr
     *
     * @mbggenerated
     */
    private String nonceTr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.fee_type
     *
     * @mbggenerated
     */
    private Long fee_type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.pay_type
     *
     * @mbggenerated
     */
    private Long payType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.sign_type
     *
     * @mbggenerated
     */
    private String sign_type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.buyer_alias
     *
     * @mbggenerated
     */
    private String buyerAlias;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.input_charset
     *
     * @mbggenerated
     */
    private String input_charset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.bank_type
     *
     * @mbggenerated
     */
    private String bank_type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.goods_id
     *
     * @mbggenerated
     */
    private Long goodsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.product_info
     *
     * @mbggenerated
     */
    private String productInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.pay_info
     *
     * @mbggenerated
     */
    private String payInfo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.is_subscribe
     *
     * @mbggenerated
     */
    private Boolean isSubscribe;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.app_signature
     *
     * @mbggenerated
     */
    private String appSignature;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.trade_mode
     *
     * @mbggenerated
     */
    private Long trade_mode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.order_state
     *
     * @mbggenerated
     */
    private Long orderState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.buy_num
     *
     * @mbggenerated
     */
    private Long buyNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.brand_id
     *
     * @mbggenerated
     */
    private Long brandId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.deliver_state
     *
     * @mbggenerated
     */
    private Long deliverState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order.source
     *
     * @mbggenerated
     */
    private Long source;
    
    private String mobile;
    private String drawbackDesc;
    private Boolean jifenState;
    private Long masterId; //消费记录id
    private String cardNo; //消费卡号
    private String password;//密码
    
    private String firstAddr;//省
    private String secondAddr;//市
    private String thirdAddr;//县
    private String detailAddr;//街道
    private String receiver;//收件人名字
    private String zipcode;//邮编
    private String drawbackNo;//退款单号
    private String expressCompany;//快递公司
    private String expressNo;//快递单号
    private Integer productType;//2 虚拟卡 1实体卡
    private String xnCardNo;//虚拟卡号
    
	public String getXnCardNo() {
		return xnCardNo;
	}

	public void setXnCardNo(String xnCardNo) {
		this.xnCardNo = xnCardNo;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getFirstAddr() {
		return firstAddr;
	}

	public void setFirstAddr(String firstAddr) {
		this.firstAddr = firstAddr;
	}

	public String getSecondAddr() {
		return secondAddr;
	}

	public void setSecondAddr(String secondAddr) {
		this.secondAddr = secondAddr;
	}

	public String getThirdAddr() {
		return thirdAddr;
	}

	public void setThirdAddr(String thirdAddr) {
		this.thirdAddr = thirdAddr;
	}

	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getDrawbackNo() {
		return drawbackNo;
	}

	public void setDrawbackNo(String drawbackNo) {
		this.drawbackNo = drawbackNo;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public Boolean getJifenState() {
		return jifenState;
	}

	public void setJifenState(Boolean jifenState) {
		this.jifenState = jifenState;
	}

	public String getDrawbackDesc() {
		return drawbackDesc;
	}

	public void setDrawbackDesc(String drawbackDesc) {
		this.drawbackDesc = drawbackDesc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getBank_billno() {
		return bank_billno;
	}

	public void setBank_billno(String bank_billno) {
		this.bank_billno = bank_billno;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Long getProduct_fee() {
		return product_fee;
	}

	public void setProduct_fee(Long product_fee) {
		this.product_fee = product_fee;
	}

	public Long getTransport_fee() {
		return transport_fee;
	}

	public void setTransport_fee(Long transport_fee) {
		this.transport_fee = transport_fee;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public Double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}

	public Date getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(Date timeBegin) {
		this.timeBegin = timeBegin;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getTrade_state() {
		return trade_state;
	}

	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getNonceTr() {
		return nonceTr;
	}

	public void setNonceTr(String nonceTr) {
		this.nonceTr = nonceTr;
	}

	public Long getFee_type() {
		return fee_type;
	}

	public void setFee_type(Long fee_type) {
		this.fee_type = fee_type;
	}

	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getBuyerAlias() {
		return buyerAlias;
	}

	public void setBuyerAlias(String buyerAlias) {
		this.buyerAlias = buyerAlias;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public Boolean getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(Boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getAppSignature() {
		return appSignature;
	}

	public void setAppSignature(String appSignature) {
		this.appSignature = appSignature;
	}

	public Long getTrade_mode() {
		return trade_mode;
	}

	public void setTrade_mode(Long trade_mode) {
		this.trade_mode = trade_mode;
	}

	public Long getOrderState() {
		return orderState;
	}

	public void setOrderState(Long orderState) {
		this.orderState = orderState;
	}

	public Long getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getDeliverState() {
		return deliverState;
	}

	public void setDeliverState(Long deliverState) {
		this.deliverState = deliverState;
	}

	public Long getSource() {
		return source;
	}

	public void setSource(Long source) {
		this.source = source;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}