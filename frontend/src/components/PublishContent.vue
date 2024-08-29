<template>
  <div id="content">
    <div id="title">发布属性设置 <button id="publish" @click="publishDocument">发布文档</button></div>
    
    <div class="setDoc">文档标题：
      <input type="text" v-model="title" placeholder="请输入内容(1-50字符)" id="titleText">
    </div>

    <div class="setDoc">文档标签：
      <input type="text" v-model="label" placeholder="请输入内容(1-50字符)" id="labelText">
    </div>
    
    <div class="setDoc">文档简介：
      <textarea type="text" v-model="summary" placeholder="请输入内容(1-500字符,不可为空)" id="summaryText"> </textarea>
    </div>

    <div class="setDoc"><!-- 文件选择按钮 -->
    请选择要发布的文档(*必选项)
    <!-- 隐藏的文件输入元素 -->
    <!-- 自定义按钮 -->
    <button @click="openDrawer" id="fileChooser">上传文档</button>
    </div>

    <div class="setDoc"><!-- 文件选择按钮 -->
    请选择文档的封面(如若不选择，则会赋予默认封面)
    <!-- 隐藏的文件输入元素 -->
    <!-- 自定义按钮 -->
    <button @click="triggerCoverInput" id="coverChooser">上传文档封面</button>
    <!-- 隐藏的文件输入 -->
    <input type="file" ref="coverInput" @change="handleCoverSelect" style="display: none;" accept="image/*" />
    <input type="text" :value="coverName" :readonly="true" id="coverpath" />
    </div>

    <div class="setDoc">文档分类：
      <select v-model="selectedOption">
        <!-- 使用v-for生成选项 -->
        <option v-for="(option, index) in options" :key="index" :value="index">
          {{ option }}
        </option>
      </select>
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

<script>
import axios from 'axios';
import { ElMessage } from 'element-plus'
import { computed } from 'vue';
import { mapState, useStore } from 'vuex';

export default {
  data() {
    return {
      title: '',
      label: '',
      summary: '',
      selectedOption: 'option1',
      fileName: '',
      fileContent:null,
      coverName:'',
      coverContent:'',
      options :[
  '国内图书', '国外图书', '小说', '诗/散文', '人文',
  '家庭/育儿', '料理', '健康', '科技', '日本小说',
  '爱好', '经济', '艺术', '初/高中参考书', '外语',
  '旅行', '漫画', '幼儿'
      ],
      table:false,
      chapters:[],
      nextId:1,
      formLabelWidth : '140px',
      drafts:[],
    };
  },
  computed:{
      ...mapState(['mul_isLoggedIn', 'mul_user'])
    },
  methods: {
    complete(){
      this.table=false;
    },

    cancelDraft(chapter){
      chapter.sourceName='';
      chapter.draftVisible = false;
    },

    //选择完成后将草稿的属性赋值给chapter
    confirmDraft(chapter) {
      // 找到选择的草稿对象
      const selectedDraft = this.drafts.find(draft => draft.title === chapter.sourceName);
      if (selectedDraft) {
        // 更新chapter.content和chapter.sourceName
        chapter.content = selectedDraft.content;
        chapter.sourceName = selectedDraft.title;
      }
      console.log(chapter.content);
      // 关闭对话框
      chapter.draftVisible = false;
    },

    // 获取草稿的方法
    async fetchDrafts() {
      try {
        const response = await axios.get('http://localhost:8081/users/api/getDrafts/', {
          params: { userID: this.mul_user.userID }
        });
        this.drafts = response.data;
      } catch (error) {
        console.error('Error fetching drafts:', error);
      }
    },

    //打开草稿选择对话框
    openDialog(chapter){
      chapter.draftVisible=true;
    },

    //打开抽屉
    openDrawer(){
      this.table=true;
    },

    //点击“选择文件后”出现的事件
    handleSelectChange(index,chapter) {
      if (chapter.documentSource === 'local') {
        // 找到对应的文件输入元素并使其可见
        this.chapters[index].draftDisplay=false;
        this.chapters[index].display=true;
      }
      else{
        this.chapters[index].draftDisplay=true;
        this.chapters[index].display=false;
      }
    },

    handleFileChange(file,chapter) {
    // 获取文件名
    chapter.sourceName = file.raw.name;
    console.log('文件名:', chapter.sourceName);

    // 创建FileReader来读取文件内容
    const reader = new FileReader();
    reader.onload = (e) => {
      // 获取文件内容
      const fileContent = e.target.result;
      chapter.content=fileContent;
      console.log('文件内容:', chapter.content);

      // 你可以在这里执行其他操作，例如将文件名和内容保存到data属性中
      // this.fileName = fileName;
      // this.fileContent = fileContent;
    };
    reader.readAsText(file.raw); // 读取文件内容
  },

    triggerCoverInput(){
      this.$refs.coverInput.click(); // 触发文件输入的点击事件
    },
    //处理用户选择要发布的文档
    handleFileSelect(event) {
      const file = event.target.files[0];
      if (file) {
        this.fileName = file.name;
        const reader = new FileReader();
        reader.onload = () => {
          const fileContent = reader.result; // 获取文件内容
          

          // 将文件内容赋值给一个变量（例如 this.fileContent）
          this.fileContent = fileContent;
        };
    reader.readAsText(file); // 以文本格式读取文件内容
      }
    },

    //处理用户选的的文档封面
    handleCoverSelect (event)  {
      const file = event.target.files[0];
      this.coverName=file.name;
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          const base64String = e.target.result.split(',')[1]; // 移除"data:image/png;base64,"部分

          this.coverContent=base64String;
        };
        // 读取文件并转换为Base64编码
        reader.readAsDataURL(file);
      }
    },

    fetchImage() {
      const imageUrl = new URL('../images/cover.png', import.meta.url).href;
      fetch(imageUrl)
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.blob(); // 获取图片的Blob数据
        })
        .then(blob => {
          // 此时的blob就是图片的二进制内容
          this.coverContent = URL.createObjectURL(blob); // 将Blob转换为URL，并赋值给coverContent
        })
        .catch(error => {
          console.error('Fetching image failed:', error);
        });
    },
    //发布文档
    publishDocument() {

      if(this.coverContent==null){
        this.fetchImage();
      }
      const documentData = {
        autherID:this.mul_user.userID,
        title: this.title,
        tags: this.label,
        summary: this.summary,
        chapterNum:this.chapters.length,
        categoryID: this.selectedOption,
        cover:this.coverContent,
        DocumentID:null
      };
      axios.post('http://localhost:8081/users/api/documents', documentData)
        .then(response => {
          console.log('Document published:', response.data);

          const documentId = response.data; // 假设返回的数据是新创建文档的id
          
          // 现在传输章节信息
          this.chapters.forEach((chapter, index) => {
            const chapterData = {
              documentID:documentId ,
              title: chapter.title,
              content: chapter.content,
              num: index+1
            };
           axios.post('http://localhost:8081/users/api/chapters/', chapterData)
              .then(chapterResponse => {
                console.log('Chapter ${index + 1} published:', chapterResponse.data);
              })
                .catch(chapterError => {
                console.error('Error publishing chapter ${index + 1}:', chapterError);
              });
          });
          ElMessage({
            message: '文档发布成功',
            type: 'success',
          });
          this.chapters=[];
          this.nextId=1;
        })
        .catch(error => {
          console.error('Error publishing document:', error);
        });
        
    },

    addChapter(){
      this.chapters.push({ id: this.nextId++, title: '' ,
      sourceName:'',content:'',display:false ,
      draftDisplay:false,draftVisible:false});
    },

    removeChapter(index){
      this.chapters.splice(index, 1);
    },
  },
  mounted() {
    this.fileInput = this.$refs.fileInput;
    this.fetchDrafts();
  },

};
</script>

<style scoped>
  
  
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
  #content{
    display: flex;
    flex-direction: column;
    margin-left: 290px;
    margin-right:100px;
    padding-top:90px;
    background-color: rgb(219, 255, 241);
    height: 200vh;
    width: 100%;

  }

  #title{
    display: flex;
    color: rgb(0, 0, 0);
    font-family: 'KaiTi', 'STKaiti', serif;
    font-size:35px;
    margin-left: 40%;
  }

  .setDoc{
    font-size: 20px;
    margin-top:30px;
    margin-left: 30px;
    display: flex;
    flex-direction: column;
    margin-right: 100px;
  }

  #titleText{
    margin-top:15px;
    height: 50px;
    border: none; /* 取消边框 */
    outline: none; /* 取消获得焦点时的轮廓线 */
    font-size: 16px;
    background-color: rgb(236, 249, 249);
  }

  #labelText{
    margin-top:15px;
    height: 50px;
    border: none; /* 取消边框 */
    outline: none; /* 取消获得焦点时的轮廓线 */
    font-size: 16px;
    background-color: rgb(236, 249, 249);
  }

  #summaryText{
    margin-top:15px;
    height: 200px;
    border: none; /* 取消边框 */
    outline: none; /* 取消获得焦点时的轮廓线 */
    font-size: 16px;
    background-color: rgb(236, 249, 249);
  }

  #fileChooser{
    margin-top:20px;
    width: 150px;
    height: 50px;
    border: none; /* 取消边框 */
    outline: none; /* 取消获得焦点时的轮廓线 */
    font-size: 16px;
    letter-spacing: 2px;
    background-color: rgb(236, 249, 249);
  }

  #coverChooser{
    margin-top:20px;
    width: 150px;
    height: 50px;
    border: none; /* 取消边框 */
    outline: none; /* 取消获得焦点时的轮廓线 */
    font-size: 16px;
    letter-spacing: 2px;
    background-color: rgb(236, 249, 249);
  }
  #fileChooser:hover{
    margin-top:20px;
    width: 150px;
    height: 50px;
    border: none; /* 取消边框 */
    outline: none; /* 取消获得焦点时的轮廓线 */
    font-size: 16px;
    letter-spacing: 2px;
    background-color: rgb(190, 221, 248);
  }
  #coverChooser:hover{
    margin-top:20px;
    width: 150px;
    height: 50px;
    border: none; /* 取消边框 */
    outline: none; /* 取消获得焦点时的轮廓线 */
    font-size: 16px;
    letter-spacing: 2px;
    background-color: rgb(190, 221, 248);
  }
  #path{
    margin-top:15px;
    height: 40px;
    border: none; /* 取消边框 */
    outline: none; /* 取消获得焦点时的轮廓线 */
    font-size: 16px;
    background-color: rgb(236, 249, 249);
  }

  #coverpath{
    margin-top:15px;
    height: 40px;
    border: none; /* 取消边框 */
    outline: none; /* 取消获得焦点时的轮廓线 */
    font-size: 16px;
    background-color: rgb(236, 249, 249);
  }

  select{
    margin-top:20px;
    border: none; /* 取消边框 */
    outline: none; /* 取消获得焦点时的轮廓线 */
    height:30px;
    width:200px;
  }

  #publish{
    height: 50px;
    width:140px;
    margin-right: 100px;
    border-width: 0px;
    background-color: rgb(188, 255, 210);
    font-size: 18px;
    letter-spacing: 3px;
    color: rgb(183, 194, 253);
    margin-left: 40%;
    margin-top:10px;
  }

  #publish:hover{
    height: 50px;
    width:140px;
    margin-right: 100px;
    border-width: 0px;
    background-color: rgb(149, 255, 184);
    font-size: 18px;
    letter-spacing: 3px;
    color: rgb(183, 194, 253);
    margin-left: 40%;
    margin-top:10px;
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