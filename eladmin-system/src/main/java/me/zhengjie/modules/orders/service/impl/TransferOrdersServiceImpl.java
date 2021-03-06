package me.zhengjie.modules.orders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.constants.CommonConstant;
import me.zhengjie.enums.OrderStatusEnum;
import me.zhengjie.enums.OrderTypeEnum;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.employee.service.dto.TbEmployeeDto;
import me.zhengjie.modules.employee.service.mapper.TbEmployeeMapper;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoDto;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.mapper.TbWhInventoryMapper;
import me.zhengjie.modules.orders.domain.TbOrders;
import me.zhengjie.modules.orders.domain.vo.GoodsInfoVo;
import me.zhengjie.modules.orders.domain.vo.OrderVo;
import me.zhengjie.modules.orders.domain.vo.ReportGoodsListVo;
import me.zhengjie.modules.orders.service.TransferOrdersService;
import me.zhengjie.modules.orders.service.dto.OrderGoodsInfoDto;
import me.zhengjie.modules.orders.service.dto.TbOrdersDto;
import me.zhengjie.modules.orders.service.dto.TbOrdersQueryParam;
import me.zhengjie.modules.orders.service.mapper.TbOrdersMapper;
import me.zhengjie.modules.tb_order_goods.domain.TbOrderGoods;
import me.zhengjie.modules.tb_order_goods.service.mapper.TbOrderGoodsMapper;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utlis.JasperReportUtil;
import me.zhengjie.utlis.OrderUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
* @author LiangBin
* @date 2022-01-03
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbOrdersService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TransferOrdersServiceImpl extends CommonServiceImpl<TbOrdersMapper, TbOrders> implements TransferOrdersService {

    //private final RedisUtils redisUtils;
    private final TbOrdersMapper tbOrdersMapper;
    private final TbOrderGoodsMapper tbOrderGoodsMapper;
    private final TbWhInventoryMapper tbWhInventoryMapper;
    private final TbEmployeeMapper tbEmployeeMapper;
    private final OrderUtil orderUtil;

    @Override
    public PageInfo<TbOrdersDto> queryAll(TbOrdersQueryParam query, Pageable pageable) {
        IPage<TbOrders> queryPage = PageUtil.toMybatisPage(pageable);
        QueryWrapper<TbOrders> predicate = QueryHelpMybatisPlus.getPredicate(query);
        predicate.eq("order_type", OrderTypeEnum.TRANSFER_OUT.getCode());
        IPage<TbOrders> page = tbOrdersMapper.selectPage(queryPage, predicate);
        return ConvertUtil.convertPage(page, TbOrdersDto.class);
    }

    @Override
    public List<TbOrdersDto> queryAll(TbOrdersQueryParam query){
        return ConvertUtil.convertList(tbOrdersMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbOrdersDto.class);
    }

    @Override
    public TbOrders getById(Long id) {
        return tbOrdersMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbOrdersDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbOrdersDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(OrderVo vo) {
        TbOrders entity = ConvertUtil.convert(vo, TbOrders.class);
        // ??????
        entity.setOrderNo(orderUtil.getIncrOrderNo(CommonConstant.DC));
        // ?????????????????????
        entity.setOrderType(OrderTypeEnum.TRANSFER_OUT.getCode());
        // ????????????
        entity.setStatus(OrderStatusEnum.NOT_APPROVE.getCode());
        // ??????????????????(1.??? 2.???)
        entity.setInOutType(2);
        // ??????????????????
        int insert = tbOrdersMapper.insert(entity);

        // ????????????????????????
        TbOrders transferIn = ConvertUtil.convert(vo, TbOrders.class);
        transferIn.setOrderNo(orderUtil.getIncrOrderNo(CommonConstant.DR));
        transferIn.setOrderType(OrderTypeEnum.TRANSFER_IN.getCode());
        transferIn.setStatus(OrderStatusEnum.NOT_APPROVE.getCode());
        transferIn.setPid(entity.getId());
        transferIn.setInOutType(1);
        tbOrdersMapper.insert(transferIn);

        List<TbOrderGoods> outOrderGoodList = new ArrayList<>();
        List<TbOrderGoods> inOrderGoodList = new ArrayList<>();

        List<GoodsInfoVo> goodList = vo.getGoodList();
        for (GoodsInfoVo goodsInfoVo : goodList) {
            TbOrderGoods tbOrderGoods = new TbOrderGoods();
            tbOrderGoods.setOrderId(entity.getId());
            tbOrderGoods.setGoodId(goodsInfoVo.getId());
            tbOrderGoods.setGoodNum(goodsInfoVo.getGoodNum().toString());
            tbOrderGoods.setPrice(goodsInfoVo.getPurchasePrice());
            tbOrderGoods.setTotalPrice(goodsInfoVo.getTotalPrice());
            outOrderGoodList.add(tbOrderGoods);

            TbOrderGoods inOrderGoods = new TbOrderGoods();
            BeanUtils.copyProperties(tbOrderGoods, inOrderGoods);
            inOrderGoods.setOrderId(transferIn.getId());
            inOrderGoodList.add(inOrderGoods);
        }
        // ????????????????????????
        tbOrderGoodsMapper.insertBatchSomeColumn(outOrderGoodList);
        tbOrderGoodsMapper.insertBatchSomeColumn(inOrderGoodList);

        return insert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(OrderVo vo){

        // ??????????????????
        TbOrders order = tbOrdersMapper.lambdaQuery()
                .eq(TbOrders::getId, vo.getId()).one();
        if (order == null) {
            throw new BadRequestException("??????????????????!");
        } else if (order.getStatus().equals(OrderStatusEnum.APPROVE.getCode())) {
            throw new BadRequestException("????????????:" + order.getOrderNo() + "????????????????????????!");
        }

        TbOrders entity = ConvertUtil.convert(vo, TbOrders.class);
        int ret = tbOrdersMapper.updateById(entity);
        // delCaches(resources.id);

        // ??????????????????????????????
        LambdaQueryWrapper<TbOrderGoods> wrapper = new LambdaQueryWrapper();
        wrapper.eq(TbOrderGoods::getOrderId, vo.getId());
        tbOrderGoodsMapper.delete(wrapper);

        TbOrders transferIn = tbOrdersMapper.lambdaQuery().eq(TbOrders::getPid, vo.getId()).one();
        LambdaQueryWrapper<TbOrderGoods> wrapper1 = new LambdaQueryWrapper();
        wrapper1.eq(TbOrderGoods::getOrderId, transferIn.getId());
        tbOrderGoodsMapper.delete(wrapper1);

        List<TbOrderGoods> newOrderGoodList = new ArrayList<>();
        List<TbOrderGoods> inOrderGoodList = new ArrayList<>();

        List<GoodsInfoVo> goodList = vo.getGoodList();
        for (GoodsInfoVo goodsInfoVo : goodList) {
            TbOrderGoods tbOrderGoods = new TbOrderGoods();
            tbOrderGoods.setOrderId(entity.getId());
            tbOrderGoods.setGoodId(goodsInfoVo.getId());
            tbOrderGoods.setGoodNum(goodsInfoVo.getGoodNum().toString());
            tbOrderGoods.setPrice(goodsInfoVo.getPurchasePrice());
            tbOrderGoods.setTotalPrice(goodsInfoVo.getTotalPrice());
            newOrderGoodList.add(tbOrderGoods);

            TbOrderGoods inOrderGoods = new TbOrderGoods();
            BeanUtils.copyProperties(tbOrderGoods, inOrderGoods);
            inOrderGoods.setOrderId(transferIn.getId());
            inOrderGoodList.add(inOrderGoods);
        }
        // ????????????????????????
        tbOrderGoodsMapper.insertBatchSomeColumn(newOrderGoodList);
        tbOrderGoodsMapper.insertBatchSomeColumn(inOrderGoodList);

        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id){
        Set<Long> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        Set<Long> pids = new HashSet<>();
        ids.forEach(id -> {
            TbOrders order = tbOrdersMapper.lambdaQuery().eq(TbOrders::getId, id).one();
            if (order == null) {
                throw new BadRequestException("??????????????????!");
            } else if (order.getStatus().equals(OrderStatusEnum.APPROVE.getCode())) {
                throw new BadRequestException("????????????:" + order.getOrderNo() + "????????????????????????!");
            }
            TbOrders dr = tbOrdersMapper.lambdaQuery().eq(TbOrders::getPid, id).one();
            pids.add(dr.getId());
        });
        tbOrdersMapper.deleteBatchIds(pids);
        return tbOrdersMapper.deleteBatchIds(ids);
    }

    /*
    private void delCaches(Long id) {
        redisUtils.delByKey(CACHE_KEY + "::id:", id);
    }

    private void delCaches(Set<Long> ids) {
        for (Long id: ids) {
            delCaches(id);
        }
    }*/

    /*
    @Override
    public void download(List<TbOrdersDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbOrdersDto tbOrders : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("????????????", tbOrders.getOrderType());
              map.put("??????", tbOrders.getOrderNo());
              map.put("????????????", tbOrders.getOrderDate());
              map.put("?????????id", tbOrders.getOrderPersonId());
              map.put("???????????????", tbOrders.getOrderPerson());
              map.put("?????????id", tbOrders.getManagerId());
              map.put("???????????????", tbOrders.getManager());
              map.put("????????????", tbOrders.getDate());
              map.put("????????????id", tbOrders.getWhId());
              map.put("??????????????????", tbOrders.getWhName());
              map.put("????????????id(?????????????????????id)", tbOrders.getSourceId());
              map.put("????????????(????????????????????????????????????)", tbOrders.getSourceName());
              map.put("????????????", tbOrders.getOriginOrderNo());
              map.put("??????????????? 1 ????????? 2 ????????? 3 ?????????", tbOrders.getStatus());
              map.put("????????????", tbOrders.getVerifyDate());
              map.put("?????????id", tbOrders.getVerifyPersonId());
              map.put("???????????????", tbOrders.getVerifyPerson());
              map.put("????????????", tbOrders.getDelFlag());
              map.put("??????????????????", tbOrders.getUpdateTime());
              map.put("???????????????", tbOrders.getUpdateBy());
              map.put("??????", tbOrders.getRemark());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/

    @Override
    public Object getManagerSelect() {
        return ConvertUtil.convertList(tbEmployeeMapper.lambdaQuery().list(), TbEmployeeDto.class);
    }

    @Override
    public Object getOrderNo() {
        return orderUtil.getOrderNoForShow(CommonConstant.DC);
    }

    @Override
    public Object getGoodChooseList(TbGoodsInfoQueryParam query, Pageable pageable) {
        IPage<TbGoodsInfoDto> queryPage = PageUtil.toMybatisPage(pageable);
        QueryWrapper<Object> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getGName())) {
            wrapper.like("g.g_name", query.getGName());
        }
        if (StringUtils.isNotEmpty(query.getGCode())) {
            wrapper.like("g.g_code", query.getGCode());
        }
        wrapper.eq("i.wh_id", query.getWhId());
        wrapper.orderByDesc("g.id");

        IPage<TbGoodsInfoDto> page = tbOrdersMapper.getGoodChooseList(queryPage, wrapper);
        return ConvertUtil.convertPage(page, GoodsInfoVo.class);
    }

    @Override
    public List<GoodsInfoVo> getOrderGoodList(Long id, Long whId) {
        List<OrderGoodsInfoDto> list = tbOrdersMapper.getInOrderGoodListWithWhId(id, whId);
        return ConvertUtil.convertList(list, GoodsInfoVo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveOrders(Set<Long> ids) {
        ids.forEach(id -> {
            // ??????????????????
            TbOrders order = tbOrdersMapper.lambdaQuery().eq(TbOrders::getId, id).one();
            if (order == null) {
                throw new BadRequestException("??????????????????!");
            } else if (order.getStatus().equals(OrderStatusEnum.APPROVE.getCode())) {
                throw new BadRequestException("????????????:" + order.getOrderNo() + "?????????!");
            }
            order.setStatus(OrderStatusEnum.APPROVE.getCode());
            order.setVerifyDate(new Date());
            order.setVerifyPerson(SecurityUtils.getCurrentUsername());
            order.setVerifyPersonId(SecurityUtils.getCurrentUserId());
            tbOrdersMapper.updateById(order);

            TbOrders childOrder = tbOrdersMapper.lambdaQuery()
                    .eq(TbOrders::getPid, id).one();
            childOrder.setStatus(OrderStatusEnum.APPROVE.getCode());
            childOrder.setVerifyDate(new Date());
            childOrder.setVerifyPerson(SecurityUtils.getCurrentUsername());
            childOrder.setVerifyPersonId(SecurityUtils.getCurrentUserId());
            tbOrdersMapper.updateById(childOrder);

            //??????????????????????????????
            //Runnable runnable = () -> {
                try {
                    // ??????????????????
                    List<TbOrderGoods> oldOrderGoodList = tbOrderGoodsMapper.lambdaQuery().eq(TbOrderGoods::getOrderId, id).list();
                    for (TbOrderGoods tbOrderGoods : oldOrderGoodList) {
                        TbWhInventory out = tbWhInventoryMapper.lambdaQuery()
                                .eq(TbWhInventory::getWhId, order.getSourceId())
                                .eq(TbWhInventory::getGoodId, tbOrderGoods.getGoodId())
                                .one();
                        out.setCount(out.getCount() - Integer.parseInt(tbOrderGoods.getGoodNum()));
                        tbWhInventoryMapper.updateById(out);

                        TbWhInventory in = tbWhInventoryMapper.lambdaQuery()
                                .eq(TbWhInventory::getWhId, order.getWhId())
                                .eq(TbWhInventory::getGoodId, tbOrderGoods.getGoodId())
                                .one();
                        in.setCount(in.getCount() + Integer.parseInt(tbOrderGoods.getGoodNum()));
                        tbWhInventoryMapper.updateById(in);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //};
            //new Thread(runnable).start();
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reApproveOrders(Set<Long> ids) {
        ids.forEach(id -> {
            // ??????????????????
            TbOrders order = tbOrdersMapper.lambdaQuery().eq(TbOrders::getId, id).one();
            if (order == null) {
                throw new BadRequestException("??????????????????!");
            } else if (!order.getStatus().equals(OrderStatusEnum.APPROVE.getCode())) {
                throw new BadRequestException("????????????:" + order.getOrderNo() + "???????????????????????????!");
            }
            order.setStatus(OrderStatusEnum.RE_APPROVE.getCode());
            tbOrdersMapper.updateById(order);

            TbOrders childOrder = tbOrdersMapper.lambdaQuery()
                    .eq(TbOrders::getPid, id).one();
            childOrder.setStatus(OrderStatusEnum.RE_APPROVE.getCode());
            tbOrdersMapper.updateById(childOrder);

            //??????????????????????????????
            //Runnable runnable = () -> {
                try {
                    // ??????????????????
                    List<TbOrderGoods> oldOrderGoodList = tbOrderGoodsMapper.lambdaQuery().eq(TbOrderGoods::getOrderId, id).list();
                    for (TbOrderGoods tbOrderGoods : oldOrderGoodList) {
                        TbWhInventory out = tbWhInventoryMapper.lambdaQuery()
                                .eq(TbWhInventory::getWhId, order.getSourceId())
                                .eq(TbWhInventory::getGoodId, tbOrderGoods.getGoodId())
                                .one();
                        out.setCount(out.getCount() + Integer.parseInt(tbOrderGoods.getGoodNum()));
                        tbWhInventoryMapper.updateById(out);

                        TbWhInventory in = tbWhInventoryMapper.lambdaQuery()
                                .eq(TbWhInventory::getWhId, order.getWhId())
                                .eq(TbWhInventory::getGoodId, tbOrderGoods.getGoodId())
                                .one();
                        in.setCount(in.getCount() - Integer.parseInt(tbOrderGoods.getGoodNum()));
                        tbWhInventoryMapper.updateById(in);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //};
            //new Thread(runnable).start();
        });
    }

    @Override
    public void printOrderReport(Long id, HttpServletResponse response) throws Exception {
        // ????????????
        TbOrders order = tbOrdersMapper.lambdaQuery().eq(TbOrders::getId, id).one();

        List<OrderGoodsInfoDto> list = tbOrdersMapper.getInOrderGoodList(id);
        //List<GoodsInfoVo> orderGoodList = ConvertUtil.convertList(list, GoodsInfoVo.class);
        List<ReportGoodsListVo> reportGoodsListVos = ConvertUtil.convertList(list, ReportGoodsListVo.class);
        //????????????
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderNo", order.getOrderNo());
        params.put("inDate", DateFormatUtils.format(order.getDate(), "yyyy-MM-dd"));
        params.put("printDate", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        params.put("whName", order.getWhName());
        params.put("supplier", order.getSourceName());
        params.put("orderPerson", order.getOrderPerson());
        params.put("manager", order.getManager());
        params.put("originOrderNo", order.getOriginOrderNo());
        params.put("remark", order.getRemark() == null ? "" : order.getRemark());
        params.put("upperPrice", order.getUpperCasePrice());
        params.put("amountCount", order.getAmountCount());
        params.put("amountPrice", order.getAmountPrice());

        for (int i = 0; i < reportGoodsListVos.size(); i++) {
            reportGoodsListVos.get(i).setIndex(String.valueOf(i+1));
        }
        params.put("goodList", reportGoodsListVos);

        String jasperPath = JasperReportUtil.getJasperFileDir("purchase");
        JasperReportUtil.exportToHtml(jasperPath, params, null, response);
    }

    @Override
    public Object getOrderPrintingInfo(Long id) {
        // ????????????
        TbOrders order = tbOrdersMapper.lambdaQuery().eq(TbOrders::getId, id).one();

        List<OrderGoodsInfoDto> list = tbOrdersMapper.getInOrderGoodList(id);
        List<GoodsInfoVo> orderGoodList = ConvertUtil.convertList(list, GoodsInfoVo.class);
        //????????????
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("orderNo", order.getOrderNo());
        resultMap.put("inDate", DateFormatUtils.format(order.getDate(), "yyyy-MM-dd"));
        resultMap.put("printDate", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        resultMap.put("whName", order.getWhName());
        resultMap.put("supplier", order.getSourceName());
        resultMap.put("orderPerson", order.getOrderPerson());
        resultMap.put("manager", order.getManager());
        resultMap.put("originOrderNo", order.getOriginOrderNo());
        resultMap.put("remark", order.getRemark() == null ? "" : order.getRemark());
        resultMap.put("upperPrice", order.getUpperCasePrice());
        resultMap.put("amountCount", order.getAmountCount());
        resultMap.put("amountPrice", order.getAmountPrice());

        resultMap.put("goodList", orderGoodList);

        return resultMap;
    }
}
