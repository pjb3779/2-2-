<template>
  <div id="app_prebook">
    <main>
      <div class="book-detail">
        <div class="book-cover">
          <img :src="url" alt="书封面" />
        </div>
        <div class="book-info">
          <h1>{{ title }}</h1>
          <p>作者: {{ author }}</p>
          <p>简介: {{ summary }}</p>
          <router-link :to="{path:'/docpreview/docdetailview', query:{num:1,DocumentID:this.documentId}}">
            <button @click="addRecord">立即阅读</button>
          </router-link>
          <button @click="toggleBookshelf">{{ isInBookshelf ? '移出书架' : '加入书架' }}</button>        
        </div>
      </div>
      <div class="book-catalog">
        <h2>目录</h2>
        <ul>
          <li v-for="(chapter, index) in chapters" :key="index">
            <router-link :to="{path: '/docpreview/docdetailview', query:{num:chapter.num, DocumentID:this.documentId}}" class="chapter-link">
              <span @click="addRecord">{{ chapter.title }}</span>
            </router-link>
            <span >{{ chapter.date }}</span>

          </li>
        </ul>
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
    </main>
  </div>
</template>

<script>
import axios from 'axios';
import {mapActions, mapState} from "vuex";
import image from "@/assets/testDocImg.png";
export default {
  data() {
    return {
      title: '',
      author: '',
      authorId: 0,
      userName:'',
      summary: '',
      documentId: 0,
      userId: 0,
      chapters: [],
      comments: [],
      likedComments: [],
      newComment: '',
      isInBookshelf:true,
      coverImage:'',
      url:'',
    };
  },
  computed: {
    ...mapState(['mul_isLoggedIn', 'mul_user'])
  },
  mounted() {
    this.documentId=this.$route.query.data;
    this.userId=this.mul_user.userID;
    this.userName=this.mul_user.userName;
    this.initialLize();
    this.showChapters();
    this.checkBookshelf();
  },
  methods: {
    initialLize() {
      axios
        .get('http://localhost:8081/users/api/Document/', {
          params: { id: this.documentId },
        })
        .then((response) => {
          console.log(response);
          this.title = response.data.title;
          this.authorId = response.data.autherID;
          this.summary = response.data.summary;
          if(response.data.cover!==null){
            this.ImgData=response.data.cover;
            this.url="data:image/png;base64,"+this.ImgData;
          }else {
            this.url=image;
          }
          axios
            .get('http://localhost:8081/users/api/UserAccount/', {
              params: { param: this.authorId },
            })
            .then((response) => {
              this.author = response.data.name;
            })
            .catch((error) => {
              console.error('There was a problem with the fetch author operation:', error);
         });

      // Fetch comments after getting document and author information
      axios
        .get('http://localhost:8081/users/api/Comment/', {
          params: {
            DocumentID: this.documentId,
            chapterNum: 0,
          },
        })
        .then((response) => {
          console.log(response);
          
          this.comments = response.data.map(comment => ({
            username: comment.userName, // Replace with actual username if available
            timestamp: new Date(comment.PublishTime),
            content: comment.content,
            likes: comment.likes, // Assume likes are 0 initially or fetch from backend if available
          }));
        })
        .catch((error) => {
          console.error('There was a problem with the fetch comments operation:', error);
        });
    })
    .catch((error) => {
      console.error('There was a problem with the fetch operation:', error);
    });
    },
    showChapters(){
      axios
      .get('http://localhost:8081/users/api/chapters/',{
        params:{
          DocumentID: this.documentId
        }
      })
      .then((response) => {
        console.log(response);
        this.chapters = response.data.map(chapter => ({
            title: chapter.title,
            date: chapter.creatTime,
            num: chapter.num,
          }));
      })
      .catch((error)=>{
        console.log(error);
      })
    },
    addRecord(){
      axios.post('http://localhost:8081/records/post/',{
        userID:this.userId,
        documentId:this.documentId,
      })
      .catch((error)=>{
        console.error(error);
      })
    },
    checkBookshelf(){
      axios
        .get('http://localhost:8081/users/api/Bookshelf/', {
          params: {
            UserID: this.userId,
            DocumentID: this.documentId,
          },
        })
        .then((response) => {
          this.isInBookshelf = response.status === 200;
          console.log(this.isInBookshelf);
        })
        .catch(() => {
          this.isInBookshelf = false;
          console.log(this.isInBookshelf);

        });
    },
    toggleBookshelf() {
      if (this.isInBookshelf) {
        axios
          .delete('http://localhost:8081/users/api/Bookshelf/', {
            data: {
              UserID: this.userId,
              DocumentID: this.documentId,
            },
          })
          .then(() => {
            this.isInBookshelf = false;
            alert("移出书架成功！");
          })
          .catch((error) => {
            console.error('There was a problem with the remove book operation:', error);
          });
      } else {
        axios
          .post('http://localhost:8081/users/api/Bookshelf/', {
            UserID: this.userId,
            DocumentID: this.documentId,
            FavorTime: new Date().toISOString(),
            chapterNum: 0,
          })
          .then(() => {
            this.isInBookshelf = true;
            alert("加入书架成功！");
          })
          .catch((error) => {
            console.error('There was a problem with the add book operation:', error);
          });
      }
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
            DocumentID: this.documentId,
            likes: 0,
            chapterNum:0,
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
  },
};
</script>

<style scoped>
#app_prebook {
  background: #f6eeee;
  min-height: 800px;
  box-shadow: 0 10px 10px #718361;
  text-align: left;

}

header {
  display: flex;
  justify-content: space-between;
  padding: 20px;
  background-color: #f8f8f8;
  border-bottom: 1px solid #e8e8e8;
}

.header-left {
  display: flex;
  align-items: center;
}

.logo {
  height: 40px;
  margin-right: 20px;
}

.search-input {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.header-right button {
  margin-left: 10px;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  background-color: #ff4d4f;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

.book-detail {
  display: flex;
  padding: 20px;
}
.book-cover {
  border:#888 1px solid;
}

.book-cover img {
  width: 200px;
  height: 300px;
  object-fit: cover;
}

.book-info {
  margin-left: 20px;
}

.book-info h1 {
  margin: 0;
  font-size: 24px;
}

.book-info p {
  margin: 10px 0;
  line-height: 1.5;
}

.book-info button {
  margin-right: 10px;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  background-color: #1890ff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

.book-catalog {
  padding: 20px;
}

.book-catalog h2 {
  margin-bottom: 10px;
  font-size: 20px;
}

.book-catalog ul {
  list-style: none;
  padding: 0;
}

.book-catalog li {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
}

.book-catalog span {
  justify-content: space-between;
}

.chapter-link {
  display: flex;
  justify-content: space-between;
  text-decoration: none;
  color: inherit;
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
</style>
