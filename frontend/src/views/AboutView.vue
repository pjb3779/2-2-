<template>
  <div class="about">
    <div class="categories">
      <button
          v-for="category in categories"
          :key="category"
          @click="fetchDocuments(category.id)"
          :class="{'on': category.id===curcategory }">
        {{ category.name }}
      </button>
    </div>
    <div class="selectedCategory">
      <div class="selectedCategoryHeader">
        <el-divider content-position="center" style="background-color: #f6eeee"></el-divider>
        <div class="selectType">
          <el-segmented v-model="value" :options="options">
            <template #default="{ item }">
              <div class="flex flex-col items-center gap-2 p-2">
                <el-icon size="25" style="margin: 10px 5px 5px 5px ">
                  <component :is="item.icon" />
                </el-icon>
              </div>
            </template>
          </el-segmented>
          <el-tag style="margin: 5px 0 0 5px;color: #070707" size="large" type="info">{{ value }}</el-tag>
        </div>

      </div>
      <div class="selectedGrid" v-infinite-scroll="loadDocs" :infinite-scroll-distance="50"
           v-loading.fullscreen="loading"  style="width: 100%"  element-loading-text="Loading..."
           element-loading-background="transparent">
        <div v-for="document in documents"
            :key="document.DocumentID"
            class="grid-item">
          <DocComponent2 :docid="document.DocumentID" />
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { ref } from "vue";
import axios from "axios";
import DocComponent from "@/components/DocComponent";
import normalUp from "@/components/IconComponents/normalUp";
import TimeDown from "@/components/IconComponents/TimeDown";
import TimeUp from "@/components/IconComponents/TimeUp";
import hotDown from "@/components/IconComponents/hotDown";
import hotUp from "@/components/IconComponents/hotUp";
import DocComponent2 from "@/components/DocComponent2";
import http from "@/http";

export default {
  components: {
    DocComponent2,
    DocComponent,
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
      type: 0,
      page: 1, // 当前页码
      pageSize: 3, // 每页显示的文档数量
      loading: false, // 是否正在加载数据
    };
  },
  watch: {
    value(newvalue,oldvalue ) {
      //监视选择器选择的排序方式
      if (newvalue === '最新') {
        this.type = 0;
      } else if (newvalue === '最晚') {
        this.type = 1;
      } else if (newvalue === '热度降序') {
        this.type = 2;
      } else if (newvalue === '热度升序') {
        this.type = 3;
      } else if (newvalue === '综合') {
        this.type = 4;
      }
      this.loading=true;
      http.get('/Doc/getDocs/'+this.type+"/"+this.curcategory)
          .then(response => {
            this.documents=[];
            this.documents=response.data;
            this.loading=false;
          })
          .catch(error => {
            console.error(error);
          });
    },
  },
  mounted() {
    this.loading=true;
    http.get('/Doc/getDocs/'+this.type+"/"+this.curcategory)
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
    fetchDocuments(categoryID) {
      this.selectedCategory = this.categories.find(
          (category) => category.id === categoryID
      ).name;
      this.curcategory=categoryID;
      this.loading=true;
      http.get('/Doc/getDocs/'+this.type+"/"+this.curcategory)
          .then(response => {
            this.documents=[];
            this.documents=response.data;
            this.loading=false;
          })
          .catch(error => {
            console.error(error);
          });
    },
    fetchNewDocuments() {
      axios
          .get("/api/documents/new")
          .then((response) => {
            this.newDocuments = response.data;
          })
          .catch((error) => {
            console.error(
                "There was an error fetching the new documents!",
                error
            );
          });
    },
    createDocument(document) {
      axios.post("/api/documents", document)
          .then((response) => {
            console.log("Document created:", response.data);
          })
          .catch((error) => {
            console.error("There was an error creating the document!", error);
          });
    },
    updateDocument(documentID, document) {
      axios
          .put(`/api/documents/${documentID}`, document)
          .then((response) => {
            console.log("Document updated:", response.data);
          })
          .catch((error) => {
            console.error("There was an error updating the document!", error);
          });
    },
    deleteDocument(documentID) {
      axios
          .delete(`/api/documents/${documentID}`)
          .then((response) => {
            console.log("Document deleted");
          })
          .catch((error) => {
            console.error("There was an error deleting the document!", error);
          });
    },
  },

};
</script>

<style scoped>
.about {
  overflow: auto;
  margin: 0 20px;
  background: #f6eeee;
  min-height: 800px;
  box-shadow: 0 10px 10px #718361; /* 添加一个向下的白色阴影 */
}

.categories {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: stretch;
  margin: 20px 20px 0 20px;
}
.categories .on{
  background-color: #497358;
}

.categories button {
  flex: 0 0 15%;
  margin: 5px 0;
  padding: 15px;
  background-color: #82c8a0;
  color: white;
  border: none;
  cursor: pointer;
  text-align: center;
  box-sizing: border-box;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 0.4s ease-out;
}

.categories button:hover {
  background-color: #497358;
  transform: translateY(-2%);
  box-shadow: 1px 1px 10px 2px #CCC;
}

.selectedCategory {
  margin-top: 20px;
  margin-bottom: 20px;
}

.selectedCategoryHeader {
  font-family: "Arial", sans-serif;
  font-size: 24px;
  margin-bottom: 20px;
}
.selectType .el-segmented {
  --el-segmented-item-selected-bg-color: #d3dede;
  --el-segmented-item-selected-color: #171616;
  --el-segmented-bg-color: transparent;
  --el-border-radius-base: 8px;
  font-weight: 600;
}

.selectType {
  font-family: "Quicksand", sans-serif;
  margin-top: -10px;
  font-size: 25px;
  left: 20px;
  display: flex;
  flex-direction: row;
}

.selectedCategoryText {
  font-weight: bold;
}

.selectedGrid {
  text-align: left;
  display: flex;
  flex-direction: column;
}



.homeTop3 {
  margin-top: 20px;
}

.newDoc {
  font-family: "Arial", sans-serif;
  font-size: 24px;
  margin-bottom: 20px;
}

.newText {
  font-weight: bold;
}

.newGrid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  grid-gap: 10px;
}
</style>
