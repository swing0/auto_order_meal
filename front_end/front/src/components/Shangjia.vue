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
          <el-input placeholder="搜索商家" v-model="searchres.name" clearable @clear="getUserList">
            <!-- <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button> -->
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="searchRes">搜索</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加商家</el-button>
        </el-col>
      </el-row>
   

    <!-- 用户列表区域 -->
      <el-table :data="shangjialist" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="商家ID" prop="id"></el-table-column>
        <el-table-column label="商家" prop="name"></el-table-column>
        <el-table-column label="地址" prop="address"></el-table-column>
        <el-table-column label="电话" prop="phone"></el-table-column>
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
           <!-- 下单按钮 -->
            <el-tooltip effect="dark" content="添加菜品" placement="top" :enterable="false">
               <el-button type="primary" icon="iconfont icon-danju" size="mini"> </el-button>
            </el-tooltip>         
          </template> 
        </el-table-column>
      </el-table>
    </el-card>
      <!-- 添加用户的对话框 -->
    <el-dialog title="添加商家" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="商家名" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="商家地址" prop="address">
          <el-input v-model="addForm.address"></el-input>
        </el-form-item>
        <el-form-item label="商家电话" prop="phone">
          <el-input v-model="addForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="商家类别" prop="classification">
          <el-input v-model="addForm.classification"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRes">确 定</el-button>
      </span>
    </el-dialog>
     <el-dialog title="更新商家" :visible.sync="updateDialogVisible" width="50%" @close="updateDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="updateForm" :rules="addFormRules" ref="updateFormRef" label-width="100px">
        <el-form-item label="商家ID：" prop="id">
          <el-input v-model="updateForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="商家名：" prop="name">
          <el-input v-model="updateForm.name"></el-input>
        </el-form-item>
        <el-form-item label="商家地址：" prop="address">
          <el-input v-model="updateForm.address"></el-input>
        </el-form-item>
        <el-form-item label="商家电话：" prop="phone">
          <el-input v-model="updateForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="菜品类别：" prop="classification">
          <el-input v-model="updateForm.classification"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRes">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data(){
    return {
      searchres:{
        name:''
      },
     addForm: {
        name: '',
        address: '',
        phone: '',
        classification: ''
      },
       updateForm: {
         id:'',
         name: '',
         address: '',
         phone: '',
         classification: ''
      },
      queryInfo:{
          type:3,
          query: '',
        // 当前的页数
        pagenum: 1,
        // 当前每页显示多少条数据
        pagesize: 5
      },
      total:1,
      // 控制添加商家对话框的显示与隐藏
      addDialogVisible: false,
      updateDialogVisible: false,
     
      shangjialist:[
        {
          address:'槐荫区经七路778号',
          classification:'东北菜',
          id:1,
          name:'东北老院子',
          phone:'13589100788',
          scoring_times:3,
          total_score:12,
        }
      ],
       // 添加表单的验证规则对象
      addFormRules: {
        name: [
          { required: true, message: '请输入商家名', trigger: 'blur' },
          {
            min: 1,
            max: 10,
            message: '用户名的长度在1~10个字符之间',
            trigger: 'blur'
          }
        ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' },
          {
            min: 3,
            max: 15,
            message: '用户名的长度在3~15个字符之间',
            trigger: 'blur'
          }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          {  min: 3,
            max: 15,
            message: '手机号的长度在3~15个字符之间',
            trigger: 'blur' }
        ],
        classification: [
          { required: true, message: '请输入菜品类别', trigger: 'blur' },
          { min: 1,
            max: 10,
            message: '菜品类别的长度在1~10个字符之间',
            trigger: 'blur' }
        ]
      }
    }
  },
  created(){
    this.get_res()
  },
  methods:{
     handleClick(row){
      this.updateForm=row
      console.log(this.updateForm)
      this.updateDialogVisible = true
    },
    get_res(){
      this.$axios.get('api/restaurant/info', {
        params: this.queryInfo
      }).then(res=>{
                console.log(res);
                  // if (res.status !== 200) return this.$message.error('获取失败！')
                  // this.$message.success('获取成功')
                  console.log(res)
                 this.shangjialist=res.data.data
                 console.log(this.shangjialist)
                
                 
           })
    },
    addRes(){
       this.$axios.post('api/restaurant/register', this.addForm).then(res=>{
                console.log(res);
                  if (res.status !== 200) return this.$message.error('添加失败！')
                  this.$message.success('添加成功')
                  this.addDialogVisible = false
                  this.get_res()
           })
    },
     updateRes(){
       this.$axios.post('api/restaurant/update', this.updateForm).then(res=>{
                console.log(res);
                  if (res.status !== 200) return this.$message.error('修改失败！')
                  this.$message.success('修改成功')
                  this.updateDialogVisible = false
                  this.get_res()
           })
    },
     searchRes(){
       this.$axios.post('api/restaurant/infoByName', this.searchres).then(res=>{
                console.log(res);
                  if (res.status !== 200) return this.$message.error('查询失败！')
                  this.$message.success('查询成功')
                  this.shangjialist=res.data
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
  },
}
</script>

