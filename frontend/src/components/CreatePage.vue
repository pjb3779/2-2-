<template>
  <div id="editor">
    <CKEditor.component
      :editor="state.editor"
      v-model="state.editorData"
      :config="state.editorConfig"
      id="edit"
    ></CKEditor.component>
    <div id="choose">
        <button @click="submitContent" class="opti">保存至草稿箱</button>
        <button @click="cancelEdit" class="opti">放弃本次编辑</button>
    </div>
    
    
  </div>
  
</template>

<script setup>
import { reactive, ref } from "vue";
import CKEditor from '@ckeditor/ckeditor5-vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { ElMessage, ElMessageBox } from 'element-plus';
import axios from 'axios';
import { computed } from 'vue';
import { useStore } from 'vuex';

// 使用 useStore 钩子获取 store 实例
const store = useStore();

// 使用 computed 创建响应式状态
const mul_isLoggedIn = computed(() => store.state.mul_isLoggedIn);//判断是否登录
const mul_user = computed(() => store.state.mul_user);//一个user对象
const userID = computed(() => mul_user.value.userID);


const state = reactive({
  editor: ClassicEditor,
  editorData: "",
  editorConfig: {
    // The configuration of the editor.
    
  },
  draftTitle: '', 
});


const submitContent= () => {
  ElMessageBox.prompt('请输入草稿的标题', 'Title', {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      inputPattern: /^.{1,50}$/, // Match 1-50 characters
      inputErrorMessage: 'Invalid input (1-50 characters)',
    })
      .then(({ value }) => {
        state.draftTitle = value; // 将输入的内容赋值给draftTitle
        console.log(state.draftTitle);
        ElMessage({
          type: 'success',
          message: `成功保存至草稿箱`,
        });


        const draftData = {
          title: state.draftTitle,
          content: state.editorData,
          userID:userID.value
        };
        console.log(draftData.content)
        axios.post('http://localhost:8080/users/api/addToDraft', draftData)
        .then(response => {
          console.log('Draft Saved:', response.data);
          state.editorData='';
        })
        .catch(error => {
          console.error('Error publishing document:', error);
        });

      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: 'Input canceled',
        })
      })
}

// 新增放弃编辑的方法
function cancelEdit() {
  // 弹出警示窗口并获取用户的选择
  if (confirm("您确定要放弃本次编辑吗？")) {
    state.editorData = ''; // 如果用户点击“确定”，则清空编辑器内容
  }
}


</script>

<style scoped>
  #editor{
    margin-left: 220px;
    margin-right:20px;
    padding-top:100px;
    background-color: rgb(219, 255, 241);
    box-shadow: 0 10px 10px #718361;
    height: 100vh;
    width: 100%;
  }

  #edit{
    height: 500px;
  }

  #choose{
    display:flex;
    justify-content: center;
    margin-top:40px;
  }

  .opti{
    height: 50px;
    width:140px;
    margin-right: 100px;
    margin-left: 100px;
    border-width: 0px;
    background-color: rgb(188, 255, 210);
    font-size: 15px;
    letter-spacing: 2px;
    color: rgb(183, 194, 253);
  }

  .opti:hover{
    height: 50px;
    width:140px;
    margin-right: 100px;
    margin-left: 100px;
    border-width: 0px;
    background-color: rgb(149, 255, 184);
    font-size: 15px;
    letter-spacing: 2px;
    color: rgb(183, 200, 253);
  }
</style>


