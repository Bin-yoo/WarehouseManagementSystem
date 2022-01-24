package me.zhengjie.modules.orders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.constants.CommonConstant;
import me.zhengjie.enums.EnterpriseTypeEnum;
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
import me.zhengjie.modules.orders.service.PurchaseRefundOrdersService;
import me.zhengjie.modules.orders.service.dto.OrderGoodsInfoDto;
import me.zhengjie.modules.orders.service.dto.TbOrdersDto;
import me.zhengjie.modules.orders.service.dto.TbOrdersQueryParam;
import me.zhengjie.modules.orders.service.mapper.TbOrdersMapper;
import me.zhengjie.modules.partnercompanyinfo.domain.TbPartnerCompanyInfo;
import me.zhengjie.modules.partnercompanyinfo.service.dto.TbPartnerCompanyInfoDto;
import me.zhengjie.modules.partnercompanyinfo.service.mapper.TbPartnerCompanyInfoMapper;
import me.zhengjie.modules.tb_order_goods.domain.TbOrderGoods;
import me.zhengjie.modules.tb_order_goods.service.mapper.TbOrderGoodsMapper;
import me.zhengjie.utils.*;
import me.zhengjie.utlis.JasperReportUtil;
import me.zhengjie.utlis.OrderUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
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
public class PurchaseRefundOrdersServiceImpl extends CommonServiceImpl<TbOrdersMapper, TbOrders> implements PurchaseRefundOrdersService {

    //private final RedisUtils redisUtils;
    private final TbOrdersMapper tbOrdersMapper;
    private final TbOrderGoodsMapper tbOrderGoodsMapper;
    private final TbWhInventoryMapper tbWhInventoryMapper;
    private final TbPartnerCompanyInfoMapper tbPartnerCompanyInfoMapper;
    private final TbEmployeeMapper tbEmployeeMapper;
    private final OrderUtil orderUtil;

    @Override
    public PageInfo<TbOrdersDto> queryAll(TbOrdersQueryParam query, Pageable pageable) {
        IPage<TbOrders> queryPage = PageUtil.toMybatisPage(pageable);
        QueryWrapper<TbOrders> predicate = QueryHelpMybatisPlus.getPredicate(query);
        predicate.eq("order_type", OrderTypeEnum.PURCHASE_REFUND.getCode());
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
        // 单号
        entity.setOrderNo(orderUtil.getIncrOrderNo(CommonConstant.CT));
        // 单据类型为采购退货
        entity.setOrderType(OrderTypeEnum.PURCHASE_REFUND.getCode());
        // 单据状态
        entity.setStatus(OrderStatusEnum.NOT_APPROVE.getCode());
        // 插入单据
        int insert = tbOrdersMapper.insert(entity);

        List<TbOrderGoods> orderGoodList = new ArrayList<>();

        List<GoodsInfoVo> goodList = vo.getGoodList();
        for (GoodsInfoVo goodsInfoVo : goodList) {
            TbOrderGoods tbOrderGoods = new TbOrderGoods();
            tbOrderGoods.setOrderId(entity.getId());
            tbOrderGoods.setGoodId(goodsInfoVo.getId());
            tbOrderGoods.setGoodNum(goodsInfoVo.getGoodNum().toString());
            tbOrderGoods.setPrice(goodsInfoVo.getPurchasePrice());
            tbOrderGoods.setTotalPrice(goodsInfoVo.getTotalPrice());
            orderGoodList.add(tbOrderGoods);
        }
        // 批量插入单据货品
        tbOrderGoodsMapper.insertBatchSomeColumn(orderGoodList);

        //另起线程更新库存信息
        Runnable runnable = () -> {
            try {
                for (GoodsInfoVo goodInfo : goodList) {
                    TbWhInventory one = tbWhInventoryMapper.lambdaQuery()
                            .eq(TbWhInventory::getWhId, vo.getWhId())
                            .eq(TbWhInventory::getGoodId, goodInfo.getId())
                            .one();
                    one.setCount(one.getCount() - goodInfo.getGoodNum());
                    tbWhInventoryMapper.updateById(one);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();

        return insert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(OrderVo vo){

        // 单据状态校验
        TbOrders order = tbOrdersMapper.lambdaQuery()
                .eq(TbOrders::getId, vo.getId()).one();
        if (order == null) {
            throw new BadRequestException("该订单不存在!");
        } else if (order.getStatus().equals(OrderStatusEnum.APPROVE.getCode())) {
            throw new BadRequestException("订单编号:" + order.getOrderNo() + "已审批，无法修改!");
        }

        TbOrders entity = ConvertUtil.convert(vo, TbOrders.class);
        int ret = tbOrdersMapper.updateById(entity);
        // delCaches(resources.id);

        // 还原库存数量
        List<TbOrderGoods> oldOrderGoodList = tbOrderGoodsMapper.lambdaQuery().eq(TbOrderGoods::getOrderId, entity.getId()).list();
        for (TbOrderGoods tbOrderGoods : oldOrderGoodList) {
            TbWhInventory one = tbWhInventoryMapper.lambdaQuery()
                    .eq(TbWhInventory::getWhId, vo.getWhId())
                    .eq(TbWhInventory::getGoodId, tbOrderGoods.getGoodId())
                    .one();
            one.setCount(one.getCount() + Integer.parseInt(tbOrderGoods.getGoodNum()));
            tbWhInventoryMapper.updateById(one);
        }

        // 清除旧的订单货品数据
        LambdaQueryWrapper<TbOrderGoods> wrapper = new LambdaQueryWrapper();
        wrapper.eq(TbOrderGoods::getOrderId, entity.getId());
        tbOrderGoodsMapper.delete(wrapper);

        List<TbOrderGoods> newOrderGoodList = new ArrayList<>();

        List<GoodsInfoVo> goodList = vo.getGoodList();
        for (GoodsInfoVo goodsInfoVo : goodList) {
            TbOrderGoods tbOrderGoods = new TbOrderGoods();
            tbOrderGoods.setOrderId(entity.getId());
            tbOrderGoods.setGoodId(goodsInfoVo.getId());
            tbOrderGoods.setGoodNum(goodsInfoVo.getGoodNum().toString());
            tbOrderGoods.setPrice(goodsInfoVo.getPurchasePrice());
            tbOrderGoods.setTotalPrice(goodsInfoVo.getTotalPrice());
            newOrderGoodList.add(tbOrderGoods);
        }
        // 批量插入单据货品
        tbOrderGoodsMapper.insertBatchSomeColumn(newOrderGoodList);

        //另起线程更新库存信息
        Runnable runnable = () -> {
            try {
                for (GoodsInfoVo goodInfo : goodList) {
                    TbWhInventory one = tbWhInventoryMapper.lambdaQuery()
                            .eq(TbWhInventory::getWhId, vo.getWhId())
                            .eq(TbWhInventory::getGoodId, goodInfo.getId())
                            .one();
                    one.setCount(one.getCount() - goodInfo.getGoodNum());
                    tbWhInventoryMapper.updateById(one);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();

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
        ids.forEach(id -> {
            TbOrders order = tbOrdersMapper.lambdaQuery()
                    .eq(TbOrders::getId, id).one();
            if (order == null) {
                throw new BadRequestException("该订单不存在!");
            } else if (order.getStatus().equals(OrderStatusEnum.APPROVE.getCode())) {
                throw new BadRequestException("订单编号:" + order.getOrderNo() + "已审批，无法删除!");
            } else {
                //另起线程更新库存信息
                Runnable runnable = () -> {
                    try {
                        // 还原库存数量
                        List<TbOrderGoods> oldOrderGoodList = tbOrderGoodsMapper.lambdaQuery().eq(TbOrderGoods::getOrderId, id).list();
                        for (TbOrderGoods tbOrderGoods : oldOrderGoodList) {
                            TbWhInventory one = tbWhInventoryMapper.lambdaQuery()
                                    .eq(TbWhInventory::getWhId, order.getWhId())
                                    .eq(TbWhInventory::getGoodId, tbOrderGoods.getGoodId())
                                    .one();
                            one.setCount(one.getCount() + Integer.parseInt(tbOrderGoods.getGoodNum()));
                            tbWhInventoryMapper.updateById(one);
                        }

                        // 清除旧的订单货品数据
                        /*LambdaQueryWrapper<TbOrderGoods> wrapper = new LambdaQueryWrapper();
                        wrapper.eq(TbOrderGoods::getOrderId, order.getId());
                        tbOrderGoodsMapper.delete(wrapper);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                new Thread(runnable).start();
            }
        });
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
              map.put("单据类型", tbOrders.getOrderType());
              map.put("单号", tbOrders.getOrderNo());
              map.put("制单日期", tbOrders.getOrderDate());
              map.put("制单人id", tbOrders.getOrderPersonId());
              map.put("制单人名称", tbOrders.getOrderPerson());
              map.put("经办人id", tbOrders.getManagerId());
              map.put("经办人名称", tbOrders.getManager());
              map.put("入库日期", tbOrders.getDate());
              map.put("存入仓库id", tbOrders.getWhId());
              map.put("存入仓库名称", tbOrders.getWhName());
              map.put("货品来源id(来往单位或部门id)", tbOrders.getSourceId());
              map.put("货品来源(来往单位、部门或仓库名称)", tbOrders.getSourceName());
              map.put("原始单号", tbOrders.getOriginOrderNo());
              map.put("采购单状态 1 未审批 2 已通过 3 反审核", tbOrders.getStatus());
              map.put("审核日期", tbOrders.getVerifyDate());
              map.put("审核人id", tbOrders.getVerifyPersonId());
              map.put("审核人名称", tbOrders.getVerifyPerson());
              map.put("删除标识", tbOrders.getDelFlag());
              map.put("最后修改日期", tbOrders.getUpdateTime());
              map.put("最后修改人", tbOrders.getUpdateBy());
              map.put("备注", tbOrders.getRemark());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/

    @Override
    public Object getSupplierSelect() {
        List<TbPartnerCompanyInfo> list = tbPartnerCompanyInfoMapper.lambdaQuery()
                .in(TbPartnerCompanyInfo::getType, EnterpriseTypeEnum.SUPPLIER.getCode(), EnterpriseTypeEnum.BOTH.getCode()).list();
        return ConvertUtil.convertList(list, TbPartnerCompanyInfoDto.class);
    }

    @Override
    public Object getManagerSelect() {
        return ConvertUtil.convertList(tbEmployeeMapper.lambdaQuery().list(), TbEmployeeDto.class);
    }

    @Override
    public Object getOrderNo() {
        return orderUtil.getOrderNoForShow(CommonConstant.CT);
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
    public List<GoodsInfoVo> getOrderGoodList(String id) {
        List<OrderGoodsInfoDto> list = tbOrdersMapper.getOrderGoodList(id);
        return ConvertUtil.convertList(list, GoodsInfoVo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveOrders(Set<Long> ids) {
        ids.forEach(id -> {
            // 单据状态校验
            TbOrders order = tbOrdersMapper.lambdaQuery()
                    .eq(TbOrders::getId, id).one();
            if (order == null) {
                throw new BadRequestException("该订单不存在!");
            } else if (order.getStatus().equals(OrderStatusEnum.APPROVE.getCode())) {
                throw new BadRequestException("订单编号:" + order.getOrderNo() + "已审批!");
            }
            order.setStatus(OrderStatusEnum.APPROVE.getCode());
            order.setVerifyDate(new Date());
            order.setVerifyPerson(SecurityUtils.getCurrentUsername());
            order.setVerifyPersonId(SecurityUtils.getCurrentUserId());
            tbOrdersMapper.updateById(order);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reApproveOrders(Set<Long> ids) {
        ids.forEach(id -> {
            // 单据状态校验
            TbOrders order = tbOrdersMapper.lambdaQuery()
                    .eq(TbOrders::getId, id).one();
            if (order == null) {
                throw new BadRequestException("该订单不存在!");
            } else if (!order.getStatus().equals(OrderStatusEnum.APPROVE.getCode())) {
                throw new BadRequestException("订单编号:" + order.getOrderNo() + "尚未审批，无法反审!");
            }
            order.setStatus(OrderStatusEnum.RE_APPROVE.getCode());
            tbOrdersMapper.updateById(order);
        });
    }

    @Override
    public void printOrderReport(String id, HttpServletResponse response) throws Exception {
        // 订单信息
        TbOrders order = tbOrdersMapper.lambdaQuery()
                .eq(TbOrders::getId, id)
                .eq(TbOrders::getOrderType, OrderTypeEnum.PURCHASE_REFUND.getCode())
                .one();

        List<GoodsInfoVo> orderGoodList = getOrderGoodList(id);
        List<ReportGoodsListVo> reportGoodsListVos = ConvertUtil.convertList(orderGoodList, ReportGoodsListVo.class);
        //定义参数
        Map<String, Object> params = new HashMap<>();
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
    public Object getOrderPrintingInfo(String id) {
        // 订单信息
        TbOrders order = tbOrdersMapper.lambdaQuery()
                .eq(TbOrders::getId, id)
                .eq(TbOrders::getOrderType, OrderTypeEnum.PURCHASE_REFUND.getCode())
                .one();

        List<GoodsInfoVo> orderGoodList = getOrderGoodList(id);
        //定义参数
        Map<String, Object> resultMap = new HashMap<>();
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

    @Override
    public PageInfo<TbOrdersDto> getOrderChooseList(TbOrdersQueryParam query, Pageable pageable) {
        IPage<TbOrders> queryPage = PageUtil.toMybatisPage(pageable);
        QueryWrapper<TbOrders> predicate = QueryHelpMybatisPlus.getPredicate(query);
        predicate.eq("order_type", OrderTypeEnum.PURCHASE.getCode());
        predicate.eq("status", OrderStatusEnum.APPROVE.getCode());
        IPage<TbOrders> page = tbOrdersMapper.selectPage(queryPage, predicate);
        return ConvertUtil.convertPage(page, TbOrdersDto.class);
    }
}
