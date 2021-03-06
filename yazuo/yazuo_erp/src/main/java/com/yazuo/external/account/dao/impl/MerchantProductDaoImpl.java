/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.external.account.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yazuo.external.account.dao.MerchantProductDao;
import com.yazuo.external.account.vo.MerchantProductVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-6 下午5:36:42
 */
@Repository
public class MerchantProductDaoImpl implements MerchantProductDao {
	@Resource(name = "jdbcTemplateCrm210")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "jdbcTemplateCrm210Account")
	private JdbcTemplate jdbcTemplateCrm210Account;
	
	@Resource(name = "jdbcTemplateCrm210Trade")
	private JdbcTemplate jdbcTemplateCrm210Trade;

	@Override
	public List<String> getProductsByMerchantId(Integer merchantId) {
		Assert.notNull(merchantId, "商户ID不能为空");
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.product_name , p.product_id FROM " + " account.merchant_product AS mp,account.product AS p"
				+ " WHERE  p.product_id = mp.product_id ");
		if (merchantId.intValue() == 0) {
			sb.append(" AND mp.product_id IN(602, 33, 37)");
		}
		
		List<Map<String, Object>> list1 = jdbcTemplateCrm210Account.queryForList(sb.toString());
		
		List<Integer> list2 = jdbcTemplateCrm210Trade.queryForList("select merchant_id from trade.merchant" +
				" where merchant_id = " + merchantId , Integer.class);
		
		List<String> result = new ArrayList<String>();
		for (Map<String, Object> map : list1) {
			Integer productId = (Integer)map.get("product_id");
			for (Integer merchantId2 : list2) {
				if(productId.equals(merchantId2))
				result.add(map.get("product_name")+"");
			}
		}
		return result;
	}

	@Override
	public List<MerchantProductVO> getProductsAllMerchant(List<Integer> brandIdsList) {
		String sql = "SELECT mp.merchant_id,mp.product_id,p.product_name AS productName FROM "
				+ " account.merchant_product AS mp,account.product AS p"
				+ " WHERE P .product_id = mp.product_id  ORDER BY mp.merchant_id";
		 List<MerchantProductVO> list1 = jdbcTemplateCrm210Account.query(sql, new BeanPropertyRowMapper<MerchantProductVO>(MerchantProductVO.class));
		 
		 String theSql = "select merchant_id from trade.merchant " +
	 		"where merchant_id = ANY(ARRAY "+ brandIdsList + ")";
		 List<MerchantProductVO> list2 = jdbcTemplateCrm210Trade.query(theSql,new BeanPropertyRowMapper<MerchantProductVO>(MerchantProductVO.class));
		 
		 List<MerchantProductVO> result = new ArrayList<MerchantProductVO>();
		 for (MerchantProductVO merchantProductVO : list1) {
			 Integer merchantId = merchantProductVO.getMerchantId();
			 for (MerchantProductVO merchantProduct : list2) {
				if(merchantProduct.getMerchantId().equals(merchantId))
					result.add(merchantProductVO);
				}
		}
		 return result;
	}
}
