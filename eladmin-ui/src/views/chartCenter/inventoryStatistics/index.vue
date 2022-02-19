<template>
  <div class="dashboard-container">
    <div class="dashboard-editor-container">
      <!-- <github-corner class="github-corner" /> -->
      <el-row :gutter="32">
        <el-alert :closable="false" type="success">
          <template slot="title">
            <span style="fontSize: 15px;">单据分类占比</span>
          </template>
        </el-alert>
        <el-col :xs="24" :sm="24" :lg="24" class="chart-wrapper">
          <el-col :xs="24" :sm="24" :lg="12">
            <pie-chart />
          </el-col>
          <el-col :xs="24" :sm="24" :lg="12">
            <div>
              <el-table :data="pieChartData.pieData" style="width: 100%; margin-left: 10px;">
                <el-table-column prop="name" width="180" />
                <el-table-column prop="percentage" width="180">
                  <template slot-scope="scope">
                    {{ scope.row.percentage }} %
                  </template>
                </el-table-column>
                <el-table-column prop="value" width="180">
                  <template slot-scope="scope">
                    {{ scope.row.value }} 单
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-col>
        </el-col>
        <!-- <el-col :xs="24" :sm="24" :lg="12">
          <div class="chart-wrapper">
            <pie-chart />
          </div>
        </el-col> -->
      </el-row>
      <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
        <el-alert :closable="false" type="success">
          <template slot="title">
            <span style="fontSize: 15px;">全年出入库数量</span>
          </template>
        </el-alert>
        <line-chart />
      </el-row>
      <!-- <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
        <span>全年销售金额</span>
        <bar-chart />
      </el-row> -->
    </div>
  </div>
</template>

<script>
import LineChart from './LineChart'
// import BarChart from './BarChart'
import PieChart from './PieChart'
import InventoryStatistics from '@/api/inventoryStatistics.js'

export default {
  name: 'InventoryStatistics',
  components: {
    LineChart,
    // BarChart,
    PieChart
  },
  data() {
    return {
      pieChartData: []
    }
  },
  created() {
    this.queryPieChartData()
  },
  methods: {
    queryPieChartData() {
      InventoryStatistics.queryPieChartData().then(res => {
        this.pieChartData = res
      })
    },
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .el-alert{
    .el-alert__content{
      .el-alert__title{
        font-size: 18px !important;
      }
    }
  }
  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    position: relative;

    .github-corner {
      position: absolute;
      top: 0;
      border: 0;
      right: 0;
    }

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }

  @media (max-width:1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>
