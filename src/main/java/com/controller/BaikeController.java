
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 宠物百科
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/baike")
public class BaikeController {
    private static final Logger logger = LoggerFactory.getLogger(BaikeController.class);

    private static final String TABLE_NAME = "baike";

    @Autowired
    private BaikeService baikeService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private ChongwuService chongwuService;//宠物
    @Autowired
    private ChongwuCollectionService chongwuCollectionService;//宠物收藏
    @Autowired
    private ChongwuLiuyanService chongwuLiuyanService;//宠物留言
    @Autowired
    private ChongwuOrderService chongwuOrderService;//宠物领养
    @Autowired
    private DictionaryService dictionaryService;//字典
    @Autowired
    private ForumService forumService;//论坛
    @Autowired
    private NewsService newsService;//新闻信息
    @Autowired
    private YonghuService yonghuService;//用户
    @Autowired
    private UsersService usersService;//管理员


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        params.put("baikeDeleteStart",1);params.put("baikeDeleteEnd",1);
        CommonUtil.checkMap(params);
        PageUtils page = baikeService.queryPage(params);

        //字典表数据转换
        List<BaikeView> list =(List<BaikeView>)page.getList();
        for(BaikeView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        BaikeEntity baike = baikeService.selectById(id);
        if(baike !=null){
            //entity转view
            BaikeView view = new BaikeView();
            BeanUtils.copyProperties( baike , view );//把实体数据重构到view中
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody BaikeEntity baike, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,baike:{}",this.getClass().getName(),baike.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<BaikeEntity> queryWrapper = new EntityWrapper<BaikeEntity>()
            .eq("baike_name", baike.getBaikeName())
            .eq("baike_address", baike.getBaikeAddress())
            .eq("baike_types", baike.getBaikeTypes())
            .eq("baike_video", baike.getBaikeVideo())
            .eq("baike_delete", baike.getBaikeDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        BaikeEntity baikeEntity = baikeService.selectOne(queryWrapper);
        if(baikeEntity==null){
            baike.setBaikeDelete(1);
            baike.setInsertTime(new Date());
            baike.setCreateTime(new Date());
            baikeService.insert(baike);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody BaikeEntity baike, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,baike:{}",this.getClass().getName(),baike.toString());
        BaikeEntity oldBaikeEntity = baikeService.selectById(baike.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        if("".equals(baike.getBaikePhoto()) || "null".equals(baike.getBaikePhoto())){
                baike.setBaikePhoto(null);
        }
        if("".equals(baike.getBaikeVideo()) || "null".equals(baike.getBaikeVideo())){
                baike.setBaikeVideo(null);
        }

            baikeService.updateById(baike);//根据id更新
            return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<BaikeEntity> oldBaikeList =baikeService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        ArrayList<BaikeEntity> list = new ArrayList<>();
        for(Integer id:ids){
            BaikeEntity baikeEntity = new BaikeEntity();
            baikeEntity.setId(id);
            baikeEntity.setBaikeDelete(2);
            list.add(baikeEntity);
        }
        if(list != null && list.size() >0){
            baikeService.updateBatchById(list);
        }

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<BaikeEntity> baikeList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("../../upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            BaikeEntity baikeEntity = new BaikeEntity();
//                            baikeEntity.setBaikeName(data.get(0));                    //宠物百科名称 要改的
//                            baikeEntity.setBaikeUuidNumber(data.get(0));                    //宠物百科编号 要改的
//                            baikeEntity.setBaikePhoto("");//详情和图片
//                            baikeEntity.setBaikeAddress(data.get(0));                    //宠物百科地点 要改的
//                            baikeEntity.setBaikeTypes(Integer.valueOf(data.get(0)));   //宠物百科类型 要改的
//                            baikeEntity.setBaikeVideo(data.get(0));                    //宠物百科视频 要改的
//                            baikeEntity.setBaikeContent("");//详情和图片
//                            baikeEntity.setBaikeDelete(1);//逻辑删除字段
//                            baikeEntity.setInsertTime(date);//时间
//                            baikeEntity.setCreateTime(date);//时间
                            baikeList.add(baikeEntity);


                            //把要查询是否重复的字段放入map中
                                //宠物百科编号
                                if(seachFields.containsKey("baikeUuidNumber")){
                                    List<String> baikeUuidNumber = seachFields.get("baikeUuidNumber");
                                    baikeUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> baikeUuidNumber = new ArrayList<>();
                                    baikeUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("baikeUuidNumber",baikeUuidNumber);
                                }
                        }

                        //查询是否重复
                         //宠物百科编号
                        List<BaikeEntity> baikeEntities_baikeUuidNumber = baikeService.selectList(new EntityWrapper<BaikeEntity>().in("baike_uuid_number", seachFields.get("baikeUuidNumber")).eq("baike_delete", 1));
                        if(baikeEntities_baikeUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(BaikeEntity s:baikeEntities_baikeUuidNumber){
                                repeatFields.add(s.getBaikeUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [宠物百科编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        baikeService.insertBatch(baikeList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }




    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = baikeService.queryPage(params);

        //字典表数据转换
        List<BaikeView> list =(List<BaikeView>)page.getList();
        for(BaikeView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        BaikeEntity baike = baikeService.selectById(id);
            if(baike !=null){


                //entity转view
                BaikeView view = new BaikeView();
                BeanUtils.copyProperties( baike , view );//把实体数据重构到view中

                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody BaikeEntity baike, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,baike:{}",this.getClass().getName(),baike.toString());
        Wrapper<BaikeEntity> queryWrapper = new EntityWrapper<BaikeEntity>()
            .eq("baike_name", baike.getBaikeName())
            .eq("baike_uuid_number", baike.getBaikeUuidNumber())
            .eq("baike_address", baike.getBaikeAddress())
            .eq("baike_types", baike.getBaikeTypes())
            .eq("baike_video", baike.getBaikeVideo())
            .eq("baike_delete", baike.getBaikeDelete())
//            .notIn("baike_types", new Integer[]{102})
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        BaikeEntity baikeEntity = baikeService.selectOne(queryWrapper);
        if(baikeEntity==null){
            baike.setBaikeDelete(1);
            baike.setInsertTime(new Date());
            baike.setCreateTime(new Date());
        baikeService.insert(baike);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}

