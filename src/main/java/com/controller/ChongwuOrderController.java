
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
 * 宠物领养
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/chongwuOrder")
public class ChongwuOrderController {
    private static final Logger logger = LoggerFactory.getLogger(ChongwuOrderController.class);

    private static final String TABLE_NAME = "chongwuOrder";

    @Autowired
    private ChongwuOrderService chongwuOrderService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private BaikeService baikeService;//宠物百科
    @Autowired
    private ChongwuService chongwuService;//宠物
    @Autowired
    private ChongwuCollectionService chongwuCollectionService;//宠物收藏
    @Autowired
    private ChongwuLiuyanService chongwuLiuyanService;//宠物留言
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
        CommonUtil.checkMap(params);
        PageUtils page = chongwuOrderService.queryPage(params);

        //字典表数据转换
        List<ChongwuOrderView> list =(List<ChongwuOrderView>)page.getList();
        for(ChongwuOrderView c:list){
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
        ChongwuOrderEntity chongwuOrder = chongwuOrderService.selectById(id);
        if(chongwuOrder !=null){
            //entity转view
            ChongwuOrderView view = new ChongwuOrderView();
            BeanUtils.copyProperties( chongwuOrder , view );//把实体数据重构到view中
            //级联表 宠物
            //级联表
            ChongwuEntity chongwu = chongwuService.selectById(chongwuOrder.getChongwuId());
            if(chongwu != null){
            BeanUtils.copyProperties( chongwu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setChongwuId(chongwu.getId());
            }
            //级联表 用户
            //级联表
            YonghuEntity yonghu = yonghuService.selectById(chongwuOrder.getYonghuId());
            if(yonghu != null){
            BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setYonghuId(yonghu.getId());
            }
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
    public R save(@RequestBody ChongwuOrderEntity chongwuOrder, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,chongwuOrder:{}",this.getClass().getName(),chongwuOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("用户".equals(role))
            chongwuOrder.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        chongwuOrder.setCreateTime(new Date());
        chongwuOrder.setInsertTime(new Date());
        chongwuOrderService.insert(chongwuOrder);

        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ChongwuOrderEntity chongwuOrder, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,chongwuOrder:{}",this.getClass().getName(),chongwuOrder.toString());
        ChongwuOrderEntity oldChongwuOrderEntity = chongwuOrderService.selectById(chongwuOrder.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            chongwuOrder.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

            chongwuOrderService.updateById(chongwuOrder);//根据id更新
            return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<ChongwuOrderEntity> oldChongwuOrderList =chongwuOrderService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        chongwuOrderService.deleteBatchIds(Arrays.asList(ids));

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
            List<ChongwuOrderEntity> chongwuOrderList = new ArrayList<>();//上传的东西
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
                            ChongwuOrderEntity chongwuOrderEntity = new ChongwuOrderEntity();
//                            chongwuOrderEntity.setChongwuOrderUuidNumber(data.get(0));                    //订单编号 要改的
//                            chongwuOrderEntity.setChongwuId(Integer.valueOf(data.get(0)));   //宠物 要改的
//                            chongwuOrderEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            chongwuOrderEntity.setChongwuOrderTypes(Integer.valueOf(data.get(0)));   //领养类型 要改的
//                            chongwuOrderEntity.setInsertTime(date);//时间
//                            chongwuOrderEntity.setCreateTime(date);//时间
                            chongwuOrderList.add(chongwuOrderEntity);


                            //把要查询是否重复的字段放入map中
                                //订单编号
                                if(seachFields.containsKey("chongwuOrderUuidNumber")){
                                    List<String> chongwuOrderUuidNumber = seachFields.get("chongwuOrderUuidNumber");
                                    chongwuOrderUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> chongwuOrderUuidNumber = new ArrayList<>();
                                    chongwuOrderUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("chongwuOrderUuidNumber",chongwuOrderUuidNumber);
                                }
                        }

                        //查询是否重复
                         //订单编号
                        List<ChongwuOrderEntity> chongwuOrderEntities_chongwuOrderUuidNumber = chongwuOrderService.selectList(new EntityWrapper<ChongwuOrderEntity>().in("chongwu_order_uuid_number", seachFields.get("chongwuOrderUuidNumber")));
                        if(chongwuOrderEntities_chongwuOrderUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(ChongwuOrderEntity s:chongwuOrderEntities_chongwuOrderUuidNumber){
                                repeatFields.add(s.getChongwuOrderUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [订单编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        chongwuOrderService.insertBatch(chongwuOrderList);
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
        PageUtils page = chongwuOrderService.queryPage(params);

        //字典表数据转换
        List<ChongwuOrderView> list =(List<ChongwuOrderView>)page.getList();
        for(ChongwuOrderView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ChongwuOrderEntity chongwuOrder = chongwuOrderService.selectById(id);
            if(chongwuOrder !=null){


                //entity转view
                ChongwuOrderView view = new ChongwuOrderView();
                BeanUtils.copyProperties( chongwuOrder , view );//把实体数据重构到view中

                //级联表
                    ChongwuEntity chongwu = chongwuService.selectById(chongwuOrder.getChongwuId());
                if(chongwu != null){
                    BeanUtils.copyProperties( chongwu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setChongwuId(chongwu.getId());
                }
                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(chongwuOrder.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
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
    public R add(@RequestBody ChongwuOrderEntity chongwuOrder, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,chongwuOrder:{}",this.getClass().getName(),chongwuOrder.toString());
            ChongwuEntity chongwuEntity = chongwuService.selectById(chongwuOrder.getChongwuId());
            if(chongwuEntity == null){
                return R.error(511,"查不到该宠物");
            }
            // Double chongwuNewMoney = chongwuEntity.getChongwuNewMoney();

            if(false){
            }

            //计算所获得积分
            Double buyJifen =0.0;
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            YonghuEntity yonghuEntity = yonghuService.selectById(userId);
            if(yonghuEntity == null)
                return R.error(511,"用户不能为空");;
            chongwuOrder.setChongwuOrderTypes(101); //设置订单状态为已申请领养
            chongwuOrder.setYonghuId(userId); //设置订单支付人id
            chongwuOrder.setChongwuOrderUuidNumber(String.valueOf(new Date().getTime()));
            chongwuOrder.setInsertTime(new Date());
            chongwuOrder.setCreateTime(new Date());
                chongwuOrderService.insert(chongwuOrder);//新增订单


            return R.ok();
    }


    /**
    * 取消申请
    */
    @RequestMapping("/refund")
    public R refund(Integer id, HttpServletRequest request){
        logger.debug("refund方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        String role = String.valueOf(request.getSession().getAttribute("role"));

            ChongwuOrderEntity chongwuOrder = chongwuOrderService.selectById(id);//当前表service
            Integer chongwuId = chongwuOrder.getChongwuId();
            if(chongwuId == null)
                return R.error(511,"查不到该宠物");
            ChongwuEntity chongwuEntity = chongwuService.selectById(chongwuId);
            if(chongwuEntity == null)
                return R.error(511,"查不到该宠物");

            Integer userId = (Integer) request.getSession().getAttribute("userId");
            YonghuEntity yonghuEntity = yonghuService.selectById(userId);
            if(yonghuEntity == null)
                return R.error(511,"用户不能为空");
            Double zhekou = 1.0;

                //计算金额

                //计算所获得积分
                Double buyJifen = 0.0;





            chongwuOrder.setChongwuOrderTypes(102);//设置订单状态为已取消申请
            chongwuOrderService.updateById(chongwuOrder);//根据id更新
            yonghuService.updateById(yonghuEntity);//更新用户信息
            chongwuService.updateById(chongwuEntity);//更新订单中宠物的信息

            return R.ok();
    }

    /**
     * 同意领养
     */
    @RequestMapping("/deliver")
    public R deliver(Integer id  , HttpServletRequest request){
        logger.debug("refund:,,Controller:{},,ids:{}",this.getClass().getName(),id.toString());
        ChongwuOrderEntity  chongwuOrderEntity = chongwuOrderService.selectById(id);
        chongwuOrderEntity.setChongwuOrderTypes(103);//设置订单状态为已同意领养
        chongwuOrderService.updateById( chongwuOrderEntity);

        return R.ok();
    }


    /**
     * 领养
     */
    @RequestMapping("/receiving")
    public R receiving(Integer id , HttpServletRequest request){
        logger.debug("refund:,,Controller:{},,ids:{}",this.getClass().getName(),id.toString());
        ChongwuOrderEntity  chongwuOrderEntity = chongwuOrderService.selectById(id);
        chongwuOrderEntity.setChongwuOrderTypes(104);//设置订单状态为领养
        chongwuOrderService.updateById( chongwuOrderEntity);
        return R.ok();
    }

}

