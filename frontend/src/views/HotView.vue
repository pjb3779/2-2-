<template>
    <div class="hot">
      <div class="hotTop">
        <HotImg />
        <h1>热&#9;门&#9;文&#9;档</h1>
      </div>
      <el-divider content-position="right" style=".el-divider__text {
      background-color: #f6eeee;font-size: 20px;}">Hot Docs Top 10</el-divider>
      <div class="hotGrid" v-infinite-scroll="loadDocs" :infinite-scroll-distance="50"
           v-loading.fullscreen="loading"  style="width: 100%"  element-loading-text="Loading..."
           element-loading-background="transparent">
        <div v-for="(document,index) in documents"
             :key="document.DocumentID"
             class="grid-item">
          <div v-if="index===0" class="hotRank" ><el-icon size="20">
            <hotFirst />
          </el-icon></div>
          <div v-if="index===1" class="hotRank" ><el-icon size="20">
            <hotSecond />
          </el-icon></div>
          <div v-if="index===2" class="hotRank" ><el-icon size="20">
            <hotThird />
          </el-icon></div>
          <div v-if="index>2" class="hotRank">{{ index+1 }}</div>
          <DocComponent2 class="hotDoc" :docid="document.DocumentID" />
        </div>
      </div>
    </div>
</template>
<script>
import DocComponent2 from "@/components/DocComponent2";
import http from "@/http";
import HotImg from "@/components/IconComponents/HotImg";
import hotFirst from "@/components/IconComponents/hotFirst";
import hotSecond from "@/components/IconComponents/hotSecond";
import hotThird from "@/components/IconComponents/hotThird";

export default {
  components: {
    HotImg,
    DocComponent2,
    hotFirst,
    hotSecond,
    hotThird,
  },
  data() {
    return {
      categories: [
        { id: 0, name: "全部" },
        { id: 1, name: "学术论文" },
        { id: 2, name: "研究报告" },
        { id: 3, name: "技术文档" },
        { id: 4, name: "政府文件" },
        { id: 5, name: "其它" },
      ],
      curcategory:0,
      selectedCategory: "全部",
      documents: [],
      visibleDocuments:[],
      type: 3,
      loading: false, // 是否正在加载数据
    };
  },
  watch: {

  },
  mounted() {
    this.loading=true;
    http.get('/Doc/getHotDocs')
        .then(response => {
          this.documents=[];
          this.documents=response.data;
          this.loading=false;
          console.log(this.documents)
        })
        .catch(error => {
          console.error(error);
        });
  },
  methods: {
    loadDocs(){
      // 如果正在加载数据或没有更多数据,则返回
      if (this.isLoading || this.page * this.pageSize >= this.documents.length) return;
      this.isLoading = true;
      // 计算下一页应该显示的文档
      const startIndex = (this.page - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      this.visibleDocuments = this.documents.slice(startIndex, endIndex);
      this.page += 1;
      this.isLoading = false;
    },
  },

};
</script>
<style scoped>
@font-face {
  font-family: "NewFont";
  src: url("../assets/AlimamaFangYuanTiVF-Thin.ttf");
}
.hot {
  overflow: auto;
  margin: 0 20px 0 20px;
  background: #f6eeee;
  min-height: 800px;
  box-shadow: 0 10px 10px #718361; /* 添加一个向下的白色阴影 */
}

.hotGrid {
  text-align: left;
  display: flex;
  flex-direction: column;
  margin: 0px 0 20px 0;
}
.hotGrid .grid-item {
  display: flex;
  flex-direction: row;
}
.hotGrid .grid-item .hotRank {
  flex: 1;
  display: flex;
  justify-items: center;
  align-items: center;
  justify-content: space-around;
}
.hotGrid .grid-item .hotDoc {
  flex: 30;

}
.hotTop {
  font-family: NewFont,serif;
  margin: 10px 0 -10px 20px;
  text-align: left;
  display: flex;
  flex-direction: row;
}
.hotTop h1{
  margin-left: 15px;
}

</style>