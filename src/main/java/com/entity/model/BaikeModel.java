package com.entity.model;

import com.entity.BaikeEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 宠物百科
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class BaikeModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 宠物百科名称
     */
    private String baikeName;


    /**
     * 宠物百科编号
     */
    private String baikeUuidNumber;


    /**
     * 宠物百科照片
     */
    private String baikePhoto;


    /**
     * 宠物百科地点
     */
    private String baikeAddress;


    /**
     * 宠物百科类型
     */
    private Integer baikeTypes;


    /**
     * 宠物百科视频
     */
    private String baikeVideo;


    /**
     * 宠物百科介绍
     */
    private String baikeContent;


    /**
     * 逻辑删除
     */
    private Integer baikeDelete;


    /**
     * 录入时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 创建时间  show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
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
	 * 获取：创建时间  show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间  show1 show2 photoShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
