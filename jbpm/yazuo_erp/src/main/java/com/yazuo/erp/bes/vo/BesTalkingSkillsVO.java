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

package com.yazuo.erp.bes.vo;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class BesTalkingSkillsVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "话术表";
	public static final String ALIAS_ID = "话术ID";
	public static final String ALIAS_RESOURCE_REMARK = "资源ID";
	public static final String ALIAS_RESOURCE_EXTRA_REMARK = "其他资源ID";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_CONTENT = "内容";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_RESOURCE_REMARK = "resourceRemark";
	public static final String COLUMN_RESOURCE_EXTRA_REMARK = "resourceExtraRemark";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"话术ID";
	private java.lang.String resourceRemark; //"资源ID";
	private java.lang.String resourceExtraRemark; //"其他资源ID";
	private java.lang.String title; //"标题";
	private java.lang.String content; //"内容";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END
	//self-defined attrubltes START
	private List<Object> dropDownList;
	private java.lang.Integer pageNumber;
	private java.lang.Integer pageSize;
	private String firstResource;
	private String secondResource;
	//self-defined attrubltes END
	
	public java.lang.Integer getPageNumber() {
		return pageNumber;
	}


	public String getFirstResource() {
		return firstResource;
	}


	public void setFirstResource(String firstResource) {
		this.firstResource = firstResource;
	}


	public String getSecondResource() {
		return secondResource;
	}


	public void setSecondResource(String secondResource) {
		this.secondResource = secondResource;
	}


	public void setPageNumber(java.lang.Integer pageNumber) {
		this.pageNumber = pageNumber;
	}


	public java.lang.Integer getPageSize() {
		return pageSize;
	}


	public void setPageSize(java.lang.Integer pageSize) {
		this.pageSize = pageSize;
	}


	public BesTalkingSkillsVO(){
	}


	public List<Object> getDropDownList() {
		return dropDownList;
	}


	public void setDropDownList(List<Object> dropDownList) {
		this.dropDownList = dropDownList;
	}


	public BesTalkingSkillsVO(
		java.lang.Integer id
	){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setResourceRemark(java.lang.String value) {
		this.resourceRemark = value;
	}
	
	public java.lang.String getResourceRemark() {
		return this.resourceRemark;
	}
	public void setResourceExtraRemark(java.lang.String value) {
		this.resourceExtraRemark = value;
	}
	
	public java.lang.String getResourceExtraRemark() {
		return this.resourceExtraRemark;
	}
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setContent(java.lang.String value) {
		this.content = value;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setIsEnable(java.lang.String value) {
		this.isEnable = value;
	}
	
	public java.lang.String getIsEnable() {
		return this.isEnable;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setInsertBy(java.lang.Integer value) {
		this.insertBy = value;
	}
	
	public java.lang.Integer getInsertBy() {
		return this.insertBy;
	}
	public void setInsertTime(java.util.Date value) {
		this.insertTime = value;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateBy(java.lang.Integer value) {
		this.updateBy = value;
	}
	
	public java.lang.Integer getUpdateBy() {
		return this.updateBy;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ResourceRemark",getResourceRemark())
			.append("ResourceExtraRemark",getResourceExtraRemark())
			.append("Title",getTitle())
			.append("Content",getContent())
			.append("IsEnable",getIsEnable())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BesTalkingSkillsVO == false) return false;
		if(this == obj) return true;
		BesTalkingSkillsVO other = (BesTalkingSkillsVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

