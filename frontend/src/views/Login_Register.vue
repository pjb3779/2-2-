<template>
  <div class="loginback">
    <div id="video">
    <video ref="videoElement" autoplay muted loop>
      <source src="../assets/LoginAssets/bg.mp4" type="video/mp4">
    </video>
    </div>
  <div class="login">

    <div class="ring">
      <div class="circle" >
        <i style="--clr:#7fd283;" ></i>
        <i style="--clr:#ea598a;" ></i>
        <i style="--clr:#b4b252;" ></i>
      </div>
      <div class="login">
        <h2>L&#9;o&#9;g&#9;i&#9;n</h2>
        <div class="inputBx">
          <input v-model="this.user.username" type="text" placeholder="Username">
        </div>
        <div class="inputBx">
          <input v-model="this.user.password" type="password" placeholder="Password">
        </div>
        <div class="inputBx">
          <input type="submit" value="Sign in" @click="jumpToHome_login">
        </div>
        <div class="links">
          <a @click="navigateToForget">Forget Password</a>
          <a @click="navigateToSignUp">Signup</a>
        </div>
      </div>
    </div>
  </div>
  </div>
</template>

<script >
import { mapGetters, mapActions } from 'vuex';
import {ElMessageBox, ElNotification, ElText} from 'element-plus';
import http from "@/http";

export default {
  name: "LoginRegister",
  components: {
  },
  computed: {
    ...mapGetters(['mul_isLoggedIn', 'mul_user']),
  },
  data(){
    return {
      user:{
        username:'',
        password:'',
        email:'',
        createDate:'',
      },
    }
  },
  mounted() {
    this.$refs.videoElement.play();
  },
  methods:{
    ...mapActions(['login','logout']),
    getUserData(){

    },
    jumpToHome_login(){
      if(this.user.username===''){
        ElNotification.error({
          title: 'Error',
          message: 'Input username!',
        })
      }else if(this.user.password===''){
        ElNotification.error({
          title: 'Error',
          message: 'Password is empty!',
        })
      }
      else {
        http.get(`/checkLogin/${this.user.username}/${this.user.password}`)
            .then(response => {
              if (response.data.toString() === "No such user account !") {
                //登录信息有问题
                ElMessageBox.alert(
                    'Wrong username or password,try again!',
                    'info',
                    {
                      confirmButtonText: 'OK',
                      type: "error",
                    }
                )
              } else {
                http.get(`/getLoginMsg/${this.user.username}/${this.user.password}`)
                    .then(response => {
                      this.user=response.data[0];
                      console.log(this.user);
                      console.log(response.data[0]);
                      this.login(this.user);
                      this.$router.push('/home');
                    }).catch(error => {
                  console.error(error);
                });

              }
            }).catch(error => {
          console.error(error);
        });
      }
    },
    navigateToSignUp() {
      this.$router.push('/signup');
    },
    navigateToForget() {
      this.$router.push('/forget');
    },
  }
}
</script>

<style scoped>
body {
  overflow: hidden;
}

.loginback {
  background: #854a61;

}

#video {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 0;

}
#video video {
  width: 100%;
  height: 100%;
  box-shadow: 0 0 5px grey;
  object-fit: fill;
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
   margin-top: -59px;
   gap: 20px;
 }
.login h2 {
  font-size: 2em;
  color: #3d3d3d;
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
  cursor: pointer;
  transition: 0.5s ease-in-out;
}
.login .links a:hover {
  color: #fff;
  text-decoration: none;
  cursor: pointer;
  transform: scale(1.1);
}


</style>