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
          <el-input placeholder="搜索订单" v-model="searchorder.id" clearable @clear="getUserList">
            <!-- <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button> -->
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="searchOrder">搜索</el-button>
        </el-col>
      </el-row>
   

    <!-- 用户列表区域 -->
      <el-table :data="orderlist" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="订单ID" prop="id"></el-table-column>
        <el-table-column label="价格" prop="price"></el-table-column>
        <el-table-column label="日期" prop="date"></el-table-column>
        <el-table-column label="状态" prop="state"></el-table-column>
        <el-table-column label="餐厅ID" prop="restaurant_id"></el-table-column>
        <el-table-column label="菜品ID" prop="dish_id_list"></el-table-column>
        <el-table-column label="顾客ID" prop="customer_id"></el-table-column>
        <el-table-column label="操作" width="180px">
        <template >
            <!-- 修改按钮 -->
            <el-tooltip effect="dark" content="修改" placement="top" :enterable="false">
               <el-button type="warning" icon="el-icon-edit" size="mini" @click="addDialogVisible = true"></el-button>
            </el-tooltip>
            
            <!-- 删除按钮 -->
            <el-tooltip effect="dark" content="删除" placement="top" :enterable="false">
               <el-button type="danger" icon="el-icon-delete" size="mini"></el-button>
            </el-tooltip>          
          </template> 
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
           <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum" :page-sizes="[1, 2, 5, 10]" :page-size="queryInfo.pagesize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-card>
    <el-dialog title="修改订单状态" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="updateForm" :rules="updateFormRules" ref="updateFormRef" label-width="70px">
        <el-form-item label="订单ID" prop="id">
          <el-input v-model="updateForm.id"></el-input>
        </el-form-item>
        <el-form-item label="订单状态" prop="state">
          <el-input v-model="updateForm.state"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateOrder">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data(){
    return {
      searchorder:{
        id:''
      },
      updateForm: {
        id:"",
	      state:""
      },
       addDialogVisible: false,
        updateFormRules: {
        name: [
          { required: true, message: '订单ID', trigger: 'blur' },
          {
            min: 1,
            max: 10,
            message: '用户名的长度在1~10个字符之间',
            trigger: 'blur'
          }
        ],
        address: [
          { required: true, message: '订单状态', trigger: 'blur' },
          {
            min: 1,
            max: 1,
            message: '数字为1/2/3',
            trigger: 'blur'
          }
        ],
      },
      queryInfo:{
          query: '',
        // 当前的页数
        pagenum: 1,
        // 当前每页显示多少条数据
        pagesize: 2
      },
      orderlist:[
        {
            "id": 1,
            "price": 300,
            "date": "2020-06-23 09:40:54",
            "state": 1,
            "restaurant_id": 1,
            "dish_id_list": "1,6",
            "customer_id": 2
        }
      ],
      
      
    }
  },
  created(){
    this.getOrderList()
  },
  methods:{
    getOrderList(){
      this.$axios.get('api/order/allOrder', {
        params: this.queryInfo
      }).then(res=>{
                console.log(res);
                 this.orderlist=res.data.data
                 console.log(this.orderlist)     
           })
    },
     searchOrder(){
      this.$axios.post('api/order/infoById', this.searchorder).then(res=>{
                console.log(res);
                 this.orderlist=res.data
                   
           })
    },
     updateOrder(){
       this.$axios.post('api/order/updateState', this.updateForm).then(res=>{
                console.log(res);
                  if (res.status !== 200) return this.$message.error('修改失败！')
                  this.$message.success('修改成功')
                  this.addDialogVisible = false
                  this.getOrderList()
           })
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

