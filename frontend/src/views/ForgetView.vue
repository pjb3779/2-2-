<template>
  <div class="forgetback">
    <div class="forget">
      <el-page-header id="forgey-header"  content="Reset Your Password!" @back="returnToLogin">

      </el-page-header>
      <el-steps id="el-steps" ref="elsteps" simple :active="activeStep" align-center=true finish-status="success" >
        <el-step title="输入邮箱" description="第一步">
        </el-step>
        <el-step title="输入代码" description="第二步">
        </el-step>
        <el-step title="修改密码" description="第三步">
        </el-step>
      </el-steps>
      <div class="forget_Inputs">
        <div class="inputBx">
          <input v-show="activeStep===0" v-model="this.email" type="email" placeholder="Your Email">
        </div>
        <div class="inputBx">
          <input v-show="activeStep===1" v-model="this.key" type="text" placeholder="Key">
        </div>
        <div class="inputBx">
          <input v-show="activeStep===2" v-model="this.newPassword" type="text" placeholder="New Password">
        </div>
        <div class="inputBx">
          <input type="submit" :value="this.next" @click="nextStep">
        </div>
      </div>
    </div>

  </div>

</template>

<script>
import {ElMessageBox, ElNotification} from "element-plus";
import http from "@/http";
export default {
  name: "ForgetView",
  data(){
    return{
      email:'',
      key:'',
      newPassword:'',
      activeStep: 0,
      next: '下一步',
      correctKey:'',
    }
  },
  methods: {
    returnToLogin(){
      this.$router.push('/login');
    },
    nextStep(){
      if(this.next=== '完成')this.$router.push('/login');
      if(this.activeStep<3) {
        if(this.activeStep===0){
          //第一步,邮箱验证
          if(this.email===''||!this.email.match(/[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/)){
            //邮箱格式不正确
            this.alert('Illegal Email!')
          }else {
            http.get(`/checkEmailUsed/${this.email}`).then(response=>{
              if(response.data.toString()==='repeat'){
                http.get(`/checkEmail/${this.email}`).then(response=>{
                  this.correctKey=response.data.toString();
                })
                this.activeStep++;
              }else {
                this.alert("Unused Email !")
              }
            })
          }
        }else if(this.activeStep===1){
          //第二步，令牌验证
          if(this.key===this.correctKey){
            ElNotification.success({
              title: 'Success',
              message: 'Key Right!',
            })
            this.activeStep++;
          }else {
            this.alert("Incorrect Key!try again")
          }
        }else if(this.activeStep===2){
          if(this.newPassword===''||!this.newPassword.match("^[A-Za-z\\d]{6,}$")){
            this.alert("Invalid password!")
          }else {
            //第三步，输入新密码，更新用户数据
            http.get(`/updatePassword/${this.email}/${this.newPassword}`)
            this.activeStep++;

          }

        }

      }
      if(this.activeStep===3)this.next = '完成';
    },
    alert(error){
      ElNotification.error({
        title: 'Error',
        message: error,
      })
    },

  }

}
</script>

<style scoped>
.forgetback{
  background: rgba(54, 73, 45, 0.79);
}
.forget {
  top:0px;
  min-height: 100vh;
  width: 100%;
  background: transparent;
  margin-top: -55px;
}
#forgey-header {
  background: white;
  font-size: 30px;
  font-style: oblique;
  padding-left: 20px;
}

.forget_Inputs {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  background: transparent;
  margin-top: 100px;
}
.forget_Inputs .inputBx input{
  position: relative;
  width: 100%;
  padding: 12px 20px;
  background: transparent;
  border: 2px solid #fff;
  border-radius: 40px;
  font-size: 1.2em;
  color: #fff;
  box-shadow: none;
  outline: none;
}
.forget_Inputs .inputBx input[type="submit"] {
  margin-top: 20px;
  width: 100%;
  background: #4279b7;
  cursor: pointer;
  color: #ffffff;
  transition: 0.5s ease-in-out;
}
.forget_Inputs .inputBx input[type="submit"]:hover {
  transform: scale(1.05);
}
.forget_Inputs .inputBx input::placeholder {
  color: rgba(255, 255, 255, 0.75);
}

</style>