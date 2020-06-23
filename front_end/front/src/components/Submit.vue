<template>
<div class="submit_container">
 <div class="submit_order_box " >
        <!-- 面包屑导航区域 -->
            <el-breadcrumb separator-class="el-icon-arrow-right">
              <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>用户点餐</el-breadcrumb-item>
              <el-breadcrumb-item>创建订单</el-breadcrumb-item>
            </el-breadcrumb>
      <!-- 登录表单区域 -->
        <el-form class="submit_order_form " ref="submitformRef" :model="submitform" label-width="80px">
          <!-- 用户名 -->
          <el-form-item  label="订单ID" >
            <el-input disabled v-model="submitform.oerder_id" style="width:300px"></el-input>
          </el-form-item>
           <el-form-item  label="订单总价" >
            <el-input disabled v-model="submitform.order_price" style="width:300px"></el-input>
          </el-form-item>
           <el-form-item  label="订单时间" >
            <el-input disabled v-model="submitform.order_time" style="width:300px"></el-input>
          </el-form-item>
          <el-form-item  label="订单状态" >
            <el-input disabled v-model="submitform.order_state" style="width:300px"></el-input>
          </el-form-item>
          <el-form-item  label="菜品" >
            <el-input disabled style="width:300px" v-model="item.name" v-for="item in dishdata" :key="item.id"></el-input>
          </el-form-item>
          <el-form-item  >
            <el-button-group class="btns">
              <el-button   @click="del">删除菜品</el-button>
              <el-button   @click="add" >添加菜品</el-button>
            </el-button-group>
          </el-form-item>
          <el-form-item  label="饭店ID" >
            <el-input disabled v-model="submitform.order_res_id" style="width:300px"></el-input>
          </el-form-item>
          <el-form-item  label="顾客ID" >
            <el-input disabled v-model="submitform.order_cus_id" style="width:300px"></el-input>
          </el-form-item>
          <el-form-item  >
            <el-button-group class="btns">
              <el-button   @click="cancel" >取消订单</el-button>
              <el-button   @click="submit" >提交订单</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>

  </div>
</div>
</template>
<script>
export default {
  data(){
    return{
      submitform:{
        oerder_id:1,
        order_price:999,
        order_time:'2020.6.19',
        order_state:'未付款',
        order_res_id:111111,
        order_cus_id:222222,
      },
      dishdata:[
        {id:1,name:'鱼香肉丝' },
        {id:1,name:'宫保鸡丁' },
        ],
      orderdishData:[{
        label: '查看菜品',
          children: [{
            label: '鱼香肉丝',
          }, {
            label: '宫保鸡丁',
          }]
        }],
     defaultProps: {
          children: 'children',
          label: 'label'
        }
    }
  },
  methods:{
    del() {
        this.$prompt('请输入要删除的菜品', '删除菜品', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern:'',
        }).then(({ value }) => {
          this.$message({
            type: 'success',
            message: '已删除菜品: ' + value,
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });       
        });
    },
    add() {
        this.$prompt('请输入要删除的菜品', '添加菜品', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern:'',
        }).then(({ value }) => {
          this.$message({
            type: 'success',
            message: '已添加菜品: ' + value,
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });       
        });
    },
     cancel() {
      window.sessionStorage.clear()
      this.$router.push('/welcome')
    },
     submit() {
        this.$message({
          message:"提交成功",
          type:"success"
        })
      window.sessionStorage.clear()
      this.$router.push('/welcome')
    },
  }
}
</script>
<style lang="less" scoped>
.submit_order_form{
    height: 0px;
    width: 10px;
    position: absolute;
    left: 40%;
    transform: translate(-50%, -50%); 
}
.btns {
  width: 250px;

  display: flex;
  justify-content: flex-end;
}
</style>