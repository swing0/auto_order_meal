<template>
<div class="register_container">
  <div class="register_box">
    <el-form ref="register_form" :model="form" :rules="registerFormRules" label-width="80px">
       <el-form-item label="用户名:">
             <el-input prefix-icon="iconfont icon-user" v-model="form.account" label-width="80px"></el-input>
        </el-form-item>
        <el-form-item label="密码:" >
             <el-input prefix-icon="iconfont icon-3702mima" v-model="form.password" label-width="80px"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" class="phone_box" >
          <el-form :inline="true" >
            <el-form-item>
              <el-input prefix-icon="el-icon-mobile" v-model="form.phone" label-width="80px" style="width:230px"></el-input>
            </el-form-item>
              <el-form-item>
              <el-button type="primary" @click="send_code" style="width:120px">{{isRun?`${runTime}s后重新获取`:`获取验证码`}}</el-button>
              </el-form-item>
          </el-form>
        </el-form-item>
        <el-form-item label="验证码:" label-width="80px" >
             <el-input prefix-icon="el-icon-message" v-model="form.code" ></el-input>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" @click="register">注册</el-button>
          <el-button type="info" @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
  </div>
</div>
</template>

<script>
export default {
    data() {
    return {
        isRun:false,
        runTime:30,
        form: {
        account: '',
        password: '',
        phone:'',
        code:'',
        
      },
      registerFormRules: {
        // 验证用户名是否合法
        account: [
          { required: true, message: '请输入登录名称', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        // 验证密码是否合法
        password: [
          { required: true, message: '请输入登录密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { min: 11, max: 11, message: '长度为11个数字', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { min: 6, max: 6, message: '长度为6个数字', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    send_code() {
      if(this.isRun) return;
      this.$message({
          message:"发送成功",
          type:"success"
        })
        this.isRun=true;
        this.autoTimer =setInterval(()=>{
          if(this.runTime===0){
            this.runTime=30;
            this.isRun=false;
            clearInterval(this.autoTimer);
            return;
          }
          this.runTime--;
        },1000);
    },
      register() {
      this.$message({
          message:"注册成功",
          type:"success"
        })
    },
    cancel() {
      // window.sessionStorage.clear()
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="less" scoped>
.register_box {
  width: 450px;
  height: 400px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

</style>