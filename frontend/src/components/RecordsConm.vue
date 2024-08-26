<template>
  <el-container>
    <el-main>
      <div class="showcase">
        <div v-for="(doc, index) in paginatedDocs" :key="index" class="doc-item">
          <a @click="jumpToDoc(doc.documentId)">
            <img :src="doc.cover ? `data:image/png;base64,${doc.cover}` : image">
            <div class="doc-titles">{{ truncateTitle(doc.title) }}</div>
          </a>
        </div>
      </div>
    </el-main>
    <el-footer class="pagination-footer">
      <el-pagination background layout="prev, pager, next" :page-count="pageCount" v-model:current-page="currentPage" @current-change="fetchRecords" />
    </el-footer>
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import http from '../http';
import { useStore } from 'vuex';
import image from "@/assets/testDocImg.png";

// 使用 useStore 钩子获取 store 实例
const store = useStore();

// 使用 computed 创建响应式状态
const mul_isLoggedIn = computed(() => store.state.mul_isLoggedIn);//判断是否登录
const mul_user = computed(() => store.state.mul_user);//一个user对象
const switch_value = ref(true);
const userId = computed(() => mul_user.value.userID); // 用户id

const records = ref([]);
const currentPage = ref(1);
const itemsPerPage = 15;

const paginatedDocs = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  return records.value.slice(start, start + itemsPerPage);
});

const pageCount = computed(() => {
  return Math.ceil(records.value.length / itemsPerPage);
});

const fetchRecords = async () => {
  try {
    const response = await http.get(`/records/select/${userId.value}`);
    const docs = response.data;

    

    records.value = docs;
  } catch (error) {
    console.error("Error fetching records:", error);
  }
};

const truncateTitle = (title) => {
  const maxLength = 8;
  return title.length > maxLength ? title.slice(0, maxLength) + '...' : title;
};

watch(switch_value, fetchRecords);

onMounted(fetchRecords);
</script>

<script>
export default {
  methods: {
    changeToFavorites() {
      this.$emit("toFavorites");
    },
    jumpToDoc(docid){
      this.$router.push({ path: '/docpreview', query: { data: docid }});
    },
    changeToPublish() {
      this.$emit("changeToPublishPage");
    },

    changeToCreate() {
      this.$emit("changeToCreatePage");
    },

    showSelect() {
      this.showButtons = true;
    }
  }
}
</script>

<style scoped>
.order {
  margin-top: -3%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.showcase {
  margin-top: 3%;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: flex-start; /* 确保文档不满5个时，自左到右排序 */
}

.doc-item {
  flex: 1 0 calc(20% - 10px); /* 5 items per row */
  max-width: calc(20% - 10px);
  text-align: center;
  margin-bottom: 20px;
}

.doc-titles {
  text-align: center;
  color: #000000;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-top: 5px; /* 添加间距，使文字在图片正下方 */
}

.pagination-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60px;
}

/* 修改前后箭头未点击时的背景颜色 */
:deep(.el-pagination .btn-prev),
:deep(.el-pagination .btn-next) {
  background-color: #c0f485 !important;
}

/* 修改未点击时的数字方块背景颜色 */
:deep(.el-pagination .el-pager li:not(.active):not(.disabled):hover) {
  background-color: #588145 !important;
}

/* 未点击时的数字方块背景颜色 */
:deep(.el-pagination .el-pager li:not(.active):not(.disabled)) {
  background-color: #c0f485 !important;
  color: #000000;
}

:deep(.el-pagination.is-background .el-pager li:not(.is-disabled).is-active) {
  background-color: #f6ff00 !important;
}
</style>
