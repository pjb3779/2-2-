<template>
    <div class="searchview">
      <div class="content1">
        <div id="viewSearch">
          <input type="text" v-model="searchTerm" :placeholder="searchTerm" class="searchFrame2" />
          <button @click="search" class="searchButton2">
            <svg  t="1716008678130" class="search_icon2" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1454" width="30" height="30">
              <path d="M441.66 783.66c-44.57 0-88.1-8.23-129.47-24.48-44.12-17.33-83.8-43.06-117.95-76.46-34.14-33.4-60.73-72.51-79.03-116.25-17.66-42.22-26.87-86.93-27.38-132.88s7.73-90.85 24.46-133.45c17.33-44.12 43.06-83.8 76.46-117.95 33.4-34.14 72.51-60.73 116.25-79.03 42.22-17.66 86.93-26.87 132.88-27.38 45.93-0.51 90.85 7.73 133.45 24.46 44.12 17.33 83.8 43.06 117.95 76.46 34.14 33.4 60.73 72.51 79.03 116.24 17.66 42.22 26.87 86.93 27.38 132.88 0.5 45.95-7.73 90.85-24.46 133.45-17.33 44.12-43.06 83.8-76.46 117.95-33.4 34.14-72.51 60.73-116.25 79.03-42.22 17.66-86.93 26.87-132.88 27.38-1.33 0.02-2.65 0.03-3.98 0.03z m0.13-637.83c-73.78 0-147.5 28.49-203.05 85.26-109.52 111.94-107.54 292.12 4.4 401.64 54.23 53.05 125.88 81.8 201.73 80.98 75.86-0.83 146.85-31.15 199.91-85.38 53.05-54.23 81.81-125.87 80.98-201.73s-31.16-146.85-85.38-199.9c-55.17-53.97-126.91-80.87-198.59-80.87z" fill="#231815" p-id="1455"></path><path d="M238.81 529.87c-13.44 0-25.45-9.46-28.18-23.14-9.09-45.46-5.95-93.84 9.07-139.9 14.92-45.74 41.33-88.45 76.38-123.51 11.24-11.24 29.45-11.24 40.69-0.01 11.24 11.23 11.24 29.45 0.01 40.69-58.44 58.46-84.5 137.5-69.72 211.44 3.12 15.58-6.99 30.74-22.57 33.86-1.91 0.39-3.81 0.57-5.68 0.57z" fill="#F7B52C" p-id="1456"></path><path d="M860.4 874.25c-8.82 0-17.64-3.32-24.45-9.97l-178.56-174.7c-13.8-13.5-14.04-35.64-0.54-49.44 13.5-13.8 35.64-14.04 49.44-0.54l178.56 174.7c13.8 13.5 14.04 35.64 0.54 49.44a34.9 34.9 0 0 1-24.99 10.51z" fill="#231815" p-id="1457"></path>
            </svg>
          </button>
        </div>
        <el-divider content-position="right" class="font-color:black">Search Returns</el-divider>
        <div class="selectType">
          <el-segmented v-model="value" :options="options">
            <template #default="{ item }">
              <div class="flex flex-col items-center gap-2 p-2">
                <el-icon size="32" style="margin: 10px 5px 5px 5px ">
                  <component :is="item.icon" />
                </el-icon>
                <div style="font-size: 25px">{{ item.label }}</div>
              </div>
            </template>
          </el-segmented>
        </div>
      </div>
      <div class="content2">
        <div id="pageContainer">
        <div id="docsGrid">
          <div v-for="doc in curDocuments" :key="doc.DocumentID" >
            <DocComponent :docid="doc.DocumentID" />
          </div>
        </div>
        <div id="pageDump">
          <el-pagination
            v-model:current-page="currentPage"
            background layout="prev, pager, next"
            v-model:page-size="pageItem"
            :total="totalItems"/>
        </div>
      </div>
        <div class="classification">
          <div class="filter-block">
            <div class="filter-name">类型</div>
            <ul class="filter-item-wrapper">
              <li v-for="(item, index) in filterItem1"
                  :title="item.title"
                  :key="index"
                  :class="{ 'filter-item': true, 'on': index === activeIndex1 }"
                  @click="handleFilterItemClick(index,1)">
                {{ item.title }}
              </li>
            </ul>
          </div>
          <div class="filter-block">
            <div class="filter-name">时间</div>
            <ul class="filter-item-wrapper">
              <li v-for="(item, index) in filterItem2"
                  :title="item.title"
                  :key="index"
                  :class="{ 'filter-item': true, 'on': index === activeIndex2 }"
                  @click="handleFilterItemClick(index,2)">
                {{ item.title }}
              </li>
            </ul>
          </div>

       </div>
      </div>
    </div>
</template>
<script>
import { ref } from 'vue'
import DocComponent from "@/components/DocComponent";
import http from '../http.js';
import hotDown from '@/components/IconComponents/hotDown'
import TimeUp from "@/components/IconComponents/TimeUp";
import TimeDown from "@/components/IconComponents/TimeDown";
import normalUp from "@/components/IconComponents/normalUp";
import hotUp from "@/components/IconComponents/hotUp";
export default {
  components: {
    DocComponent
  },
  setup(){
    const value = ref('综合');
    const options=[
          {
            label: 'normal',
            value: '综合',
            icon:normalUp,
          },
          {
            label: 'TimeDown',
            value: '最新',
            icon: TimeDown,
          },
          {
            label: 'TimeUp',
            value: '最晚',
            icon: TimeUp,
          },
          {
            label: 'hotDown',
            value: '热度降序',
            icon: hotDown,
          },
          {
            label: 'hotUp',
            value: '热度升序',
            icon: hotUp,
          },
        ];
    return {
      value,
      options
    };
  },
  data(){
    return {
      searchTerm: '',
      currentPage:1,
      pageItem:24,
      totalItems:0,
      filterItem1:[
        {title:'全部'},
        {title:'学术论文'},
        {title:'研究报告'},
        {title:'技术文档'},
        {title:'政府文件'},
        {title:'其他'},
      ],
      filterItem2:[
        {title:'全部'},
        {title:'最近一天'},
        {title:'最近一周'},
        {title:'最近一月'},
      ],
      activeIndex1:0,
      activeIndex2:0,
      documents:[],
      curDocuments:[],
      //排序类型
      type:0,
      //文档类型
      classify:0,
      //文档时间
      time:0,
    }
  },
  mounted() {
    document.getElementById("topSearch").style.display='none';
    this.searchTerm = this.$route.query.searchTerm;
    http.get('/Doc/searchDoc/'+encodeURIComponent(this.searchTerm)+"/"+this.type+"/"+this.classify+"/"+this.time)
        .then(response => {
          this.documents=[];
          this.documents=response.data;
          this.totalItems=this.documents.length;
          this.curDocuments=[];
          this.curDocuments=this.documents.slice((this.currentPage-1)*24,
              (this.totalItems>=(this.currentPage)*24<this.totalItems)?(this.currentPage)*24:this.totalItems);
          console.log(this.curDocuments)
        })
        .catch(error => {
          console.error(error);
        });

  },
  watch: {
    currentPage(newPage,oldPage){
      console.log(this.currentPage);
      this.curDocuments=[];
      this.curDocuments=this.documents.slice((newPage-1)*24,(newPage)*24);
      console.log(this.curDocuments)
    },
    value(newvalue,oldvalue ){
      //监视选择器选择的排序方式
      if(newvalue==='最新'){
        this.type=0;
      }else if(newvalue==='最晚'){
        this.type=1;
      }else if(newvalue==='热度降序'){
        this.type=2;
      }else if(newvalue==='热度升序'){
        this.type=3;
      }else if(newvalue==='综合'){
        this.type=4;
      }
      http.get('/Doc/searchDoc/'+encodeURIComponent(this.searchTerm)+"/"+this.type+"/"+this.classify+"/"+this.time)
          .then(response => {
            this.documents=[];
            this.documents=response.data;
            this.totalItems=this.documents.length;
            this.curDocuments=[];
            this.curDocuments=this.documents.slice((this.currentPage-1)*24,
                (this.totalItems>=(this.currentPage)*24<this.totalItems)?(this.currentPage)*24:this.totalItems);
            console.log(this.curDocuments)
          })
          .catch(error => {
            console.error(error);
          });
      console.log(newvalue);
    },
    activeIndex1(newvalue,oldvalue){
      this.classify=newvalue;
      console.log('/Doc/searchDoc/'+encodeURIComponent(this.searchTerm)+"/"+this.type+"/"+this.classify+"/"+this.time)
      http.get('/Doc/searchDoc/'+encodeURIComponent(this.searchTerm)+"/"+this.type+"/"+this.classify+"/"+this.time)
          .then(response => {
            this.documents=[];
            this.documents=response.data;
            this.totalItems=this.documents.length;
            this.curDocuments=[];
            this.curDocuments=this.documents.slice((this.currentPage-1)*24,
                (this.totalItems>=(this.currentPage)*24<this.totalItems)?(this.currentPage)*24:this.totalItems);
            console.log(this.curDocuments)
          })
          .catch(error => {
            console.error(error);
          });
    },
    activeIndex2(newvalue,oldvalue){
      this.time=newvalue;
      http.get('/Doc/searchDoc/'+encodeURIComponent(this.searchTerm)+"/"+this.type+"/"+this.classify+"/"+this.time)
          .then(response => {
            this.documents=[];
            this.documents=response.data;
            this.totalItems=this.documents.length;
            this.curDocuments=[];
            this.curDocuments=this.documents.slice((this.currentPage-1)*24,
                (this.totalItems>=(this.currentPage)*24<this.totalItems)?(this.currentPage)*24:this.totalItems);
            console.log(this.curDocuments)
          })
          .catch(error => {
            console.error(error);
          });
    },
  },
  methods: {
    search() {
      console.log('Searching for: ' + this.searchTerm);
      //执行搜索逻辑，搜索的逻辑发送给搜索页面
      http.get('/Doc/searchDoc/'+encodeURIComponent(this.searchTerm)+"/"+this.type+"/"+this.classify+"/"+this.time)
          .then(response => {
            this.documents=[];
            this.documents=response.data;
            this.totalItems=this.documents.length;
            this.curDocuments=[];
            this.curDocuments=this.documents.slice((this.currentPage-1)*24,
                (this.totalItems>=(this.currentPage)*24<this.totalItems)?(this.currentPage)*24:this.totalItems);
            console.log(this.curDocuments)
          })
          .catch(error => {
            console.error(error);
          });
    },
    handleFilterItemClick(index,type){
      if(type===1){
        this.activeIndex1 = index;
      }
      if(type===2){
        this.activeIndex2 = index;
      }
    }
  },
  beforeRouteLeave(to, from, next) {
    // 在离开当前路由之前执行的操作
    document.getElementById("topSearch").style.display='flex'
    next(); // 调用 next() 进行路由跳转
  }

}

</script>
<style scoped>
@font-face {
  font-family: "cha";
  src: url("../assets/TsangerYuYangT_W02_W02.ttf");
}
.searchview {
  overflow: auto;
  margin: 0 20px 0 20px;
  background: #f6eeee;
  min-height: 800px;
  box-shadow: 0 10px 10px #718361; /* 添加一个向下的白色阴影 */
  display: flex;
  flex-direction: column;
  justify-items: center;
}
.content1 {
  display: flex;
  flex-direction: column;
  justify-items: center;
  align-items: center;
}
#viewSearch {
  display: flex;
  flex-direction: row;
  margin-top: 10px;
}

.searchButton2 {
  margin: 10px 0 10px 0;
  padding-left: 1.5%;
  text-align: center;
  border-radius: 0 17px 17px 0;
  background: rgba(149, 218, 106, 0.71);
  border-width: 1px 1px 1px 0;
  border-color: transparent ;
  transition-duration: 0.4s;
  transition: transform 0.3s ease-in-out;
}

.search_icon2 {
  padding: 5px 0 5px 0;
  margin-right: 5px;
}

.searchButton2:hover {
  margin: 10px 0 10px 0;
  padding-left: 1.5%;
  text-align: center;
  border-radius: 2px 17px 17px 2px;
  background: rgba(129, 218, 73, 0.72);
  border-width: 1px 1px 1px 0;
  border-color: transparent ;
  transform: scale(1.1);
}

.searchButton2:active {
  margin: 10px 0 10px 0;
  padding-left: 1.5%;
  text-align: center;
  border-radius: 2px 17px 17px 2px;
  background: rgba(129, 218, 73, 0.72);
  border-width: 1px 1px 1px 0;
  border-color: transparent ;
  transform: scale(1);
  transition-duration: 0.1s;
}

.searchFrame2 {
  min-width: 400px;
  font-family: "Quicksand", sans-serif;
  font-size: 25px;
  margin: 10px 0 10px 0;
  text-align: center;
  border-radius: 17px 0 0 17px;
  border-width: 1px 0 1px 1px;
  border-color: transparent ;
  outline: none;
  background: #ffffff;
  width: 45%;
  transition-duration: 0.4s;
}

.searchFrame2:hover {
  border-radius: 17px 0 0 17px;
  border-width: 1px 0 1px 1px;
  border-style: solid;
  border-color: #445067;
}

.searchFrame:hover + .searchButton2 {
  border-width: 1px 1px 1px 0;
  border-style: solid;
  border-color: #445067;
}
.content2 {
  display: flex;
  flex-direction: row;
  margin-top: 20px;
}
#docsGrid {

  position: relative;
  display: grid;
  grid-template-columns: repeat(auto-fill,minmax(346px,1fr));
  row-gap: 20px;
}
#docsGrid div {
  display: flex;
  justify-content: center;
  align-items: center;
}
#pageDump {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.selectType .el-segmented {
  --el-segmented-item-selected-bg-color: #d3dede;
  --el-segmented-item-selected-color: #171616;
  --el-segmented-bg-color: transparent;
  --el-border-radius-base: 8px;
  font-weight: 600;
}

.selectType {
  margin-top: 10px;
  left: 20px;
}
#pageContainer {
  display: flex;
  flex-direction: column;
  min-width: 80%;
}
.classification {
  width: 100%;
  font-family: cha,serif;
  font-size: 40px;
  display: flex;
  flex-direction: column;
  margin: 0;
  padding: 0;
}
.classification li,.classification ul {
  list-style: none;
}
.classification ul {
  margin: 0;
  padding: 0;
}
.classification .filter-block {
  position: relative;
  margin-bottom: 16px;
  overflow: hidden;

}
.classification .filter-block .filter-name {
  display: block;
  float: left;
  line-height: 1;
  font-size: 20px;
  color: #99a2aa;
}
.classification .filter-block .filter-item-wrapper {
  margin-top: -20px;
  margin-left: 30px;
  float: right;
  overflow: hidden;
  -webkit-transition: all .3s linear;
  -o-transition: all .3s linear;
  transition: all .3s linear;
  min-height: 30px;
}
.classification .filter-block .filter-item-wrapper .filter-item {
  display: inline-block;
  margin: 0 5px 0 5px;
  vertical-align: top;
  line-height: 1;
  text-align: left;
  min-width: 50px;
  height: 30px;
  font-size: 20px;
  overflow: hidden;
  -o-text-overflow: ellipsis;
  text-overflow: ellipsis;
  padding-right: 2px;
  cursor: pointer;
  outline: 0;
  transition: 0.3s ease-in;
}
.classification .filter-block .filter-item-wrapper .filter-item.on {
  font-weight: 500;
  color: #00a1d6;
}
</style>