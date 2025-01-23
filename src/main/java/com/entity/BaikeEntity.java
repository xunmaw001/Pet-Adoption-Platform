package com.entity;

import com.annotation.ColumnInfo;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;
import java.util.*;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.utils.DateUtil;


/**
 * 宠物百科
 *
 * @author 
 * @email
 */
@TableName("baike")
public class BaikeEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public BaikeEntity() {

	}

	public BaikeEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ColumnInfo(comment="主键",type="int(11)")
    @TableField(value = "id")

    private Integer id;


    /**
     * 宠物百科名称
     */
    @ColumnInfo(comment="宠物百科名称",type="varchar(200)")
    @TableField(value = "baike_name")

    private String baikeName;


    /**
     * 宠物百科编号
     */
    @ColumnInfo(comment="宠物百科编号",type="varchar(200)")
    @TableField(value = "baike_uuid_number")

    private String baikeUuidNumber;


    /**
     * 宠物百科照片
     */
    @ColumnInfo(comment="宠物百科照片",type="varchar(200)")
    @TableField(value = "baike_photo")

    private String baikePhoto;


    /**
     * 宠物百科地点
     */
    @ColumnInfo(comment="宠物百科地点",type="varchar(200)")
    @TableField(value = "baike_address")

    private String baikeAddress;


    /**
     * 宠物百科类型
     */
    @ColumnInfo(comment="宠物百科类型",type="int(11)")
    @TableField(value = "baike_types")

    private Integer baikeTypes;


    /**
     * 宠物百科视频
     */
    @ColumnInfo(comment="宠物百科视频",type="varchar(200)")
    @TableField(value = "baike_video")

    private String baikeVideo;


    /**
     * 宠物百科介绍
     */
    @ColumnInfo(comment="宠物百科介绍",type="text")
    @TableField(value = "baike_content")

    private String baikeContent;


    /**
     * 逻辑删除
     */
    @ColumnInfo(comment="逻辑删除",type="int(11)")
    @TableField(value = "baike_delete")

    private Integer baikeDelete;


    /**
     * 录入时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="录入时间",type="timestamp")
    @TableField(value = "insert_time",fill = FieldFill.INSERT)

    private Date insertTime;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="创建时间",type="timestamp")
    @TableField(value = "create_time",fill = FieldFill.INSERT)

    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }
    /**
	 * 设置：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：宠物百科名称
	 */
    public String getBaikeName() {
        return baikeName;
    }
    /**
	 * 设置：宠物百科名称
	 */

    public void setBaikeName(String baikeName) {
        this.baikeName = baikeName;
    }
    /**
	 * 获取：宠物百科编号
	 */
    public String getBaikeUuidNumber() {
        return baikeUuidNumber;
    }
    /**
	 * 设置：宠物百科编号
	 */

    public void setBaikeUuidNumber(String baikeUuidNumber) {
        this.baikeUuidNumber = baikeUuidNumber;
    }
    /**
	 * 获取：宠物百科照片
	 */
    public String getBaikePhoto() {
        return baikePhoto;
    }
    /**
	 * 设置：宠物百科照片
	 */

    public void setBaikePhoto(String baikePhoto) {
        this.baikePhoto = baikePhoto;
    }
    /**
	 * 获取：宠物百科地点
	 */
    public String getBaikeAddress() {
        return baikeAddress;
    }
    /**
	 * 设置：宠物百科地点
	 */

    public void setBaikeAddress(String baikeAddress) {
        this.baikeAddress = baikeAddress;
    }
    /**
	 * 获取：宠物百科类型
	 */
    public Integer getBaikeTypes() {
        return baikeTypes;
    }
    /**
	 * 设置：宠物百科类型
	 */

    public void setBaikeTypes(Integer baikeTypes) {
        this.baikeTypes = baikeTypes;
    }
    /**
	 * 获取：宠物百科视频
	 */
    public String getBaikeVideo() {
        return baikeVideo;
    }
    /**
	 * 设置：宠物百科视频
	 */

    public void setBaikeVideo(String baikeVideo) {
        this.baikeVideo = baikeVideo;
    }
    /**
	 * 获取：宠物百科介绍
	 */
    public String getBaikeContent() {
        return baikeContent;
    }
    /**
	 * 设置：宠物百科介绍
	 */

    public void setBaikeContent(String baikeContent) {
        this.baikeContent = baikeContent;
    }
    /**
	 * 获取：逻辑删除
	 */
    public Integer getBaikeDelete() {
        return baikeDelete;
    }
    /**
	 * 设置：逻辑删除
	 */

    public void setBaikeDelete(Integer baikeDelete) {
        this.baikeDelete = baikeDelete;
    }
    /**
	 * 获取：录入时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }
    /**
	 * 设置：录入时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }
    /**
	 * 设置：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Baike{" +
            ", id=" + id +
            ", baikeName=" + baikeName +
            ", baikeUuidNumber=" + baikeUuidNumber +
            ", baikePhoto=" + baikePhoto +
            ", baikeAddress=" + baikeAddress +
            ", baikeTypes=" + baikeTypes +
            ", baikeVideo=" + baikeVideo +
            ", baikeContent=" + baikeContent +
            ", baikeDelete=" + baikeDelete +
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
