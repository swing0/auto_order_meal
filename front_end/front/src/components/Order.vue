<template>
  <div>
     <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
    </el-breadcrumb>

 <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="搜索订单" v-model="queryInfo.query" clearable @clear="getUserList">
            <!-- <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button> -->
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" >搜索</el-button>
        </el-col>
      </el-row>
   

    <!-- 用户列表区域 -->
      <el-table :data="shangjialist" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="价格" prop="name"></el-table-column>
        <el-table-column label="日期" prop="address"></el-table-column>
        <el-table-column label="状态" prop="phone"></el-table-column>
        <el-table-column label="餐厅ID" prop="classificat"></el-table-column>
        <el-table-column label="菜品ID" prop="scroing_times"></el-table-column>
        <el-table-column label="顾客ID" prop="total_scroe"></el-table-column>
        <el-table-column label="操作" width="180px">
        <template >
            <!-- 修改按钮 -->
            <el-tooltip effect="dark" content="修改" placement="top" :enterable="false">
               <el-button type="warning" icon="el-icon-edit" size="mini"></el-button>
            </el-tooltip>
            
            <!-- 删除按钮 -->
            <el-tooltip effect="dark" content="删除" placement="top" :enterable="false">
               <el-button type="danger" icon="el-icon-delete" size="mini"></el-button>
            </el-tooltip>
            
            <!-- 下单按钮 -->
            <el-tooltip effect="dark" content="提交订单" placement="top" :enterable="false">
               <el-button type="primary" icon="iconfont icon-danju" size="mini"></el-button>
            </el-tooltip>           
          </template> 
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
           <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum" :page-sizes="[1, 2, 5, 10]" :page-size="queryInfo.pagesize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-card>
  </div>
</template>
<script>
export default {
  data(){
    return {
      queryInfo:{
          query: '',
        // 当前的页数
        pagenum: 1,
        // 当前每页显示多少条数据
        pagesize: 2
      },
      orderlist:[
        {
         
        }
      ],
      
      
    }
  },
  created(){
    this.getUserList()
  },
  methods:{
    getUserList(){

    },
   // 监听 pagesize 改变的事件
    handleSizeChange(newSize) {
      console.log(newSize)
      this.queryInfo.pagesize = newSize
      this.getUserList()
    },
    // 监听 页码值 改变的事件
    handleCurrentChange(newPage) {
      // this.queryInfo.pagenum = 10
      // this.getUserList()
    },
  }
}
</script>

