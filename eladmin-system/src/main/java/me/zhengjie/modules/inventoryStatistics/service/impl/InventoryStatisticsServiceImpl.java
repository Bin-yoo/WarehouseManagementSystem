package me.zhengjie.modules.inventoryStatistics.service.impl;

import lombok.AllArgsConstructor;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.enums.OrderTypeEnum;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventoryStatistics.service.InventoryStatisticsService;
import me.zhengjie.modules.inventoryStatistics.service.dto.LineBarDto;
import me.zhengjie.modules.inventoryStatistics.service.dto.PieChartDto;
import me.zhengjie.modules.inventoryStatistics.service.mapper.InventoryStatisticsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbWhInventoryService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InventoryStatisticsServiceImpl extends CommonServiceImpl<InventoryStatisticsMapper, TbWhInventory> implements InventoryStatisticsService {

    // private final RedisUtils redisUtils;
    private final InventoryStatisticsMapper inventoryStatisticsMapper;

    @Override
    public Object queryLineBarData() {
        List<LineBarDto> inLineBarList = inventoryStatisticsMapper.queryLineBarDataByInOutType(1);
        // 分离日期和数据
        List<String> days = inLineBarList.stream().map(LineBarDto::getDays).collect(Collectors.toList());
        List<Integer> inLineBarData = inLineBarList.stream().map(LineBarDto::getCount).collect(Collectors.toList());

        List<LineBarDto> outLineBarList = inventoryStatisticsMapper.queryLineBarDataByInOutType(2);
        List<Integer> outLineBarData = outLineBarList.stream().map(LineBarDto::getCount).collect(Collectors.toList());

        HashMap<Object, Object> map = new HashMap<>();
        map.put("days", days);
        map.put("inLineBarData", inLineBarData);
        map.put("outLineBarData", outLineBarData);

        return map;
    }

    @Override
    public Object queryPieChartData() {
        Integer count = inventoryStatisticsMapper.queryStatisticsOrderCount();

        // 获取饼图数据
        List<PieChartDto> paymentPieStatistics = inventoryStatisticsMapper.queryPieChartData();
        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < paymentPieStatistics.size(); i++) {
            PieChartDto pieChartDto = paymentPieStatistics.get(i);
            if (i == paymentPieStatistics.size() - 1) {
                // 用100减去其余类型占比的和, 当作经营业务为"其他"的占比, 解决最后百分比加起来不为100的问题
                pieChartDto.setPercentage(new BigDecimal(100).subtract(sum));
            } else {
                // 计算百分比
                BigDecimal value = pieChartDto.getValue();
                BigDecimal divisor = BigDecimal.valueOf(count);
                BigDecimal percentage = value.divide(divisor, 4, BigDecimal.ROUND_HALF_UP);
                percentage = percentage.multiply(BigDecimal.valueOf(100));
                pieChartDto.setPercentage(percentage);
                sum = sum.add(percentage);
            }
            // 将从数据库中获取的字典值转换名称
            pieChartDto.setName(OrderTypeEnum.getDescription(Integer.parseInt(pieChartDto.getName())));
        }

        Set<String> data = paymentPieStatistics.stream().map(PieChartDto::getName).collect(Collectors.toSet());

        HashMap<Object, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("pieData", paymentPieStatistics);

        return map;
    }
}
