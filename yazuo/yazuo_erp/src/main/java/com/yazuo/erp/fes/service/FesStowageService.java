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

package com.yazuo.erp.fes.service;

import java.util.List;
import java.util.Map;

import java.util.*;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface FesStowageService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
 * @param sessionUser 
	 */
	JsonResult saveFesStowage (FesStowageVO fesStowage, SysUserVO sessionUser);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertFesStowages (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateFesStowage (FesStowageVO fesStowage);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateFesStowagesToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateFesStowagesToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteFesStowageById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteFesStowageByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	FesStowageVO getFesStowageById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<FesStowageVO> getFesStowages (FesStowageVO fesStowage);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getFesStowagesMap (FesStowageVO fesStowage);
	List<Map<String, Object>> querySysDicForStowage();
	JsonResult saveFesStowages(FesStowageVO[] fesStowages, SysUserVO sessionUser);
	Object getSysUserMerchantTel(FesStowageVO fesStowage);
	

}
