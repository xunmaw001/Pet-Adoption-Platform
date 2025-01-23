package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.BaikeEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 宠物百科
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("baike")
public class BaikeView extends BaikeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	* 宠物百科类型的值
	*/
	@ColumnInfo(comment="宠物百科类型的字典表值",type="varchar(200)")
	private String baikeValue;




	public BaikeView() {

	}

	public BaikeView(BaikeEntity baikeEntity) {
		try {
			BeanUtils.copyProperties(this, baikeEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//当前表的
	/**
	* 获取： 宠物百科类型的值
	*/
	public String getBaikeValue() {
		return baikeValue;
	}
	/**
	* 设置： 宠物百科类型的值
	*/
	public void setBaikeValue(String baikeValue) {
		this.baikeValue = baikeValue;
	}




	@Override
	public String toString() {
		return "BaikeView{" +
			", baikeValue=" + baikeValue +
			"} " + super.toString();
	}
}
