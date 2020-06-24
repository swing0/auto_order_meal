<template>
<div>
     <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>菜品列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
        <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="搜索菜品" v-model="searchdish.name"   @clear="getUserList">
            <!-- <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button> -->
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="searchDish">搜索</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加菜品</el-button>
        </el-col>
      </el-row>
      <!-- 用户列表区域 -->
      <el-table :data="dishlist" border stripe>
        <el-table-column type="index"></el-table-column>
         <el-table-column label="菜品ID" prop="id"></el-table-column>
        <el-table-column label="菜品" prop="name"></el-table-column>
        <el-table-column label="价格" prop="price"></el-table-column>
        <el-table-column label="类型" prop="classification"></el-table-column>
        <el-table-column label="评价" prop="cuisine"></el-table-column>
        <el-table-column label="图片" ></el-table-column>
        <el-table-column label="餐厅ID" prop="restaurant_id"></el-table-column>
        <el-table-column label="销量" prop="sales_volume"></el-table-column>
        <el-table-column label="评分次数"  prop="scoring_times"></el-table-column>
        <el-table-column label="评分得分"  prop="total_score"></el-table-column>
        <el-table-column label="操作" width="180px">
        <template slot-scope="scope">
           <!-- 修改按钮 -->
          <el-tooltip effect="dark" content="编辑" placement="top" :enterable="false">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleClick(scope.row)"></el-button>
          </el-tooltip>
            <!-- 删除按钮 -->
          <el-tooltip effect="dark" content="删除" placement="top" :enterable="false">
            <el-button type="danger" icon="el-icon-delete" size="mini"></el-button>
          </el-tooltip>         
          </template> 
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加用户的对话框 -->
    <el-dialog title="添加菜品" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="菜品ID" prop="id">
          <el-input v-model="addForm.id"></el-input>
        </el-form-item>
        <el-form-item label="菜品" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="addForm.price"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="classification">
          <el-input v-model="addForm.classification"></el-input>
        </el-form-item>
        <el-form-item label="评价" prop="cuisine">
          <el-input v-model="addForm.cuisine"></el-input>
        </el-form-item>
        <el-form-item label="餐厅ID" prop="restaurant_id">
          <el-input v-model="addForm.restaurant_id"></el-input>
        </el-form-item>
        <el-form-item label="sales_volume" prop="sales_volume">
          <el-input v-model="addForm.sales_volume"></el-input>
        </el-form-item>
        <el-form-item label="评分次数" prop="scoring_times">
          <el-input v-model="addForm.scoring_times"></el-input>
        </el-form-item>
        <el-form-item label="评分得分" prop="total_score">
          <el-input v-model="addForm.total_score"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updatedish">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="更新菜品" :visible.sync="updateDialogVisible" width="50%" @close="updateDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="updateForm"  ref="updateFormRef" label-width="100px">
        <el-form-item label="菜品ID" prop="id">
          <el-input v-model="updateForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="菜品" prop="name">
          <el-input v-model="updateForm.name"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="updateForm.price"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="classification">
          <el-input v-model="updateForm.classification"></el-input>
        </el-form-item>
        <el-form-item label="评价" prop="cuisine">
          <el-input v-model="updateForm.cuisine"></el-input>
        </el-form-item>
        <el-form-item label="餐厅ID" prop="restaurant_id">
          <el-input v-model="updateForm.restaurant_id"></el-input>
        </el-form-item>
        <el-form-item label="sales_volume" prop="sales_volume">
          <el-input v-model="updateForm.sales_volume"></el-input>
        </el-form-item>
        <el-form-item label="评分次数" prop="scoring_times">
          <el-input v-model="updateForm.scoring_times"></el-input>
        </el-form-item>
        <el-form-item label="评分得分" prop="total_score">
          <el-input v-model="updateForm.total_score"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updatedish">确 定</el-button>
      </span>
    </el-dialog>
</div>
</template>
<script>
export default {
    data(){
        return {
          searchdish:{
            name:''
          },
                addForm: {
                      classification:"",
                      cuisine:"",
                      image:'',
                      name:"",
                      price:'',
                      restaurant_id:'',
                      sales_volume:"",
                      scoring_times:'',
                      total_score:''
            },
             updateForm: {
               classification:"",
                cuisine:"",
                id:"",
                name:"",
                price:'',
                restaurant_id:'',
                sales_volume:"",
                scoring_times:'',
                total_score:""
      },
            addDialogVisible: false,
            updateDialogVisible: false,
            dishlist:[
                {
                classification:"炒菜",
                cuisine:"干、辣、好吃",
                id:1,
                image:"https://p1.meituan.net/waimaipoi/09704589a23e35a65116e9bc5b69207f290816.jpg@380w_214h_1e_1c",
                name:"干锅牛肉",
                price:25,
                restaurant_id:1,
                sales_volume:"13",
                scoring_times:5,
                total_score:12}
            ],
            addFormRules: {
        name: [
          { required: true, message: '请输入菜品', trigger: 'blur' },
          {
            min: 1,
            max: 10,
            message: '用户名的长度在1~10个字符之间',
            trigger: 'blur'
          }
        ],
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' },
          {
            min: 1,
            max: 5,
            message: '用户名的长度在1~5个字符之间',
            trigger: 'blur'
          }
        ],
        classification: [
          { required: true, message: '请输入类型', trigger: 'blur' },
          {  min: 1,
            max: 10,
            message: '手机号的长度在1~10个字符之间',
            trigger: 'blur' }
        ],
        cuisine: [
          { required: true, message: '请输入评价', trigger: 'blur' },
          { min: 1,
            max: 20,
            message: '菜品类别的长度在1~10个字符之间',
            trigger: 'blur' }
        ],
        restaurant_id: [
          { required: true, message: '请输入餐厅ID', trigger: 'blur' },
          { min: 1,
            max: 5,
            message: '菜品类别的长度在1~5个字符之间',
            trigger: 'blur' }
        ],
        sales_volume: [
          { required: true, message: '请输入sales_volume', trigger: 'blur' },
          { min: 1,
            max: 10,
            message: '菜品类别的长度在1~10个字符之间',
            trigger: 'blur' }
        ],
        scoring_times: [
          { required: true, message: '请输入评分次数', trigger: 'blur' },
          { min: 1,
            max: 3,
            message: '菜品类别的长度在1~3个字符之间',
            trigger: 'blur' }
        ],
        total_score: [
          { required: true, message: '请输入评分得分', trigger: 'blur' },
          { min: 1,
            max: 3,
            message: '菜品类别的长度在1~3个字符之间',
            trigger: 'blur' }
        ],
        classification: [
          { required: true, message: '请输入菜品类别', trigger: 'blur' },
          { min: 1,
            max: 10,
            message: '菜品类别的长度在1~10个字符之间',
            trigger: 'blur' }
        ],
      },
      
        }
    },
    created(){
    this.get_dish()
  },
  methods:{
    handleClick(row){
      this.updateForm=row
      console.log(this.updateForm)
      this.updateDialogVisible = true
    },
    searchDish(){
      this.$axios.post('api/dish/infoByName', this.searchdish).then(res=>{
                console.log(res);
                  if (res.status !== 200) return this.$message.error('查询失败！')
                  this.$message.success('查询成功')
                  this.dishlist=res.data.data
           })
    },
      get_dish(){
          this.$axios.get('api/dish/allDishes', {
        params: ''
      }).then(res=>{
                console.log(res);
                  console.log(res)
                 this.dishlist=res.data.data
                 console.log(this.dishlist)
           })
      },
      adddish(){
       this.$axios.post('api/dish/add', this.addForm).then(res=>{
                console.log(res);
                  if (res.status !== 200) return this.$message.error('添加失败！')
                  this.$message.success('添加成功')
                  this.addDialogVisible = false
                  this.get_dish()
           })
       },
      updatedish(){
       this.$axios.post('api/dish/update', this.updateForm).then(res=>{
                console.log(res);
                  if (res.status !== 200) return this.$message.error('修改失败！')
                  this.$message.success('修改成功')
                  this.updateDialogVisible = false
                  this.get_dish()
           })
       },
  }
}
</script>