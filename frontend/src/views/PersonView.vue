<template>
  <div class="container">
    <el-container>
      <el-header>
        <div class="panel">
          <el-container>
            <el-aside width="200px">
              <div class="circle-avatar">
                <el-avatar :size="124" :src="avatarurl" />
              </div>
            </el-aside>
            <el-main>
              <div class="info">
                <el-descriptions title="User Information" :column="3" class="descriptions" border>
                  <el-descriptions-item label="Username" label-align="center" align="center">{{ userInfo.userName
                    }}</el-descriptions-item>
                  <el-descriptions-item label="Account" label-align="center" align="center">{{ userID
                    }}</el-descriptions-item>
                  <el-descriptions-item label="Email" label-align="center" align="center">{{ userInfo.email
                    }}</el-descriptions-item>
                  <el-descriptions-item label="Level" label-align="center" align="center">
                    <el-tag size="small">fresh-man</el-tag>
                  </el-descriptions-item>
                </el-descriptions>
              </div>
            </el-main>
          </el-container>
        </div>
      </el-header>

      <el-main>
        <div class="main-panel">
          <el-container>
            <el-header>
              <div class="menu">
                <div class="left-buttons">
                  <el-button size="large" type="success" @click="toFavorites">我的收藏</el-button>
                  <el-button size="large" type="success" @click="toRecords">阅读记录</el-button>
                </div>
                <el-button class="edit-button" size="large" type="success">编辑</el-button>
              </div>
            </el-header>
            <el-divider></el-divider>
            <el-main>
              <component :is="tabComponent"></component>
            </el-main>
          </el-container>
        </div>
        <div class="message" @click="drawer = true">
          <div class="mess-icon">
            <el-icon>
              <ChatSquare style="width: 4em; height: 4em; margin-right: 8px;" />
            </el-icon>
            <div v-if="showBadge" class="badge"></div>
          </div>

          <el-drawer v-model="drawer" title="消息通知" :with-header="true" :direction="'rtl'" size="40%">
            <div class="notifications-container">
              <div v-for="(msg, index) in messages" :key="index" class="notification-button"
                @click="showMessageDetail(msg)">
                <div class="notification">
                  <div class="notification-sender"><strong>{{ msg.userName }}</strong></div>
                  <div class="notification-message">{{ msg.message }}</div>
                </div>
              </div>
            </div>
            <div class="sendmessage">
              <el-button color="green" size="large" @click="openSendMessageDialog">send message</el-button>
            </div>
          </el-drawer>
        </div>
      </el-main>
    </el-container>

    <el-dialog v-model="dialogVisible" title="消息详情">
      <p><strong>发件人: </strong> {{ selectedMessage.userName }} @{{ selectedMessage.senderId }}</p>
      <p><strong>消息: </strong> {{ selectedMessage.message }}</p>
    </el-dialog>

    <el-dialog v-model="sendMessageDialogVisible" title="发送消息">
      <el-form :model="newMessage" label-width="80px" class="send-message-form">
        <el-form-item label="目的地">
          <el-input v-model="newMessage.receiver" placeholder="Please input receiver id"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="newMessage.content" :autosize="{ minRows: 6, maxRows: 6 }"
            placeholder="Your message here..." class="message-input"></el-input>
        </el-form-item>
      </el-form>
      <div  class="dialog-footer">
        <el-button @click="sendMessageDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="sendNewMessage">发送</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';

import favorites from "../components/FavoritesCom.vue";
import records from "../components/RecordsConm.vue";
import { ChatSquare } from '@element-plus/icons-vue';
import http from '../http.js';
import { useStore } from 'vuex';
import { ElMessage, ElMessageBox } from 'element-plus';
// 使用 useStore 钩子获取 store 实例
const store = useStore();

// 使用 computed 创建响应式状态
const mul_isLoggedIn = computed(() => store.state.mul_isLoggedIn);//判断是否登录
const mul_user = computed(() => store.state.mul_user);//一个user对象

const userID = computed(() => mul_user.value.userID);  // 用户ID
const avatarurl= 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png';
const tabComponent = ref(favorites);
const userInfo = ref({ userName: '', email: '', createDate: "" });
const drawer = ref(false);
const showBadge = ref(false);
const messages = ref([]);
const dialogVisible = ref(false);
const selectedMessage = ref({ userName: '', senderId: '', message: '' });

const fetchUserInfo = async () => {
  try {
    const response = await http.get(`/info/${userID.value}`);
    userInfo.value = response.data;
  } catch (error) {
    console.error("There was an error fetching the user info!", error);
  }
};

const fetchMessages = async () => {
  try {
    const response = await http.get(`/messages/select/${userID.value}`);
    messages.value = response.data;
    showBadge.value = messages.value.length > 0;
  } catch (error) {
    console.error("There was an error fetching the messages!", error);
  }
};

const showMessageDetail = (msg) => {
  selectedMessage.value = msg;
  dialogVisible.value = true;
};

onMounted(() => {
  fetchUserInfo();
  fetchMessages();
});

const toFavorites = () => {
  tabComponent.value = favorites;
  console.log("jump to favorites");
};

const toRecords = () => {
  tabComponent.value = records;
  console.log("jump to records");
};

const sendMessageDialogVisible = ref(false);
const newMessage = ref({ receiver: '', content: '' });

const openSendMessageDialog = () => {
  sendMessageDialogVisible.value = true;
};

const sendNewMessage = async () => {
  try {
    const response = await http.post('/messages/send', {
      userId: newMessage.value.receiver,
      message: newMessage.value.content,
      senderId: userID.value,
    });
    ElMessage({
        message: '发送成功',
        type: 'success',
      });
    sendMessageDialogVisible.value = false;
    newMessage.value.receiver = '';
    newMessage.value.content = '';
  } catch (error) {
    console.error("There was an error sending the message!", error);
  }
};
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
  position: fixed;
  background-color: #e8fadf;
}

.panel {
  height: 12vw;
  width: 60vw;
  padding: 20px;
  background-color: #d6ffbf;
  border-radius: 6px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  margin-right: auto;
  margin-left: auto;
}

.circle-avatar {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.info {
  align-items: center;
  width: 100%;
  height: 100%;
}

.descriptions {
  background-color: #bcf4be;
  border-radius: 4px;
}

.main-panel {
  display: flex;
  margin-right: auto;
  margin-left: auto;
  margin-top: 12%;
  width: 68%;
  height: 28vw;
  background-color: #d6ffbf;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.menu {
  padding-top: 2%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.left-buttons {
  display: flex;
  gap: 10px;
}

.edit-button {
  margin-left: auto;
}

.message {
  position: fixed;
  bottom: 24px;
  right: 24px;
  cursor: pointer;
  z-index: 1000;
}

.mess-icon {
  background-color: #007bff;
  color: white;
  border-radius: 50%;
  padding: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: transform 0.2s;
  position: relative;
  font-size: 3em;
}

.mess-icon:hover {
  transform: scale(1.1);
}

.badge {
  position: absolute;
  top: 4px;
  right: 4px;
  width: 20px;
  height: 20px;
  background-color: red;
  border-radius: 50%;
}

.notifications-container {
  padding: 10px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
  width: 90%;
  margin: 0 auto;
}

.notification-button {
  width: 100%;
  text-align: left;
}

.notification {
  padding: 15px;
  background-color: #f1f1f1;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  width: 100%;
  height: 20%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-bottom: 10px;
}

.notification-sender {
  font-weight: bold;
  margin-bottom: 5px;
}

.notification-message {
  color: #333;
  white-space: normal;
  overflow: hidden;
  text-overflow: ellipsis;
}

.sendmessage {
  position: fixed;
  bottom: 24px;
  right: 24px;
  cursor: pointer;
  z-index: 1000;
}

.dialog-footer {
  text-align: right;
}

.send-message-form {
  width: 100%;
}

.message-input {
  height: 150px;
}
</style>
