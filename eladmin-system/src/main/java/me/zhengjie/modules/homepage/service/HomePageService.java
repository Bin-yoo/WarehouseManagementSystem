package me.zhengjie.modules.homepage.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryDto;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryQueryParam;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface HomePageService extends CommonService<TbWhInventory>  {

    static final String CACHE_KEY = "tbWhInventory";

    Object queryData();

    Object queryLineBarData();
}
