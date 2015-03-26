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
package com.yazuo.erp.statistics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.statistics.dao.MerchantCardCountStatisticsDao;
import com.yazuo.erp.statistics.service.MerchantCardCountStatisticsService;
import com.yazuo.erp.sys.dao.SysRemindDao;
import com.yazuo.erp.sys.dao.SysUserMerchantDao;
import com.yazuo.erp.sys.vo.SysRemindVO;
import com.yazuo.erp.sys.vo.SysUserMerchantVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-8 上午11:03:26
 */
@Service
public class MerchantCardCountStatisticsServiceImpl implements MerchantCardCountStatisticsService {
	@Resource
	private MerchantCardCountStatisticsDao merchantCardCountStatisticsDao;

	@Resource
	private SysUserMerchantDao sysUserMerchantDao;

	@Resource
	private SysRemindDao sysRemindDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean merchantCardCountStatistics(int type, int count, int quantity) {

		List<SysRemindVO> sysRemindList = new ArrayList<SysRemindVO>();// 提醒列表
		SysRemindVO sysRemindVO = null;

		// 1、商户卡余量统计
		List<Map<String, Object>> list = merchantCardCountStatisticsDao.merchantCardCountStatistics(type, count, quantity);

		// 2、创建提醒
		for (Map<String, Object> map : list) {
			// 1、查询商户对应的前端用户
			List<SysUserMerchantVO> sysUserMerchantList = sysUserMerchantDao.getSysUserMerchantByMerchantId((Integer) map
					.get("merchant_id"));

			for (SysUserMerchantVO sysUserMerchantVO : sysUserMerchantList) {
				sysRemindVO = createSysRemindVO(map, sysUserMerchantVO.getUserId());
				sysRemindList.add(sysRemindVO);
			}
		}

		// 3、批量插入提醒
		if (!CollectionUtils.isEmpty(sysRemindList)) {
			batchInsertSysRemind(sysRemindList);
		}
		return true;
	}

	/**
	 * @Description
	 * @param sysRemindList
	 * @return void
	 * @throws
	 */
	private void batchInsertSysRemind(List<SysRemindVO> sysRemindList) {
		int size = sysRemindList.size();
		if (size > 2000) {
			List<SysRemindVO> sysRemindVOListSplit = new ArrayList<SysRemindVO>();
			for (int i = 0; i < size; i++) {
				sysRemindVOListSplit.add(sysRemindList.get(i));
				if (sysRemindVOListSplit.size() == 2000) {
					sysRemindDao.batchInsertSysRemind(sysRemindVOListSplit);
					sysRemindVOListSplit.clear();
				}
			}
			sysRemindDao.batchInsertSysRemind(sysRemindVOListSplit);
		} else {
			sysRemindDao.batchInsertSysRemind(sysRemindList);
		}
	}

	private SysRemindVO createSysRemindVO(Map<String, Object> map, Integer userId) {
		SysRemindVO sysRemindVO = new SysRemindVO();
		sysRemindVO.setMerchantId((Integer) map.get("merchant_id"));
		sysRemindVO.setUserId(userId);
		sysRemindVO.setPriorityLevelType("01");
		sysRemindVO.setItemType("02");
		sysRemindVO.setItemContent(map.get("cardtype_name") + Constant.MERCHANT_CARD_REMIND_CONTENT);
		sysRemindVO.setItemStatus("1");
		sysRemindVO.setIsEnable("1");
		sysRemindVO.setInsertBy(Constant.DEFAULTUSERID);
		sysRemindVO.setInsertTime(new Date());
		sysRemindVO.setUpdateBy(Constant.DEFAULTUSERID);
		sysRemindVO.setUpdateTime(new Date());
		return sysRemindVO;
	}
}
