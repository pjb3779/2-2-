<template>
  <div>
    <div class="container">
      <!-- 头部 -->
      <header>
        <nav>
          <router-link to="/">首页</router-link>
          <!-- <router-link to=""></router-link> -->
          <a href="#">我的书架</a>
          <router-link :to="{path:'/docpreview', query:{data:this.DocumentID}}">
            {{ bookTitle }}
          </router-link>
        </nav>
      </header>
  
      <!-- 中部 -->
      <div class="middle">
        <!-- 中间文本框 -->
        <div>
          <a @click="previousChapter" :class="{disabled: chapterNum<=minChapter}" class="prevChap">&lt;&lt;上一章</a>
          <a @click="nextChapter" :class="{disabled: chapterNum>=maxChapter}" class="nextChap">下一章>></a>
          <div class="chapter-title">{{ chapterTitle }}</div>
          <div class="content" id="content"></div>
        </div>
      </div>
      <div class="comments-section">
        <h2>评论区</h2>
        <div class="comment" v-for="(comment, index) in comments" :key="index">
          <div class="comment-header">
            <span class="username">{{ comment.username }}</span>
            <span class="timestamp">{{ comment.timestamp }}</span>
          </div>
          <p class="comment-content">{{ comment.content }}</p>
          <div class="comment-actions">
            <button @click="toggleLike(index)">👍 {{ comment.likes }}</button>
          </div>
        </div>
        <div class="add-comment">
          <textarea v-model="newComment" placeholder="添加评论"></textarea>
          <button @click="postComment">发表评论</button>
        </div>
      </div>
    </div>
    <!-- 左侧工具栏 -->
    <div class="toolbar">
      <img @click="toggleFontSizeSlider" src="../assets/字体.png">
      <div class="slider" :class="{ hidden: !isSliderVisible }">
        <input type="range" min="16" max="30" v-model="fontSize" @input="updateFontSize" class="slider-input">
      </div>
      <input type="number" v-model="pageNumber" placeholder="页码" min="1" :max="this.pages.length">
      <img @click="jumpToPage" src="../assets/跳转.png">
      <!-- <img @click="highlightSelectedText" src="../assets/高亮.png"> -->
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import {mapActions, mapState} from "vuex";
export default {
  name: 'ReadingInterface',
  data() {
    return {
      chapterTitle:'',
      bookTitle:'',
      pages: [],
      comments:[],
      currentPage: 0,
      pageNumber: 1,
      isSliderVisible: false,
      fontSize: 16,
      lineHeight: 1.5,
      userId:0,
      userName:'',
      highlights: [],
      DocumentID:this.$route.query.DocumentID,
      chapterNum:this.$route.query.num,
      maxChapter:0,
      minChapter:0,
      newComment:'',
    };
  },
  computed: {
    ...mapState(['mul_isLoggedIn', 'mul_user'])
  },
  mounted() {
    this.userId=this.mul_user.userID;
    this.initialLize();
    // this.fetchHighlights(); // 获取高亮数据

    window.addEventListener('resize', () => this.loadContent());
  },
  beforeUnmount() {
    window.removeEventListener('resize', () => this.loadContent());
  },
  methods: {
    initialLize(){
      axios
        .get('http://localhost:8081/users/api/UserAccount/', {
          params: { param: this.userId },
        })
        .then((response) => {
          console.log(response);
          this.userName = response.data.name;
        })
        .catch((error) => {
          console.error('There was a problem with the fetch name operation:', error);
      });

      this.loadContent();
      this.loadComment();
    },
    loadContent() {
      axios.get('http://localhost:8081/users/api/chapterContent/',{
        params:{
          DocumentID:this.DocumentID,
          chapterNum:this.chapterNum,
        }
      })
      .then(response => {
          this.text = response.data.content;
          this.text= this.text.replace(/<\/?p>/g,'');
          this.text=this.text.replace(/<br>/g,'');
          console.log(this.text);
          this.chapterTitle = response.data.title;
          axios
          .get('http://localhost:8081/users/api/Document/', {
            params: { id: this.DocumentID },
          })
          .then((response) =>{
            this.bookTitle = response.data.title;
            this.maxChapter = response.data.chapterNum;
          })
          this.paginateText();
          this.renderAllPages();
        })
        .catch(error => {
          console.error('There was a problem with the fetch operation:', error);
        });
    },
    loadComment(){
      axios
        .get('http://localhost:8081/users/api/Comment/', {
          params: {
            DocumentID: this.DocumentID,
            chapterNum: this.chapterNum,
          },
        })
        .then((response) => {
          this.comments = response.data.map(comment => ({
            username: comment.userName,
            timestamp:new Date(comment.PublishTime),
            content: comment.content,
            likes: comment.likes,
          }));
        })
        .catch((error) => {
          console.error('There was a problem with the fetch comments operation:', error);
        });
    },
    previousChapter(){
      if(this.chapterNum>this.minChapter){
        this.chapterNum--;
        this.initialLize();
      }
    },
    nextChapter(){
      console.log(this.chapterNum)
      if(this.chapterNum<this.maxChapter){
        this.chapterNum++;
        this.initialLize();
      }
    },
    paginateText() {
      const widthForSplitingText = this.$el.querySelector('#content').getBoundingClientRect().width - this.fontSize*5;
      const context = document.createElement('canvas').getContext('2d');
      context.font = `${this.fontSize}px Arial`;
      this.pages=[];
      let currentPage = [];
      let currentLine = '';
      let lineWidth = 0;
      const lineHeightPx = this.fontSize * this.lineHeight;

      for (let i = 0; i <this.text.length; i++) {
        const char = this.text[i];
        const charWidth = context.measureText(char).width;
      
        if (lineWidth + charWidth > widthForSplitingText) {
          currentPage.push(currentLine);
          currentLine = char;
          lineWidth = charWidth;
        } else {
          currentLine += char;
          lineWidth += charWidth;
        }

        if ((currentPage.length + 1) * lineHeightPx > 600) {
          this.pages.push(currentPage.join('<br>'));
          currentPage = [];
        }
      }

      if (currentLine) {
        currentPage.push(currentLine);
      }
      if (currentPage.length > 0) {
        this.pages.push(currentPage.join('<br>'));
      }
    },
    renderAllPages() {
      const contentDiv = this.$el.querySelector('#content');
      contentDiv.innerHTML = '';
      this.pages.forEach((page, pageIndex) => {
        const pageDiv = document.createElement('div');
        pageDiv.style.backgroundColor = 'rgba(149, 218, 106, 0.71)';
        pageDiv.style.marginBottom = '15px';
        pageDiv.style.padding = '20px';
        pageDiv.style.textAlign = 'justify';
        pageDiv.style.textAlignLast = 'justify';  // 使最后一行也对齐
        pageDiv.style.color = 'black';
        const lines = page.split('<br>');
        lines.forEach((line, lineIndex) => {
        const lineDiv = document.createElement('div');
        lineDiv.className = 'justifyLine';
        
        // If this is the last line of the last page, don't justify it
        if (pageIndex === this.pages.length - 1 && lineIndex === lines.length - 1) {
          lineDiv.style.textAlignLast = 'left';
        }
        
        lineDiv.innerHTML = line;
        pageDiv.appendChild(lineDiv);
        });

        contentDiv.appendChild(pageDiv);
      });

      // 渲染高亮
      this.highlights.forEach(highlight => {
        this.applyHighlight(highlight);
      });
    },
    updateFontSize() {
      const content = this.$el.querySelector('#content');
      content.style.fontSize = this.fontSize + 'px';
      content.style.lineHeight = this.lineHeight + 'em';
      this.paginateText();
      this.renderAllPages();
    },
    jumpToPage() {
      const pageNumber = this.pageNumber - 1;
      if (!isNaN(pageNumber) && pageNumber >= 0 && pageNumber < this.pages.length) {
        const targetPageDiv = this.$el.querySelector('#content').children[pageNumber];
        targetPageDiv.scrollIntoView();
      }
    },
    highlightSelectedText() {
      const selection = window.getSelection();
      if (selection.rangeCount > 0) {
        const range = selection.getRangeAt(0);
        const selectedText = range.toString();
        console.log(selectedText +" range: " + range);
        // 创建 span 并高亮选中文字
        const span = document.createElement('span');
        span.className = this.userId === 1 ? 'highlight-current-user' : 'highlight-other-user';
        range.surroundContents(span);

        // 保存高亮信息到数据库
        this.saveHighlight({
          userId: this.userId,
          text: selectedText,
          startOffset: range.startOffset,
          endOffset: range.endOffset,
        });
      }
    },
    saveHighlight() {
    },
    fetchHighlights() {
      fetch('https://localhost:8081/api/highlights')
        .then(response => response.json())
        .then(data => {
          this.highlights = data;
          this.renderAllPages();
        })
        .catch(error => {
          console.error('Error:', error);
        });
    },
    applyHighlight(highlight) {
      const contentDiv = this.$el.querySelector('#content');
      const textNodes = this.getTextNodes(contentDiv);
      const startNode = textNodes[highlight.startOffset];
      const endNode = textNodes[highlight.endOffset - 1];
      const range = document.createRange();
      range.setStart(startNode, 0);
      range.setEnd(endNode, endNode.textContent.length);

      const span = document.createElement('span');
      span.className = highlight.userId === this.userId ? 'highlight-current-user' : 'highlight-other-user';
      range.surroundContents(span);
    },
    getTextNodes(node) {
      let all = [];
      for (node = node.firstChild; node; node = node.nextSibling) {
        if (node.nodeType === 3) all.push(node);
        else all = all.concat(this.getTextNodes(node));
      }
      return all;
    },
    toggleFontSizeSlider() {
      this.isSliderVisible = !this.isSliderVisible;
    },
    toggleLike(index) {
      if (this.likedComments.includes(index)) {
        this.comments[index].likes--;
        this.likedComments = this.likedComments.filter((i) => i !== index);
      } else {
        this.comments[index].likes++;
        this.likedComments.push(index);
      }
    },
    postComment() {
      if (this.newComment.trim() !== '') {
        axios.post('http://localhost:8081/users/api/Comment/', {
            UserID: this.userId,
            Content: this.newComment,
            DocumentID: this.DocumentID,
            likes: 0,
            chapterNum:this.chapterNum,
            userName:this.userName,
        })
        .then(() => {

          this.comments.push({
            username: this.userName,
            timestamp: new Date().toLocaleString(),
            content: this.newComment,
            likes: 0,
          });
          this.newComment = '';
        })
        .catch(error => {
          console.error('There was a problem with the post comment operation:', error);
        });
      }
    },
  }
};
</script>

<style scoped>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 10px;
  border-bottom: 1px solid #444;
  margin-top: 10px
}

nav {
  float: right;
  position: relative;
}

nav a {
  color: #000000;
  text-decoration: none;
  margin-top: 10px;
  padding-left: 10px;
  padding-right: 10px;
  border-right: 1px solid;
}

.container {
  line-height: 30px;
  width: 80%;
  margin: 0 auto;
  background: #b5d96c;
  min-height: 800px;
  box-shadow: 0 10px 10px #718361;
  text-align: left;
  font-family: "newFont",serif;
}

.button .text-right {
  color: #000000;
  font-size: 20px;
}

.button .slider {
  display: flex;
  align-items: center;
  margin-left: 10px;
}

.hidden {
  display: none;
}

.slider-input {
  margin: 0 10px;
}

.toolbar {
  position: fixed;
  bottom: 0;
  margin: auto;
  width: 35px;
  align-items: center;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-bottom: 10px;
  left: 10px;
}

.toolbar img {
  padding: 10px;
  margin-bottom: 10px;
  background: rgba(149, 218, 106, 0.71);
  width: 80%;
  border-radius: 50%;
  cursor: pointer;
}

.toolbar select,
.toolbar input {
  margin-bottom: 10px;
  background: rgba(149, 218, 106, 0.71);
  color: white;
  margin-right: 5px;
  width: 100%;
}

.content {
  color: #e0e0e0;
  text-align: justify;
  width: 100%;
}

.pagination {
  text-align: center;
  margin-top: 10px;
}

.chapter-title {
  width: 50%;
  display: block;
  margin: auto;
  margin-top: 20px;
  margin-bottom: 20px;
  padding: 10px 20px;
  background-color: #056608;
  color: #e0e0e0;
  border:2px solid #eeea05;
  font-size: 18px;
  text-align: center;
  transition: background-color 0.3s, border-color 0.3s;
}

.highlight-current-user {
  background-color: yellow !important;
}

.highlight-other-user {
  background-color: lightblue !important;
}

.justifyLine {
  text-align: justify;
  line-height: 1.5em;
}

.comments-section {
  padding: 20px;
  border-top: 1px solid #f0f0f0;
}

.comments-section h2 {
  margin-bottom: 10px;
  font-size: 20px;
}

.comment {
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
}

.comment-header {
  display: flex;
  align-items: center;
}

.username {
  font-weight: bold;
  margin-right: 10px;
}

.timestamp {
  color: #888;
}

.comment-content {
  margin: 10px 0;
}

.comment-actions button {
  margin-right: 10px;
  background: none;
  border: none;
  color: #1890ff;
  cursor: pointer;
}

.add-comment {
  display: flex;
  flex-direction: column;
  margin-top: 20px;
}

.add-comment textarea {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
}

.add-comment button {
  align-self: flex-end;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  background-color: #1890ff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}
.prevChap{
  float: left;
  cursor: pointer;
}
.nextChap{
  float: right;
  cursor: pointer;
}
.disabled{
  cursor: default
}
</style>
