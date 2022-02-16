package me.zhengjie.modules.homepage.service.impl;

import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.enums.OrderTypeEnum;
import me.zhengjie.modules.homepage.service.HomePageService;
import me.zhengjie.modules.homepage.service.dto.LineBarDto;
import me.zhengjie.modules.homepage.service.mapper.HomePageMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryDto;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryQueryParam;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbWhInventoryService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class HomePageServiceImpl extends CommonServiceImpl<HomePageMapper, TbWhInventory> implements HomePageService {

    // private final RedisUtils redisUtils;
    private final HomePageMapper homePageMapper;

    @Override
    public Object queryData() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 设置开始时间
        final int firstDayOfMonth = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, firstDayOfMonth);
        Date startTime = calendar.getTime();
        // 设置结束时间
        final int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endTime = calendar.getTime();

        Integer purchaseOrderCount = homePageMapper.selectPurchaseOrderCountByMonth(startTime, endTime);
        Integer sellOrderCount = homePageMapper.selectSellOrderCountByMonth(startTime, endTime);
        Integer waitApproveOrderCount = homePageMapper.selectWaitApproveOrderCountByMonth();
        Integer inventoryWarningCount = homePageMapper.selectInventoryWarningCountByMonth();

        HashMap<Object, Object> map = new HashMap<>();
        map.put("purchaseOrderCount", purchaseOrderCount);
        map.put("sellOrderCount", sellOrderCount);
        map.put("waitApproveOrderCount", waitApproveOrderCount);
        map.put("inventoryWarningCount", inventoryWarningCount);

        return map;
    }

    @Override
    public Object queryLineBarData() {
        List<LineBarDto> purchaseLineBarList = homePageMapper.queryLineBarDataByOrderType(OrderTypeEnum.PURCHASE.getCode());
        // 分离日期和数据
        List<String> days = purchaseLineBarList.stream().map(LineBarDto::getDays).collect(Collectors.toList());
        List<Integer> purchaseLineBarData = purchaseLineBarList.stream().map(LineBarDto::getCount).collect(Collectors.toList());

        List<LineBarDto> produceLineBarList = homePageMapper.queryLineBarDataByOrderType(OrderTypeEnum.PRODUCE.getCode());
        List<Integer> produceLineBarData = produceLineBarList.stream().map(LineBarDto::getCount).collect(Collectors.toList());

        List<LineBarDto> sellRefundLineBarList = homePageMapper.queryLineBarDataByOrderType(OrderTypeEnum.SELL_REFUND.getCode());
        List<Integer> sellRefundLineBarData = sellRefundLineBarList.stream().map(LineBarDto::getCount).collect(Collectors.toList());

        List<LineBarDto> purchaseRefundLineBarList = homePageMapper.queryLineBarDataByOrderType(OrderTypeEnum.PURCHASE_REFUND.getCode());
        List<Integer> purchaseRefundLineBarData = purchaseRefundLineBarList.stream().map(LineBarDto::getCount).collect(Collectors.toList());

        List<LineBarDto> sellLineBarList = homePageMapper.queryLineBarDataByOrderType(OrderTypeEnum.SELL.getCode());
        List<Integer> sellLineBarData = sellLineBarList.stream().map(LineBarDto::getCount).collect(Collectors.toList());

        HashMap<Object, Object> map = new HashMap<>();
        map.put("days", days);
        map.put("purchaseLineBarData", purchaseLineBarData);
        map.put("produceLineBarData", produceLineBarData);
        map.put("sellRefundLineBarData", sellRefundLineBarData);
        map.put("purchaseRefundLineBarData", purchaseRefundLineBarData);
        map.put("sellLineBarData", sellLineBarData);

        return map;
    }


}
