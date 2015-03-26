/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.bes.service.impl;

import static junit.framework.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Strings;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.bes.dao.BesMonthlyReportDao;
import com.yazuo.erp.bes.dao.BesRequirementDao;
import com.yazuo.erp.bes.service.BesCallRecordService;
import com.yazuo.erp.bes.service.BesMonthlyReportService;
import com.yazuo.erp.bes.service.BesRequirementService;
import com.yazuo.erp.bes.service.BesTalkingSkillsService;
import com.yazuo.erp.bes.vo.BesCallRecordVO;
import com.yazuo.erp.bes.vo.BesMonthlyReportVO;
import com.yazuo.erp.bes.vo.BesRequirementVO;
import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.fes.service.FesPlanAttachmentService;
import com.yazuo.erp.fes.service.FesPlanService;
import com.yazuo.erp.fes.vo.FesPlanAttachmentVO;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.service.ResourceService;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysDocumentService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.service.SysRemindService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.service.impl.SysOperationLogServiceImpl;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysDocumentDtlOptionVO;
import com.yazuo.erp.system.vo.SysDocumentDtlVO;
import com.yazuo.erp.system.vo.SysDocumentVO;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysRemindVO;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.DateUtil;
import com.yazuo.util.StringUtil;

@Service
@SuppressWarnings("unused")
public class BesRequirementServiceImpl implements BesRequirementService {
	 
	@Resource private BesRequirementDao besRequirementDao;
	@Resource private SysDictionaryService sysDictionaryService;
	@Resource private SysUserDao sysuser;
	@Resource private ResourceService resourceService;
	@Resource private SysOperationLogService sysOperationLogService;
	@Resource private SynMerchantService synMerchantService;
	@Resource private MktContactService mktContactService;
	@Resource private SysUserMerchantService sysUserMerchantService;
	@Resource private FesPlanService fesPlanService;
	@Resource private FesPlanAttachmentService fesPlanAttachmentService;
	@Resource private SysAttachmentService sysAttachmentService;
	@Resource private BesMonthlyReportService besMonthlyReportService;
	@Resource private SysUserDao sysUserDao;
	@Resource private BesCallRecordService besCallRecordService;
	@Resource private SysDocumentService sysDocumentService;
	@Resource private BesMonthlyReportDao besMonthlyReportDao;
	@Resource private SysRemindService sysRemindService;
	@Resource private BesTalkingSkillsService besTalkingSkillsService;
	@Resource private SysUserService sysUserService;
	
	private static final Log LOG = LogFactory.getLog(BesRequirementServiceImpl.class);
	private static String splitMark = "@"; //日志分隔符
    private static final String operation_log_type = "19";

    //页面显示图标
    static class Icon {
    	private static String markName = "iconCat"; //图标分类
		private static int start = 1;
		private static int running = 2;
		private static int todo = 3;
		private static int close = 4;
	}
    /**
     *  定时器调用
     * 
     *  1、提醒的类型按照需求的类型划分
		2、提醒时间是需求处理的时间
		如果处理时间是当天，则即时提醒
		如果处理时间是明天及以后，则每天0时定时器跑出，提醒当天的
		3、创建时添加提醒，修改时不提醒
		4、添加修改，均加流水
		5、提醒文案
		您有一个新需求，请尽快处理
     */
    @Override
    public void saveReqRemindForTimer(){
    	List<BesRequirementVO> requirementForTimer = this.besRequirementDao.getRequirementForTimer(null);
    	SysUserVO sessionUser = new SysUserVO();
    	sessionUser.setId(Constant.DEFAULT_ADD_USER);
    	for (BesRequirementVO besRequirement : requirementForTimer) {
			sysRemindService.saveSysRemindForReq(besRequirement, sessionUser);
			LOG.info("定时器保存了提醒： "+besRequirement.getId());
		}
    }
	@Override
	public BesRequirementVO saveOrUpdateBesRequirement(BesRequirementVO besRequirement, SysUserVO sessionUser) {
		Integer id = besRequirement.getId();
		Integer[] ids = besRequirement.getIds();
		Integer userId = sessionUser.getId();
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(userId);
		String userName = sysUserVO.getUserName();
		int row = -1;
		if (id == null && (ids == null || ids.length == 0)) {// 新增
			//处理时间由前端传过来
			besRequirement.setInsertBy(userId);
			besRequirement.setInsertTime(new Date());
			besRequirement.setUpdateBy(userId);
			besRequirement.setUpdateTime(new Date());
			besRequirement.setIsEnable(Constant.Enable);
			besRequirement.setNodeLastTime(new Date());
			//根据不同的资源类型保存流程和需求状态
			this.saveReqStatus(besRequirement);
			row = besRequirementDao.saveBesRequirement(besRequirement);
			//保存操作流水 有处理人 一次保存两条流水
			sysOperationLogService.saveOperationLogForReq(besRequirement, sessionUser);
			//创建提醒 ,改为定时器每天扫描
			if("1".equals(besRequirement.getIsRemind())){
				FastDateFormat instance = FastDateFormat.getInstance("yyyy-MM-dd");
				if(instance.format(new Date()).equals(instance.format(besRequirement.getHandledTime()))){
					//处理日期是今天
					sysRemindService.saveSysRemindForReq(besRequirement, sessionUser);
				}
			}
		} else if (ids != null && ids.length >= 0) {// 批量更新处理人和处理状态
			for (Integer reqId : ids) {
				BesRequirementVO besRequirement1 = new BesRequirementVO();
				Integer handlerId = besRequirement.getHandlerId();
				besRequirement1.setId(reqId);
				String description = "修改了需求";
				if(handlerId!=null && handlerChange(reqId, handlerId)){
					besRequirement1.setHandlerId(handlerId);
					besRequirement1.setStatus("2"); // 未完成
					besRequirement1.setProcessStatus("b_2");
					besRequirement1.setUpdateBy(userId);
					besRequirement1.setHandledTime(new Date());
					besRequirement1.setUpdateTime(new Date());
					besRequirement1.setIsEnable(Constant.Enable);
					String contactName = this.sysUserDao.getSysUserById(handlerId).getUserName();
					//指派不算状态流转， 不更新处理时间
				    description = userName + " 将需求指派给了"+ contactName;
				    String fesLogType = "18";// 月报，需求指派
				    this.addOperationLogs(reqId, besRequirement1, description, fesLogType, 1);
				    besRequirement1.setNodeLastTime(new Date());
					row = besRequirementDao.updateBesRequirement(besRequirement1);
				}else{
					//添加操作流水
					besRequirement.setId(reqId);
					this.sysOperationLogService.updateOperationLogForReq(besRequirement, sessionUser);
					besRequirement.setUpdateBy(userId);
					besRequirement.setHandledTime(new Date());
					besRequirement.setUpdateTime(new Date());
					row = besRequirementDao.updateBesRequirement(besRequirement);
				}
			}
		}
		return besRequirement;
	}
	@Override
	public boolean handlerChange(Integer reqId, Integer handlerId) {
		BesRequirementVO besRequirementById = this.getBesRequirementById(reqId);
		if(handlerId.equals(besRequirementById.getHandlerId())){
			return false;
		}
		return true;
	}
	/**根据不同的资源类型保存流程和需求状态*/
	private void saveReqStatus(BesRequirementVO besRequirement) {
		if(besRequirement.getResourceRemark().equals("marketing_act_148")){//营销活动
			if(besRequirement.getHandlerId()!=null){
				besRequirement.setStatus("2");
				besRequirement.setProcessStatus("2");
			}else{
				besRequirement.setStatus("1");
				besRequirement.setProcessStatus("1");
			}
		}
	}
	/**
	 * 保存新增的操作流水记录
	 */
	private void addOperationLogs(Integer reqId, BesRequirementVO besRequirement1,String description,String fesLogType, int flag) {
		BesRequirementVO besRequirementById = this.getBesRequirementById(reqId);
		Integer[] operationLogIds = besRequirementById.getOperationLogIds();
		assertNotNull( "操作人不允许为空 !", besRequirement1.getUpdateBy());
		//保存流水 
        int newOperId = sysOperationLogService.saveSysOperationLogForMonthlyReport(besRequirementById,
				description, fesLogType, besRequirement1.getUpdateBy(), flag);
		if(operationLogIds!=null){
			operationLogIds = (Integer[]) ArrayUtils.add(operationLogIds, newOperId);
			besRequirement1.setOperationLogIds(operationLogIds);
		}else{
			besRequirement1.setOperationLogIds(new Integer[]{newOperId});
		}
	}
    public int saveBesRequirement (BesRequirementVO besRequirement) {
		return besRequirementDao.saveBesRequirement(besRequirement);
	}
	public int batchInsertBesRequirements (Map<String, Object> map){
		return besRequirementDao.batchInsertBesRequirements(map);
	}
	public int updateBesRequirement (BesRequirementVO besRequirement){
		return besRequirementDao.updateBesRequirement(besRequirement);
	}
	public int batchUpdateBesRequirementsToDiffVals (Map<String, Object> map){
		return besRequirementDao.batchUpdateBesRequirementsToDiffVals(map);
	}
	public int batchUpdateBesRequirementsToSameVals (Map<String, Object> map){
		return besRequirementDao.batchUpdateBesRequirementsToSameVals(map);
	}
	public int deleteBesRequirementById (Integer id){
		return besRequirementDao.deleteBesRequirementById(id);
	}
	public int batchDeleteBesRequirementByIds (List<Integer> ids){
		return besRequirementDao.batchDeleteBesRequirementByIds(ids);
	}
	@Override 
	public BesRequirementVO getBesRequirementById(Integer id, final SysUserVO sessionUser){
		 final BesRequirementVO besRequirementById = besRequirementDao.getBesRequirementById(id);
		 //封装 需求信息 
		 this.encapsulateBesReq(sessionUser, besRequirementById);
		 //封装流水
		List<Map<String, Object>> operstionLogs = this.encapsulateOperationLog(besRequirementById);
		this.handleOperationLogs(besRequirementById, operstionLogs, sessionUser.getId());
		besRequirementById.setOperstionLogs(operstionLogs);
	    return besRequirementById;
	}
	
	/**
	 * 操作流水添加按钮信息
	 * 
	 * note: 此操作不更改数据库中的流水，只是更新TODO流水和最后一步按钮显示文字
	 */
	@SuppressWarnings("serial")
	private void handleOperationLogs(final BesRequirementVO besRequirement, final List<Map<String, Object>> operstionLogs, final Integer userId) {
		final BesRequirementVO besRequirementById = this.getBesRequirementById(besRequirement.getId());
        boolean isSelf = userId != null && userId.equals(besRequirementById.getHandlerId());//是否为处理人本人
        //查询当前用户具有月报回访权限的记录
        List<SysUserVO> resources = 
        	resourceService.getAllUsersByResourceCode(new SysResourceVO(){{setUserId(userId); setRemark("monthly_rpt_161");}});
        // 添加下一步月报回访待办事项
		@SuppressWarnings("rawtypes")
		final Map[] buttons = { ArrayUtils.toMap(new String[][] { { "type", "1" }, { "text", "回访" } }),
				ArrayUtils.toMap(new String[][] { { "type", "2" }, { "text", "再次回访" } }),
				ArrayUtils.toMap(new String[][] { { "type", "3" }, { "text", "放弃" } }),
				ArrayUtils.toMap(new String[][] { { "type", "4" }, { "text", "完成" } }),
				ArrayUtils.toMap(new String[][] { { "type", "5" }, { "text", "指派负责人" } }),
				ArrayUtils.toMap(new String[][] { { "type", "6" }, { "text", "查看回访单" } })
				};
		final Integer merchantId = besRequirementById.getMerchantId();
		
		if ("1".equals(besRequirementById.getStatus())) {// 未指派
			operstionLogs.add(new HashMap<String, Object>() {
				{
					put(Icon.markName, Icon.todo);
					put(SysOperationLogVO.COLUMN_DESCRIPTION, "未指派处理人");
					put("buttons", ArrayUtils.subarray(buttons, 4, 5));
				}
			});
		}else if ("3".equals(besRequirementById.getStatus())) { // 已完成
			// 系统关闭图标没有区分是放弃还是完成
			Map<String, Object> mapOperationLogs = operstionLogs.get(operstionLogs.size() - 1);
			mapOperationLogs.put(Icon.markName, Icon.close);
            mapOperationLogs.put("buttons", ArrayUtils.subarray(buttons, 5, 6));
            //处理人为本人+有月报回访权限+不是已放弃的状态 显示回访按钮 
        } else if(isSelf && CollectionUtils.isNotEmpty(resources) && !"4".equals(besRequirement.getStatus())) {
			operstionLogs.add(new HashMap<String, Object>() {
				{
					put(Icon.markName, Icon.todo);
					put(SysOperationLogVO.COLUMN_DESCRIPTION, "请对商户进行月报回访");
					// 存在回访记录
					if (BesRequirementServiceImpl.this.existMonthlyReport(merchantId,"2", DateUtil.fromMonth(), new Date())) {
						put(SysOperationLogVO.COLUMN_DESCRIPTION, "是否需要再次回访，无需回访，请选择完成");
						Object[] addAll = ArrayUtils.addAll(ArrayUtils.subarray(buttons, 1, 2),
								ArrayUtils.subarray(buttons, 3, 4));
						put("buttons", addAll);
					} else {
						put("buttons", ArrayUtils.subarray(buttons, 0, 1));
						// 如果有点击过电话未接通, 最后一条TODO需要特殊处理
						Map<String, Object> mapOperationLogs = operstionLogs.get(operstionLogs.size() - 1);
						if (SysOperationLogServiceImpl.connected.equals(mapOperationLogs.get(SysOperationLogVO.COLUMN_REMARK))) {
							put("buttons", ArrayUtils.subarray(buttons, 1, 3));
						}
					}
				}
			});
		}
		//处理流水提示语
		CollectionUtils.collect(operstionLogs, new Transformer() {
			@SuppressWarnings("unchecked")
			public Object transform(Object input) {
				Map<String, Object> inputMap = (Map<String, Object>)input;
				String description = inputMap.get(SysOperationLogVO.COLUMN_DESCRIPTION).toString();
				String[] descs = description.split(splitMark);
				inputMap.put(SysOperationLogVO.COLUMN_DESCRIPTION,descs[0]);
				if(descs.length>1){
					List<Map<String, String>> tipsList = new ArrayList<Map<String,String>>();
					for (String tip : (String[])ArrayUtils.subarray(descs, 1, descs.length)) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("text", tip);
						map.put("path", "");
						tipsList.add(map);
					}
					inputMap.put("tips" , tipsList);
				}
//				LOG.info(JsonResult.getJsonString(inputMap));
				return inputMap;
			}
		});
	}

	/**
	 * 是否包含发送月报的记录
	 */
    private boolean existMonthlyReport(Integer merchantId,String accessFactor,Date beginTime,Date endTime) {
        BesMonthlyReportVO monthlyReportVO = new BesMonthlyReportVO();
        monthlyReportVO.setMerchantId(merchantId);
        monthlyReportVO.setAccessFactor(accessFactor);
        monthlyReportVO.setStatus("1");//发送
        monthlyReportVO.setBeginTime(beginTime);
        monthlyReportVO.setEndTime(endTime);
        List<BesMonthlyReportVO> monthlyReportVOs = this.besMonthlyReportDao.getBesMonthlyReports(monthlyReportVO);
        return monthlyReportVOs.size() > 0;
    }
	/**
	 * 封装流水
	 * 
	 * 1.查询操作流水封装成map的形式
	 * 2.添加显示图标标识
	 */
	private List<Map<String, Object>> encapsulateOperationLog(BesRequirementVO besRequirementById) {
		besRequirementById = this.getBesRequirementById(besRequirementById.getId());
		Integer[] operationLogIds = besRequirementById.getOperationLogIds();
		 List<Map<String, Object>> operstionLogs = new ArrayList<Map<String,Object>>();
		 if(operationLogIds==null){
			 return operstionLogs;
		 }
		 int i=0;
		 for (Integer logId : operationLogIds) {
			SysOperationLogVO sysOperationLogById = this.sysOperationLogService.getSysOperationLogById(logId);
			Map<String, Object> map = new HashMap<String, Object>();
			if(i==0){
				map.put(Icon.markName, Icon.start);
			}else{
				map.put(Icon.markName, Icon.running);
			}
			map.put(SysOperationLogVO.COLUMN_ID, sysOperationLogById.getId());
			map.put(SysOperationLogVO.COLUMN_OPERATING_TIME, sysOperationLogById.getOperatingTime());
			map.put(SysOperationLogVO.COLUMN_DESCRIPTION, sysOperationLogById.getDescription());
			//判断 是否保存了接通回访电话 特殊处理
			map.put(SysOperationLogVO.COLUMN_REMARK, sysOperationLogById.getRemark()); 
			operstionLogs.add(map);
			i++;
		}
		return operstionLogs;
	}
	@Override
	public List<BesRequirementVO> getBesRequirements (BesRequirementVO besRequirement, final SysUserVO sessionUser){
		String status = besRequirement.getStatus();
		Integer sessionUserId = sessionUser.getId();
		//未指派 
		if("1".equals(status)){
			besRequirement.setSortColumns("handled_time");
		}
		//如果是 未完成 显示自己和下属的需求
		if(Arrays.asList("2").contains(status)){
			besRequirement.setBaseUserId(sessionUserId);
			besRequirement.setSortColumns("handled_time");
		}
		// 已完成 已放弃
		if(Arrays.asList("3","4").contains(status)){
			besRequirement.setBaseUserId(sessionUserId);
			besRequirement.setSortColumns("handled_time desc");
		}
		//我发起,只显示自己发起的需求
		if("0".equals(status)){
			besRequirement.setInsertBy(sessionUserId);
			besRequirement.setStatus(null); //不过滤status
			besRequirement.setSortColumns("handled_time desc");
		}
		 List<BesRequirementVO> besRequirements = besRequirementDao.getBesRequirements(besRequirement);
		 //添加数据字典
		 for (final BesRequirementVO besRequirementVO : besRequirements) {
			 this.encapsulateBesReq(sessionUser, besRequirementVO);
		 }
		return besRequirements;
	}
	
	private void encapsulateBesReq(final SysUserVO sessionUser, final BesRequirementVO besRequirementVO) {
        besRequirementVO.setDicRowSourceCat(sysDictionaryService.getSysDictionaryMapByTypeAndKey("00000103", besRequirementVO.getSourceCat()));
        besRequirementVO.setDicRowSourceContent(sysDictionaryService.getSysDictionaryMapByTypeAndKey("00000104", besRequirementVO.getSourceContent()));
        besRequirementVO.setDicRowContactCat(sysDictionaryService.getSysDictionaryMapByTypeAndKey("00000102", besRequirementVO.getContactCat()));
        if (besRequirementVO != null && !StringUtil.isNullOrEmpty(besRequirementVO.getStatus())) {
            besRequirementVO.setDicRowReqStatus(sysDictionaryService.getSysDictionaryMapByTypeAndKey("00000113", besRequirementVO.getStatus()));
        }
        //处理人
        final SysUserVO sysUserById = sysuser.getSysUserById(besRequirementVO.getHandlerId());
        @SuppressWarnings("serial")
        Map<String, Object> dicRowHandler = new HashMap<String, Object>() {
            {
                if (sysUserById != null) {
                    put("userId", sysUserById.getId());
                    if (sessionUser.getUserName().equals(sysUserById.getUserName())) {
                        put("userName", "自己");
                    } else {
                        put("userName", sysUserById.getUserName());
                    }
                    put("tel", sysUserById.getTel());
                }
            }

        };
        besRequirementVO.setDicRowHandler(dicRowHandler);
        //添加资源描述
        @SuppressWarnings("serial")
        List<SysResourceVO> resources = this.resourceService.getSysResources(new HashMap<String, Object>() {{
            put("remark", besRequirementVO.getResourceRemark());
        }});
        String resourceName = ((SysResourceVO) resources.get(0)).getResourceName();
        @SuppressWarnings("serial")
        List<SysResourceVO> resources1 = this.resourceService.getSysResources(new HashMap<String, Object>() {{
            put("remark", besRequirementVO.getResourceExtraRemark());
        }});
        String resourceName1 = ((SysResourceVO) resources1.get(0)).getResourceName();
        besRequirementVO.setResourceName(resourceName);
        besRequirementVO.setResourceExtraName(resourceName1);
        //前端负责任人
        Integer merchantId = besRequirementVO.getMerchantId();
        SysUserVO fesUser = this.sysUserMerchantService.getFesUserByMerchantId(merchantId);
        besRequirementVO.setFesUserName(fesUser.getUserName());
        besRequirementVO.setFesUserTel(fesUser.getTel());
        //联系人
        SynMerchantVO synMerchantById = synMerchantService.getSynMerchantById(merchantId);
        String merchantName = synMerchantById.getMerchantName();
        MktContactVO mktContact = mktContactService.getMktContactById(besRequirementVO.getContactId());
        if (mktContact == null) {
            mktContact = this.mktContactService.getLastContactForMerchant(besRequirementVO.getMerchantId());
            if (mktContact != null) {
                // 如果不存在联系人则添加联系人
                BesRequirementVO requirementVO = this.besRequirementDao.getBesRequirementById(besRequirementVO.getId());
                requirementVO.setContactId(mktContact.getId());
                requirementVO.setHandledTime(new Date());
                this.besRequirementDao.updateBesRequirement(requirementVO);
            }
        }
        besRequirementVO.setMktContact(mktContact);
        besRequirementVO.setMerchantName(merchantName);
        //是否已回访
        if (this.sysOperationLogService.getSysOperationByTypeAndIds(besRequirementVO.getOperationLogIds(), "19")) {
            besRequirementVO.setReVisitedText("已回访");
        }
        //保存流程数据字典行
        sysDictionaryService.setStdDicRow(besRequirementVO, "00000117", "processStatus","dicRowProcessStatus");
    }

    public List<Map<String, Object>>  getBesRequirementsMap (BesRequirementVO besRequirement){
		return besRequirementDao.getBesRequirementsMap(besRequirement);
	}
	@Override
	public BesRequirementVO getBesRequirementById(Integer id) {
		return besRequirementDao.getBesRequirementById(id);
	}

	/**
	 * 创建需求的时候查找联系人 下拉列表框
	 */
	@Override
	public List<Map<String, Object>> getContactListsOfReq(final BesRequirementVO besRequirementVO) {
		final Integer merchantId = besRequirementVO.getMerchantId();
		final String contactCat = besRequirementVO.getContactCat();
		final Integer contactId = besRequirementVO.getContactId();
		final List<Map<String, Object>> contactCats = sysDictionaryService.querySysDictionaryByTypeStd("00000102"); //联系人分类
		CollectionUtils.filter(contactCats,  new Predicate() {
			@Override public boolean evaluate(Object object) {
				@SuppressWarnings("unchecked")  Map<String, Object> map = (Map<String, Object>)object;
				//如果是 新增弹窗, 没有商户ID， 联系人分类只有前端
				if("1".equals(map.get("value").toString()) && merchantId == null){//商户
					return false;
				}
				//如果是 新增弹窗， 联系人分类只有前端
				if("2".equals(map.get("value").toString()) && merchantId != null){//前端
					return false;
				}
				return true;
			}
		});
		//过滤前端
		CollectionUtils.forAllDo(contactCats, new Closure() {
			public void execute(Object input) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>)input;
				String catValue = map.get("value").toString();
				map.put(Constant.DropDownList.isSelected, "0"); //默认不选中
				if(catValue.equals(contactCat)) map.put(Constant.DropDownList.isSelected, "1"); //更改行为,默认选中商户
				//查询前端联系人
				List<Map<String, Object>> contacts = sysUserService.getUsersByResourceCode(new SysResourceVO(){{setRemark("front_end_service");}});
				if(catValue.equals("1")){ //查询商户联系人
					contacts = mktContactService.getContactsDropDownList(new MktContactVO(){
						 {setMerchantId(merchantId);setStoreId(besRequirementVO.getStoreId());}});
				}
				CollectionUtils.forAllDo(contacts, new Closure() {
					public void execute(Object input) {
						Map<String, Object> contact = (Map<String, Object>)input;
						 contact.put(Constant.DropDownList.isSelected, "0");
						if(contact.get("id")!=null && contact.get("id").equals(contactId))  contact.put(Constant.DropDownList.isSelected, "1");
					}
				});
				map.put("children", contacts);
			}
		});
		return contactCats;
	}
	
	@Value("${planFilePath}")
	private String planFilePath;
	/**
	 * 查找当前月 的工作计划
	 * @return 
	 */
	@Override
	public List<Map<String, String>> getMonthlyFesPlan(String realPath, BesRequirementVO besRequirementVO) {
		List<FesPlanVO> fesPlans = this.getFesPlanForReq(besRequirementVO.getMerchantId());
		if(fesPlans.size()>0){
			LOG.info("本月月报工作管理个数:　"+fesPlans.size());
			FesPlanVO fesPlanVO = (FesPlanVO)fesPlans.get(0);
			//查询附件关系
			FesPlanAttachmentVO fesPlanAttachment = new FesPlanAttachmentVO();
			fesPlanAttachment.setPlanId(fesPlanVO.getId());
			List<FesPlanAttachmentVO> fesPlanAttachments = this.fesPlanAttachmentService.getFesPlanAttachments(fesPlanAttachment );
			List<Map<String , String>> list = new  ArrayList<Map<String,String>>();
			for (FesPlanAttachmentVO fesPlanAttachmentVO : fesPlanAttachments) {
				Map<String , String> map = new HashMap<String, String>();
				Integer attachmentId = fesPlanAttachmentVO.getAttachmentId();
				SysAttachmentVO sysAttachmentById = sysAttachmentService.getSysAttachmentById(attachmentId);
				String fileFullPath = planFilePath+"/"+sysAttachmentById.getAttachmentName();
				sysAttachmentById.setFileFullPath(fileFullPath);
				LOG.info("fileFullPath= "+fileFullPath);
				map.put("fileFullPath", fileFullPath);
				map.put("attachmentSize", sysAttachmentById.getAttachmentSize());
				map.put("fileName", sysAttachmentById.getOriginalFileName());
				list.add(map);
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 查找当前商户当月的工作计划
	 * select * from fes.fes_plan where  plan_item_type = '1' and is_enable = '1' and status != '3' 
		AND start_time >= '2014-12-1'  AND start_time <= '2014-12-31'   
	   ORDER BY  start_time desc 
	 */
	@Override
	public List<FesPlanVO> getFesPlanForReq(Integer merchantId) {
        return this.getFesPlanForReq(merchantId, new Date());
    }

    @Override
    public List<FesPlanVO> getFesPlanForReq(Integer merchantId, Date time) {
		FesPlanVO fesPlan = new FesPlanVO();
		fesPlan.setPlanItemType("1");//讲解月报
		fesPlan.setIsEnable("1");
		fesPlan.setNonStatus("3");//已放弃
		fesPlan.setFesBeginTime(DateUtils.truncate(time,Calendar.MONTH));
        fesPlan.setFesEndTime(DateUtils.addMonths(DateUtils.truncate(time, Calendar.MONTH), 1));
        fesPlan.setMerchantId(merchantId);
		fesPlan.setSortColumns("start_time"); //按开始时间降序
		List<FesPlanVO> fesPlans = this.fesPlanService.getFesPlans(fesPlan);
		return fesPlans;
	}
	
	/**
	 *  回访 电话未接通
	 */
	@Override
	public List<Map<String, Object>> saveVisteFailTelCall(BesRequirementVO besRequirement, SysUserVO sessionUser) {
		
		final Integer reqId = besRequirement.getId();
		String visiteButtonName = besRequirement.getVisiteButtonName();
		Integer userId = sessionUser.getId();
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(userId);
		String userName = sysUserVO.getUserName();
		BesRequirementVO besRequirementById = this.getBesRequirementById(reqId);
		assertNotNull("联系人不能为空! ", besRequirementById.getContactId());
		String contactName = this.mktContactService.getMktContactById(besRequirementById.getContactId()).getName();
		String description = userName + " 回访了" +contactName  + ""
			+ ",电话" + visiteButtonName;
		//放弃或完成特殊处理
		Integer btnFlag = besRequirement.getBtnFlag();
		if(btnFlag!= null){
			if(1 == btnFlag){
				besRequirement.setStatus("4"); // 已放弃
				besRequirement.setProcessStatus("b_4");
				description = userName + "放弃了回访";
			}else{
				description = userName + "完成了回访";
				besRequirement.setStatus("3"); // 已完成
				besRequirement.setProcessStatus("b_3");
				//保存提醒
				this.sysRemindService.saveSysRemind(this.getSysRemindVO(besRequirementById, sessionUser));
			}
	    	if(besRequirement.getRemark()!=null){ //有填写放弃原因
	    		description+= splitMark+besRequirement.getRemark();
	    	}
		}
		
	    List<Map<String, Object>> operationLogs = this.saveReqAndOperationLog(sessionUser, 2, description, new BesRequirementVO(){{setId(reqId);}});
	    if(btnFlag!= null ){  
	    	//完成和放弃都 更改后端需求的状态为完成
	    	this.updateStatusToComplete(besRequirement, reqId, userId);
	    }

        // 如果是第一次访问记录时间信息
        if (!this.sysOperationLogService.getSysOperationByTypeAndIds(besRequirementById.getOperationLogIds(), "19")) {
            BesRequirementVO vo = new BesRequirementVO();
            vo.setId(besRequirementById.getId());
            vo.setNodeLastTime(new Date());
            this.besRequirementDao.updateBesRequirement(vo);
        }
        // 添加操作流水按钮信息
		this.handleOperationLogs(besRequirementById, operationLogs, sessionUser.getId());
		return operationLogs;
	}
	private SysRemindVO getSysRemindVO(BesRequirementVO besRequirementById, SysUserVO user) {
		Integer merchantId = besRequirementById.getMerchantId();
		SysUserVO fesUser = this.sysUserMerchantService.getFesUserByMerchantId(merchantId);
		FastDateFormat fdf = FastDateFormat.getInstance("MM");
		String customDateTime = fdf.format(DateUtils.addMonths(new Date(), -1));
		SysRemindVO sysRemindVO = new SysRemindVO();
		sysRemindVO.setUserId(fesUser.getId());
		sysRemindVO.setMerchantId(merchantId);
		sysRemindVO.setPriorityLevelType("01");
		sysRemindVO.setItemType("07");
		String content = customDateTime+"月月报讲解已完成。";
		sysRemindVO.setItemContent(content);
		sysRemindVO.setItemStatus("1");
		sysRemindVO.setIsEnable("1");
		sysRemindVO.setInsertBy(user.getId());
		sysRemindVO.setInsertTime(new Date());
		sysRemindVO.setUpdateBy(user.getId());
		sysRemindVO.setUpdateTime(new Date());
		return sysRemindVO;
	}

    /**
     * 原有方法的重写
     * @param merchantId
     * @param userId
     * @return
     */
    private SysRemindVO getSysRemindVO(Integer merchantId,Integer userId) {
        SysUserVO fesUser = this.sysUserMerchantService.getFesUserByMerchantId(merchantId);
        FastDateFormat fdf = FastDateFormat.getInstance("MM");
        String customDateTime = fdf.format(new Date());
        SysRemindVO sysRemindVO = new SysRemindVO();
        sysRemindVO.setUserId(fesUser.getId());
        sysRemindVO.setMerchantId(merchantId);
        sysRemindVO.setPriorityLevelType("01");
        sysRemindVO.setItemType("07");
        String content = customDateTime+"月月报讲解已完成。";
        sysRemindVO.setItemContent(content);
        sysRemindVO.setItemStatus("1");
        sysRemindVO.setIsEnable("1");
        sysRemindVO.setInsertBy(userId);
        sysRemindVO.setInsertTime(new Date());
        sysRemindVO.setUpdateBy(userId);
        sysRemindVO.setUpdateTime(new Date());
        return sysRemindVO;
    }


	private void updateStatusToComplete(BesRequirementVO besRequirement, Integer reqId, Integer userId) {
		BesRequirementVO besRequirement1 = new BesRequirementVO();
		besRequirement1.setId(reqId);
		besRequirement1.setStatus(besRequirement.getStatus());
		besRequirement1.setHandlerId(besRequirement.getHandlerId());
		besRequirement1.setUpdateBy(userId);
		besRequirement1.setUpdateTime(new Date());
        besRequirement1.setNodeLastTime(new Date());
		this.updateBesRequirement(besRequirement1);
	}
	
	/**
	 * 电话接通或未接通时保存流水
	 */
	private List<Map<String, Object>> saveReqAndOperationLog(SysUserVO sessionUser, int flag, String description, BesRequirementVO besRequirement ) {
		
		Integer reqId = besRequirement.getId();
		Integer userId = sessionUser.getId();
		BesRequirementVO besRequirement1 = new BesRequirementVO();
		String fesLogType = "19";// 月报[回访]
		besRequirement1.setId(reqId);
		
		Integer contactId = besRequirement.getContactId();
		if(contactId!=null){
			besRequirement1.setContactId(contactId);
		}
		besRequirement1.setUpdateBy(userId);
		besRequirement1.setUpdateTime(new Date());
		besRequirement1.setIsEnable(Constant.Enable);
		
		// 封装流水
		BesRequirementVO besRequirementById = this.getBesRequirementById(reqId);
		// 保存新增的操作流水记录
		this.addOperationLogs(reqId, besRequirement1, description, fesLogType, flag);
		besRequirement1.setHandledTime(new Date());
		// 更新后台需求流水
		this.besRequirementDao.updateBesRequirement(besRequirement1);
		// 封装操作流水 
		List<Map<String, Object>> operationLogs = this.encapsulateOperationLog(besRequirementById);
		return operationLogs;
	}
	
	/**
	 * 电话接通后的保存操作
	 * 
	 * 1. 保存问卷调研单
	 * 2. 保存通话记录
	 * 3. 生成流水
	 */
	@Override
	public List<Map<String, Object>> savePaperAndCallRecord(BesCallRecordVO besCallRecord,SysDocumentVO sysDocument,
			SysUserVO sessionUser, HttpServletRequest request) {
		
		final Integer reqId = besCallRecord.getRequirementId();
		final BesRequirementVO besRequirementById = this.getBesRequirementById(reqId);
		final Integer merchantId = besRequirementById.getMerchantId();
		Map<String, Object> contactChangedMap = besCallRecord.getContactChangedMap();
		Integer contactId = null; 
		Object objectContactId = contactChangedMap.get("contactId");
		if(objectContactId!=null && !"".equals(objectContactId)){
			contactId = objectContactId.getClass().toString().contains("String")? 
					Integer.parseInt(objectContactId.toString()): (Integer)contactChangedMap.get("contactId");
		}
		//1. 保存通话记录
		Integer contactIdFromReq= besRequirementById.getContactId();
        boolean isChanged = contactId != null && !contactId.equals(contactIdFromReq);//是否变更
        contactId = isChanged ? contactId : contactIdFromReq;

        besCallRecord.setContactId(contactId);
		besCallRecord.setHandlerId(besRequirementById.getHandlerId());
		int saveBesCallRecord = besCallRecordService.saveBesCallRecord(besCallRecord, sessionUser);
		//3. 生成流水描述
		Integer userId = sessionUser.getId();
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(userId);
		String userName = sysUserVO.getUserName();
		String formatPeriod = DurationFormatUtils.formatPeriod(besCallRecord.getBeginTime().getTime(), besCallRecord.getEndTime().getTime(), "mm:ss");
		String description = userName + " 回访了" + this.mktContactService.getMktContactById(contactId).getName() + ""
			+ ",通话用时" + formatPeriod;
		//变更联系人处理流水
		BesRequirementVO besReqForUpdate = new BesRequirementVO();
		besReqForUpdate.setId(reqId);
        if (isChanged) {  //修改后的联系人
            besReqForUpdate.setContactId(contactId);
            MktContactVO merchantContact = this.mktContactService.getMktContactById(contactId);
            String others = "变更了联系人为" + merchantContact.getName() + ",联系电话" + merchantContact.getMobilePhone();
            description += splitMark + others;
        }

		if (!Strings.isNullOrEmpty(besCallRecord.getRemark())) {
			description += " 备注：" + besCallRecord.getRemark();
		}
		//2.  保存问卷调研单
        if (sysDocument.getId() == null) {
            this.sysDocumentService.saveSysDocument(sysDocument, request, sessionUser);
        } else {
            this.sysDocumentService.updateSysDocument(sysDocument, request, sessionUser);
        }
        //添加满意 反馈意见等流水
		List<SysDocumentDtlVO> sysDocumentDtlList = sysDocument.getSysDocumentDtlList();
        Date from = DateUtils.truncate(besRequirementById.getInsertTime(), Calendar.MONTH);
        Date to = DateUtils.addMonths(from, 1);
        description = this.savePaperOperationLog(description, merchantId, userId, sysDocumentDtlList, from, to);
        List<Map<String, Object>> operationLogs = this.saveReqAndOperationLog(sessionUser, 1, description, besReqForUpdate);
		//月报回访信息表添加 已回访 记录
		this.saveMonthlyReport(new BesMonthlyReportVO() {
			{
				setMerchantId(merchantId);
				setAccessFactor("2");
				setStatus("1");// 已回访
			}
		}, userId);
		// 添加操作流水按钮信息
		this.handleOperationLogs(this.getBesRequirementById(reqId), operationLogs, sessionUser.getId());
		return operationLogs;
	}
	private void saveMonthlyReport(BesMonthlyReportVO besMonthlyReport, Integer userId) {
		besMonthlyReport.setInsertBy(userId);
		besMonthlyReport.setInsertTime(new Date());
		besMonthlyReport.setUpdateBy(userId);
		besMonthlyReport.setUpdateTime(new Date());
		besMonthlyReport.setIsEnable(Constant.Enable);
		besMonthlyReport.setOperatorTime(new Date());
		this.besMonthlyReportService.saveBesMonthlyReport(besMonthlyReport);
	}
/**
 * 保存评分操作日志
 * 保存满意度 月报信息
 */
	private String savePaperOperationLog(String description, Integer merchantId, Integer userId, List<SysDocumentDtlVO> sysDocumentDtlList, Date from, Date to) {

		final List<Map<String, Object>> contactCats = sysDictionaryService.querySysDictionaryByTypeStd("00000108");
		for (SysDocumentDtlVO sysDocumentDtlVO : sysDocumentDtlList) {
			if(sysDocumentDtlVO.getQuestionId()==24){
				List<SysDocumentDtlOptionVO> sysDocumentDtlOptionList = sysDocumentDtlVO.getSysDocumentDtlOptionList();
				for (SysDocumentDtlOptionVO sysDocumentDtlOptionVO : sysDocumentDtlOptionList) {
					if(sysDocumentDtlOptionVO.getIsSelected().equals("1")){
						description += splitMark+"综合评分："+sysDocumentDtlOptionVO.getOptionContent();
						for (Map<String, Object> map : contactCats) {
							if(map.get("text").equals(sysDocumentDtlOptionVO.getOptionContent())){
								//更新或保存满意度记录
								String status = map.get("value").toString();
								BesMonthlyReportVO monthlyReport = 
									this.besMonthlyReportService.getMonthlyReport(merchantId, "1",from, to);
								if(monthlyReport!=null){
									monthlyReport.setStatus(status);
									monthlyReport.setUpdateTime(new Date());
									monthlyReport.setUpdateBy(userId);
									this.besMonthlyReportService.updateBesMonthlyReport(monthlyReport);
								}else{
									BesMonthlyReportVO besMonthlyReport = new BesMonthlyReportVO();
									besMonthlyReport.setMerchantId(merchantId);
									besMonthlyReport.setAccessFactor("1");
									besMonthlyReport.setStatus(status);
									this.saveMonthlyReport(besMonthlyReport, userId);
								}
							}
						}
					}
				}
			}else if(sysDocumentDtlVO.getQuestionId()==25){
				List<SysDocumentDtlOptionVO> sysDocumentDtlOptionList = sysDocumentDtlVO.getSysDocumentDtlOptionList();
				for (SysDocumentDtlOptionVO sysDocumentDtlOptionVO : sysDocumentDtlOptionList) {
					if(sysDocumentDtlOptionVO.getIsSelected().equals("1") && sysDocumentDtlOptionVO.getIsOpenTextarea().equals("1")){
						description += splitMark+"反馈意见："+sysDocumentDtlOptionVO.getComment();
					}
				}
            }else if (sysDocumentDtlVO.getQuestionId() == 21) { // 是否讲解
                List<SysDocumentDtlOptionVO> sysDocumentDtlOptionList = sysDocumentDtlVO.getSysDocumentDtlOptionList();
                for (SysDocumentDtlOptionVO sysDocumentDtlOptionVO : sysDocumentDtlOptionList) {
                    if (sysDocumentDtlOptionVO.getIsSelected().equals("1")) {
                        BesMonthlyReportVO monthlyReport = this.besMonthlyReportService.getMonthlyReport(merchantId, "4", from, to);
                        FesPlanVO planVO = this.fesPlanService.getCurrentMonthlyPlan(merchantId, from, to);
                        boolean isCompleted = planVO.getStatus().equals("4");
                        boolean isExplain = sysDocumentDtlOptionVO.getOptionContent().equals("是");//表单中是否讲解
                        String status = isExplain && isCompleted ? "1" : "3"; //是否讲解
                        Date explainTime = isCompleted ? planVO.getEndTime() : new Date();
                        if (monthlyReport != null) {
                            monthlyReport.setStatus(status);
                            monthlyReport.setUpdateBy(userId);
                            monthlyReport.setUpdateTime(new Date());
                            monthlyReport.setOperatorTime(explainTime);
                            this.besMonthlyReportService.updateBesMonthlyReport(monthlyReport);
                        } else {
                            BesMonthlyReportVO besMonthlyReportVO = new BesMonthlyReportVO();
                            besMonthlyReportVO.setMerchantId(merchantId);
                            besMonthlyReportVO.setAccessFactor("4");
                            besMonthlyReportVO.setStatus(status);
                            besMonthlyReportVO.setInsertBy(userId);
                            besMonthlyReportVO.setInsertTime(new Date());
                            besMonthlyReportVO.setUpdateBy(userId);
                            besMonthlyReportVO.setUpdateTime(new Date());
                            besMonthlyReportVO.setOperatorTime(explainTime);
                            besMonthlyReportVO.setIsEnable("1");
                            this.besMonthlyReportService.saveBesMonthlyReport(besMonthlyReportVO);
                        }
                    }
                }
			}
		}
		return description;
	}
	
	@Override
	public Map<String, Object> getContactMap(final Integer besReqId) {
		final Map<String, Object> contactMap = new HashMap<String, Object>();
		BesRequirementVO besRequirementById = this.getBesRequirementById(besReqId);
		Integer contactId = besRequirementById.getContactId();
		if(contactId!=null){
			MktContactVO mktContactById = this.mktContactService.getMktContactById(contactId);
			contactMap.put("id", mktContactById.getId());
			contactMap.put("name", mktContactById.getName());
			contactMap.put("mobile_phone", mktContactById.getMobilePhone());
		}else{
			LOG.info("月报需求联系人为空!");
		}
		return contactMap;
	}
	
	/**
	 * 月报回访 电话已接通的时候显示联系人
	 */
	@Override
	public Map<String, Object> encapsulateChangeContact(final Integer merchantId, final Map<String, Object> contactMap) {
		final List<Map<String, Object>> contactCats = sysDictionaryService.querySysDictionaryByTypeStd("00000102");
		//过滤前端
		CollectionUtils.filter(contactCats, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>)object;
				if(map.get("value")!=null && map.get("value").equals("1")){
					map.put("isSelected", "1"); //更改行为,默认选中商户
					//添加联系人children

					final List<Map<String, Object>> contactsDropDownList = mktContactService.getContactsDropDownList(new MktContactVO(){
						 {setMerchantId(merchantId);}});
					map.put("children", contactsDropDownList);
					return true;
				}
				return false;
			}
		});
		
		final Map<String, Object> changeContact = new HashMap<String, Object>() {
			{
				put("contactCat", contactCats);
				put("mobile", contactMap.get("mobile_phone"));
			}
		};
		return changeContact;
	}

    @Override
    public void saveCreateBesReq(final FesPlanVO fesPlanVO) {
        final Integer merchantId = fesPlanVO.getMerchantId();
        String merchantName = this.synMerchantService.getSynMerchantById(merchantId).getMerchantName();
        FastDateFormat fdf = FastDateFormat.getInstance("MM");
        Date startTime = fesPlanVO.getStartTime();
        Date formerDate = DateUtils.addMonths(startTime, -1);
        String month = fdf.format(formerDate);
        String title = merchantName + "商户" + month + "月月报讲解";
        BesRequirementVO besRequirement = new BesRequirementVO();
        besRequirement.setMerchantId(merchantId);
        besRequirement.setTitle(title);
        besRequirement.setContactCat("2");//前端
        besRequirement.setSourceCat("2");//前端
        besRequirement.setSourceContent("3");//ERP
        besRequirement.setResourceRemark("re_visite_151");
        besRequirement.setResourceExtraRemark("monthly_rpt_161");
        besRequirement.setStatus("1");//未指派
        besRequirement.setIsEnable("1");
        besRequirement.setNodeLastTime(new Date());
        //保存工作计划流水
        List<SysOperationLogVO> sysOperationLogs = this.sysOperationLogService
                .getSysOperationLogs(new SysOperationLogVO() {
                    {
                        setMerchantId(merchantId);
                        setModuleType("bes");
                        setFesLogType("17");//工作管理
                        setBeginTime(DateUtil.fromMonth());
                        setEndTime(new Date());
                    }
                });

        List<Integer> operIds = new ArrayList<Integer>();
        for (SysOperationLogVO sysOperationLogVO : sysOperationLogs) {
            operIds.add(sysOperationLogVO.getId());
        }
        besRequirement.setOperationLogIds(operIds.toArray(new Integer[operIds.size()]));
        besRequirement.setContactId(fesPlanVO.getContactId());//取工作计划的联系人
        besRequirement.setInsertBy(Constant.DEFAULT_ADD_USER);
        besRequirement.setInsertTime(new Date());
        besRequirement.setUpdateBy(Constant.DEFAULT_ADD_USER);
        besRequirement.setUpdateTime(new Date());
        this.saveBesRequirement(besRequirement);
    }


    @Override
    public void batchSaveVisitFailTelCall(List<Integer> requirementIds, SysUserVO userVO, String content, boolean abandoned) {
        // 需改为批量更新
        for (Integer requirementId : requirementIds) {
            BesRequirementVO persistReqVO = this.besRequirementDao.getBesRequirementById(requirementId);
            //添加提醒，生成日志描述信息
            String description = this.generateDesc(userVO.getUserName(),content,abandoned);
            if (!abandoned) {
                SysRemindVO remindVO = this.getSysRemindVO(persistReqVO.getMerchantId(), userVO.getId());
                this.sysRemindService.saveSysRemind(remindVO);
            }
            //添加日志
            int operationLogId = this.sysOperationLogService.saveSysOperationLogForMonthlyReport(persistReqVO, description, operation_log_type, userVO.getId(), 1);

            //更新需求信息
            Date now = new Date();
            Integer[] operationLogIds = persistReqVO.getOperationLogIds();
            persistReqVO.setOperationLogIds((Integer[]) ArrayUtils.add(operationLogIds, operationLogId));
            persistReqVO.setStatus("4");//2015-2-9 17:45:23 批量放弃状态应为4
            persistReqVO.setUpdateBy(userVO.getId());
            persistReqVO.setUpdateTime(now);
            persistReqVO.setNodeLastTime(now);
            this.besRequirementDao.updateBesRequirement(persistReqVO);
        }
    }
	/**
	 * 上传文件
	 * @dir //文件全目录
	 */
	public JsonResult uploadFile(MultipartFile myfile, String basePath, String dir, SysUserVO sessionUser) {
		JsonResult fileInfo =null;
		try {
			//文件路径加入到map中: note: 为了解决static 类型不能用@value的问题
			fileInfo = FileUploaderUtil.uploadFile(myfile, basePath+dir, null,0L);	
			Map<String, Object> file = (Map<String, Object>)fileInfo.getData();
			file.put("fileFullPath", dir+file.get("attachmentName"));
			LOG.info(file.get("fileFullPath"));
			if (fileInfo.getFlag() == 1) {// 上传成功
				Integer attachmentId = this.saveAttachement(sessionUser.getId(), fileInfo);
				file.put("attachmentId", attachmentId);
			}
		} catch (IOException e) {
			throw new FesBizException("文件读写错误"+e);
		}
		return fileInfo;
	}
	/**
	 * 保存文件信息到数据库
	 * @return attachmentId
	 */
	private Integer saveAttachement(Integer userId, JsonResult fileInfo) {
		Map<String, Object> file = (Map<String, Object>)fileInfo.getData();
		//上传附件每次保存一个附件，对应表中插入一条记录
		SysAttachmentVO sysAttachment = new SysAttachmentVO();	
		sysAttachment.setAttachmentName(file.get("attachmentName").toString());
		sysAttachment.setOriginalFileName(file.get("originalFileName").toString());
		sysAttachment.setAttachmentType("3");// 文件类型
		sysAttachment.setAttachmentSuffix(file.get("fileSuffix").toString());
		sysAttachment.setModuleType(Constant._REQ);
		sysAttachment.setIsEnable(Constant.IS_ENABLE);
		sysAttachment.setIsDownloadable("1");
		Object fileSizeObject = file.get("fileSize");
		if(fileSizeObject instanceof Long){
			Long fileSize = (Long)fileSizeObject;
			sysAttachment.setAttachmentSize(Long.toString(fileSize));
		}else{
			Integer fileSize = (Integer)fileSizeObject;
			sysAttachment.setAttachmentSize(fileSize.toString());
		}
		Object attachmentPath = file.get("attachmentPath");
		sysAttachment.setAttachmentPath(attachmentPath==null? null: attachmentPath.toString());
		sysAttachment.setInsertBy(userId);
		sysAttachment.setUpdateBy(userId);
		sysAttachment.setInsertTime(new Date());
		sysAttachment.setUpdateTime(new Date());
		this.sysAttachmentService.saveSysAttachment(sysAttachment);
		return sysAttachment.getId();
	}

    /**
     * 生成日志的描述信息
     *
     * @return
     */
    private String generateDesc(String username, String content,boolean abandoned) {
        String description = null;
        if (abandoned) {
            description = username + "放弃了回访";
        } else {
            description = username + "完成了回访";
        }
        if (abandoned) {
            description = description + splitMark + content;
        }
        return description;
    }


}
