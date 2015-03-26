/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yazuo.erp.bes.vo.BesRequirementVO;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @author erp team
 * @date 
 */
public interface SysOperationLogService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysOperationLog (SysOperationLogVO sysOperationLog);
	/**
	 * 新增多个对象 
	 */
	int batchInsertSysOperationLogs (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysOperationLog (SysOperationLogVO sysOperationLog);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysOperationLogsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysOperationLogsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteSysOperationLogById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysOperationLogByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SysOperationLogVO getSysOperationLogById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysOperationLogVO> getSysOperationLogs (SysOperationLogVO sysOperationLog);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSysOperationLogsMap (SysOperationLogVO sysOperationLog);
	int saveSysOperationLogForFesPlan(FesPlanVO fesPlan);
	int saveSysOperationLogForMonthlyReport(BesRequirementVO besRequirement, Object... array);
	boolean getSysOperationByTypeAndIds(Integer[] operationLogIds, String string);
	SysOperationLogVO saveOperationLogForReq(BesRequirementVO besRequirement, SysUserVO sessionUser);
	SysOperationLogVO updateOperationLogForReq(BesRequirementVO besRequirement, SysUserVO sessionUser);

	/**
	 * 据ID列表查询日志信息
	 * @param operationLogIds
	 * @param type
	 * @param beginTime
	 *@param endTime @return
	 */
	Page<SysOperationLogVO> getSysOperationByTypeAndIds(List<Integer> operationLogIds, String type, Date beginTime, Date endTime);
}
