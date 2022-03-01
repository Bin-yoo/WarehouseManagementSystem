package me.zhengjie.modules.dataImport.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.enums.EnterpriseTypeEnum;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.dataImport.domain.vo.CompanyImportVo;
import me.zhengjie.modules.dataImport.domain.vo.EmployeeImportVo;
import me.zhengjie.modules.dataImport.service.DataImportService;
import me.zhengjie.modules.dataImport.domain.vo.GoodsImportVo;
import me.zhengjie.modules.dataImport.service.mapper.DataImportMapper;
import me.zhengjie.modules.employee.domain.TbEmployee;
import me.zhengjie.modules.employee.service.mapper.TbEmployeeMapper;
import me.zhengjie.modules.goodsinfo.domain.TbGoodsInfo;
import me.zhengjie.modules.goodsinfo.service.mapper.TbGoodsInfoMapper;
import me.zhengjie.modules.goodstype.domain.TbGoodsType;
import me.zhengjie.modules.goodstype.service.mapper.TbGoodsTypeMapper;
import me.zhengjie.modules.goodsunit.domain.TbGoodsUnit;
import me.zhengjie.modules.goodsunit.service.mapper.TbGoodsUnitMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.mapper.TbWhInventoryMapper;
import me.zhengjie.modules.partnercompanyinfo.domain.TbPartnerCompanyInfo;
import me.zhengjie.modules.partnercompanyinfo.service.mapper.TbPartnerCompanyInfoMapper;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.service.mapper.DeptMapper;
import me.zhengjie.modules.system.service.mapper.JobMapper;
import me.zhengjie.modules.tb_wh_goods.domain.TbWhGoods;
import me.zhengjie.modules.tb_wh_goods.service.mapper.TbWhGoodsMapper;
import me.zhengjie.modules.warehouse.domain.TbWarehouse;
import me.zhengjie.modules.warehouse.service.mapper.TbWarehouseMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utlis.PinYinCodeUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.*;
import java.util.function.Supplier;

/**
* @author LiangBin
* @date 2021-12-08
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbEmployeeService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DataImportServiceImpl extends CommonServiceImpl<DataImportMapper, TbEmployee> implements DataImportService {

    // private final RedisUtils redisUtils;
    //private final DataImportMapper dataImportMapper;
    private final TbGoodsTypeMapper tbGoodsTypeMapper;
    private final TbGoodsUnitMapper tbGoodsUnitMapper;
    private final TbGoodsInfoMapper tbGoodsInfoMapper;
    private final TbWarehouseMapper tbWarehouseMapper;
    private final TbWhGoodsMapper tbWhGoodsMapper;
    private final TbWhInventoryMapper tbWhInventoryMapper;
    private final TbPartnerCompanyInfoMapper tbPartnerCompanyInfoMapper;
    private final TbEmployeeMapper tbEmployeeMapper;
    private final DeptMapper deptMapper;
    private final JobMapper jobMapper;

    @Override
    public void getGoodsImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/import/goodsInfo.xls");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLDecoder.decode("货品资料导入模板.xls","UTF-8"));
        response.setContentType("application/msexcel");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @Override
    public void getCompanyImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/import/companyInfo.xls");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLDecoder.decode("往来单位资料导入模板.xls","UTF-8"));
        response.setContentType("application/msexcel");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @Override
    public void getEmployeeImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/import/employeeInfo.xls");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLDecoder.decode("员工资料导入模板.xls","UTF-8"));
        response.setContentType("application/msexcel");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @Override
    public void getInitialGoodsImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/import/InitialGoodInfo.xls");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLDecoder.decode("货品资料导入模板.xls","UTF-8"));
        response.setContentType("application/msexcel");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void goodsImport(MultipartFile file) throws IOException {
        GoodsImportVo goodsImportDto = new GoodsImportVo();
        // 读取DTO类字段名,读取后Excel数据后,方便json转成DTO类
        Field[] fields = goodsImportDto.getClass().getDeclaredFields();
        ArrayList<String> columnNames = new ArrayList<>();
        for (Field field : fields) {
            columnNames.add(field.getName());
        }
        // Excel中提取数据
        List<Map<String, Object>> datalist = FileUtil.uploadExcel(file, columnNames.toArray(new String[columnNames.size()]));
        List<GoodsImportVo> importVoList = JSON.parseArray(JSON.toJSONString(datalist), GoodsImportVo.class);

        Assert.notNull(importVoList, "该Excel表导入的数据为空");

        List<TbGoodsInfo> tbGoodsInfoList = new ArrayList<>();
        for (GoodsImportVo importVo : importVoList) {
            TbGoodsInfo tbGoodsInfo = new TbGoodsInfo();
            BeanUtil.copyProperties(importVo, tbGoodsInfo, CopyOptions.create().setIgnoreCase(true));

            TbGoodsType tbGoodsType = tbGoodsTypeMapper.lambdaQuery().eq(TbGoodsType::getGtName, importVo.getTypeName()).one();
            if (tbGoodsType == null) {
                throw new BadRequestException("货品类型:" + importVo.getTypeName() + "不存在!");
            }
            // 设置货品类型
            tbGoodsInfo.setType(tbGoodsType.getId());

            TbGoodsUnit tbGoodsUnit = tbGoodsUnitMapper.lambdaQuery().eq(TbGoodsUnit::getGuName, importVo.getUnitName()).one();
            if (tbGoodsUnit == null) {
                throw new BadRequestException("货品单位:" + importVo.getTypeName() + "不存在!");
            }
            // 设置货品单位
            tbGoodsInfo.setUnit(tbGoodsUnit.getId());

            // 货品编码为空,自动生成填充
            if (tbGoodsInfo.getGCode() == null) {
                //获取货品类别名称的拼音码
                String typePyCode = PinYinCodeUtil.getPYIndexStr2(tbGoodsType.getGtName(), true);
                QueryWrapper<TbGoodsInfo> wrapper = new QueryWrapper<>();
                wrapper.eq("type", tbGoodsType.getId());
                Integer count = tbGoodsInfoMapper.selectCount(wrapper);
                String format = String.format("%s%05d", typePyCode, count+1);
                tbGoodsInfo.setGCode(format);
            }
            // 货品拼音码为空,自动生成填充
            if (tbGoodsInfo.getPyCode() == null) {
                String namePyCode = PinYinCodeUtil.getPYIndexStr2(importVo.getGName(), true);
                tbGoodsInfo.setPyCode(namePyCode);
            }

            // 进队,等待批量插入
            tbGoodsInfoMapper.insert(tbGoodsInfo);
            tbGoodsInfoList.add(tbGoodsInfo);
        }

        // 批量插入
        //tbGoodsInfoMapper.insertBatchSomeColumn(tbGoodsInfoList);

        List<TbWarehouse> tbWarehouseList = tbWarehouseMapper.selectList(null);

        List<TbWhGoods> tbWhGoodsList = new ArrayList<>();
        List<TbWhInventory> tbWhInventoryList = new ArrayList<>();

        for (TbGoodsInfo tbGoodsInfo : tbGoodsInfoList) {
            for (TbWarehouse tbWarehouse : tbWarehouseList) {
                // 设置仓初信息
                TbWhGoods tbWhGood = new TbWhGoods();
                tbWhGood.setGoodId(tbGoodsInfo.getId());
                tbWhGood.setWhId(tbWarehouse.getWhId());
                tbWhGood.setInitialCount(0);
                tbWhGood.setUpperLimit(0);
                tbWhGood.setLowerLimit(0);
                tbWhGoodsList.add(tbWhGood);

                // 库存信息
                TbWhInventory tbWhInventory = new TbWhInventory();
                tbWhInventory.setGoodId(tbGoodsInfo.getId());
                tbWhInventory.setWhId(tbWarehouse.getWhId());
                tbWhInventory.setCount(0);
                tbWhInventoryList.add(tbWhInventory);
            }
        }
        tbWhGoodsMapper.insertBatchSomeColumn(tbWhGoodsList);
        tbWhInventoryMapper.insertBatchSomeColumn(tbWhInventoryList);
    }

    @Override
    public void companiesImport(MultipartFile file) throws IOException {
        CompanyImportVo companyImportVo = new CompanyImportVo();
        // 读取DTO类字段名,读取后Excel数据后,方便json转成DTO类
        Field[] fields = companyImportVo.getClass().getDeclaredFields();
        ArrayList<String> columnNames = new ArrayList<>();
        for (Field field : fields) {
            columnNames.add(field.getName());
        }
        // Excel中提取数据
        List<Map<String, Object>> datalist = FileUtil.uploadExcel(file, columnNames.toArray(new String[columnNames.size()]));
        List<CompanyImportVo> importVoList = JSON.parseArray(JSON.toJSONString(datalist), CompanyImportVo.class);

        Assert.notNull(importVoList, "该Excel表导入的数据为空");

        for (CompanyImportVo importVo : importVoList) {
            TbPartnerCompanyInfo tbPartnerCompanyInfo = new TbPartnerCompanyInfo();
            BeanUtil.copyProperties(importVo, tbPartnerCompanyInfo, CopyOptions.create().setIgnoreCase(true));
            if (tbPartnerCompanyInfo.getPyCode() == null) {
                String namePyCode = PinYinCodeUtil.getPYIndexStr2(importVo.getCName(), true);
                tbPartnerCompanyInfo.setPyCode(namePyCode);
            }
            if (tbPartnerCompanyInfo.getCCode() == null){
                Integer count = tbPartnerCompanyInfoMapper.selectCount(null);
                String format = String.format("C%05d", count+1);
                tbPartnerCompanyInfo.setCCode(format);
            }
            tbPartnerCompanyInfo.setType(EnterpriseTypeEnum.getCode(importVo.getTypeName()));
            tbPartnerCompanyInfoMapper.insert(tbPartnerCompanyInfo);
        }
    }

    @Override
    public void employeeImport(MultipartFile file) throws IOException {
        EmployeeImportVo employeeImportVo = new EmployeeImportVo();
        // 读取DTO类字段名,读取后Excel数据后,方便json转成DTO类
        Field[] fields = employeeImportVo.getClass().getDeclaredFields();
        ArrayList<String> columnNames = new ArrayList<>();
        for (Field field : fields) {
            columnNames.add(field.getName());
        }
        // Excel中提取数据
        List<Map<String, Object>> datalist = FileUtil.uploadExcel(file, columnNames.toArray(new String[columnNames.size()]));
        List<EmployeeImportVo> importVoList = JSON.parseArray(JSON.toJSONString(datalist), EmployeeImportVo.class);

        Assert.notNull(importVoList, "该Excel表导入的数据为空");

        List<TbEmployee> tbEmployeeList = new ArrayList<TbEmployee>();
        for (EmployeeImportVo importVo : importVoList) {
            TbEmployee tbEmployee = new TbEmployee();
            BeanUtil.copyProperties(importVo, tbEmployee, CopyOptions.create().setIgnoreCase(true));
            Dept dept = deptMapper.lambdaQuery().eq(Dept::getName, importVo.getDeptName()).list().stream().findFirst().orElse(new Dept());
            if (dept.getId() == null) {
                throw new BadRequestException("部门不存在!");
            }
            tbEmployee.setDeptId(dept.getId());
            Job job = jobMapper.lambdaQuery().eq(Job::getName, importVo.getJobName()).list().stream().findFirst().orElse(new Job());
            if (job.getId() == null) {
                throw new BadRequestException("岗位不存在!");
            }
            tbEmployee.setJobId(job.getId());
            if (tbEmployee.getPyCode() == null) {
                String namePyCode = PinYinCodeUtil.getPYIndexStr2(importVo.getName(), true);
                tbEmployee.setPyCode(namePyCode);
            }
            tbEmployeeList.add(tbEmployee);
        }
        tbEmployeeMapper.insertBatchSomeColumn(tbEmployeeList);
    }
}
