<template>
    <!-- 本组件用于个人创作中心点击“草稿箱”按钮之后显示草稿箱界面 -->
    <div id="Box">
        <div id="title">
          我的作品
        </div>
        <div class="draftContainer"> 
          <div class="draftItems" v-for="doc in docs" :key="doc.DocumentID"> 
            <div class="docCover"> 
                <img :src="'data:image/png;base64,' + doc.cover" />
            </div>
    
            <div class="left-Item"> 
              <div class="basic">
                <div class="draftName"> 
                  {{ doc.title }}
                </div>
                <div class="category"> 
                    {{ doc.tags }}
                </div>
              </div>
              <div class="otherInfo">
                <div class="createDate"> 
                    发布时间：{{doc.publishTime}}
                </div>
                <div class="updateDate"> 
                    上次更新于：{{ doc.currentModifyTime }}
                </div>
              </div>
            </div>

            <div class="medium"> 

            </div>

            <div class="right-Item">
              <div class="right">
                <div class="modify" @click="openDrawer(doc.documentID)"> 
                  <svg t="1717661601323" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2601" width="20" height="20"><path d="M192.008043 1024A192.265427 192.265427 0 0 1 0 831.991957V255.967827A192.136735 192.136735 0 0 1 192.008043 63.959784h192.008043a64.02413 64.02413 0 0 1 0 128.048259H192.008043a64.345859 64.345859 0 0 0-64.024129 63.959784v576.02413a63.959784 63.959784 0 0 0 64.024129 63.895438h575.959784a63.895438 63.895438 0 0 0 64.02413-63.895438V639.983914a64.02413 64.02413 0 0 1 128.048259 0v192.008043a192.329773 192.329773 0 0 1-192.008043 192.008043z m127.983914-256.032173a64.345859 64.345859 0 0 1-64.02413-64.345859V511.935654a64.345859 64.345859 0 0 1 18.788991-45.042101L722.732688 18.982028a63.831092 63.831092 0 0 1 90.534624 0l192.008043 191.943698a63.959784 63.959784 0 0 1 0 90.534623l-447.97587 447.911525a63.573709 63.573709 0 0 1-45.042102 18.788991z m64.345859-229.586025v101.602112H485.811235l384.016087-384.016087-101.537766-101.47342z" fill="#8a8a8a" p-id="2602"></path></svg>
                  编辑
                </div>

                <div id="split"></div>
                <div class="delete" @click="deleteDoc(doc.documentID)"> 
                  <svg t="1717662892499" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4746" width="23" height="20"><path d="M162.181567 327.67055h97.332564v695.916023H162.181567zM374.919368 394.409505h97.332564v463.333718H374.919368zM59.53351 247.465682V150.133118h904.93298V247.465682z" fill="#8a8a8a" p-id="4747"></path><path d="M162.181567 1024V926.667436h735.605029v97.332564zM587.657169 394.409505h97.332565v463.333718H587.657169z" fill="#8a8a8a" p-id="4748"></path><path d="M800.394971 327.67055h97.332564v695.916023h-97.332564zM528.123659 61.305341a93.788903 93.788903 0 0 1 93.788903 93.729842h61.305341a155.094244 155.094244 0 0 0-310.129427 0h61.305341a93.788903 93.788903 0 0 1 93.729842-93.729842z" fill="#8a8a8a" p-id="4749"></path></svg> 
                  <div class="del">删除</div>
                </div>
              </div>
            </div>
          </div>

        </div>
    </div>






  <el-drawer
    v-model="table"
    direction="rtl"
    size="50%"
  >

  <!-- 使用 header 插槽自定义标题 -->
  <!-- 使用 header 插槽自定义标题 -->
  <template #header>
      <div class="drawer-title">选择你要发布的文件</div>
      <div class="drawer-header">
      <div></div> <!-- 占位元素 -->
      <el-button @click="addChapter" type="primary">添加章节</el-button>
      <el-button @click="complete" type="primary">完成</el-button>
      </div>
  </template>
  <!-- 添加章节的按钮 -->
  <!-- Drawer 的头部，使用 Flexbox 布局 -->
  <!-- 章节列表 -->
  <div v-for="(chapter, index) in chapters" :key="chapter.id" class="chapter-row">
    
      <span class="first">第{{ index + 1 }}章：</span>
      <el-input v-model="chapter.title" placeholder="请输入标题" class="chaTitle"></el-input>
      <!-- 添加 el-select 元素 -->
      <el-select v-model="chapter.documentSource" placeholder="传输文档" class="waySel" 
      @change="handleSelectChange(index,chapter)">
        <el-option label="从草稿箱" value="draft"></el-option>
        <el-option label="从本地" value="local"></el-option>
      </el-select>
      <!--用于从本地选择文件上传-->
      <el-upload
        ref="upload"
        action="https://jsonplaceholder.typicode.com/posts/"
        :auto-upload="false"
        @change="(file) => handleFileChange(file, chapter)"  class="fileChooser" v-if="chapter.display">
        <el-button class="fileButton">选择文件</el-button>
      </el-upload>
      
      <!--用于从草稿箱选择文件上传-->
      <div class="draftChooser" v-if="chapter.draftDisplay"> 
        <el-button class="draftButton" @click="openDialog(chapter)"> 选择草稿</el-button>
        <el-input v-model="chapter.sourceName" :readonly="true" class="draftInput"></el-input>
      </div>

      <el-button @click="removeChapter(index)" type="primary" class="deleColum">删除</el-button>

      <!--用于显示草稿选择对话框-->
      <el-dialog v-model="chapter.draftVisible" title="请选择你的草稿" width="500">
      <el-form>
        <el-form-item label="草稿" :label-width="formLabelWidth">
          <el-select v-model="chapter.sourceName" placeholder="Please select a draft">
          <el-option
            v-for="draft in drafts"
            :key="draft.id"
            :label="draft.title"
            :value="draft.title" />
          </el-select>
        </el-form-item>
      </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="() => cancelDraft(chapter)">Cancel</el-button>
        <el-button type="primary" @click="() => confirmDraft(chapter)">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
  </div>
  </el-drawer>
   
</template>

<script setup>
import { ref, onMounted, computed,reactive} from 'vue';
import axios from 'axios';
import { ElMessage, ElMessageBox ,ElDrawer} from 'element-plus';

import { useStore } from 'vuex';

// 使用 useStore 钩子获取 store 实例
const store = useStore();

// 使用 computed 创建响应式状态
const mul_isLoggedIn = computed(() => store.state.mul_isLoggedIn);//判断是否登录
const mul_user = computed(() => store.state.mul_user);//一个user对象
const userID = computed(() => mul_user.value.userID);
// 使用ref创建响应式数据
const docs = ref([]);
// 定义响应式数据
const table = ref(false);
const drafts = ref([]);
const chapters = ref([]);
const chapterdatas=ref([]);
const nextId = ref(1);
const docID=ref(0);
const formLabelWidth =ref('140px');
const fileName = ref('');
const fileContent = ref('');

const openDrawer = async (id) => {
  try {
    const response = await axios.get('http://localhost:8081/users/api/getChapters/', {
      params: { documentID: id }
    });
    chapterdatas.value = response.data;
    docID.value=id;
    // 清空chapters数组以避免重复添加
    chapters.value = [];

    // 使用获取到的数据填充chapters数组
    chapterdatas.value.forEach((chapterdata) => {
      chapters.value.push({
        id: nextId.value++,
        title: chapterdata.title,
        sourceName: chapterdata.title,
        content: chapterdata.content,
        display: false,
        draftDisplay: false,
        draftVisible: false
      });
    });

    console.log(chapters.value);
    table.value = true;
  } catch (error) {
    console.error('Error fetching chapters:', error);
  }
};

const complete = async () => {
  try {
    // 第一步：删除chapter表中所有DocumentID等于docID的元素
    await axios.delete('http://localhost:8081/users/api/deleteChapters/', {
      params: { documentID: docID.value }
    });
    console.log(docID.value);
    // 第二步：添加chapters数组中的所有元素到chapter表中
    for (const chapter of chapters.value) {
      await axios.post('http://localhost:8081/users/api/chapters/', {
        num: chapter.id,
        documentID: docID.value,
        content: chapter.content,
        title: chapter.title
      });
    }
    // 第一步：删除chapter表中所有DocumentID等于docID的元素
    await axios.put('http://localhost:8081/users/api/updateChapterNum/', {
      documentID: docID.value ,
      chapterNum: chapters.value.length
    });
    ElMessage({
      type: 'success',
      message: '文档更新成功',
    });
    // 成功后，可以进行其他操作，例如关闭表格视图
    table.value = false;
    
  } catch (error) {
    console.error('Error completing the operation:', error);
  }
};


const cancelDraft = (chapter) => {
  chapter.sourceName = '';
  chapter.draftVisible = false;
};

const confirmDraft = (chapter) => {
  const selectedDraft = drafts.value.find(draft => draft.title === chapter.sourceName);
  if (selectedDraft) {
    chapter.content = selectedDraft.content;
    chapter.sourceName = selectedDraft.title;
  }
  console.log(chapter.content);
  chapter.draftVisible = false;
};

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

const openDialog = (chapter) => {
  chapter.draftVisible = true;
};

const handleSelectChange = (index, chapter) => {
  if (chapter.documentSource === 'local') {
    chapters.value[index].draftDisplay = false;
    chapters.value[index].display = true;
  } else {
    chapters.value[index].draftDisplay = true;
    chapters.value[index].display = false;
  }
};

const handleFileChange = (file, chapter) => {
  chapter.sourceName = file.raw.name;
  console.log('文件名:', chapter.sourceName);

  const reader = new FileReader();
  reader.onload = (e) => {
    const fileContent = e.target.result;
    chapter.content = fileContent;
    console.log('文件内容:', chapter.content);
  };
  reader.readAsText(file.raw);
};


const addChapter = () => {
  chapters.value.push({ id: nextId.value++, title: '',
    sourceName:'', content:'', display:false,
    draftDisplay:false, draftVisible:false });
};

const removeChapter = (index) => {
  chapters.value.splice(index, 1);
};


// 获取草稿的方法
const fetchDocs = async () => {
  try {
    const response = await axios.get('http://localhost:8081/users/api/getDocs/', {
      params: { userID: userID.value }
    });
    docs.value = response.data;
  } catch (error) {
    console.error('Error fetching drafts:', error);
  }
};
// 生命周期钩子
onMounted(() => {
  fetchDocs();
  fetchDrafts();
});

// 删除文档的方法
const deleteDoc = async (id) => {
  await ElMessageBox.confirm(
    '确定要下架这个文档吗?',
    'Warning',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
    }
  ).then(async () => {
    try {
      const response = await axios.delete('http://localhost:8081/users/api/deleteDoc/', {
        params: { id: id }
      });
      if (response.status === 200) {
        docs.value = docs.value.filter(doc => doc.documentID !== id);
        ElMessage({
          message: '文档下架成功',
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
    padding-top:10px;
    padding-left: 4%;
    padding-bottom: 5px;
    border-bottom: 1px solid rgb(224, 224, 224); /* 设置2像素宽的红色边框 */
  }

   img{
    height: 120px;
    width: 90px;
   }


   .left-Item{
        width: 50%;
        padding-top:30px;
   }

  .basic{
    display: flex;
  }
  .draftName{
    margin-left: 5%;
    font-size: 25px;
    font-family: "楷体", "楷体_GB2312";
    font-weight: 600;
  }

  .category{
    padding-top:7px ;
    padding-left: 10px;
    font-size: 14px;
    color: rgb(60, 60, 60);
    margin-left: 8%;
  }
  .createDate{
    padding-top:18px;
    padding-left: 5px;
    font-size: 14px;
    color: rgb(60, 60, 60);
    margin-left: 5%;
  }

  .updateDate{
    padding-top:18px;
    padding-left: 5px;
    font-size: 14px;
    color: rgb(60, 60, 60);
    margin-left: 5%;
  }
  .right-Item{
    flex-grow: 1;
    display: flex;
    justify-content: flex-end;
  }

  .right{
    display: flex;
    margin-right: 50px;
    justify-content: center;
    align-items: center;
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
    height: 38px;
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

  .otherInfo{
    display: flex;
  }

  .draftChooser{
    display: flex;
    margin-left:3%;
    width: 28%;
  }

  .draftButton{
    width: 38%;
  }

  .fileButton{
    width: 98%;
  }

  .draftInput{
    margin-left: 5%;
    width: 50%;
  }

  .fileChooser{
    display: flex;
    width: 28%;
    margin-left: 3%;
  }

  .deleColum{
    margin-left: 3%;
  }

  .name{
    width: 28%;
    margin-left: 3%;
  }
  
  .drawer-title{
  font-size:25px;
  }

  .topLine{
    display: flex;
  }
  .first{
    width: 70px;
    margin-top: auto;
    margin-bottom: auto;
    font-size: 17px;
    font-family:"方正小标宋简体";
    color:rgb(112, 112, 112)
  }
  .chapter-row {
    display: flex;
    margin-bottom: 10px;
  }

  .chaTitle{
    width:32%;
  }

  .drawer-header {
    display: flex;
    justify-content: flex-end; /* 将按钮推到右边 */
    padding-right: 40px; /* 或者您需要的任何内边距 */
    margin-top:20px;
  }

  .waySel{
    margin-left:2%;
    width: 14%;
  }
</style>