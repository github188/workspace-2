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

package com.yazuo.erp.system.dao;

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface SysProductOperationDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysProductOperation (SysProductOperationVO sysProductOperation);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertSysProductOperations (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysProductOperation (SysProductOperationVO sysProductOperation);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysProductOperationsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysProductOperationsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteSysProductOperationById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysProductOperationByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SysProductOperationVO getSysProductOperationById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysProductOperationVO> getSysProductOperations (SysProductOperationVO sysProductOperation);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSysProductOperationsMap (SysProductOperationVO sysProductOperation);
	

}
