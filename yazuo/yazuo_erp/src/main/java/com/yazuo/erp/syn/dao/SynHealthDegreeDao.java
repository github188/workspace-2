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

package com.yazuo.erp.syn.dao;

import java.util.*;
import com.yazuo.erp.syn.vo.*;
import com.yazuo.erp.syn.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface SynHealthDegreeDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSynHealthDegree (SynHealthDegreeVO synHealthDegree);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertSynHealthDegrees (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSynHealthDegree (SynHealthDegreeVO synHealthDegree);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSynHealthDegreesToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSynHealthDegreesToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteSynHealthDegreeById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSynHealthDegreeByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SynHealthDegreeVO getSynHealthDegreeById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SynHealthDegreeVO> getSynHealthDegrees (Map<String, Object> paramMap);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSynHealthDegreesMap (SynHealthDegreeVO synHealthDegree);
	

}