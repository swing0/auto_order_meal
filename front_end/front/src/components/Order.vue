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
          <el-input placeholder="请输入订单ID" v-model="searchorder.id" clearable @clear="getOrderList()">
            <!-- <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button> -->
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="searchOrder">搜索</el-button>
        </el-col>
      </el-row>
   

    <!-- 用户列表区域 -->
      <el-table :data="orderlist" border stripe>
        <el-table-column type="index" width="100px"></el-table-column>
        <el-table-column label="订单ID" prop="id" width="100px"></el-table-column>
        <el-table-column label="价格" prop="price"></el-table-column>
        <el-table-column label="日期" prop="date" width="150px"></el-table-column>
        <el-table-column label="状态" prop="state"></el-table-column>
        <el-table-column label="餐厅ID" prop="restaurant_id"></el-table-column>
        <el-table-column label="菜品ID" prop="dish_id_list"></el-table-column>
        <el-table-column label="顾客ID" prop="customer_id"></el-table-column>
        <el-table-column label="操作">
        <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-tooltip effect="dark" content="编辑" placement="top" :enterable="false">
               <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleClick(scope.row)">编辑订单</el-button>
            </el-tooltip>  
          </template> 
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
           <el-pagination layout="total" :total="total">
      </el-pagination>
    </el-card>
    <el-dialog title="修改订单状态" :visible.sync="updateDialogVisible" width="50%" @close="updateDialogClosed=true">
      <!-- 内容主体区域 -->
      <el-form :model="updateForm" :rules="updateFormRules" ref="updateFormRef" label-width="70px">
        <el-form-item label="订单ID" prop="id">
          <el-input v-model="updateForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="订单价格" prop="price">
          <el-input v-model="updateForm.price"></el-input>
        </el-form-item>
        <el-form-item label="订单日期" prop="date">
          <el-input v-model="updateForm.date"></el-input>
        </el-form-item>
        <el-form-item label="订单状态" prop="state">
          <el-input v-model="updateForm.state" placeholder="1、未付款 2、已付款,未发货 3、已发货,未确认 4、交易成功 5、交易关闭 6、已评分"></el-input>
        </el-form-item>
        <el-form-item label="饭店ID" prop="restaurant_id">
          <el-input v-model="updateForm.restaurant_id"></el-input>
        </el-form-item>
        <el-form-item label="菜品ID" prop="dish_id_list">
          <el-input v-model="updateForm.dish_id_list"></el-input>
        </el-form-item>
        <el-form-item label="顾客ID" prop="customer_id">
          <el-input v-model="updateForm.customer_id"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogVisible = false">取 消</el-button>
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
        id: '',
        price: '',
        date: '',
        state: '',
        restaurant_id: '',
        dish_id_list: '',
        customer_id: ''
      },
       updateDialogVisible: false,
       updateDialogClosed:false,
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
      total:0,
      orderlist:[
        {
            id: 1,
            price: 300,
            date: "2020-06-23 09:40:54",
            state: 1,
            restaurant_id: 1,
            dish_id_list: "1,6",
            customer_id: 2
        }
      ],
         msg:{
        "code":'',
        "data":'',
        "msg":''
      },
      
    }
  },
  created(){
    this.getOrderList()
  },
  methods:{
    handleClick(row){
      this.updateForm=row
      console.log(this.updateForm)
      this.updateDialogVisible = true
    },
    getOrderList(){
      this.$axios.get('api/order/allOrder', ).then(res=>{
                console.log(res);
                 this.orderlist=res.data.data
                 this.total=Object.values(this.orderlist).length    
           })
    },
     searchOrder(){
      this.$axios.post('api/order/infoById', this.searchorder).then(res=>{
                console.log(res);
                this.msg=res.data
                delete this.msg.code
                delete this.msg.msg
                this.orderlist=this.msg 
           })
    },
     updateOrder(){
       this.$axios.post('api/order/updateState', this.updateForm).then(res=>{
                console.log(res);
                  if (res.status !== 200) return this.$message.error('修改失败！')
                  this.$message.success('修改成功')
                  this.updateDialogVisible = false
                  this.getOrderList()
           })
    }
  }
}
</script>

