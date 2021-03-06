package me.zhengjie.modules.waitapproveorders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.enums.OrderTypeEnum;
import me.zhengjie.modules.orders.domain.TbOrders;
import me.zhengjie.modules.orders.domain.vo.ReportGoodsListVo;
import me.zhengjie.modules.orders.service.*;
import me.zhengjie.modules.orders.service.dto.TbOrdersDto;
import me.zhengjie.modules.searchorders.service.dto.SearchOrderGoodsInfoDto;
import me.zhengjie.modules.searchorders.service.dto.SearchOrdersQueryParam;
import me.zhengjie.modules.waitapproveorders.service.WaitApproveOrdersService;
import me.zhengjie.modules.waitapproveorders.service.mapper.WaitApproveOrdersMapper;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utlis.JasperReportUtil;
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
public class WaitApproveOrdersServiceImpl extends CommonServiceImpl<WaitApproveOrdersMapper, TbOrders> implements WaitApproveOrdersService {

    //private final RedisUtils redisUtils;
    private final WaitApproveOrdersMapper waitApproveOrdersMapper;
    private final PurchaseOrdersService purchaseOrdersService;
    private final ProductOrdersService productOrdersService;
    private final SellRefundOrdersService sellRefundOrdersService;
    private final PurchaseRefundOrdersService purchaseRefundOrdersService;
    private final SellOrdersService sellOrdersService;
    private final TransferOrdersService transferOrdersService;
    private final InventoryOrdersService inventoryOrdersService;

    @Override
    public PageInfo<TbOrdersDto> queryAll(SearchOrdersQueryParam query, Pageable pageable) {
        IPage<TbOrders> queryPage = PageUtil.toMybatisPage(pageable);
        QueryWrapper<TbOrders> predicate = QueryHelpMybatisPlus.getPredicate(query);
        predicate.ne("status", 2);
        predicate.ne("order_type", OrderTypeEnum.TRANSFER_IN.getCode());
        IPage<TbOrders> page = waitApproveOrdersMapper.selectPage(queryPage, predicate);
        return ConvertUtil.convertPage(page, TbOrdersDto.class);
    }

    @Override
    public List<TbOrdersDto> queryAll(SearchOrdersQueryParam query){
        return ConvertUtil.convertList(waitApproveOrdersMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbOrdersDto.class);
    }

    @Override
    public TbOrders getById(Long id) {
        return waitApproveOrdersMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbOrdersDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbOrdersDto.class);
    }

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
    public List<SearchOrderGoodsInfoDto> getOrderGoodList(Long id) {
        return waitApproveOrdersMapper.getInventoryOrderGoodList(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveOrders(Set<Long> ids) {
        ids.forEach(id -> {
            // ??????????????????
            TbOrders order = waitApproveOrdersMapper.lambdaQuery().eq(TbOrders::getId, id).one();
            if (order != null) {
                switch (OrderTypeEnum.getValue(order.getOrderType())) {
                    case PURCHASE : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        purchaseOrdersService.approveOrders(apIds);
                        break;
                    }
                    case PRODUCE : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        productOrdersService.approveOrders(apIds);
                        break;
                    }
                    case SELL_REFUND : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        sellRefundOrdersService.approveOrders(apIds);
                        break;
                    }
                    case PURCHASE_REFUND : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        purchaseRefundOrdersService.approveOrders(apIds);
                        break;
                    }
                    case SELL : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        sellOrdersService.approveOrders(apIds);
                        break;
                    }
                    case TRANSFER_OUT : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        transferOrdersService.approveOrders(apIds);
                        break;
                    }
                    case INVENTORY : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        inventoryOrdersService.approveOrders(apIds);
                        break;
                    }
                }
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reApproveOrders(Set<Long> ids) {
        ids.forEach(id -> {
            // ??????????????????
            TbOrders order = waitApproveOrdersMapper.lambdaQuery().eq(TbOrders::getId, id).one();
            if (order != null) {
                switch (OrderTypeEnum.getValue(order.getOrderType())) {
                    case PURCHASE : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        purchaseOrdersService.reApproveOrders(apIds);
                        break;
                    }
                    case PRODUCE : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        productOrdersService.reApproveOrders(apIds);
                        break;
                    }
                    case SELL_REFUND : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        sellRefundOrdersService.reApproveOrders(apIds);
                        break;
                    }
                    case PURCHASE_REFUND : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        purchaseRefundOrdersService.reApproveOrders(apIds);
                        break;
                    }
                    case SELL : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        sellOrdersService.reApproveOrders(apIds);
                        break;
                    }
                    case TRANSFER_OUT : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        transferOrdersService.reApproveOrders(apIds);
                        break;
                    }
                    case INVENTORY : {
                        Set<Long> apIds = new HashSet<>();
                        apIds.add(id);
                        inventoryOrdersService.reApproveOrders(apIds);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void printOrderReport(Long id, HttpServletResponse response) throws Exception {
        // ????????????
        TbOrders order = waitApproveOrdersMapper.lambdaQuery().eq(TbOrders::getId, id).one();

        List<SearchOrderGoodsInfoDto> orderGoodList = getOrderGoodList(id);
        List<ReportGoodsListVo> reportGoodsListVos = ConvertUtil.convertList(orderGoodList, ReportGoodsListVo.class);
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
        TbOrders order = waitApproveOrdersMapper.lambdaQuery().eq(TbOrders::getId, id).one();

        List<SearchOrderGoodsInfoDto> orderGoodList = getOrderGoodList(id);
        //????????????
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("orderNo", order.getOrderNo());
        resultMap.put("inOutType", order.getInOutType());
        resultMap.put("orderType", order.getOrderType());
        resultMap.put("date", DateFormatUtils.format(order.getDate(), "yyyy-MM-dd"));
        resultMap.put("printDate", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        resultMap.put("whName", order.getWhName());
        resultMap.put("sourceName", order.getSourceName());
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
