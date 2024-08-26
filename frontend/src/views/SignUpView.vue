<template>
  <div class="loginback">
    <div id="backToLogin">
      <el-page-header id="forgey-header" @back="returnToLogin">
      </el-page-header>
    </div>

    <div class="login">

      <div class="ring">
        <div class="circle" >
          <i style="--clr:#7fd283;" ></i>
          <i style="--clr:#ea598a;" ></i>
          <i style="--clr:#b4b252;" ></i>
        </div>
        <div class="login">
          <h2>S&#9;i&#9;g&#9;n&#9;u&#9;p</h2>
          <div class="inputBx">
            <input v-model="username" type="text" placeholder="Username">
          </div>
          <div class="inputBx">
            <input v-model="email" type="text" placeholder="Email">
          </div>
          <div class="inputBx">
            <input v-model="password" type="text" placeholder="Password">
          </div>
          <div class="inputBx">
            <input v-model="passwordCheck" type="text" placeholder="CheckPassword">
          </div>
          <div class="inputBx">
            <input type="submit" value="Finish" @click="jumpToLogin">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/http";
import {ElMessage, ElMessageBox, ElNotification} from 'element-plus'
export default {
  name: "SignUpView",
  data(){
    return{
      username:'',
      email:'',
      password: '',
      passwordCheck: '',
      checkKey:'',
      haveRepeatName:'',
      emailUsed:'',
    }
  },
  methods: {
    returnToLogin(){
      this.$router.push('/login');
    },
    jumpToLogin(){
      console.log(this.username,this.email,this.password,this.passwordCheck);
      if(this.username===''){
        this.alert('Invalid Name')
      }
      //验证用户名的重复性
      http.get(`/checkUsername/${this.username}`)
          .then(response => {
            this.haveRepeatName=response.data.toString();
            if(this.haveRepeatName==='repeat!'){
              this.alert('Repeat Name');
            }
            else {
              if (!this.email.match(/[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/)) {
                this.alert('Illegal Email');
              } else if (this.password === '') {
                this.alert('Invalid Password!');
              } else if (this.passwordCheck !== this.password) {
                this.alert('Confirm Your Password Again!');
              } else {
                //邮箱是否已经使用
                http.get(`/checkEmailUsed/${this.email}`)
                    .then(response => {
                      this.emailUsed = response.data;
                      if(this.emailUsed==="well"){
                        //验证邮箱的有效性
                        http.get(`/checkEmail/${this.email}`)
                            .then(response => {
                              this.checkKey = response.data.toString();
                              ElMessageBox.prompt('Please input your key we send to you', 'Tip', {
                                confirmButtonText: 'OK',
                                cancelButtonText: 'Cancel',
                                inputErrorMessage: '',
                                inputValidator: (value) => {
                                  // 验证用户输入的内容
                                  if (!value) {
                                    return "input your key";
                                  }
                                  if ((value + '') !== this.checkKey) {
                                    console.log(value, this.checkKey);
                                    return "error key";
                                  }
                                  // 如果验证通过,返回 null 表示验证成功
                                  return true;
                                }
                              }).then(({value}) => {
                                //提交用户注册
                                http.post('/userInsert',
                                    {
                                      username: this.username,
                                      email: this.email,
                                      password: this.password,
                                    }).catch(error => {
                                  ElNotification.error({
                                    title: 'Error',
                                    message: 'Register Fail!',
                                  })
                                });

                                ElNotification.success({
                                  title: 'Success',
                                  message: 'Register Success!',
                                })
                                this.$router.push("/login");
                              }).catch(() => {
                                ElMessage({
                                  type: 'info',
                                  message: 'Input canceled',
                                })
                              })
                              console.log(this.checkKey + typeof this.checkKey);
                            }).catch(error => {
                          console.error(error);
                        });

                      }
                      else {
                        this.alert("Used Email!");
                      }
                      console.log(this.emailUsed);
                    }).catch(error => {
                  console.error(error);
                });
              }
            }
          }).catch(error => {
        console.error(error);
      });
    },
    alert(error){
      ElMessageBox.alert(
          error,
          'info',
          {
            confirmButtonText: 'OK',
            type: "error",
          }
      )
    },
  }

}
</script>

<style scoped>
body {
  overflow: hidden;
}

.loginback {
  background: #5c6752;

}
#backToLogin {
  position: absolute;
  font-size: 25px;
  color: #ffffff;
  margin-left: 10px;
}

.login {
  top:0px;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;

  width: 100%;
  overflow: hidden;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Quicksand", sans-serif;
}

.ring {
  position: relative;
  margin-top: -50px;
  width: 500px;
  height: 500px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.circle {
  position: relative;
  top:-300px;
  left: -50px;
  z-index: 0;
}

.circle i {
  position: absolute;
  width: 600px;
  height: 600px;
  inset: 0;
  border: 2px solid #b4ea7b;
  transition: 0.5s;
  z-index: 100;
}
.circle i:nth-child(1) {
  border-radius: 38% 62% 63% 37% / 41% 44% 56% 59%;
  animation: animate 6s linear infinite;
}
.circle i:nth-child(2) {
  border-radius: 41% 44% 56% 59%/38% 62% 63% 37%;
  animation: animate 5s linear infinite;
}
.circle i:nth-child(3) {
  border-radius: 41% 44% 56% 59%/38% 62% 63% 37%;
  animation: animate2 10s linear infinite;
}
.circle:hover  i {
  border: 6px solid var(--clr);
  transform: scale(1.1);
  filter: drop-shadow(0 0 20px var(--clr));
}
@keyframes animate {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
@keyframes animate2 {
  0% {
    transform: rotate(360deg);
  }
  100% {
    transform: rotate(0deg);
  }
}
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  background: transparent;
  margin-top: -55px;
  gap: 20px;
}
@media (max-width: 1000px) {
  .login {
    margin-top: -130px;
  }
}
.login h2 {
  font-size: 2em;
  color: #ccc7c7;
}
.login .inputBx {
  position: relative;
  width: 100%;
}
.login .inputBx input {
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
.login .inputBx input[type="submit"] {
  width: 100%;
  background: #0078ff;
  background: linear-gradient(45deg, #f54e87, #c5bb5b);
  border: none;
  cursor: pointer;
}
.login .inputBx input::placeholder {
  color: rgba(255, 255, 255, 0.75);
}
.login .links {
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}
.login .links a {
  color: #fff;
  text-decoration: none;
}


</style>