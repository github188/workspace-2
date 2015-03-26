/**
 * @Description 
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.service.impl;

import static com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.resource_fes_card_and_materials;
import static com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.toDoListStep6_init;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.base.PathConfig;
import com.yazuo.erp.fes.dao.FesOnlineProcessDao;
import com.yazuo.erp.fes.dao.FesOnlineProgramDao;
import com.yazuo.erp.fes.dao.FesProcessAttachmentDao;
import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.fes.service.FesMarketingActivityService;
import com.yazuo.erp.fes.service.FesMaterialRequestService;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesOnlineProgramService;
import com.yazuo.erp.fes.service.FesOpenCardApplicationService;
import com.yazuo.erp.fes.service.FesProcessAttachmentService;
import com.yazuo.erp.fes.service.FesStepService;
import com.yazuo.erp.fes.service.FesTrainPlanService;
import com.yazuo.erp.fes.vo.FesMarketingActivityVO;
import com.yazuo.erp.fes.vo.FesMaterialRequestVO;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.fes.vo.FesOnlineProgramVO;
import com.yazuo.erp.fes.vo.FesOpenCardApplicationVO;
import com.yazuo.erp.fes.vo.FesProcessAttachmentVO;
import com.yazuo.erp.fes.vo.FesRemarkVO;
import com.yazuo.erp.fes.vo.FesStepVO;
import com.yazuo.erp.fes.vo.FesStowageDtlVO;
import com.yazuo.erp.fes.vo.FesStowageVO;
import com.yazuo.erp.fes.vo.FesTrainPlanVO;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.service.MktShopSurveyService;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.mkt.vo.MktShopSurveyVO;
import com.yazuo.erp.syn.dao.SynMerchantDao;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.dao.SysAttachmentDao;
import com.yazuo.erp.system.dao.SysDocumentDao;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysDocumentService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysDictionaryVO;
import com.yazuo.erp.system.vo.SysDocumentVO;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.trade.service.TradeMessageTemplateService;
import com.yazuo.erp.trade.vo.TradeCardtypeVO;
import com.yazuo.erp.trade.vo.TradeMessageTemplateVO;
import com.yazuo.erp.train.service.CalendarService;
import com.yazuo.external.account.vo.MerchantVO;
import com.yazuo.util.EmailUtil;
/**
 * @Description 流程相关的接口
 * @author erp team
 * @date  
 *
 * 所有的上传，所有的删除只涉及到状态的变更， 不涉及待办事项的历史更新和待办事项的创建，也不涉及到操作日志的添加
 * 
 * 关于ERP中的状态变更设计,分为：
 * 主动更新（点击页面按钮的时候改变状态 updateFesOnlineProcessStatus）和
 * 被动更新（上传附件saveUploadFiles或删除附件deleteSysAttachmentById的时候 ），
 *   最后要统一调用子函数  updateSpecificFesOnlineProcessStatues(id, onlineProcessStatus)
 *   
 * 状态更改[点击按钮]的时候需要关闭上一个状态的待办事项，生成下一个步骤的待办事项
 */
@Service
public class FesOnlineProcessServiceImpl implements FesOnlineProcessService {

	private static final Log LOG = LogFactory.getLog(FesOnlineProcessServiceImpl.class);
	/*
	 * 待办事项描述
	 */
	public static final String  toDoListStep6_12 = "实体卡配送，请尽快安排。";
	public static final String  toDoListStep9_16 = "实施培训回访，请尽快安排。";
	public static final String toDoListStep4_06 = "营销活动创建，请尽快安排。";
	public static final String toDoListStep6_10 = "开卡申请，请尽快安排。";
	public static final String toDoListStep10_04 = "上线回访，请尽快安排。";
	//分配负责人的时候处理的待办事项
	public static final String toDoListStep6_init = "实体卡制作，请尽快安排。";
	public static final String toDoListStep6_init_non_period = "实体卡制作，请尽快安排";
	public static final String toDoListStep7_init = "物料设计，请尽快安排。";
	public static final String toDoListStep2_init = "微信申请，请尽快安排。";
	public static final String toDoListStep5_init = "后台设置，请尽快安排。";
	public static final String toDoListStep8_init = "设备配送，请尽快安排。";
	/*
	 * 资源的描述  remark
	 */
	public static final String resource_sales_service = "sales_service";//销售服务
	public static final String resource_front_end_service = "front_end_service";//前端服务
	public static final String resource_fes_card_and_materials = "fes_card_and_materials";//制卡/物料
	public static final String resource_end_custom_service = "end_custom_service";//后端客服
	public static final String resource_end_delivery_service = "end_delivery_service";//设备配送
	
    @Resource  	private FesOnlineProcessDao fesOnlineProcessDao;
	@Resource  	private FesOnlineProgramService fesOnlineProgramService;
	@Resource  	private FesStepService fesStepService;
	@Resource  	private CalendarService calendarService;
	@Resource  	private SysAttachmentService sysAttachmentService;
	@Resource  	private FesProcessAttachmentService fesProcessAttachmentService;
	@Resource  	private FesProcessAttachmentDao fesProcessAttachmentDao;
	@Resource  	private SysAttachmentDao sysAttachmentDao;
	@Resource  	private SysToDoListService sysToDoListService;
	@Resource  	private SysResourceDao sysResourceDao;
	@Resource  	private FesOnlineProgramDao fesOnlineProgramDao;
	@Resource  	private SynMerchantDao synMerchantDao;
	@Resource  	private SysOperationLogService sysOperationLogService;
	@Resource  	private SysDictionaryService sysDictionaryService;
	@Resource  	private FesTrainPlanService fesTrainPlanService;
	@Resource  	private TradeMessageTemplateService tradeMessageTemplateService;
	@Resource  	private EmailUtil emailUtil;
	@Resource  	private SysDocumentService sysDocumentService;
	@Resource  	private FesMarketingActivityService fesMarketingActivityService;
	@Resource  	private FesMaterialRequestService fesMaterialRequestService;
	@Resource  	private SysUserDao sysUserDao;
	@Resource  	private SysDocumentDao sysDocumentDao;
	@Resource  	private MktShopSurveyService mktShopSurveyService;
	@Resource  	private MktContactService mktContactService;

	// 上线前步骤num
	public static enum StepNum {
		research, micro_message, draft_program, final_program, background_set, entity_cards, materials_design, equipment_distribution, train, online;
	}

	/*********************************************************************
	 * uploadFiles需要的原始数据 *
	 **********************************************************************/
	@Value("${draftProgramFilePath}")
	private String draftProgramFilePath;
	@Value("${finalProgramFilePath}")
	private String finalProgramFilePath;
	@Value("${cardFilePath}")
	private String cardFilePath;
	@Value("${materialsDesignFilePath}")
	private String materialsDesignFilePath;
	@Value("${trainFilePath}")
	private String trainFilePath;
	//上线流程代码和文件大小映射
	public static final Map<String, Long> stepNumMatchFileSize = new HashMap<String, Long>();
	//上线流程代码和日志类型映射
	public static final Map<String, String[]> stepNumAndLogTypeMatcher = new HashMap<String, String[]>();
	public static final Map<String, String[]> resourceAndStepNumsMatcher = new HashMap<String, String[]>();
	static {
		//默认不做文件大小限制
		stepNumMatchFileSize.put("draft_program", 0l);
		stepNumMatchFileSize.put("final_program", 0l);
		stepNumMatchFileSize.put("entity_cards", 0l);
		stepNumMatchFileSize.put("materials_design", 0l);
		stepNumMatchFileSize.put("train", 0l);

		stepNumAndLogTypeMatcher.put(StepNum.research.toString(), new String[]{"1", "3"});
		stepNumAndLogTypeMatcher.put(StepNum.micro_message.toString(), new String[]{"4"});
		stepNumAndLogTypeMatcher.put(StepNum.draft_program.toString(), new String[]{"5"});
		stepNumAndLogTypeMatcher.put(StepNum.final_program.toString(), new String[]{"6"});
		stepNumAndLogTypeMatcher.put(StepNum.background_set.toString(), new String[]{"7"});
		stepNumAndLogTypeMatcher.put(StepNum.entity_cards.toString(), new String[]{"8","14","15"});
		stepNumAndLogTypeMatcher.put(StepNum.materials_design.toString(), new String[]{"9"});
		stepNumAndLogTypeMatcher.put(StepNum.equipment_distribution.toString(), new String[]{"10"});
		stepNumAndLogTypeMatcher.put(StepNum.train.toString(), new String[]{"11","13"});
		stepNumAndLogTypeMatcher.put(StepNum.online.toString(), new String[]{"12"});
		
		resourceAndStepNumsMatcher.put(resource_fes_card_and_materials, new String[]{StepNum.entity_cards.toString(), StepNum.materials_design.toString()});
		resourceAndStepNumsMatcher.put(resource_end_delivery_service, new String[]{StepNum.equipment_distribution.toString()});
		resourceAndStepNumsMatcher.put(resource_end_custom_service, new String[]{StepNum.micro_message.toString(),
	StepNum.background_set.toString(),StepNum.final_program.toString(),StepNum.entity_cards.toString(),StepNum.train.toString()});
	}
	
	/**
	 * 生成工具类map， match模块num和附件路径
	 */
	private Map<String, String> initMatchNumAndPath() {
		Map<String, String> stepNumMatchFilePath = new HashMap<String, String>();
		stepNumMatchFilePath.put("micro_message", PathConfig.WEIXIN_CODE);  
		stepNumMatchFilePath.put("draft_program", draftProgramFilePath);  
		stepNumMatchFilePath.put("final_program", finalProgramFilePath);
		stepNumMatchFilePath.put("entity_cards", cardFilePath);
		stepNumMatchFilePath.put("materials_design", materialsDesignFilePath);
		stepNumMatchFilePath.put("train", trainFilePath);
		return stepNumMatchFilePath;
	}
	
	public int saveFesOnlineProcess(FesOnlineProcessVO fesOnlineProcess) {
		return fesOnlineProcessDao.saveFesOnlineProcess(fesOnlineProcess);
	}
	/**
	 * 真正更新上线流程状态的函数， 统一用这个处理
	 * @param fesOnlineProcess
	 */
	private FesOnlineProcessVO updateSpecificFesOnlineProcessStatues(FesOnlineProcessVO fesOnlineProcess) {
		String onlineProcessStatus = fesOnlineProcess.getOnlineProcessStatus();
		if(onlineProcessStatus.equals("04")||onlineProcessStatus.equals("15")){
			fesOnlineProcess.setEndTime(new Date()); //更新步骤实际完成时间
		}
		fesOnlineProcessDao.updateFesOnlineProcess(fesOnlineProcess);
		return fesOnlineProcessDao.getFesOnlineProcessById(fesOnlineProcess.getId());
	}
	/**
	 * 保存后台设置
	 */
	@Override
	public int saveBackGroundContent(FesOnlineProcessVO fesOnlineProcess, SysUserVO sessionUser) {
//		FesOnlineProcessVO fesOnlineProcessDB = this.getFesOnlineProcessById(fesOnlineProcess.getId());
//		fesOnlineProcessDB.setUpdateBy(sessionUser.getId());
//		fesOnlineProcessDB.setUpdateTime(new Date());
//		fesOnlineProcessDB.setRemark(fesOnlineProcess.getRemark());
//		int row = fesOnlineProcessDao.updateFesOnlineProcess(fesOnlineProcessDB);
		//更改状态并添加流水
		this.updateFesOnlineProcessStatus(fesOnlineProcess, sessionUser);
		return 1;
	}
	
	@Override
	public int deleteBackGroundContent(FesOnlineProcessVO fesOnlineProcessVO, SysUserVO sessionUser) {
		Integer operationLogId = fesOnlineProcessVO.getId();
		SysOperationLogVO sysOperationLogById = this.sysOperationLogService.getSysOperationLogById(operationLogId);
		
		int deleted = this.sysOperationLogService.deleteSysOperationLogById(operationLogId);
		
		Integer merchantId = sysOperationLogById.getMerchantId();
		//判断有无 保存后台需求备注的流水记录
		SysOperationLogVO sysOperationLog = new SysOperationLogVO();
		sysOperationLog.setFesLogTypes(stepNumAndLogTypeMatcher.get(StepNum.background_set.toString()));
		sysOperationLog.setMerchantId(merchantId);
		List<SysOperationLogVO> sysOperationLogs = this.sysOperationLogService.getSysOperationLogs(sysOperationLog);
		if(sysOperationLogs.size()==0){//没有则更新状态
			FesOnlineProcessVO fesOnlineProcess = new FesOnlineProcessVO();
			fesOnlineProcess.setMerchantId(merchantId);
			fesOnlineProcess.setStepNum(StepNum.background_set.toString());
			FesOnlineProcessVO process = this.getFesOnlineProcessByMerchantAndStep(fesOnlineProcess);
			final Integer processId = process.getId();
			this.updateFesOnlineProcessStatus(new FesOnlineProcessVO(){{setId(processId); setOnlineProcessStatus("02");}}, sessionUser);
		}
		return deleted;
	}
	
	public int updateFesOnlineProcess(FesOnlineProcessVO fesOnlineProcess) {
		return fesOnlineProcessDao.updateFesOnlineProcess(fesOnlineProcess);
	}

	public int batchUpdateFesOnlineProcesssToDiffVals(Map<String, Object> map) {
		return fesOnlineProcessDao.batchUpdateFesOnlineProcesssToDiffVals(map);
	}

	public int batchUpdateFesOnlineProcesssToSameVals(Map<String, Object> map) {
		return fesOnlineProcessDao.batchUpdateFesOnlineProcesssToSameVals(map);
	}

	public int deleteFesOnlineProcessById(Integer id) {
		return fesOnlineProcessDao.deleteFesOnlineProcessById(id);
	}

	public int batchDeleteFesOnlineProcessByIds(List<Integer> ids) {
		return fesOnlineProcessDao.batchDeleteFesOnlineProcessByIds(ids);
	}

	public FesOnlineProcessVO getFesOnlineProcessById(Integer id) {
		return fesOnlineProcessDao.getFesOnlineProcessById(id);
	}

	public List<FesOnlineProcessVO> getFesOnlineProcesss(FesOnlineProcessVO fesOnlineProcess) {
		return fesOnlineProcessDao.getFesOnlineProcesss(fesOnlineProcess);
	}

	public List<Map<String, Object>> getFesOnlineProcesssMap(FesOnlineProcessVO fesOnlineProcess) {
		return fesOnlineProcessDao.getFesOnlineProcesssMap(fesOnlineProcess);
	}

	/**
	 * 1.保存上线流程
	 * 2.更新上线计划中的合同id
	 */
	public int batchInsertFesOnlineProcesss(Integer merchantId, SysUserVO sessionUser) {
//		新增上线流程
		FesOnlineProgramVO fesOnlineProgram = this.saveOnlineProgram(merchantId, sessionUser);
		Integer programId = fesOnlineProgram.getId();
		// 不允许为空的字段：
		// program_id,step_id,online_process_status,insert_by,insert_time,update_by,update_time,
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(FesOnlineProcessVO.COLUMN_PROGRAM_ID, Constant.NOT_NULL);
		map.put(FesOnlineProcessVO.COLUMN_STEP_ID, Constant.NOT_NULL);
		map.put(FesOnlineProcessVO.COLUMN_ONLINE_PROCESS_STATUS, Constant.NOT_NULL);
		map.put(FesOnlineProcessVO.COLUMN_INSERT_BY, Constant.NOT_NULL);
		map.put(FesOnlineProcessVO.COLUMN_INSERT_TIME, Constant.NOT_NULL);
		map.put(FesOnlineProcessVO.COLUMN_PLAN_END_TIME, Constant.NOT_NULL);
		map.put(FesOnlineProcessVO.COLUMN_UPDATE_BY, Constant.NOT_NULL);
		map.put(FesOnlineProcessVO.COLUMN_UPDATE_TIME, Constant.NOT_NULL);
		// 上线计划id插入上线流程
		List<FesOnlineProcessVO> fesOnlineProcessVOList = new ArrayList<FesOnlineProcessVO>();
		// 上线前步骤 部分字段插入上线流程
		FesStepVO fesStep = new FesStepVO();
		fesStep.setIsEnable(Constant.IS_ENABLE);
		List<FesStepVO> fesStepsList = fesStepService.getFesSteps(fesStep);
		for (FesStepVO fesStepVO : fesStepsList) {
			FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
			fesOnlineProcessVO.setProgramId(programId);
			fesOnlineProcessVO.setStepId(fesStepVO.getId());
			fesOnlineProcessVO.setInsertBy(sessionUser.getId());
			fesOnlineProcessVO.setUpdateBy(sessionUser.getId());
//			计算计划完成时间和上线流程状态
			this.calculateFields(fesOnlineProcessVO, fesOnlineProgram, fesStepVO);
			fesOnlineProcessVOList.add(fesOnlineProcessVO);
		}
		map.put("list", fesOnlineProcessVOList);
		return fesOnlineProcessDao.batchInsertFesOnlineProcesss(map);
	}

	/**
	 * @Description 新增上线流程
	 */
	private FesOnlineProgramVO saveOnlineProgram(Integer merchantId, SysUserVO sessionUser) {
		FesOnlineProgramVO fesOnlineProgram = new FesOnlineProgramVO();
		fesOnlineProgram.setMerchantId(merchantId);
		List<FesOnlineProgramVO> fesOnlinePrograms = fesOnlineProgramService.getFesOnlinePrograms(fesOnlineProgram);
		if(fesOnlinePrograms.size()>0){//存在上线流程
			throw new FesBizException("存在上线流程");
		}
//		现在没有这个需求： 更新上线计划中的合同id
//		fesOnlineProgram.setContractId(getProgramContractId(merchantId));
		fesOnlineProgram.setBeginTime(new Date());

		Calendar calendar = Calendar.getInstance();
//		Date onlineTime = this.calendarService.afterDays(calendar, 30);
		Date onlineTime = DateUtils.addDays(new Date(), 30);
		fesOnlineProgram.setPlanOnlineTime(onlineTime);
		//note： online_time实际上线时间这里不处理， 等流程点最后一步改状态的时候更新
		fesOnlineProgram.setOnlineProgramStatus("0");//未上线
		fesOnlineProgram.setInsertBy(sessionUser.getId());
		fesOnlineProgram.setInsertTime(new Date());
		fesOnlineProgram.setUpdateBy(sessionUser.getId());
		fesOnlineProgram.setUpdateTime(new Date());
		fesOnlineProgramService.saveFesOnlineProgram(fesOnlineProgram);
		return fesOnlineProgram;
	}

	/**
	 * 新增的 计算计划完成时间和上线流程状态
	 */
	private void calculateFields(FesOnlineProcessVO fesOnlineProcessVO, FesOnlineProgramVO fesOnlineProgramVO, FesStepVO fesStepVO) {
		Date planOnlineTime = fesOnlineProgramVO.getPlanOnlineTime();
		Date resultDate = null;
		// jdk1.6 Cannot switch on a value of type String for source level below
		// 1.7. Only convertible int values or enum variables are permitted
		// switch (fesStepVO.getStepNum()) {
		String stepNum = fesStepVO.getStepNum();
		Calendar calendar = Calendar.getInstance();
		if (stepNum.equals(StepNum.research.toString())) {
			fesOnlineProcessVO.setOnlineProcessStatus("03");
		} else if (stepNum.equals(StepNum.micro_message.toString())) {
			fesOnlineProcessVO.setOnlineProcessStatus("02");
		} else if (stepNum.equals(StepNum.draft_program.toString())) { // step 3
			fesOnlineProcessVO.setOnlineProcessStatus("02");
			calendar.setTime(planOnlineTime);
//			resultDate = this.calendarService.afterDays(calendar, 7);
			resultDate = DateUtils.addDays(new Date(), 7);
		} else if (stepNum.equals(StepNum.final_program.toString())) {
			fesOnlineProcessVO.setOnlineProcessStatus("02");
		} else if (stepNum.equals(StepNum.background_set.toString())) {
			fesOnlineProcessVO.setOnlineProcessStatus("02");
		} else if (stepNum.equals(StepNum.entity_cards.toString())) {// step 6
			fesOnlineProcessVO.setOnlineProcessStatus("07");
		} else if (stepNum.equals(StepNum.materials_design.toString())) { // step 7
			fesOnlineProcessVO.setOnlineProcessStatus("18"); //初始化物料设计改成18
			calendar.setTime(planOnlineTime);
//			resultDate = this.calendarService.afterDays(calendar, 20);
			resultDate = DateUtils.addDays(new Date(), 20);
		} else if (stepNum.equals(StepNum.equipment_distribution.toString())) { //step 8
			fesOnlineProcessVO.setOnlineProcessStatus("02");
		} else if (stepNum.equals(StepNum.train.toString())) {//step 9
			fesOnlineProcessVO.setOnlineProcessStatus("02");
		} else if (stepNum.equals(StepNum.online.toString())) { // step 10
			fesOnlineProcessVO.setOnlineProcessStatus("02");
			calendar.setTime(planOnlineTime);
//			resultDate = this.calendarService.afterDays(calendar, 30);
			resultDate = DateUtils.addDays(new Date(), 30);
		} else {
			//do nothing.
		}
		fesOnlineProcessVO.setPlanEndTime(resultDate);
	}
	@Resource FesOpenCardApplicationService fesOpenCardApplicationService;
	/**
	 * 上线前 查找流程中的十个步骤
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FesOnlineProcessVO> getComplexFesOnlineProcesss(FesOnlineProcessVO fesOnlineProcessVO) {
		this.initInputFesOnlineProcess(fesOnlineProcessVO);
		List<FesOnlineProcessVO> complexFesOnlineProcesss = fesOnlineProcessDao.getComplexFesOnlineProcesss(fesOnlineProcessVO);
		// 对附件，按更新时间排序
		String resourceRemark = fesOnlineProcessVO.getResourceRemark();
		List<FesOnlineProcessVO> resourceContainsProcessVOs = new LinkedList<FesOnlineProcessVO>();
		for (FesOnlineProcessVO fesOnlineProcess : complexFesOnlineProcesss) {
			//添加门店名称
			List<MktShopSurveyVO> mktShopSurveys = fesOnlineProcess.getMktShopSurveys();
			for (MktShopSurveyVO mktShopSurveyVO : mktShopSurveys) {
				MerchantVO merchantVOFromCRM = mktShopSurveyService.getMerchantVOFromCRM(mktShopSurveyVO.getMerchantId());
				mktShopSurveyVO.setStoreName(merchantVOFromCRM!=null? merchantVOFromCRM.getMerchantName(): "");
			}
			// 文件路径加入到map中: note: 为了解决static 类型不能用@value的问题
			Map<String, String> stepNumMatchFilePath = this.initMatchNumAndPath();
			String fileConfigPath = stepNumMatchFilePath.get(fesOnlineProcess.getFesStep().getStepNum());
			fesOnlineProcess.setFileConfigPath(fileConfigPath);
			this.addSortBy(fesOnlineProcess);
			//查询操作日志
			String stepNum = fesOnlineProcess.getFesStep().getStepNum();
			//第九步 需要添加回访单的信息
			this.addVisiteInfoForStep9(fesOnlineProcess, stepNum);
			//增加操作流水并排序
			this.addOperationLogAndSortBy(fesOnlineProcess, stepNum);
			//如果参数中有资源的描述，需要过滤资源对应的步骤
			if(StringUtils.isNotEmpty(resourceRemark)){
				String[] stepNums = resourceAndStepNumsMatcher.get(resourceRemark);
				if(Arrays.asList(stepNums).contains(stepNum)){
					String onlineProcessStatus = fesOnlineProcess.getOnlineProcessStatus();
					//实体卡步骤跨角色需要特殊处理
					if(StepNum.entity_cards.toString().equals(stepNum)){
						//判断是否已经存在开卡申请
						FesOpenCardApplicationVO fesOpenCardApplication = new FesOpenCardApplicationVO();
						fesOpenCardApplication.setMerchantId(fesOnlineProcessVO.getMerchantId());
						fesOpenCardApplication.setIsEnable(Constant.IS_ENABLE);
						List<FesOpenCardApplicationVO> cards = fesOpenCardApplicationService.getFesOpenCardApplications(fesOpenCardApplication);
						fesOnlineProcess.setHasCardReq(CollectionUtils.isNotEmpty(cards)? "1": "0");
						//需要显示具体的流程判断条件
						if((resourceRemark.equals(resource_fes_card_and_materials)&& Arrays.asList("07","08","09","12","13","20").contains(onlineProcessStatus))
								||(resourceRemark.equals(resource_end_custom_service)&&Arrays.asList("10","11","20").contains(onlineProcessStatus))
								||"1".equals(fesOnlineProcessVO.getItemStatus())){ //如果待办事项为已完成， 直接显示当前流程
							resourceContainsProcessVOs.add(fesOnlineProcess);
						}
					}else{
						resourceContainsProcessVOs.add(fesOnlineProcess);
					}
				}
			}
			//添加物料步骤 需求相关
			this.addMaterialRequsts(fesOnlineProcess);
			//添加默认值
			fesOnlineProcess.setOnlineStatuesForUI("0");
			if(fesOnlineProcess.getVisiterState()==null){
				fesOnlineProcess.setVisiterState("4");//给一个默认值
			}
			//代办事项已完成的
			if("1".equals(fesOnlineProcessVO.getItemStatus())){
				fesOnlineProcess.setOnlineProcessStatus("04");
			}
		}
		
		
		if(StringUtils.isNotEmpty(resourceRemark)){
			return resourceContainsProcessVOs;
		}else{
			return complexFesOnlineProcesss;
		}
	}

	/**
	 * @Description 添加物料步骤 需求相关
	 */
	private void addMaterialRequsts(FesOnlineProcessVO fesOnlineProcess) {
		FesMaterialRequestVO fesMaterialRequest = new FesMaterialRequestVO();
		fesMaterialRequest.setIsEnable("1");
		fesMaterialRequest.setProcessId(fesOnlineProcess.getId());
		List<FesMaterialRequestVO> fesMaterialRequests = this.fesMaterialRequestService.getFesMaterialRequests(fesMaterialRequest);
		fesOnlineProcess.setFesMaterialRequests(fesMaterialRequests);
	}
	/**
	 * 上线后 查找流程中的步骤
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FesOnlineProcessVO> getComplexFesOnlineProcesssAfterOnline(FesOnlineProcessVO fesOnlineProcessVO) {
		Integer merchantId = fesOnlineProcessVO.getMerchantId();
		List<FesOnlineProcessVO> fesOnlineProcesss = new LinkedList<FesOnlineProcessVO>();
		//查询营销活动
		FesMarketingActivityVO fesMarketingActivity = new FesMarketingActivityVO();
		fesMarketingActivity.setMerchantId(merchantId);
		fesMarketingActivity.setMarketingActivityStatus("1");//待创建
		fesMarketingActivity.setIsEnable("1");
		//查找营销活动和附件信息
		List<FesMarketingActivityVO> fesMarketingActivitys = this.fesMarketingActivityService.getFesMarketingActivitys(fesMarketingActivity);
		if(fesMarketingActivitys.size()>0){
			//添加营销活动 流水和附件信息
			FesOnlineProcessVO onlineProcessVO = this.addMarketingActivityLogAndAttachment(merchantId, fesMarketingActivitys);
			
			FesStepVO fesStep = new FesStepVO();
			fesStep.setStepName("营销活动创建");
			onlineProcessVO.setFesStep(fesStep);
			onlineProcessVO.setOnlineProcessStatus("1");
			fesOnlineProcesss.add(onlineProcessVO);
			if(onlineProcessVO.getVisiterState()==null){
				onlineProcessVO.setVisiterState("4");//给一个默认值
			}
		}
		/*
		 * 上线回访流程
		 */
		SysToDoListVO sysToDoList = new SysToDoListVO();
		sysToDoList.setBusinessTypes(new String[]{"11"}); //上线流程，实施培训回访
		sysToDoList.setInputItemTypes(new String[]{"04"});
		sysToDoList.setIsEnable("1");
		sysToDoList.setItemStatus("0");
		sysToDoList.setMerchantId(merchantId);
		List<SysToDoListVO> sysToDoLists = this.sysToDoListService.getSysToDoLists(sysToDoList);
		if(sysToDoLists.size()>0){
			FesOnlineProcessVO onlineProcessVO = new FesOnlineProcessVO();
			onlineProcessVO.setOnlineProcessStatus("2");
			onlineProcessVO.setOnlineStatuesForUI("1");
			FesStepVO fesStep = new FesStepVO();
			fesStep.setStepName("上线回访");
			onlineProcessVO.setFesStep(fesStep);
			fesOnlineProcesss.add(onlineProcessVO);
			List<SysOperationLogVO> sysOperationLogs = this.addOperationLogAndAttachementAfterOnline(merchantId, onlineProcessVO);
			onlineProcessVO.setMerchantId(merchantId);
			this.mergeOperationLogAndAttachments(onlineProcessVO, sysOperationLogs);
			
			if(onlineProcessVO.getVisiterState()==null){
				onlineProcessVO.setVisiterState("4");//给一个默认值
			}
		}
		
		for (FesOnlineProcessVO fesOnlineProcessVO2 : fesOnlineProcesss) {
			//代办事项已完成的
			if("1".equals(fesOnlineProcessVO.getItemStatus())){
				fesOnlineProcessVO2.setOnlineProcessStatus("04");
			}
		}
		return fesOnlineProcesss;
	}

	/**
	 * @Description 上线后流程，增加操作流水和附件
	 * @param merchantId
	 * @param onlineProcessVO
	 */
	private List<SysOperationLogVO> addOperationLogAndAttachementAfterOnline(Integer merchantId,
			FesOnlineProcessVO onlineProcessVO) {
		//添加项目上线流水
		SysOperationLogVO sysOperationLog = new SysOperationLogVO();
		sysOperationLog.setFesLogTypes(new String[]{"12","32"});
		sysOperationLog.setMerchantId(merchantId);
		List<SysOperationLogVO> sysOperationLogs = this.sysOperationLogService.getSysOperationLogs(sysOperationLog);
		onlineProcessVO.setSysOperationLogVOs(sysOperationLogs);
		List<SysDocumentVO> listSysDocumentVO = this.sysDocumentService.getDocumentByMerchantAndType(merchantId, "2");
//		List<SysDocumentVO> listSysDocumentVO1 = this.sysDocumentService.getDocumentByMerchantAndType(merchantId, "3");
//		if(listSysDocumentVO.size()>0&&listSysDocumentVO1.size()>0){
//			listSysDocumentVO.addAll(listSysDocumentVO1);
//		}
		SysDictionaryVO sysDictionaryVO =
				this.sysDictionaryService.querySysDictionaryByTypeAndKey(Constant.DIC_SYS_DOCUMENT, "2");
		String documentTypeText = sysDictionaryVO.getDictionaryValue();
		for (SysDocumentVO sysDocument : listSysDocumentVO) {
			sysDocument.setDocumentTypeText(documentTypeText);
			this.sysDocumentService.addAttachmentPathToSysDocument(sysDocument);//添加路径信息
		}
		onlineProcessVO.setListSysDocumentVO(listSysDocumentVO);
		//判断是否实施培训回访和录音同时存在
		//		flag = "1";// 无录音，有内容
		//		flag = "2";// 有录音，无内容
		//		flag = "3";// 有录音，有内容
		String statusForSysDocument = this.sysDocumentService.getStatusForSysDocument(merchantId, "2");
		onlineProcessVO.setVisiterState(statusForSysDocument);
		return sysOperationLogs;
	}

	/**
	 * 封装营销活动和附件
	 */
	private FesOnlineProcessVO addMarketingActivityLogAndAttachment(Integer merchantId,
			List<FesMarketingActivityVO> fesMarketingActivitys) {
		FesOnlineProcessVO onlineProcessVO = new FesOnlineProcessVO();
		onlineProcessVO.setFesMarketingActivitys(fesMarketingActivitys);
		onlineProcessVO.setOnlineStatuesForUI("1");
		for (FesMarketingActivityVO fesMarketingActivityVO : fesMarketingActivitys) {
			//添加营销活动流水
			SysOperationLogVO sysOperationLog = new SysOperationLogVO();
			sysOperationLog.setFesLogTypes(new String[]{"16"});
			sysOperationLog.setMerchantId(merchantId);
			sysOperationLog.setRemark(fesMarketingActivityVO.getId().toString());//营销活动ID
			List<SysOperationLogVO> sysOperationLogs = this.sysOperationLogService.getSysOperationLogs(sysOperationLog);
			List<Object> listAttachmentAndOperateLog = new ArrayList<Object>();
			listAttachmentAndOperateLog.addAll(sysOperationLogs);
			
			SysAttachmentVO sysAttachmentVO = fesMarketingActivityVO.getSysAttachmentVO();
			List<SysAttachmentVO> listSysDocumentVO = new LinkedList<SysAttachmentVO>();
			if(sysAttachmentVO!=null){
				listSysDocumentVO.add(sysAttachmentVO);
			}
			if(listSysDocumentVO!=null&&listSysDocumentVO.size()>0){
				listAttachmentAndOperateLog.addAll(listSysDocumentVO);
			}
			if(listAttachmentAndOperateLog.size()>0){
				Collections.sort(listAttachmentAndOperateLog, new BeanComparator(SysOperationLogVO.COLUMN_INSERT_TIME));
			}
			fesMarketingActivityVO.setListAttachmentAndOperateLog(listAttachmentAndOperateLog);
		}
		return onlineProcessVO;
	}

	/**
	 * @Description 上线后  附件和操作流水 合成一个对象，为了排序
	 * @param onlineProcessVO
	 * @param sysOperationLogs
	 * @return void
	 * @throws 
	 */
	private void mergeOperationLogAndAttachments(FesOnlineProcessVO onlineProcessVO, List<SysOperationLogVO> sysOperationLogs) {
		List<Object> listAttachmentAndOperateLog = new ArrayList<Object>();
		listAttachmentAndOperateLog.addAll(sysOperationLogs);
		List<SysDocumentVO> listSysDocumentVO = onlineProcessVO.getListSysDocumentVO();
		if(listSysDocumentVO!=null&&listSysDocumentVO.size()>0){
			String statusForSysDocument = this.sysDocumentService.getStatusForSysDocument(onlineProcessVO.getMerchantId(), "2");
			if("2".equals(statusForSysDocument) || "3".equals(statusForSysDocument)){//有录音的时候要添加到流水中
				listAttachmentAndOperateLog.addAll(listSysDocumentVO);
			}
		}
		if(listAttachmentAndOperateLog.size()>0){
			Collections.sort(listAttachmentAndOperateLog, new BeanComparator(SysOperationLogVO.COLUMN_INSERT_TIME));
		}
		onlineProcessVO.setListAttachmentAndOperateLog(listAttachmentAndOperateLog);
	}
	/**
	 * @Description 添加回访单信息
	 * @param fesOnlineProcess
	 * @return void
	 * @throws 
	 */
	private void addVisiteInfoForStep9(FesOnlineProcessVO fesOnlineProcess, String stepNum) {
		if(StepNum.train.toString().equals(stepNum)){
			Integer merchantId = fesOnlineProcess.getFesOnlineProgram().getMerchantId();
			List<SysDocumentVO> listSysDocumentVO = this.sysDocumentService.getDocumentByMerchantAndType(merchantId, "1");
			SysDictionaryVO sysDictionaryVO =
					this.sysDictionaryService.querySysDictionaryByTypeAndKey(Constant.DIC_SYS_DOCUMENT, "1");
			String documentTypeText = sysDictionaryVO.getDictionaryValue();
			for (SysDocumentVO sysDocument : listSysDocumentVO) {
				sysDocument.setDocumentTypeText(documentTypeText);
				this.sysDocumentService.addAttachmentPathToSysDocument(sysDocument);//添加路径信息
			}
			fesOnlineProcess.setListSysDocumentVO(listSysDocumentVO);
			//判断是否实施培训回访和录音同时存在
			//		flag = "1";// 无录音，有内容
			//		flag = "2";// 有录音，无内容
			//		flag = "3";// 有录音，有内容
			String statusForSysDocument = this.sysDocumentService.getStatusForSysDocument(merchantId, "1");
			fesOnlineProcess.setVisiterState(statusForSysDocument);
		}
	}

	/**
	 * @Description 添加操作流水并排序
	 */
	private void addOperationLogAndSortBy(FesOnlineProcessVO fesOnlineProcess, String stepNum) {
		SysOperationLogVO sysOperationLog = new SysOperationLogVO();
		sysOperationLog.setFesLogTypes(stepNumAndLogTypeMatcher.get(stepNum));
		sysOperationLog.setMerchantId(fesOnlineProcess.getFesOnlineProgram().getMerchantId());
		//保存操作流水
		List<SysOperationLogVO> sysOperationLogs = this.sysOperationLogService.getSysOperationLogs(sysOperationLog);
		if(sysOperationLogs!=null&&sysOperationLogs.size()>0){
			Collections.sort(sysOperationLogs, new BeanComparator(SysOperationLogVO.COLUMN_OPERATING_TIME));
		}
		fesOnlineProcess.setSysOperationLogVOs(sysOperationLogs);
		//附件和操作流水 合成一个对象，为了排序
		List<Object> listAttachmentAndOperateLog = new ArrayList<Object>();
		listAttachmentAndOperateLog.addAll(sysOperationLogs);
		List<SysAttachmentVO> fesSysAttachments = fesOnlineProcess.getFesSysAttachments();
		//添加操作人
		this.addOperationUser(fesSysAttachments);
		listAttachmentAndOperateLog.addAll(fesSysAttachments);
		List<SysDocumentVO> listSysDocumentVO = fesOnlineProcess.getListSysDocumentVO();

		if(StepNum.train.toString().equals(stepNum)&&listSysDocumentVO!=null&&listSysDocumentVO.size()>0){
			//过滤掉不是附件的信息
			List<SysDocumentVO> newSysDocumentVO = new LinkedList<SysDocumentVO>();
			for (SysDocumentVO sysDocument : listSysDocumentVO) {
				if(!StringUtils.isEmpty(sysDocument.getDocumentFilePath())){
					newSysDocumentVO.add(sysDocument); 
				}
			}
			listAttachmentAndOperateLog.addAll(newSysDocumentVO);
		}
		if(listAttachmentAndOperateLog.size()>0){
			Collections.sort(listAttachmentAndOperateLog, new BeanComparator(SysOperationLogVO.COLUMN_INSERT_TIME));
		}
		fesOnlineProcess.setListAttachmentAndOperateLog(listAttachmentAndOperateLog);
	}

	/**
	 * @param fesSysAttachments
	 */
	private void addOperationUser(List<SysAttachmentVO> fesSysAttachments) {
		for (SysAttachmentVO sysAttachmentVO : fesSysAttachments) {
			Integer insertBy = sysAttachmentVO.getInsertBy();
			//添加操作人
			SysUserVO sysUserVO = this.sysUserDao.getSysUserById(insertBy);
			if(sysUserVO!=null){
				sysAttachmentVO.setUserName(sysUserVO.getUserName());
			}
		}
	}

	/**
	 * @Description 比较流程属性
	 * @param fesOnlineProcess
	 * @return void
	 * @throws 
	 */
	private void addSortBy(FesOnlineProcessVO fesOnlineProcess) {
		if(fesOnlineProcess.getFesSysAttachments()!=null&& fesOnlineProcess.getFesSysAttachments().size()>0){
			Collections.sort(fesOnlineProcess.getFesSysAttachments(), new BeanComparator(SysAttachmentVO.COLUMN_UPDATE_TIME));
		}
		if(fesOnlineProcess.getMktShopSurveys()!=null&& fesOnlineProcess.getMktShopSurveys().size()>0){
			Collections.sort(fesOnlineProcess.getMktShopSurveys(), new BeanComparator(MktShopSurveyVO.COLUMN_UPDATE_TIME));
		}
		if(fesOnlineProcess.getFesRemarks()!=null && fesOnlineProcess.getFesRemarks().size()>0){
			Collections.sort(fesOnlineProcess.getFesRemarks(), new BeanComparator(FesRemarkVO.COLUMN_UPDATE_TIME));
		}
		if(fesOnlineProcess.getFesOpenCardApplications()!=null&& fesOnlineProcess.getFesOpenCardApplications().size()>0){
			Collections.sort(fesOnlineProcess.getFesOpenCardApplications(), new BeanComparator(FesOpenCardApplicationVO.COLUMN_UPDATE_TIME));
		}
		if(fesOnlineProcess.getFesTrainPlans()!=null&& fesOnlineProcess.getFesTrainPlans().size()>0){
			Collections.sort(fesOnlineProcess.getFesTrainPlans(), new BeanComparator(FesTrainPlanVO.COLUMN_UPDATE_TIME));
		}
	}

	/**
	 * @param fesOnlineProcessVO
	 * @return void
	 * @throws 
	 */
	private void initInputFesOnlineProcess(FesOnlineProcessVO fesOnlineProcessVO) {
		Integer processId = fesOnlineProcessVO.getProcessId();
		if(processId==null){
			Integer merchantId = fesOnlineProcessVO.getMerchantId();
			//查找上线流程
			FesOnlineProgramVO fesOnlineProgram = new FesOnlineProgramVO();
			fesOnlineProgram.setMerchantId(merchantId);
			List<FesOnlineProgramVO> fesOnlinePrograms = this.fesOnlineProgramService.getFesOnlinePrograms(fesOnlineProgram);
			if(fesOnlinePrograms!=null && fesOnlinePrograms.size()>0){
				fesOnlineProcessVO.setProgramId(fesOnlinePrograms.get(0).getId());
			}else{
				throw new FesBizException("商户关联上线计划错误");
			}
		}
	}
	
	/**
	 * 计算上线流程的总状态
	 * @return 
	 */
	@Override
	public String calculateOrUpdateProcessFinalStatus(Integer merchantId, SysUserVO sessionUser) {
		FesOnlineProgramVO fesOnlineProgram = new FesOnlineProgramVO();
		fesOnlineProgram.setMerchantId(merchantId);
		List<FesOnlineProgramVO> fesOnlinePrograms = fesOnlineProgramService.getFesOnlinePrograms(fesOnlineProgram);
		if(fesOnlinePrograms.size()<=0){
			throw new FesBizException("还未创建上线流程");
		}else{
			FesOnlineProgramVO fesOnlineProgramVO = fesOnlinePrograms.get(0);
			FesOnlineProcessVO fesOnlineProcess = new FesOnlineProcessVO();
			fesOnlineProcess.setProgramId(fesOnlineProgramVO.getId());
			List<FesOnlineProcessVO> complexFesOnlineProcesss = this.getFesOnlineProcesss(fesOnlineProcess);
			return this.calculateProcessFinalStatus(complexFesOnlineProcesss, sessionUser);
		}
	}
	/**
	 * 计算上线流程的总状态
	 * @param sessionUser 
	 * @return 
	 */
	@Override
	public String calculateProcessFinalStatus(List<FesOnlineProcessVO> complexFesOnlineProcesss, SysUserVO sessionUser) {
		FesOnlineProcessVO fesOnlineProcessVO10 = null;// step 10
		boolean calculatedStatus = true;
		for (FesOnlineProcessVO fesOnlineProcess : complexFesOnlineProcesss) {
			String stepNum = getStepNumByProcessId(fesOnlineProcess.getId());
			if (stepNum.equals(StepNum.online.toString())) {
				fesOnlineProcessVO10 = fesOnlineProcess;
				continue;
			}
			// 如果步骤1-9所有状态对为 04（已完成），步骤8为（04，或 15），则步骤10 的上线状态变为已完成
			String onlineProcessStatus = fesOnlineProcess.getOnlineProcessStatus();
			if (stepNum.equals(StepNum.equipment_distribution.toString())) {
				if (!onlineProcessStatus.equals("04") && !onlineProcessStatus.equals("15")) {
					// 只要存在一个部位完成的状态，认为 总状态未完成，未退出循环是为了获得 step10 的数据
					calculatedStatus = false;
				}
			} else {
				if (!onlineProcessStatus.equals("04")) {
					calculatedStatus = false;
				}
			}
		}
		if (fesOnlineProcessVO10 != null) {
			fesOnlineProcessVO10.setOnlineProcessStatus(calculatedStatus ? "03" : "02");//03 待确认(为可点状态) 
			if(sessionUser!=null){ //更改状态的时候调用
				fesOnlineProcessVO10.setUpdateBy(sessionUser.getId());
				fesOnlineProcessVO10.setUpdateTime(new Date());
				this.updateSpecificFesOnlineProcessStatues(fesOnlineProcessVO10);
			}
			return fesOnlineProcessVO10.getOnlineProcessStatus();
		}
		return null;
	}

	/**
	 * @param fesOnlineProcess  [id, onlineProcessStatus]
	 * @param sessionUser
	 */
	@Override
	public JsonResult updateFesOnlineProcessStatusForReject(FesOnlineProcessVO fesOnlineProcess, SysUserVO sessionUser) {
		Integer processId = fesOnlineProcess.getId();
		FesOnlineProcessVO fesOnlineProcessDB = this.fesOnlineProcessDao.getFesOnlineProcessById(processId);
		Integer programId = fesOnlineProcessDB.getProgramId();
		FesOnlineProgramVO fesProgramVO = this.fesOnlineProgramService.getFesOnlineProgramById(programId);
		Integer merchantId = fesProgramVO.getMerchantId();
		fesOnlineProcess.setUpdateBy(sessionUser.getId());
		fesOnlineProcess.setUpdateTime(new Date());
		/*
		 * 更新状态
		 */
		FesOnlineProcessVO fesOnlineProcessVO = this.updateSpecificFesOnlineProcessStatues(fesOnlineProcess);
		String onlineProcessStatus = fesOnlineProcess.getOnlineProcessStatus();
		FesStepVO fesStepVO = this.getStepByProcessId(processId);
		Integer stepId = fesStepVO.getId();
		String stepNum = fesStepVO.getStepNum();
		//生成待办事项
		if (StepNum.entity_cards.toString().equals(stepNum)) {
			if("20".equals(onlineProcessStatus)){
				SysToDoListVO sysToDoList = new SysToDoListVO();
				sysToDoList.setRelatedId(processId);
				sysToDoList.setRelatedType("1");
				sysToDoList.setPriorityLevelType("01");// 一般
				sysToDoList.setItemType("01"); // 上线工作
				sysToDoList.setItemContent(toDoListStep6_init_non_period+"(被驳回)。");
				sysToDoList.setResourceRemark(resource_fes_card_and_materials);
				sysToDoList.setBusinessType("4"); //4-上线流程[实体卡制作]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			}
		}
		//添加流水
		SysOperationLogVO sysOperationLog = new SysOperationLogVO();
		sysOperationLog.setInsertBy(sessionUser.getId());
		sysOperationLog.setInsertTime(new Date());
		sysOperationLog.setMerchantId(merchantId);
		sysOperationLog.setOperatingTime(new Date());
		sysOperationLog.setModuleType(Constant._FES);
		if (StepNum.entity_cards.toString().equals(stepNum)) {
			if("20".equals(onlineProcessStatus)){
				//添加【驳回】按钮，能填写备注信息，并添加流水，流水内容为 开卡申请被驳回，（备注内容）。[操作人: ]
				sysOperationLog.setDescription("开卡申请被驳回,"+fesOnlineProcess.getRemark());
				sysOperationLog.setFesLogType("8");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		sysOperationLog.setFinalStatus("02");
		//从新做一次查询， 为了获得真实的状态
		fesOnlineProcessDB = this.fesOnlineProcessDao.getFesOnlineProcessById(processId);
		sysOperationLog.setOnlineProcessStatus(fesOnlineProcessDB.getOnlineProcessStatus()); 
		sysOperationLog.setStepId(stepId);
		sysOperationLog.setStepNum(stepNum);
		sysOperationLog.setProcessId(processId);
		JsonResult jsonResult = new JsonResult(fesOnlineProcessVO!=null);
		jsonResult.setData(sysOperationLog);//用流水对象封装一些信息 返回
		return jsonResult;
	}
	/**
	 * 包含所有确认按钮的操作
	 * 
	 * 1. 更新上线流程状态, 前台点击操作， 对某些状态要生成待办事项
	 * 2. 某些操作要添加流水
	 * 
	 * 待办事项相关
	 * 制卡/物料   1、实体卡配送，请尽快安排。（当步骤6，状态变更为“12-待下单”时收到的待办事项）
	 * 
		后端客服
		1、实施培训回访，请尽快安排。（当步骤9，状态变更为“16-待回访”时收到的待办事项）
		2、营销活动创建，请尽快安排。（当步骤4，状态变更为“06-待创建营销活动”时收到的待办事项）
		3、开卡申请，请尽快安排。（当步骤6，状态变更为“10-待上传开卡表单”时收到的待办事项）
		
	 * @param fesOnlineProcess  [id, onlineProcessStatus]
	 * @param sessionUser
	 */
	@Override
//	@OperationLog(moduleType="fes") //考虑到流水需要过多参数的问题，目前不启用这种方式
	public JsonResult updateFesOnlineProcessStatus(FesOnlineProcessVO fesOnlineProcess, SysUserVO sessionUser) {
		Integer processId = fesOnlineProcess.getId();
		FesOnlineProcessVO fesOnlineProcessDB = this.fesOnlineProcessDao.getFesOnlineProcessById(processId);
		Integer programId = fesOnlineProcessDB.getProgramId();
		FesOnlineProgramVO fesProgramVO = this.fesOnlineProgramService.getFesOnlineProgramById(programId);
		Integer merchantId = fesProgramVO.getMerchantId();
		fesOnlineProcess.setUpdateBy(sessionUser.getId());
		fesOnlineProcess.setUpdateTime(new Date());
		/*
		 * 更新状态
		 */
		FesOnlineProcessVO fesOnlineProcessVO = this.updateSpecificFesOnlineProcessStatues(fesOnlineProcess);
		FesStepVO fesStepVO = this.getStepByProcessId(processId);
		Integer stepId = fesStepVO.getId();
		String stepNum = fesStepVO.getStepNum();
		if (stepNum.equals(StepNum.background_set.toString())) {// step 5 后台设置
			boolean syncMsgTplsAndCardTypes = tradeMessageTemplateService.syncMsgTplsAndCardTypes(merchantId);
			LOG.info(syncMsgTplsAndCardTypes? "同步短信模板和卡类型成功": "同步短信模板和卡类型失败!!!");
		}
		String onlineProcessStatus = fesOnlineProcess.getOnlineProcessStatus();
		/*
		 * 生成待办事项(包含第十步的特殊处理)，如果有以前的待办事项，则关闭
		 */
		this.generatorOrCloseToDoList(sessionUser, processId, merchantId, stepNum, onlineProcessStatus);
		//如果总状态不是已完成， 计算总状态，如果满足条件就更新
		String finalStatus = "02";
		//只有当 点击确认的时候，并且不是第十步，才计算总状态，总状态才有可能变成03
		if (!stepNum.equals(StepNum.online.toString())&&(onlineProcessStatus.equals("04")||onlineProcessStatus.equals("15"))) {
			finalStatus = this.calculateOrUpdateProcessFinalStatus(merchantId, sessionUser);
		}
		if(stepNum.equals(StepNum.online.toString())&&(onlineProcessStatus.equals("04"))){
			finalStatus = "04";
		}
		/*
		 * 添加流水 
		 */
		SysOperationLogVO sysOperationLog = this.addOperatonLog(sessionUser, fesOnlineProcess,fesProgramVO, stepNum, onlineProcessStatus);
		if(sysOperationLog==null){
			sysOperationLog = new SysOperationLogVO();
		}
		sysOperationLog.setFinalStatus(finalStatus);
		//从新做一次查询， 为了获得真实的状态
		fesOnlineProcessDB = this.fesOnlineProcessDao.getFesOnlineProcessById(processId);
		sysOperationLog.setOnlineProcessStatus(fesOnlineProcessDB.getOnlineProcessStatus()); 
		sysOperationLog.setStepId(stepId);
		sysOperationLog.setStepNum(stepNum);
		sysOperationLog.setProcessId(processId);
		JsonResult jsonResult = new JsonResult(fesOnlineProcessVO!=null);
		jsonResult.setData(sysOperationLog);//用流水对象封装一些信息 返回
		return jsonResult;
	}

	/**
	 *  生成待办事项，如果有以前的待办事项，则关闭
	 */
	private void generatorOrCloseToDoList(SysUserVO sessionUser, Integer processId, Integer merchantId, String stepNum,
			String onlineProcessStatus) {
		//生成代办事项
		SysToDoListVO sysToDoList = new SysToDoListVO();
		sysToDoList.setRelatedId(processId);
		sysToDoList.setRelatedType("1");
		sysToDoList.setPriorityLevelType("01");//一般
		sysToDoList.setItemType("01"); //上线工作
		if (stepNum.equals(StepNum.micro_message.toString())) {// step 2 微信申请
			if (onlineProcessStatus.equals("04")) {
				this.closeToDoListsForMrechant(processId,"3", merchantId);
			}
		} else if (stepNum.equals(StepNum.equipment_distribution.toString())) {// step 08 设备配送
			if (onlineProcessStatus.equals("04") || onlineProcessStatus.equals("15")) {
				this.closeToDoListsForMrechant(processId, "8", merchantId);
			}
		} else if (stepNum.equals(StepNum.background_set.toString())) {// step 08 设备配送
			if (onlineProcessStatus.equals("04")) {
				this.closeToDoListsForMrechant(processId, "13", merchantId);
			}
		} else if (stepNum.equals(StepNum.entity_cards.toString())) {// step 6 实体卡制作
			if (onlineProcessStatus.equals("10")) {// 申请开卡 ， 点击表单确认的时候， 有fesEntityCard 类传过来
				this.closeToDoListsForMrechant(processId,"4", merchantId);
				this.closeToDoListsForMrechant(processId,"7", merchantId);//如果有被驳回的情况，需要关闭自己原来的待办事项
				sysToDoList.setItemContent(toDoListStep6_10);
				sysToDoList.setResourceRemark(resource_end_custom_service);
				sysToDoList.setBusinessType("7"); //-上线流程[开卡申请]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			}else if (onlineProcessStatus.equals("12")) {// 待下单
				this.closeToDoListsForMrechant(processId, "7", merchantId);
				sysToDoList.setItemContent(toDoListStep6_12);
				sysToDoList.setResourceRemark(resource_fes_card_and_materials);
				sysToDoList.setBusinessType("6"); //上线流程[实体卡配送]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			}else if (onlineProcessStatus.equals("04")) {
				this.closeToDoListsForMrechant(processId, "6", merchantId);
			}
		} if (stepNum.equals(StepNum.materials_design.toString())) {
			if (onlineProcessStatus.equals("14")) {
				sysToDoList.setResourceRemark(resource_fes_card_and_materials);
				sysToDoList.setItemContent(toDoListStep7_init);
				sysToDoList.setBusinessType("5"); //5-上线流程[物料设计]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			}else if (onlineProcessStatus.equals("04")) {
				 this.closeToDoListsForMrechant(processId, "5", merchantId);
			 }
		} else if (stepNum.equals(StepNum.final_program.toString())) {// step 4 会员营销方案确认
			if (onlineProcessStatus.equals("06")) {// 06-待创建营销活动
				sysToDoList.setItemContent(toDoListStep4_06);
				sysToDoList.setResourceRemark(resource_end_custom_service);
				sysToDoList.setBusinessType("9"); //9-上线流程[营销活动创建]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			}else if (onlineProcessStatus.equals("04")) {
				this.closeToDoListsForMrechant(processId, "9", merchantId);
				//生成后台设置的待办事项
				FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
				fesOnlineProcessVO.setMerchantId(merchantId);
				fesOnlineProcessVO.setStepNum(StepNum.background_set.toString());
				FesOnlineProcessVO process = this.getFesOnlineProcessByMerchantAndStep(fesOnlineProcessVO);
				sysToDoList.setRelatedId(process.getId()); //改为后台的流程
				sysToDoList.setItemContent(FesOnlineProcessServiceImpl.toDoListStep5_init);
				sysToDoList.setResourceRemark(resource_end_custom_service);
				sysToDoList.setBusinessType("13"); //上线流程[后台设置]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			}
		} else if (stepNum.equals(StepNum.train.toString())) {// step 9 培训计划
			if (onlineProcessStatus.equals("16")) { // 16-待回访 
				sysToDoList.setItemContent(toDoListStep9_16);
				sysToDoList.setResourceRemark(resource_end_custom_service);
				sysToDoList.setBusinessType("10"); //10-上线流程[实施培训回访]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			}else if (onlineProcessStatus.equals("04")) {
				this.closeToDoListsForMrechant(processId, "10", merchantId);//10-上线流程[实施培训回访]
			}
		}else if (stepNum.equals(StepNum.online.toString())) {// step 10 项目上线
			if (onlineProcessStatus.equals("04")) { // 04已完成
				this.closeToDoListsForMrechant(processId, null, merchantId);
				sysToDoList.setItemContent(toDoListStep10_04);
				sysToDoList.setResourceRemark(resource_end_custom_service);
				sysToDoList.setBusinessType("11");//上线后服务[上线回访]
				sysToDoList.setItemType("04"); //上线后服务 [特殊处理]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
				// 项目上线,更改服务结束时间
				this.updateMerchantTimeForOnline(merchantId);
				//项目上线,更改实际上线时间
				this.updateProgramOnlineTime(merchantId);
			}
		}
	}
	
	/**
	 * 上线后确认已回访后关闭待办事项
	 */
	@Override
	public void closeToDoListsAfterViste(Integer merchantId){
		SysToDoListVO sysToDoList = new SysToDoListVO();
		sysToDoList.setBusinessTypes(new String[]{"11"}); //上线流程，实施培训回访
		sysToDoList.setInputItemTypes(new String[]{"04"});
		sysToDoList.setMerchantId(merchantId);
		
		int row = this.sysToDoListService.batchUpdateCloseSysToDoLists(sysToDoList);
		LOG.info("关闭了"+row+"条待办事项");
	}
	/**
	 * @Description 项目上线,更改实际上线时间
	 * @param merchantId
	 * @return void
	 * @throws 
	 */
	@Override
	public void updateProgramOnlineTime(Integer merchantId) {
		FesOnlineProgramVO fesOnlineProgram = new FesOnlineProgramVO();
		fesOnlineProgram.setMerchantId(merchantId);
		List<FesOnlineProgramVO> fesOnlinePrograms = this.fesOnlineProgramService.getFesOnlinePrograms(fesOnlineProgram);
		FesOnlineProgramVO fesOnlineProgramVO = fesOnlinePrograms.get(0);
		fesOnlineProgram.setId(fesOnlineProgramVO.getId());
		fesOnlineProgram.setOnlineTime(new Date());
		this.fesOnlineProgramService.updateFesOnlineProgram(fesOnlineProgram);
	}

	/**
	 * @Description 项目上线,更改服务结束时间
	 * @param merchantId
	 * @return void
	 * @throws 
	 */
	private void updateMerchantTimeForOnline(Integer merchantId) {
		SynMerchantVO synMerchantVO = this.synMerchantDao.getSynMerchantById(merchantId);
		SynMerchantVO updatedMerchantVO = new SynMerchantVO();
		updatedMerchantVO.setMerchantId(synMerchantVO.getMerchantId());
		updatedMerchantVO.setUpdateTime(new Date());
		updatedMerchantVO.setMerchantStatus("1");
		updatedMerchantVO.setServiceStartTime(new Date());
		//计算服务结束时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR)+synMerchantVO.getServiceYear().intValue();
		int month = calendar.get(Calendar.MONTH)+synMerchantVO.getFreeMonth().intValue(); //赠送月份取值范围0-5
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if(month>=11){
			year = year+1;
			month = month%12;
		}
		calendar.set(year, month, day);
		updatedMerchantVO.setServiceEndTime(calendar.getTime());
		this.synMerchantDao.updateSynMerchant(updatedMerchantVO);
	}

	/**
	 * @Description 点击某些页面按钮需要添加流水
	 */
	private SysOperationLogVO addOperatonLog(SysUserVO sessionUser,FesOnlineProcessVO fesOnlineProcess,
			FesOnlineProgramVO fesProgramVO, String stepNum,
			String onlineProcessStatus) {
		Integer merchantId = fesProgramVO.getMerchantId();
		SysOperationLogVO sysOperationLog = new SysOperationLogVO();
		sysOperationLog.setInsertBy(sessionUser.getId());
		sysOperationLog.setInsertTime(new Date());
		sysOperationLog.setMerchantId(merchantId);
		sysOperationLog.setOperatingTime(new Date());
		sysOperationLog.setModuleType(Constant._FES);
		
		if(stepNum.equals(StepNum.research.toString())){//step1
			if("04".equals(onlineProcessStatus)){
				sysOperationLog.setDescription("确认完成调研。");
				sysOperationLog.setFesLogType("3");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		if(stepNum.equals(StepNum.micro_message.toString())){//step2
			if("04".equals(onlineProcessStatus)){ 
				sysOperationLog.setFesLogType("4");
				sysOperationLog.setDescription("确认完成。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		if(stepNum.equals(StepNum.draft_program.toString())){//step3
			if("04".equals(onlineProcessStatus)){ 
				sysOperationLog.setFesLogType("5");
				sysOperationLog.setDescription("确认完成。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		if(stepNum.equals(StepNum.final_program.toString())){//step4
			if("06".equals(onlineProcessStatus)){
				sysOperationLog.setFesLogType("6");
				sysOperationLog.setDescription("方案已确认，等待客服处理。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}else if("04".equals(onlineProcessStatus)){
				sysOperationLog.setFesLogType("6");
				sysOperationLog.setDescription("营销活动已创建。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		/*	原有逻辑 在 2014-10-22 17:53:50之前
      if(stepNum.equals(StepNum.background_set.toString())){//step5
			if("04".equals(onlineProcessStatus)){
				sysOperationLog.setFesLogType("7");
				sysOperationLog.setDescription("确认完成了后台设置。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}*/
		if(stepNum.equals(StepNum.background_set.toString())){//step5
			if("04".equals(onlineProcessStatus)){
				sysOperationLog.setFesLogType("7");
				sysOperationLog.setDescription("后台设置完成。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}else if("03".equals(onlineProcessStatus)){ 
				//后台设置的描述弹框填写写信即为流水内容
				sysOperationLog.setDescription(fesOnlineProcess.getRemark());
				sysOperationLog.setFesLogType("7");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		if(stepNum.equals(StepNum.entity_cards.toString())){//step6
			if("10".equals(onlineProcessStatus)){ 
				sysOperationLog.setDescription("已提交开卡申请。");
				sysOperationLog.setFesLogType("8");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}else if("20".equals(onlineProcessStatus)){ 
				sysOperationLog.setDescription("已申请开卡。");
				sysOperationLog.setFesLogType("14");//14 开卡申请单
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}else if("12".equals(onlineProcessStatus)){ 
				sysOperationLog.setDescription("客服确认已开卡。");
				sysOperationLog.setFesLogType("8");//实体卡制作
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}else if("13".equals(onlineProcessStatus)){ 
				Assert.notNull(fesOnlineProcess.getPlanReciveCardTime().getTime(), "预计出卡时间不能为空!");
				sysOperationLog.setDescription("确认下单制卡。预计出卡时间："+
						FastDateFormat.getInstance("yyyy-MM-dd").format(fesOnlineProcess.getPlanReciveCardTime().getTime()));
				sysOperationLog.setFesLogType("8");//实体卡制作
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}else if("04".equals(onlineProcessStatus)){ 
				FesStowageVO fesStowageVO = fesOnlineProcess.getFesStowageVO();
				sysOperationLog.setFesLogType("15");//15 实体卡制作[配载]
				sysOperationLog.setDescription("确认卡已配送。(快递单号:"+fesStowageVO.getLogisticsNum()+")");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		if(stepNum.equals(StepNum.materials_design.toString())){//step7
			if("14".equals(onlineProcessStatus)){ 
				sysOperationLog.setFesLogType("9");
				sysOperationLog.setDescription("确认需求已完成。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}else if("04".equals(onlineProcessStatus)){ 
				sysOperationLog.setFesLogType("9");
				sysOperationLog.setDescription("确认物料设计完成。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		if(stepNum.equals(StepNum.equipment_distribution.toString())){//step 8
			if("04".equals(onlineProcessStatus)){ 
				String desc = getStowageLogDesc(fesOnlineProcess);
				sysOperationLog.setFesLogType("10");
				sysOperationLog.setDescription(desc);
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}else if("15".equals(onlineProcessStatus)){ 
				sysOperationLog.setFesLogType("10");
				sysOperationLog.setDescription("商户无需求。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		if(stepNum.equals(StepNum.train.toString())){//step 9
			if("17".equals(onlineProcessStatus)){ 
				sysOperationLog.setFesLogType("11");
				sysOperationLog.setDescription("确认培训已完成。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}else if("04".equals(onlineProcessStatus)){
				sysOperationLog.setFesLogType("11");
				//添加客户评分
				SysDocumentVO sysDocumentVO = this.sysDocumentDao.getOptionContentByMerchantId(merchantId);
				String optionContent = sysDocumentVO==null? "": sysDocumentVO.getOptionContent();
				String rating = StringUtils.isEmpty(optionContent)?"": "(客户评分："+optionContent+")";
				sysOperationLog.setDescription("确认已回访。"+ rating);
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		if(stepNum.equals(StepNum.online.toString())){//step 10
			if("04".equals(onlineProcessStatus)){
				sysOperationLog.setFesLogType("12");
				sysOperationLog.setDescription("确认已上线。");
				sysOperationLogService.saveSysOperationLog(sysOperationLog);
			}
		}
		return sysOperationLog;
	}

	/**
	 * @Description 计算设备配送描述文字
	 */
	private String getStowageLogDesc(FesOnlineProcessVO fesOnlineProcess) {
		List<FesStowageVO> fesStowages = fesOnlineProcess.getFesStowages();
		String escape = System.getProperty("line.separator");
		String prefix = "设备已配送"+escape;
		for (FesStowageVO fesStowageVO : fesStowages) {
			List<FesStowageDtlVO> fesStowageDtls = fesStowageVO.getFesStowageDtls();
			StringBuffer  text = new StringBuffer("[");
			for (FesStowageDtlVO fesStowageDtlVO : fesStowageDtls) {
				SysDictionaryVO sysDictionary = 
					this.sysDictionaryService.querySysDictionaryByTypeAndKey(Constant.DIC_CATEGORY, fesStowageDtlVO.getCategory());
				String categoryText = sysDictionary.getDictionaryValue();
				text.append(categoryText+ "*"+fesStowageDtlVO.getAmount().intValue()+" ");
			}
			MktContactVO mktContactById = this.mktContactService.getMktContactById(fesStowageVO.getRecipient());
			String contactName = mktContactById==null? "": mktContactById.getName(); //收件人
			String desc ="   "+ text.toString()+"] 快递单号:"+fesStowageVO.getLogisticsNum()+", 收件人:"+contactName + escape;
			prefix+=desc;
		}
		return prefix;
	}

	/**
	 * @Description 为某一个商户的某一个流程批关闭待办事项
	 * @param processId
	 * @param merchantId
	 */
	@Override
	public void closeToDoListsForMrechant(Integer processId, String businessType, Integer merchantId) {
		SysToDoListVO inputSysToDoListVO = new SysToDoListVO();
		inputSysToDoListVO.setMerchantId(merchantId);
		inputSysToDoListVO.setRelatedType("1");
		inputSysToDoListVO.setBusinessType(businessType);
		inputSysToDoListVO.setRelatedId(processId);
		int row = this.sysToDoListService.batchUpdateCloseSysToDoLists(inputSysToDoListVO);
		LOG.info("关闭了"+row+"条待办事项");
	}

	
	/**
	 * 删除附件及相关的表
	 * @param attachmentId 系统附件id
	 */
	@Override
	public JsonResult deleteSysAttachmentById (Integer processId, Integer attachmentId, String prefixPath, SysUserVO sessionUser){
		String stepNum = this.getStepNumByProcessId(processId);
		//先删除关联表
		fesProcessAttachmentDao.deleteFesProcessAttachmentBySysAttId(attachmentId);
		//删除附件表
		SysAttachmentVO sysAttachmentVO = sysAttachmentDao.getSysAttachmentById(attachmentId);
		int row = sysAttachmentDao.deleteSysAttachmentById(attachmentId);
		//删除文件
		JsonResult jsonResult = new JsonResult(row > 0);
		//文件路径加入到map中: note: 为了解决static 类型不能用@value的问题
		Map<String, String> stepNumMatchFilePath = this.initMatchNumAndPath(); 
		String dbAttachmentPath =StringUtils.isEmpty(sysAttachmentVO.getAttachmentPath())?"": sysAttachmentVO.getAttachmentPath();
		String relPath = stepNumMatchFilePath.get(stepNum) + dbAttachmentPath+"/"+sysAttachmentVO.getAttachmentName();
		File file = new File(prefixPath, relPath);
		if(file.exists()){
			file.delete();
			/*
			 * 状态变更
			 */
			jsonResult.setData(this.updateProccessStatusForDeleteFile(processId, sessionUser));
		}else{
			LOG.info("要删除的文件不存在 "+ prefixPath+"/"+relPath);
//			throw new FesBizException("要删除的文件不存在 ");
		}
		return jsonResult;
	}
	
	/**
	 * @Description 上传附件 成功后 更新流程状态
	 *    如果上传成功判断附件是否等于1，等于一要变更当前流程的状态， 大于1说明状态已经做了变更(不包括步骤6和7)
	 * @param processId
	 * @return FesOnlineProcessVO
	 */
	@Override
	public FesOnlineProcessVO updateProccessStatusForDeleteFile(Integer processId, SysUserVO sessionUser) {
		FesStepVO fesStepVO = this.getStepByProcessId(processId);
		String stepNum = fesStepVO.getStepNum();
		Integer stepId = fesStepVO.getId();
		FesOnlineProcessVO fesOnlineProcessVO = this.fesOnlineProcessDao.getFesOnlineProcessById(processId);
		FesOnlineProcessVO resultFesOnlineProcessVO = new FesOnlineProcessVO();
		Integer userId = sessionUser.getId();
		//删除了所有附件的时候变更状态
		List<FesProcessAttachmentVO> attachmentListByProcess = getAttachmentListByProcess(processId, null);
		if (stepNum.equals(StepNum.draft_program.toString())) { // step 3 会员营销方案洽谈
			if(attachmentListByProcess.size()==0){
				resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "02", userId);
			}
		} else if (stepNum.equals(StepNum.final_program.toString())) {//step 4 会员方案确认
			if(attachmentListByProcess.size()==0){
				resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "02", userId);
			}
		} 
		if (stepNum.equals(StepNum.entity_cards.toString())) {// step 6 实体卡制作
			if(getAttachmentListByProcess(processId, "3").size()==0){// 3卡样源文件
				resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "07", userId);
			}else if(getAttachmentListByProcess(processId, "4").size()==0){// 4-卡样确认单
				resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "08", userId);
			}else if(getAttachmentListByProcess(processId, "12").size()==0){//开卡表单
				resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "10", userId);
			}
		} else if (stepNum.equals(StepNum.materials_design.toString())) {// step 7 物料设计
			// 是否表单和 物料设计素材都存在, 如果没有都存在，状态变回来
			if(!this.fesMaterialRequestService.checkHasBothRequirementAndAttachment(fesOnlineProcessVO)){
				if(fesOnlineProcessVO.getOnlineProcessStatus().equals("19")){
					resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "18", userId);
				}
			}
			//必须保证物料设计源文件和缩略图都为空的情况下才更改状态
			boolean flag = checkIfChangeStatusForStep7(processId);
			if(!flag){
				if(fesOnlineProcessVO.getOnlineProcessStatus().equals("02")){
					resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "14", userId);
				}
			}
		}else if (stepNum.equals(StepNum.train.toString())) {// step 9 实施培训
			if(attachmentListByProcess.size()==0){
				resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "02", userId);
			}
		}
		//如果没有更新状态，则返回原来的状态
		if(resultFesOnlineProcessVO.getOnlineProcessStatus() == null){
			resultFesOnlineProcessVO.setOnlineProcessStatus(fesOnlineProcessVO.getOnlineProcessStatus());
			resultFesOnlineProcessVO.setStepId(stepId);
			resultFesOnlineProcessVO.setStepNum(stepNum);
		}
		return resultFesOnlineProcessVO;
	}
	
	@Override
	public JsonResult saveUploadFilesForReview(MultipartFile myfile, String basePath){
		JsonResult fileInfo =null;
		try {
			//文件路径加入到map中: note: 为了解决static 类型不能用@value的问题
			fileInfo = FileUploaderUtil.uploadFile(myfile, basePath+"/temp", null,0L);	
			Map<String, Object> file = (Map<String, Object>)fileInfo.getData();
			file.put("fileFullPath", "/temp/"+file.get("attachmentName"));
		} catch (IOException e) {
			throw new FesBizException("文件读写错误"+e);
		}
		return fileInfo;
	}
	/**
	 * @Description  上传附件
	 * 所有的上传，所有的删除只涉及到状态的变更， 不涉及待办事项的历史更新和待办事项的创建，也不涉及到操作日志的添加
	 * 
	 * 1.调用API上传文件到文件实际路径
	 * 2.如果文件上传成功，保存文件信息到数据库，保存关联表，系统附件表
	 * 3.如果上传成功判断附件是否等于1，等于1要变更当前流程的状态， 大于1说明状态已经做了变更
	 * 3.查询时候已经有创建的待办事项，如果没有则创建待办事项
	 * 
	 * @param fileMap  方法saveUploadFilesForReview 返回的临时文件信息
	 */
	@Override
	public JsonResult saveUploadFiles(MultipartFile myfile, Integer processId, Integer typeId, 
			String basePath, Map<String, Object> fileMap, SysUserVO sessionUser) {
		
		Integer userId = sessionUser.getId();
		FesStepVO fesStepVO = this.getStepByProcessId(processId);
		String stepNum = fesStepVO.getStepNum();
		Integer stepId = fesStepVO.getId();
		JsonResult fileInfo =null;
		try {
			//文件路径加入到map中: note: 为了解决static 类型不能用@value的问题
			Map<String, String> stepNumMatchFilePath = this.initMatchNumAndPath(); 
			if(myfile!=null){
				fileInfo = FileUploaderUtil.uploadFile(myfile, basePath+"/"+stepNumMatchFilePath.get(stepNum), null,
						stepNumMatchFileSize.get(stepNum));
			}else{//有临时文件目录
				fileInfo = new JsonResult();
				fileInfo.setFlag(true).setMessage("上传成功");
				fileInfo.setData(fileMap);
				String attachmentName = (String)fileMap.get("attachmentName");
				File tempFile = new File(basePath+"/temp/"+attachmentName);
				FileUtils.copyFile(tempFile, new File(basePath+"/"+stepNumMatchFilePath.get(stepNum)+"/"+attachmentName));
				tempFile.delete();//删除临时文件
			}
		} catch (IOException e) {
			throw new FesBizException("文件读写错误"+e);
		}
		if (fileInfo.getFlag() == 1) {// 上传成功
			SysAttachmentVO sysAttachement = this.saveAttachement(processId, typeId, userId, fileInfo);
			//对于以下步骤，上传成功后要删除原有的附件 （上传附件覆盖源文件）
			if (stepNum.equals(StepNum.final_program.toString())||stepNum.equals(StepNum.background_set.toString())||
					(stepNum.equals(StepNum.entity_cards.toString())&&Arrays.asList(3,4).contains(typeId))||
					(stepNum.equals(StepNum.materials_design.toString())&&Arrays.asList(6,7,8,9,10,11,13,15).contains(typeId))) {
				/*
				 * 删除动作中也有状态的变更
				 */
				this.deleteOldAttachments(processId, typeId, basePath, stepNum, sysAttachement,sessionUser);
			}
			/*
			 * 如果上传成功判断附件是否等于1，等于一要变更当前流程的状态， 大于1说明状态已经做了变更(不包括步骤6和7)
			 */
			FesOnlineProcessVO fesOnlineProcessVO = this.updateProccessStatusForUpload(processId, userId); 
			String processAttachmentType = sysAttachement.getProcessAttachmentType();
			SysDictionaryVO sysDictionaryVO =
					this.sysDictionaryService.querySysDictionaryByTypeAndKey(Constant.DIC_ATTACHMENT_TYPE, processAttachmentType);
			String dictionaryValue = sysDictionaryVO.getDictionaryValue();
			//封装返回信息
			Map<String, Object> file = (Map<String, Object>)fileInfo.getData();
			file.put("updateTime", sysAttachement.getUpdateTime());
			file.put("attachmentTypeText", dictionaryValue);
			file.put("id", sysAttachement.getId());//sys_attachment_id
			file.put("processId", processId);
			file.put("stepId", stepId);
			file.put("stepNum", stepNum);
			file.put("processAttachmentType", sysAttachement.getProcessAttachmentType());
			file.put("onlineProcessStatus", fesOnlineProcessVO.getOnlineProcessStatus());
			//文件路径加入到map中: note: 为了解决static 类型不能用@value的问题
			Map<String, String> stepNumMatchFilePath = this.initMatchNumAndPath(); 
			String dbAttachmentPath =StringUtils.isEmpty(sysAttachement.getAttachmentPath())?"": sysAttachement.getAttachmentPath();
			String fileFullPath = stepNumMatchFilePath.get(stepNum) + dbAttachmentPath+"/"+sysAttachement.getAttachmentName();
			file.put("fileFullPath", fileFullPath);
			file.put("userName", sessionUser.getUserName());//操作人
		}
		return fileInfo;
	}

	/**
	 * @Description 通过流程id查找stepNum
	 * @param processId
	 */
	@Override
	public String getStepNumByProcessId(Integer processId) {
		FesOnlineProcessVO fesOnlineProcessVO = this.fesOnlineProcessDao.getFesOnlineProcessById(processId);
		FesStepVO fesStepV = this.fesStepService.getFesStepById(fesOnlineProcessVO.getStepId());
		String stepNum = fesStepV.getStepNum();
		return stepNum;
	}
	private FesStepVO getStepByProcessId(Integer processId) {
		FesOnlineProcessVO fesOnlineProcessVO = this.fesOnlineProcessDao.getFesOnlineProcessById(processId);
		return this.fesStepService.getFesStepById(fesOnlineProcessVO.getStepId());
	}

	/**
	 * @Description 上传附件 成功后 更新流程状态
	 *    如果上传成功判断附件是否等于1，等于一要变更当前流程的状态， 大于1说明状态已经做了变更(不包括步骤6和7)
	 * @param processId
	 * @return FesOnlineProcessVO
	 */
	@Override
	public FesOnlineProcessVO updateProccessStatusForUpload(Integer processId, Integer userId) {
		String stepNum = getStepNumByProcessId(processId);
		FesOnlineProcessVO fesOnlineProcessVO = this.fesOnlineProcessDao.getFesOnlineProcessById(processId);
		FesOnlineProcessVO resultFesOnlineProcessVO = new FesOnlineProcessVO();
		if(ifAttachmentsForStepEqualsOne(processId, null)){
			if (stepNum.equals(StepNum.draft_program.toString())) { // step 3 会员营销方案洽谈
				if(fesOnlineProcessVO.getOnlineProcessStatus().equals("02")){
					resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "03", userId);
				}
			} else if (stepNum.equals(StepNum.final_program.toString())) {//step 4 会员方案确认
				if(fesOnlineProcessVO.getOnlineProcessStatus().equals("02")){
					resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "05", userId);
				}
			} 
		}
		if (stepNum.equals(StepNum.entity_cards.toString())) {// step 6 实体卡制作
			//步骤6，07-待上传卡样源文件，08-待上传卡样确认单，09-待申请开卡，20-待提交申请,
			//10-待上传开卡表单，11-待确认开卡，12-待下单，13-待配送，04-已完成
			if(ifAttachmentsForStepEqualsOne(processId, "3")){// 3卡样源文件
				if(Arrays.asList("07").contains(fesOnlineProcessVO.getOnlineProcessStatus())){//07-待上传卡样源文件 
					resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "08", userId);
				}
			}
			if(ifAttachmentsForStepEqualsOne(processId, "4")){// 4-卡样确认单
				if(fesOnlineProcessVO.getOnlineProcessStatus().equals("08")||fesOnlineProcessVO.getOnlineProcessStatus().equals("10")){//08-待上传卡样确认单
					resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "09", userId);//09-待申请开卡
				}
			}
			if(ifAttachmentsForStepEqualsOne(processId, "12")){// 上传开卡表单
				if(fesOnlineProcessVO.getOnlineProcessStatus().equals("10")){//08-待上传卡样确认单
					resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "11", userId);
				}
			}
		} else if (stepNum.equals(StepNum.materials_design.toString())) {// step 7 物料设计
			// 是否表单和 物料设计素材都存在
			if(this.fesMaterialRequestService.checkHasBothRequirementAndAttachment(fesOnlineProcessVO)){
				if(fesOnlineProcessVO.getOnlineProcessStatus().equals("18")){
					resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "19", userId);
				}
			}
			boolean flag = this.checkIfChangeStatusForStep7(processId);
			if(flag){
				if(fesOnlineProcessVO.getOnlineProcessStatus().equals("14")){
					resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "02", userId);
				}
			}
		}else if (stepNum.equals(StepNum.train.toString())) {// step 9 实施培训
			if(ifAttachmentsForStepEqualsOne(processId, null)){
				if(this.fesTrainPlanService.haveFesTrainPlans(processId)){
					resultFesOnlineProcessVO = this.updateStepProcessStatus(fesOnlineProcessVO, "03", userId);
				}
			}
		}
		//如果没有更新状态，则返回原来的状态
		if(resultFesOnlineProcessVO.getOnlineProcessStatus() == null){
			resultFesOnlineProcessVO.setOnlineProcessStatus(fesOnlineProcessVO.getOnlineProcessStatus());
		}
		return resultFesOnlineProcessVO;
	}

	/**
	 * @Description 
	 * @param processId
	 */
	private boolean checkIfChangeStatusForStep7(Integer processId) {
		//逻辑更改为，只要有一个缩略图添加了就更改状态
		FesProcessAttachmentVO fesProcessAttachment = new FesProcessAttachmentVO();
		fesProcessAttachment.setProcessId(processId);
		fesProcessAttachment.setProcessAttachmentTypes(new String[]{"7","8","9","10","11","13"});
		List<FesProcessAttachmentVO> fesProcessAttachments = this.fesProcessAttachmentDao.getFesProcessAttachments(fesProcessAttachment);
		if(fesProcessAttachments.size()>0){
			return true;
		}else{
			return false;
		}
		/*判断是否所有的附加都已经上传了
		List<String> listStatus = Arrays.asList("6","7","8","9","10","11","13");
		FesProcessAttachmentVO fesProcessAttachment = new FesProcessAttachmentVO();
		fesProcessAttachment.setProcessId(processId);
		List<FesProcessAttachmentVO> fesProcessAttachments = this.fesProcessAttachmentDao.getFesProcessAttachments(fesProcessAttachment);
		List<Boolean> listBoolean = new ArrayList<Boolean>();
		for (String status : listStatus) {
			boolean flag = true;
			for (FesProcessAttachmentVO fesProcessAttachmentVO : fesProcessAttachments) {
				if(status.equals(fesProcessAttachmentVO.getProcessAttachmentType())){
					listBoolean.add(true);//存在没有上传的附件
					break;
				}
			}
		}
		if(listBoolean.size() == listStatus.size()){
			return true;
		}else{
			return false;
		}*/
	}

	/**
	 * @Description 对于步骤4,5和6(源文件)，上传成功后要删除原有的附件 （上传附件覆盖源文件）
	 * @param processId
	 * @param prefixPath //文件实际路径前缀
	 * @param stepNum
	 * @param sysAttachement //新上传的附件对象
	 * @return void
	 * @throws 
	 */
	private void deleteOldAttachments(Integer processId, Integer typeId, String prefixPath, String stepNum, SysAttachmentVO sysAttachement, SysUserVO sessionUser) {
			//step 4 或step5， 上传附件覆盖源文件
			//查找附件id不等于刚上传的附件id
			FesProcessAttachmentVO fesProcessAttachment = new FesProcessAttachmentVO();
			fesProcessAttachment.setProcessId(processId);
			String attachmentType = getProcessAttachmentType(typeId, stepNum);
			fesProcessAttachment.setProcessAttachmentType(attachmentType);
			List<FesProcessAttachmentVO> fesProcessAttachments = this.fesProcessAttachmentDao.getFesProcessAttachments(fesProcessAttachment );
			List<Integer> list = new ArrayList<Integer>();
			for (FesProcessAttachmentVO fesProcessAttachmentVO : fesProcessAttachments) {
				if(fesProcessAttachmentVO.getAttachmentId().equals(sysAttachement.getId())){//过滤掉新上传的附件
					continue;
				}
				list.add(fesProcessAttachmentVO.getAttachmentId());
			}
			if(list!=null&&list.size()>0){ //存在老附件
				for (Integer attachmentId : list) {
					/*
					 * 方法中存在状态的变更逻辑
					 */
					this.deleteSysAttachmentById(processId, attachmentId, prefixPath, sessionUser);
				}
			}
	}
	/**
	 * 根据具体的步骤更新流程状态 : 大部分是上传完成的时候更改状态，没有待办事项的创建
	 * @param fesOnlineProcessVO: 查询条件封装对象
	 * @param newStatus: 更改后的状态
	 */
	@Override
	public FesOnlineProcessVO updateStepProcessStatus(FesOnlineProcessVO fesOnlineProcessVO, String newStatus, Integer userId) {
		FesOnlineProcessVO newfesOnlineProcessVO = new FesOnlineProcessVO();
		newfesOnlineProcessVO.setId(fesOnlineProcessVO.getId());
		newfesOnlineProcessVO.setOnlineProcessStatus(newStatus);
		newfesOnlineProcessVO.setUpdateBy(userId);
		newfesOnlineProcessVO.setUpdateTime(new Date());
		return this.updateSpecificFesOnlineProcessStatues(newfesOnlineProcessVO);
	}
	/**
	 * 如果 卡类型和短信规则都添加了， 则更新状态
	 * @return 
	 */
	@Override
	public String updateStep5Status(Integer processId, String newStatus, Integer userId){
		FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
		fesOnlineProcessVO.setId(processId);
		fesOnlineProcessVO.setProcessId(processId);
		List<FesOnlineProcessVO> complexFesOnlineProcesss = this.getComplexFesOnlineProcesss(fesOnlineProcessVO);
		FesOnlineProcessVO fesOnlineProcessVO2 = complexFesOnlineProcesss.get(0);
		List<TradeCardtypeVO> tradeCardtypes = fesOnlineProcessVO2.getTradeCardtypes();
		List<TradeMessageTemplateVO> tradeMessageTemplates = fesOnlineProcessVO2.getTradeMessageTemplates();
		if(tradeCardtypes!=null&& tradeMessageTemplates!=null){
			if(tradeCardtypes.size()>0&&tradeMessageTemplates.size()>0){
				this.updateStepProcessStatus(fesOnlineProcessVO, newStatus, userId);
				return newStatus;
			}
		}
		return "02";//原来的状态
	}
	/**
	 * @Description 判断附件个数对于每一个步骤是否等于1
	 * @param processAttachmentType 对于步骤6和步骤7要判断 附件类型
	 */
	@Override
	public boolean ifAttachmentsForStepEqualsOne(Integer processId, String processAttachmentType) {
		List<FesProcessAttachmentVO> fesProcessAttachments = this.getAttachmentListByProcess(processId, processAttachmentType);
		if(fesProcessAttachments!=null&&fesProcessAttachments.size()==1){
			return true;
		}
		return false;
	}	
	
	/**
	 * 工具方法，通过商户id和步骤代码查询上线流程 
	 */
	@Override
	public FesOnlineProcessVO getFesOnlineProcessByMerchantAndStep(FesOnlineProcessVO fesOnlineProcessVO) {
		return this.fesOnlineProcessDao.getFesOnlineProcessByMerchantAndStep(fesOnlineProcessVO);
	}

	/**
	 * @Description 根据流程id和附件类型返回附件信息列表
	 * @param processId
	 * @param processAttachmentType
	 * @return List<FesProcessAttachmentVO>
	 */
	@Override
	public List<FesProcessAttachmentVO> getAttachmentListByProcess(Integer processId, String processAttachmentType) {
		FesProcessAttachmentVO fesProcessAttachment = new FesProcessAttachmentVO();
		fesProcessAttachment.setProcessId(processId);
		if(processAttachmentType!=null){
			fesProcessAttachment.setProcessAttachmentType(processAttachmentType);
		}
		return fesProcessAttachmentDao.getFesProcessAttachments(fesProcessAttachment);
	}
	
	/**
	 * 保存文件信息到数据库，保存关联表，系统附件表
	 * @return 
	 */
	private SysAttachmentVO saveAttachement(Integer processId, Integer typeId, Integer userId, JsonResult fileInfo) {
		String stepNum = getStepNumByProcessId(processId);
		Map<String, Object> file = (Map<String, Object>)fileInfo.getData();
		//上传附件每次保存一个附件，对应表中插入一条记录
		SysAttachmentVO sysAttachment = new SysAttachmentVO();	
		sysAttachment.setAttachmentName(file.get("attachmentName").toString());
		sysAttachment.setOriginalFileName(file.get("originalFileName").toString());
		sysAttachment.setAttachmentType("3");// 文件类型
		sysAttachment.setAttachmentSuffix(file.get("fileSuffix").toString());
		sysAttachment.setModuleType(Constant._FES);
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
		int row = this.sysAttachmentService.saveSysAttachment(sysAttachment);
		if(row>0){
			//保存附加上线流程关系表
			FesProcessAttachmentVO fesProcessAttachment = new FesProcessAttachmentVO();
			fesProcessAttachment.setProcessId(processId);
			fesProcessAttachment.setAttachmentId(sysAttachment.getId());
			fesProcessAttachment.setInsertBy(userId);
			fesProcessAttachment.setInsertTime(new Date());
			//开卡数据只能上传一个, 先删除,在添加
			if(typeId!=null && Arrays.asList("12").contains(typeId.toString())){
				FesProcessAttachmentVO processAttaVO = new FesProcessAttachmentVO();
				processAttaVO.setProcessId(processId);
				processAttaVO.setProcessAttachmentType("12");
				List<FesProcessAttachmentVO> fesProcessAttachments = fesProcessAttachmentService.getFesProcessAttachments(processAttaVO);
				if(CollectionUtils.isNotEmpty(fesProcessAttachments)){
					for (FesProcessAttachmentVO fesProcessAttachmentVO : fesProcessAttachments) {
						this.fesProcessAttachmentService.deleteFesProcessAttachmentById(fesProcessAttachmentVO.getId());
						this.sysAttachmentDao.deleteSysAttachmentById(fesProcessAttachmentVO.getAttachmentId());
					}
				}
			}
//		    "1-会员营销方案
//			2-会员营销方案（终版）
//			3-卡样源文件
//			4-卡样确认单
//			5-培训材料
//			6-物料设计源文件
//			7-物料设计缩略图
			String attachmentType = getProcessAttachmentType(typeId, stepNum);
			fesProcessAttachment.setProcessAttachmentType(attachmentType);
			this.fesProcessAttachmentService.saveFesProcessAttachment(fesProcessAttachment);
			sysAttachment.setProcessAttachmentType(fesProcessAttachment.getProcessAttachmentType());
			return sysAttachment;
		}else{
			throw new FesBizException("保存附件发生错误！");
		}
	}

	/**
	 * @Description 根据页面参数类型得到数据库应保存的附件类型
	 */
	private String getProcessAttachmentType(Integer typeId, String stepNum) {
		String attachmentType = null;
		if(stepNum.equals(StepNum.draft_program.toString())){
			attachmentType = "1";
		}else if(stepNum.equals(StepNum.final_program.toString())){
			attachmentType = "2";
		}else if(stepNum.equals(StepNum.entity_cards.toString())){
			attachmentType = typeId+"";  
		}else if(stepNum.equals(StepNum.train.toString())){
			attachmentType = typeId+"";  
		}else if(stepNum.equals(StepNum.materials_design.toString())){
			attachmentType = typeId+"";  
		}
		return attachmentType;
	}
}
