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

package com.yazuo.external.account.service;

import java.util.List;
import java.util.Map;

public interface MerchantProductService {
	List<String> getProductByMerchantId(Integer merchantId);

	Map<Integer, Object> getProductsAllMerchant();
}