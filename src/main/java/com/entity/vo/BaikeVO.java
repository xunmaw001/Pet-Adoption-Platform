package com.entity.vo;

import com.entity.BaikeEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 宠物百科
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("baike")
public class BaikeVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 宠物百科名称
     */

    @TableField(value = "baike_name")
    private String baikeName;


    /**
     * 宠物百科编号
     */

    @TableField(value = "baike_uuid_number")
    private String baikeUuidNumber;


    /**
     * 宠物百科照片
     */

    @TableField(value = "baike_photo")
    private String baikePhoto;


    /**
     * 宠物百科地点
     */

    @TableField(value = "baike_address")
    private String baikeAddress;


    /**
     * 宠物百科类型
     */

    @TableField(value = "baike_types")
    private Integer baikeTypes;


    /**
     * 宠物百科视频
     */

    @TableField(value = "baike_video")
    private String baikeVideo;


    /**
     * 宠物百科介绍
     */

    @TableField(value = "baike_content")
    private String baikeContent;


    /**
     * 逻辑删除
     */

    @TableField(value = "baike_delete")
    private Integer baikeDelete;


    /**
     * 录入时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


    /**
     * 创建时间  show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：宠物百科名称
	 */
    public String getBaikeName() {
        return baikeName;
    }


    /**
	 * 获取：宠物百科名称
	 */

    public void setBaikeName(String baikeName) {
        this.baikeName = baikeName;
    }
    /**
	 * 设置：宠物百科编号
	 */
    public String getBaikeUuidNumber() {
        return baikeUuidNumber;
    }


    /**
	 * 获取：宠物百科编号
	 */

    public void setBaikeUuidNumber(String baikeUuidNumber) {
        this.baikeUuidNumber = baikeUuidNumber;
    }
    /**
	 * 设置：宠物百科照片
	 */
    public String getBaikePhoto() {
        return baikePhoto;
    }


    /**
	 * 获取：宠物百科照片
	 */

    public void setBaikePhoto(String baikePhoto) {
        this.baikePhoto = baikePhoto;
    }
    /**
	 * 设置：宠物百科地点
	 */
    public String getBaikeAddress() {
        return baikeAddress;
    }


    /**
	 * 获取：宠物百科地点
	 */

    public void setBaikeAddress(String baikeAddress) {
        this.baikeAddress = baikeAddress;
    }
    /**
	 * 设置：宠物百科类型
	 */
    public Integer getBaikeTypes() {
        return baikeTypes;
    }


    /**
	 * 获取：宠物百科类型
	 */

    public void setBaikeTypes(Integer baikeTypes) {
        this.baikeTypes = baikeTypes;
    }
    /**
	 * 设置：宠物百科视频
	 */
    public String getBaikeVideo() {
        return baikeVideo;
    }


    /**
	 * 获取：宠物百科视频
	 */

    public void setBaikeVideo(String baikeVideo) {
        this.baikeVideo = baikeVideo;
    }
    /**
	 * 设置：宠物百科介绍
	 */
    public String getBaikeContent() {
        return baikeContent;
    }


    /**
	 * 获取：宠物百科介绍
	 */

    public void setBaikeContent(String baikeContent) {
        this.baikeContent = baikeContent;
    }
    /**
	 * 设置：逻辑删除
	 */
    public Integer getBaikeDelete() {
        return baikeDelete;
    }


    /**
	 * 获取：逻辑删除
	 */

    public void setBaikeDelete(Integer baikeDelete) {
        this.baikeDelete = baikeDelete;
    }
    /**
	 * 设置：录入时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：录入时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：创建时间  show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间  show1 show2 photoShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
