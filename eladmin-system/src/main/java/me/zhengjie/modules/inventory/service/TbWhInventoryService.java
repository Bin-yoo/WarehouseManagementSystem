package me.zhengjie.modules.inventory.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryDto;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbWhInventoryService extends CommonService<TbWhInventory>  {

    static final String CACHE_KEY = "tbWhInventory";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbWhInventoryDto>
    */
    PageInfo<TbWhInventoryDto> queryAll(TbWhInventoryQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbWhInventoryDto>
    */
    List<TbWhInventoryDto> queryAll(TbWhInventoryQueryParam query);

    TbWhInventory getById(String id);
    TbWhInventoryDto findById(String id);

    /**
     * 插入一条新数据。
     */
    int insert(TbWhInventoryDto resources);
    int updateById(TbWhInventoryDto resources);
    int removeById(String id);
    int removeByIds(Set<String> ids);

    Object getWareHouseSelect();

    Object getWhInOutDetail();

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbWhInventoryDto> all, HttpServletResponse response) throws IOException;
}
