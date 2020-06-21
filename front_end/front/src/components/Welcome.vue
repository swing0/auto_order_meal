<template>
  <div>
     <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户点餐</el-breadcrumb-item>
    </el-breadcrumb>

 <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getUserList">
            <!-- <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button> -->
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" >推荐菜品</el-button>
        </el-col>
      </el-row>
   

    <!-- 用户列表区域 -->
      <el-table :data="dishlist" border stripe style="width: 100%" >
        <el-table-column type="index"></el-table-column>
        <el-table-column label="评分" prop="score"></el-table-column>
        <el-table-column label="价格" prop="prise"></el-table-column>
        <el-table-column label="菜品" >
          <template>
              <el-tree :data="dishData" :props="defaultProps" @node-click="handleNodeClick">
              </el-tree>
          </template>
        </el-table-column>
        <el-table-column label="电话" prop="tel"></el-table-column>
        <el-table-column label="地址" prop="address"></el-table-column>
        <el-table-column label="操作" width="180px">
        <template > 
            <!-- 下单按钮 -->
            <el-tooltip effect="dark" content="提交订单" placement="top" :enterable="false">
               <el-button type="primary" icon="iconfont icon-danju" size="mini" @click="submit"> 提交订单</el-button>
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
      dishData:[{
        label: '查看菜品',
          children: [{
            label: '鱼香肉丝',
          }, {
            label: '宫保鸡丁',
          }]
        }],
      dishlist:[
        {
          id:0,
          score:10,
          prise:100,
          dish:'鱼香肉丝',
          tel:'1234567890',
          address:'啦啦啦'}
      ],
      
      
    }
  },
  created(){
    this.getUserList()
  },
  methods:{
    getUserList(){

    },
    submit(){
      this.$router.push('/submit')
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
<style lang="less" scoped>
.el-table__header tr,
  .el-table__header th {
    padding: 0;
    height: 40px;
}
.el-table__body tr,
  .el-table__body td {
    padding: 0;
    height: 40px;
}

</style>