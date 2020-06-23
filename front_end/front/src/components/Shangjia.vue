<template>
  <div>
     <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商家列表</el-breadcrumb-item>
    </el-breadcrumb>

 <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="搜索商家" v-model="queryInfo.query" clearable @clear="getUserList">
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
        <el-table-column label="商家" prop="name"></el-table-column>
        <el-table-column label="地址" prop="address"></el-table-column>
        <el-table-column label="电话" prop="phone"></el-table-column>
        <el-table-column label="菜品" >
           <template>
              <el-tree :data="dishData" :props="defaultProps" @node-click="handleNodeClick">
              </el-tree>
          </template>
        </el-table-column>
        <el-table-column label="评分次数" prop="scroing_times"></el-table-column>
        <el-table-column label="评分得分" prop="total_scroe"></el-table-column>
        <el-table-column label="操作" width="180px">
        <template >
           <button>
           </button>        
          </template> 
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default {
  data(){
    return {
      queryInfo:{
          type:3,
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
      shangjialist:[
        {
         id:1,
         name:'东北老院子',
         address:'槐荫区经七路778号',
         phone:'13589100788',
         classificat:'东北菜',
         scroing_times:3,
         total_scroe:12
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
  },
}
</script>

