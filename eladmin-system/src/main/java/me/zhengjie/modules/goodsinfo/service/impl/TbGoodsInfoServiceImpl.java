package me.zhengjie.modules.goodsinfo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.config.FileProperties;
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.goodsinfo.domain.vo.TbGoodsInfoVo;
import me.zhengjie.modules.tb_wh_goods.domain.vo.TbWhGoodsVo;
import me.zhengjie.modules.goodstype.domain.TbGoodsType;
import me.zhengjie.modules.goodstype.service.mapper.TbGoodsTypeMapper;
import me.zhengjie.modules.goodsunit.domain.TbGoodsUnit;
import me.zhengjie.modules.goodsunit.service.dto.TbGoodsUnitDto;
import me.zhengjie.modules.goodsunit.service.mapper.TbGoodsUnitMapper;
import me.zhengjie.modules.warehouse.domain.TbWarehouse;
import me.zhengjie.modules.warehouse.service.mapper.TbWarehouseMapper;
import me.zhengjie.modules.tb_wh_goods.domain.TbWhGoods;
import me.zhengjie.modules.tb_wh_goods.service.dto.TbWhGoodsDto;
import me.zhengjie.modules.tb_wh_goods.service.impl.TbWhGoodsServiceImpl;
import me.zhengjie.modules.tb_wh_goods.service.mapper.TbWhGoodsMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.impl.TbWhInventoryServiceImpl;
import me.zhengjie.modules.inventory.service.mapper.TbWhInventoryMapper;
import me.zhengjie.service.mapper.LocalStorageMapper;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.goodsinfo.domain.TbGoodsInfo;
import me.zhengjie.modules.goodsinfo.service.TbGoodsInfoService;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoDto;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.goodsinfo.service.mapper.TbGoodsInfoMapper;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utlis.PinYinCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// ?????????????????????
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
* @author LiangBin
* @date 2021-12-21
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbGoodsInfoService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbGoodsInfoServiceImpl extends CommonServiceImpl<TbGoodsInfoMapper, TbGoodsInfo> implements TbGoodsInfoService {

    // private final RedisUtils redisUtils;
    private final TbGoodsInfoMapper tbGoodsInfoMapper;
    private final TbGoodsUnitMapper tbGoodsUnitMapper;
    private final TbGoodsTypeMapper tbGoodsTypeMapper;
    private final TbWhGoodsMapper tbWhGoodsMapper;
    private final TbWhGoodsServiceImpl tbWhGoodsService;
    private final TbWarehouseMapper tbWarehouseMapper;
    private final TbWhInventoryMapper tbWhInventoryMapper;
    private final TbWhInventoryServiceImpl tbWhInventoryService;
    private final FileProperties properties;
    private final LocalStorageMapper localStorageMapper;

    @Override
    public PageInfo<TbGoodsInfoDto> queryAll(TbGoodsInfoQueryParam query, Pageable pageable) {
        IPage<TbGoodsInfo> queryPage = PageUtil.toMybatisPage(pageable);
        MPJLambdaWrapper<TbGoodsInfoDto> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectAll(TbGoodsInfo.class)
                .selectAs(TbGoodsType::getGtName, "typeName")
                .selectAs(TbGoodsUnit::getGuName, "unitName")
                .leftJoin(TbGoodsType.class, TbGoodsType::getId, TbGoodsInfo::getType)
                .leftJoin(TbGoodsUnit.class, TbGoodsUnit::getId, TbGoodsInfo::getUnit);

        if (StringUtils.isNotEmpty(query.getGName())) {
            mpjLambdaWrapper.like(TbGoodsInfo::getGName, query.getGName());
        }
        if (StringUtils.isNotEmpty(query.getGCode())) {
            mpjLambdaWrapper.like(TbGoodsInfo::getGCode, query.getGCode());
        }
        if (StringUtils.isNotEmpty(query.getPyCode())) {
            mpjLambdaWrapper.like(TbGoodsInfo::getPyCode, query.getPyCode());
        }
        if (query.getType() != null) {
            mpjLambdaWrapper.eq(TbGoodsInfo::getType, query.getType());
        }
        if (query.getUnit() != null) {
            mpjLambdaWrapper.eq(TbGoodsInfo::getUnit, query.getUnit());
        }
        mpjLambdaWrapper.orderByDesc(TbGoodsInfo::getId);

        IPage<TbGoodsInfoDto> page = tbGoodsInfoMapper.selectJoinPage(queryPage, TbGoodsInfoDto.class, mpjLambdaWrapper);
        return ConvertUtil.convertPage(page, TbGoodsInfoDto.class);
    }

    @Override
    public List<TbGoodsInfoDto> queryAll(TbGoodsInfoQueryParam query){
        return ConvertUtil.convertList(tbGoodsInfoMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbGoodsInfoDto.class);
    }

    @Override
    public TbGoodsInfo getById(Long id) {
        return tbGoodsInfoMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbGoodsInfoDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbGoodsInfoDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbGoodsInfoVo resources) {
        TbGoodsInfo entity = ConvertUtil.convert(resources, TbGoodsInfo.class);
        int id = tbGoodsInfoMapper.insert(entity);

        List<TbWhGoods> tbWhGoodsList = ConvertUtil.convertList(resources.getWhGoodsList(), TbWhGoods.class);
        List<TbWhInventory> tbWhInventoryList = new ArrayList<>();
        for (TbWhGoods tbWhGood :tbWhGoodsList) {
            tbWhGood.setGoodId(entity.getId());
            TbWhInventory tbWhInventory = new TbWhInventory();
            tbWhInventory.setGoodId(entity.getId());
            tbWhInventory.setWhId(tbWhGood.getWhId());
            tbWhInventory.setCount(tbWhGood.getInitialCount());
            tbWhInventoryList.add(tbWhInventory);
        }
        tbWhGoodsMapper.insertBatchSomeColumn(tbWhGoodsList);
        tbWhInventoryMapper.insertBatchSomeColumn(tbWhInventoryList);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbGoodsInfoVo resources){
        TbGoodsInfo entity = ConvertUtil.convert(resources, TbGoodsInfo.class);
        int ret = tbGoodsInfoMapper.updateById(entity);

        //????????????????????????????????????
        //Runnable runnable = () -> {
            try {
                // ?????????????????????????????????
                List<TbWhGoods> lastUpdateList = tbWhGoodsMapper.lambdaQuery().eq(TbWhGoods::getGoodId, entity.getId()).orderByAsc(TbWhGoods::getGoodId).list();
                // ????????????????????????
                List<TbWhInventory> inventoryList = tbWhInventoryMapper.lambdaQuery().eq(TbWhInventory::getGoodId, entity.getId()).orderByAsc(TbWhInventory::getGoodId).list();

                //Set<Long> ids = tbWhGoodsList.stream().map(TbWhGoods::getId).collect(Collectors.toSet());
                //Set<Long> ids = new HashSet<>();

                List<TbWhGoods> tbWhGoodsList = ConvertUtil.convertList(resources.getWhGoodsList(), TbWhGoods.class);
                for (int i = 0; i < tbWhGoodsList.size(); i++) {
                    TbWhGoods whGoods = tbWhGoodsList.get(i);
                    if (whGoods.getId() == null) {
                        tbWhGoodsMapper.insert(whGoods);
                        TbWhInventory tbWhInventory = new TbWhInventory();
                        tbWhInventory.setWhId(whGoods.getWhId());
                        tbWhInventory.setGoodId(whGoods.getGoodId());
                        tbWhInventory.setCount(whGoods.getInitialCount());
                        tbWhInventoryMapper.insert(tbWhInventory);
                        continue;
                    }
                    //ids.add(item.getId());
                    int count = 0;
                    TbWhGoods oldData = lastUpdateList.get(i);
                    TbWhGoods newData = whGoods;
                    /*
                     * ??????????????????
                     * ????????????=??????????????????-??????????????????
                     * ??????????????????,???????????????????????????
                     */
                    if ((count = newData.getInitialCount() - oldData.getInitialCount()) == 0){
                        continue;
                    }

                    Integer oldCount = inventoryList.get(i).getCount();
                    inventoryList.get(i).setCount(Math.abs(oldCount + count));
                }
                tbWhInventoryService.updateBatchById(inventoryList);
                tbWhGoodsService.updateBatchById(tbWhGoodsList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        //};
        //new Thread(runnable).start();

        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbGoodsInfoMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id){
        Set<Long> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    @Override
    public Object getGoodCode(Long typeId) {
        TbGoodsType tbGoodsType = tbGoodsTypeMapper.selectById(typeId);
        //????????????????????????????????????
        String py_code = PinYinCodeUtil.getPYIndexStr2(tbGoodsType.getGtName(), true);
        QueryWrapper<TbGoodsInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("type", typeId.toString());
        Integer count = tbGoodsInfoMapper.selectCount(wrapper);
        String format = String.format("%s%05d", py_code, count+1);
        return format;
    }

    @Override
    public Object getGoodUnitsSelect() {
        return ConvertUtil.convertList(tbGoodsUnitMapper.lambdaQuery().orderByAsc(TbGoodsUnit::getSort).list(), TbGoodsUnitDto.class);
    }

    @Override
    public Object getWareHouseGoodsInfo() {
        List<TbWarehouse> whInfoList = tbWarehouseMapper.selectList(null);
        List<TbWhGoodsVo> whList = ConvertUtil.convertList(whInfoList, TbWhGoodsVo.class);
        return whList;
    }

    @Override
    public Object getWareHouseGoodsInfoByGoodId(Long goodId) {
        List<TbWarehouse> whInfoList = tbWarehouseMapper.selectList(null);
        List<TbWhGoodsVo> whList = ConvertUtil.convertList(whInfoList, TbWhGoodsVo.class);

        MPJLambdaWrapper<TbWhGoodsVo> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectAll(TbWhGoods.class)
                //.selectAs(TbGoodsInfo::getGName, "goodName")
                .selectAs(TbWarehouse::getWhName, "whName")
                .leftJoin(TbGoodsInfo.class, TbGoodsInfo::getId, TbWhGoods::getGoodId)
                .leftJoin(TbWarehouse.class, TbWarehouse::getWhId, TbWhGoods::getWhId);
        mpjLambdaWrapper.eq(TbWhGoods::getGoodId, goodId);
        mpjLambdaWrapper.orderByAsc(TbWhGoods::getWhId);
        List<TbWhGoodsDto> whGoodsInfoList = tbWhGoodsMapper.selectJoinList(TbWhGoodsDto.class, mpjLambdaWrapper);
        /*
         * ???????????????????????????,????????????????????????
         * ????????????,??????????????????????????????
         */
        if (whGoodsInfoList != null && whGoodsInfoList.size()>0) {
            Map<Long, TbWhGoodsVo> map = new LinkedHashMap<>();
            for (TbWhGoodsVo vo : whList) {
                vo.setGoodId(goodId);
                map.put(vo.getWhId(), vo);
            }
            for (TbWhGoodsDto whGoods : whGoodsInfoList) {
                if (map.containsKey(whGoods.getWhId())){
                    TbWhGoodsVo tbWhGoodsVo = map.get(whGoods.getWhId());
                    BeanUtil.copyProperties(whGoods, tbWhGoodsVo, CopyOptions.create().setIgnoreNullValue(true));
                    map.put(whGoods.getWhId(), tbWhGoodsVo);
                }
            }
            ArrayList<TbWhGoodsVo> list = new ArrayList<>(map.values());
            return list;
        }
        return whList;
    }

    @Override
    public Object uploadGoodPic(MultipartFile multipartFile) throws IOException {
        FileUtil.checkSize(properties.getMaxSize(), multipartFile.getSize());
        // ??????????????????
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        // ??????????????????
        String type = FileUtil.getFileType(suffix);
        File file = null;
        try {
            // ???uuid???????????????????????????????????????????????????
            String fileName = IdUtil.simpleUUID();
            file = new File( properties.getPath().getPath() + fileName +"." + suffix +  File.separator).getCanonicalFile();
            // ????????????????????????
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    System.out.println("was not successful.");
                }
            }
            // MultipartFile to File
            multipartFile.transferTo(file);
            if(ObjectUtil.isNull(file)){
                throw new BadRequestException("????????????");
            }
            LocalStorage localStorage = new LocalStorage(
                    file.getName(),
                    fileName,
                    suffix,
                    file.getPath(),
                    type,
                    FileUtil.getSize(multipartFile.getSize())
            );
            localStorageMapper.insert(localStorage);
            //return "/api/pic?id=" + localStorage.getId();
            return localStorage.getId();
        } catch (IOException e) {
            FileUtil.del(file);
            throw e;
        }
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
    public void download(List<TbGoodsInfoDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbGoodsInfoDto tbGoodsInfo : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("????????????", tbGoodsInfo.getGCode());
              map.put("????????????", tbGoodsInfo.getGName());
              map.put("??????", tbGoodsInfo.getSpecification());
              map.put("?????????", tbGoodsInfo.getPyCode());
              map.put("????????????", tbGoodsInfo.getManufacturer());
              map.put("??????", tbGoodsInfo.getRemark());
              map.put("?????????", tbGoodsInfo.getBarCode());
              map.put("?????????", tbGoodsInfo.getQrCode());
              map.put("????????????", tbGoodsInfo.getType());
              map.put("??????", tbGoodsInfo.getModel());
              map.put("??????", tbGoodsInfo.getWeight());
              map.put("??????", tbGoodsInfo.getColor());
              map.put("????????????", tbGoodsInfo.getUnit());
              map.put("??????", tbGoodsInfo.getPurchasePrice());
              map.put("??????", tbGoodsInfo.getSellPrice());
              map.put("????????????", tbGoodsInfo.getUpperLimit());
              map.put("????????????", tbGoodsInfo.getLowerLimit());
              map.put("??????", tbGoodsInfo.getAllocation());
              map.put("????????????", tbGoodsInfo.getPic());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
