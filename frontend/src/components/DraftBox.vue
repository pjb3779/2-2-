<template>
    <!-- 本组件用于个人创作中心点击“草稿箱”按钮之后显示草稿箱界面 -->
    <div id="Box">
        <div id="title">
          我的草稿
        </div>
        <div class="draftContainer"> 
          <div class="draftItems"  v-for="draft in drafts" :key="draft.id"> 

            <div class="left-Item"> 
              <div class="draftName"> 
                {{ draft.title }}
              </div>
              <div class="createDate"> 
                最近修改日期: {{ draft.currentModityTime }}
              </div>
            </div>

            <div class="right-Item">
              <div class="right">
                <div class="modify" @click="editDraft(draft.id,draft.content)"> 
                  <svg t="1717661601323" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2601" width="20" height="20"><path d="M192.008043 1024A192.265427 192.265427 0 0 1 0 831.991957V255.967827A192.136735 192.136735 0 0 1 192.008043 63.959784h192.008043a64.02413 64.02413 0 0 1 0 128.048259H192.008043a64.345859 64.345859 0 0 0-64.024129 63.959784v576.02413a63.959784 63.959784 0 0 0 64.024129 63.895438h575.959784a63.895438 63.895438 0 0 0 64.02413-63.895438V639.983914a64.02413 64.02413 0 0 1 128.048259 0v192.008043a192.329773 192.329773 0 0 1-192.008043 192.008043z m127.983914-256.032173a64.345859 64.345859 0 0 1-64.02413-64.345859V511.935654a64.345859 64.345859 0 0 1 18.788991-45.042101L722.732688 18.982028a63.831092 63.831092 0 0 1 90.534624 0l192.008043 191.943698a63.959784 63.959784 0 0 1 0 90.534623l-447.97587 447.911525a63.573709 63.573709 0 0 1-45.042102 18.788991z m64.345859-229.586025v101.602112H485.811235l384.016087-384.016087-101.537766-101.47342z" fill="#8a8a8a" p-id="2602"></path></svg>
                  编辑
                </div>

                <div id="split"></div>
                <div class="delete" @click="deleteDraft(draft.id)"> 
                  <svg t="1717662892499" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4746" width="23" height="20"><path d="M162.181567 327.67055h97.332564v695.916023H162.181567zM374.919368 394.409505h97.332564v463.333718H374.919368zM59.53351 247.465682V150.133118h904.93298V247.465682z" fill="#8a8a8a" p-id="4747"></path><path d="M162.181567 1024V926.667436h735.605029v97.332564zM587.657169 394.409505h97.332565v463.333718H587.657169z" fill="#8a8a8a" p-id="4748"></path><path d="M800.394971 327.67055h97.332564v695.916023h-97.332564zM528.123659 61.305341a93.788903 93.788903 0 0 1 93.788903 93.729842h61.305341a155.094244 155.094244 0 0 0-310.129427 0h61.305341a93.788903 93.788903 0 0 1 93.729842-93.729842z" fill="#8a8a8a" p-id="4749"></path></svg> 
                  <div class="del">删除</div>
                </div>
              </div>
            </div>
          </div>

        </div>
    </div>

    <!-- 点击“编辑”按钮时弹出的文本编辑器-->
    <el-dialog v-model="dialogFormVisible" title="编辑草稿" width="1000">
    <el-form>
        <CKEditor.component
        :editor="state.editor"
        v-model="state.editorData"
        :config="state.editorConfig"
        id="edit"
      ></CKEditor.component>
   </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="update(state.editorID,state.editorData)">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted, computed,reactive} from 'vue';
import axios from 'axios';
import { ElMessage, ElMessageBox } from 'element-plus';
import CKEditor from '@ckeditor/ckeditor5-vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { useStore } from 'vuex';

// 使用 useStore 钩子获取 store 实例
const store = useStore();

// 使用 computed 创建响应式状态
const mul_isLoggedIn = computed(() => store.state.mul_isLoggedIn);//判断是否登录
const mul_user = computed(() => store.state.mul_user);//一个user对象
const userID = computed(() => mul_user.value.userID);

// 使用ref创建响应式数据
const drafts = ref([]);

const state = reactive({
  editor: ClassicEditor,
  editorData: "Content of the editor.",
  editorID:0,
  editorConfig: {
    // The configuration of the editor.
    
  },
  draftTitle: '', 
});

const dialogFormVisible = ref(false)

// 编辑草稿的方法
const editDraft = (id,content) => {
  dialogFormVisible.value=true;
  state.editorData=content;
  state.editorID=id;
};

const update = async (id,content) =>{
  try {
    // 发送PUT请求到后端API更新草稿
    const response = await axios.put('http://localhost:8081/users/api/updateDraft/',  {

    content: content,
    id: id
  }
);

    // 检查响应状态
    if (response.status === 200) {
      ElMessage({
        message: '草稿更新成功',
        type: 'success',
      });
      // 关闭对话框
      dialogFormVisible.value = false;
    } else {
      ElMessage({
        message: '草稿更新失败',
        type: 'error',
      });
    }
  } catch (error) {
    console.error('Error updating draft:', error);
    ElMessage({
      message: '草稿更新出错',
      type: 'error',
    });
  }
}

// 获取草稿的方法
const fetchDrafts = async () => {
  try {
    const response = await axios.get('http://localhost:8081/users/api/getDrafts/', {
      params: { userID: userID.value }
    });
    drafts.value = response.data;
  } catch (error) {
    console.error('Error fetching drafts:', error);
  }
};

// 删除草稿的方法
const deleteDraft = async (id) => {
  await ElMessageBox.confirm(
    '确定要删除这个草稿吗?',
    'Warning',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
    }
  ).then(async () => {
    try {
      const response = await axios.delete('http://localhost:8081/users/api/deleteDraft/', {
        params: { id: id }
      });
      if (response.status === 200) {
        drafts.value = drafts.value.filter(draft => draft.id !== id);
        ElMessage({
          message: '草稿删除成功',
          type: 'success',
        });
      }
    } catch (error) {
      console.error('Error deleting draft:', error);
    }
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: 'Delete canceled',
    });
  });
};

// 计算属性，用于排序草稿
const sortedArray = computed(() => {
  return drafts.value.slice().sort((a, b) => b.id - a.id);
});

// 生命周期钩子
onMounted(() => {
  fetchDrafts();
});
</script>


<style scoped>
  #Box{
    display: flex;
    flex-direction: column;
    margin-left: 220px;
    margin-right:20px;
    padding-top:10px;
    width: 100%;
  }

  #title{
    background-color: white;
    padding-top:25px;
    padding-left: 55px;
    padding-bottom: 15px;
    font-size:22px;
    font-weight: 600;
    letter-spacing: 3px;
    border-bottom: 1.5px solid black; /* 设置2像素宽的红色边框 */
  }

  .draftContainer{
    background-color: white;
    height: 766px; /* 您可以根据需要设置固定高度 */
    overflow-y: auto; /* 当内容超过高度时显示垂直滚动条 */
  }

  .draftItems{
    display: flex;
    padding-top:30px;
    padding-left: 7%;
    padding-bottom: 15px;
    border-bottom: 1px solid rgb(224, 224, 224); /* 设置2像素宽的红色边框 */
  }

  .draftName{
    font-size: 25px;
    font-family: "楷体", "楷体_GB2312";
    font-weight: 600;
  }

  .createDate{
    padding-top:18px;
    padding-left: 5px;
    font-size: 14px;
    color: rgb(60, 60, 60);

  }

  .right-Item{
    flex-grow: 1;
    display: flex;
    justify-content: flex-end;
  }

  .right{
    display: flex;
    margin-right: 50px;
  }

  .icon{
    margin-bottom:3px;
  }
  .modify{
    display: flex;
    flex-direction: column;
    font-size:10px;
    color:rgb(94, 94, 94);
    font-weight: 600;
    height: 49px;
    margin-right:20px;
  }

  .modify:hover{
    display: flex;
    flex-direction: column;
    font-size:10px;
    color:rgb(94, 94, 94);
    font-weight: 600;
    margin-right:20px;
    background-color: #b3b3b3;
  }

  #split{
    width: 1px;
    height: 25px;
    background: #b3b3b3;
    margin-right: 20px;
  }

  .delete{
    display: flex;
    flex-direction: column;
    font-size:10px;
    color:rgb(94, 94, 94);
    font-weight: 600;
    height: 38px;
    margin-right:20px;
  }

  .delete:hover{
    display: flex;
    flex-direction: column;
    font-size:10px;
    color:rgb(94, 94, 94);
    font-weight: 600;
    margin-right:20px;
    background-color: #b3b3b3;
  }

  .del{
    margin-left: 3px;
  }

</style>