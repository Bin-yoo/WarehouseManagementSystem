package me.zhengjie.modules.searchorders.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.orders.domain.TbOrders;
import me.zhengjie.modules.orders.service.dto.TbOrdersDto;
import me.zhengjie.modules.searchorders.service.dto.SearchOrderGoodsInfoDto;
import me.zhengjie.modules.searchorders.service.dto.SearchOrdersQueryParam;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author LiangBin
* @date 2022-01-03
*/
public interface SearchOrdersService extends CommonService<TbOrders> {

    static final String CACHE_KEY = "tbOrders";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbOrdersDto>
    */
    PageInfo<TbOrdersDto> queryAll(SearchOrdersQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbOrdersDto>
    */
    List<TbOrdersDto> queryAll(SearchOrdersQueryParam query);

    TbOrders getById(Long id);
    TbOrdersDto findById(Long id);

    List<SearchOrderGoodsInfoDto> getOrderGoodList(Long id);

    void printOrderReport(Long id, HttpServletResponse response) throws Exception;

    Object getOrderPrintingInfo(Long id);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbOrdersDto> all, HttpServletResponse response) throws IOException;
}
