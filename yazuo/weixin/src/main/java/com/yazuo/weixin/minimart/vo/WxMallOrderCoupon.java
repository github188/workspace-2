package com.yazuo.weixin.minimart.vo;

import java.util.Date;

/**
* @ClassName WxMallOrderCoupon
* @Description 微信商城订单券
* @author sundongfeng@yazuo.com
* @date 2014-8-6 下午2:35:48
* @version 1.0
*/
public class WxMallOrderCoupon {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon.mobile
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon.open_id
     *
     * @mbggenerated
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon.coupon_id
     *
     * @mbggenerated
     */
    private Long couponId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon.out_trade_no
     *
     * @mbggenerated
     */
    private String outTradeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon.brand_id
     *
     * @mbggenerated
     */
    private Long brandId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon.goods_id
     *
     * @mbggenerated
     */
    private Long goodsId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon.id
     *
     * @return the value of weixin_mall_order_coupon.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon.id
     *
     * @param id the value for weixin_mall_order_coupon.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon.mobile
     *
     * @return the value of weixin_mall_order_coupon.mobile
     *
     * @mbggenerated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon.mobile
     *
     * @param mobile the value for weixin_mall_order_coupon.mobile
     *
     * @mbggenerated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon.open_id
     *
     * @return the value of weixin_mall_order_coupon.open_id
     *
     * @mbggenerated
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon.open_id
     *
     * @param openId the value for weixin_mall_order_coupon.open_id
     *
     * @mbggenerated
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon.coupon_id
     *
     * @return the value of weixin_mall_order_coupon.coupon_id
     *
     * @mbggenerated
     */
    public Long getCouponId() {
        return couponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon.coupon_id
     *
     * @param couponId the value for weixin_mall_order_coupon.coupon_id
     *
     * @mbggenerated
     */
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon.out_trade_no
     *
     * @return the value of weixin_mall_order_coupon.out_trade_no
     *
     * @mbggenerated
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon.out_trade_no
     *
     * @param outTradeNo the value for weixin_mall_order_coupon.out_trade_no
     *
     * @mbggenerated
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon.create_time
     *
     * @return the value of weixin_mall_order_coupon.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon.create_time
     *
     * @param createTime the value for weixin_mall_order_coupon.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon.brand_id
     *
     * @return the value of weixin_mall_order_coupon.brand_id
     *
     * @mbggenerated
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon.brand_id
     *
     * @param brandId the value for weixin_mall_order_coupon.brand_id
     *
     * @mbggenerated
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon.goods_id
     *
     * @return the value of weixin_mall_order_coupon.goods_id
     *
     * @mbggenerated
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon.goods_id
     *
     * @param goodsId the value for weixin_mall_order_coupon.goods_id
     *
     * @mbggenerated
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}