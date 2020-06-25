<template>
  <div>
     <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>

 <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入用户账号" v-model="searchuser.account" clearable @clear="getUserList">
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="searchUser">搜索用户</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
        </el-col>
      </el-row>   

    <!-- 用户列表区域 -->
      <el-table :data="userlist" border stripe style="width: 100%" >
        <el-table-column type="index" width="60px"></el-table-column>
        <el-table-column label="用户ID" prop="id"  width="60px"></el-table-column>
        <el-table-column label="用户名" prop="nickname"  width="120px"></el-table-column>
        <el-table-column label="账号" prop="account"  width="120px"></el-table-column>
        <el-table-column label="密码" prop="password"  width="120px"></el-table-column>
        <el-table-column label="手机" prop="phone"  width="120px"></el-table-column>
        <el-table-column label="地址" prop="address"  width="350px"></el-table-column>
        <el-table-column label="操作" >
        <template slot-scope="scope">
          <!-- 修改按钮 -->
          <el-tooltip effect="dark" content="编辑" placement="top" :enterable="false">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleClick(scope.row)">编辑用户</el-button>
          </el-tooltip>
            <!-- 删除按钮
          <el-tooltip effect="dark" content="删除" placement="top" :enterable="false">
            <el-button type="danger" icon="el-icon-delete" size="mini"></el-button>
          </el-tooltip> -->    
          </template> 
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination 
      layout="total" 
      :total="total">
      </el-pagination>
    </el-card>
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed=true">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px">
        <el-form-item label="用户名：" prop="nickname">
          <el-input v-model="addForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="用户账号：" prop="account">
          <el-input v-model="addForm.account"></el-input>
        </el-form-item>
        <el-form-item label="用户密码：" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="用户电话：" prop="phone">
          <el-input v-model="addForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="用户地址：" prop="address">
          <el-input v-model="addForm.address"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="adduser">确 定</el-button>
      </span>
    </el-dialog>
      <el-dialog title="更新用户" :visible.sync="updateDialogVisible" width="50%" @close="updateDialogClosed=true">
      <!-- 内容主体区域 -->
      <el-form :model="updateForm" :rules="addFormRules" ref="updateFormRef" label-width="100px">
        <el-form-item label="用户ID：" prop="id">
          <el-input v-model="updateForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户名：" prop="nickname">
          <el-input v-model="updateForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="用户账号：" prop="account">
          <el-input v-model="updateForm.account"></el-input>
        </el-form-item>
        <el-form-item label="用户密码：" prop="password">
          <el-input v-model="updateForm.password"></el-input>
        </el-form-item>
        <el-form-item label="用户电话：" prop="phone">
          <el-input v-model="updateForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="用户地址：" prop="address">
          <el-input v-model="updateForm.address"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateuser">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data(){
    return {
      searchuser: {
        account: ''
      },
      total:0,
      addForm: {
        
        nickname: '',
        account: '',
        password: '',
        phone: '',
        address:'',
      },
       updateForm: {
        id: '',
        nickname: '',
        account: '',
        password: '',
        phone: '',
        address: ''
      },
      addDialogVisible: false,
      updateDialogVisible: false,
      addDialogClosed: false,
      addDialogClosed: false,
      dishlist: [
        {
          id: 0,
          score: 10,
          prise: 100,
          dish: '鱼香肉丝',
          phone: '1231412',
          address: '啦啦啦'
          }
      ],
      userlist:[
        {
          id: 1,
          nickname: '小蓝',
          account: '111',
          password: '111',
          phone: '15365247896',
          address: '海淀区龙翔路15号北京辰茂鸿翔酒店内二层（近国安剧院）'
         }
      ],
      addFormRules: {
        nickname: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 1,
            max: 10,
            message: '用户名的长度在1~10个字符之间',
            trigger: 'blur'
          }
        ],
        account: [
          { required: true, message: '请输入用户账号', trigger: 'blur' },
          {
            min: 3,
            max: 15,
            message: '用户账号的长度在3~15个字符之间',
            trigger: 'blur'
          }
        ],
        password: [
          { required: true, message: '请输入用户密码', trigger: 'blur' },
          { min: 3,
            max: 15,
            message: '用户密码的长度在3~15个字符之间',
            trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { min: 1,
            max: 10,
            message: '手机号的长度在1~10个字符之间',
            trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' },
          { min: 1,
            max: 10,
            message: '地址的长度在1~10个字符之间',
            trigger: 'blur' }
        ]
      },
      msg: {
        code: '',
        data: '',
        msg: ''
      }
    }
  },
  created(){
    this.getUserList()
  },
  methods: {
    handleClick(row){
      this.updateForm=row
      console.log(this.updateForm)
      this.updateDialogVisible = true
    },
    updateuser() {
       this.$axios.post('api/user/update', this.updateForm).then(res=> {
                console.log(res)
                if (res.status !== 200) return this.$message.error('添加失败！')
                this.$message.success('添加成功')
                this.updateDialogVisible = false
                this.getUserList()
           })
    },
    searchUser(){
      this.$axios.post('api/user/infoByAccount', this.searchuser).then(res=>{
                console.log(res);
                this.msg=res.data
                delete this.msg.code
                delete this.msg.msg
                this.userlist=this.msg                 
           })
    },
    getUserList(){
      this.$axios.get('api/user/allUser').then(res=>{
                // console.log(res)
                this.userlist=res.data.data
                this.total=Object.values(this.userlist).length
           })
    },
     adduser(){
       this.$axios.post('api/user/register', this.addForm).then(res=>{
                console.log(res);
                  if (res.status !== 200) return this.$message.error('添加失败！')
                  this.$message.success('添加成功')
                  this.addDialogVisible = false
                  this.getUserList()
           })
    },
    
    submit(){
      this.$router.push('/submit')
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