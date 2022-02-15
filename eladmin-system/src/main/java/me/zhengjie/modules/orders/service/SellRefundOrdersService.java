package me.zhengjie.modules.orders.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.orders.domain.TbOrders;
import me.zhengjie.modules.orders.domain.vo.GoodsInfoVo;
import me.zhengjie.modules.orders.domain.vo.OrderVo;
import me.zhengjie.modules.orders.service.dto.TbOrdersDto;
import me.zhengjie.modules.orders.service.dto.TbOrdersQueryParam;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2022-01-03
*/
public interface SellRefundOrdersService extends CommonService<TbOrders>  {

    static final String CACHE_KEY = "tbOrders";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbOrdersDto>
    */
    PageInfo<TbOrdersDto> queryAll(TbOrdersQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbOrdersDto>
    */
    List<TbOrdersDto> queryAll(TbOrdersQueryParam query);

    TbOrders getById(Long id);
    TbOrdersDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(OrderVo vo);
    int updateById(OrderVo vo);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    Object getCustomerSelect();

    Object getManagerSelect();

    Object getOrderNo();

    Object getGoodChooseList(TbGoodsInfoQueryParam query, Pageable pageable);

    List<GoodsInfoVo> getOrderGoodList(Long id, Long whId);

    void approveOrders(Set<Long> ids);

    void reApproveOrders(Set<Long> ids);

    void printOrderReport(Long id, HttpServletResponse response) throws Exception;

    Object getOrderPrintingInfo(Long id);

    Object getOrderChooseList(TbOrdersQueryParam query, Pageable pageable);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbOrdersDto> all, HttpServletResponse response) throws IOException;
}
