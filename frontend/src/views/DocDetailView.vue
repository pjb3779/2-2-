<template>
  <div class="docDetailView">
    <h1>{{ title }}</h1>
    <div>{{ content }}</div>
  </div>
</template>

<script>
import http from "@/http";

export default {
  name: "DocDetailView",

  data(){
    return {
      title:'',
      content:'',
      docid: 0,
    }
  },
  mounted() {
    this.docid=this.$route.query.data;
    http.get('/Doc/getDocById/'+this.docid)
        .then(response => {
          this.content = response.data[0].Content;
          this.title = response.data[0].Title;
        })
        .catch(error => {
          console.error(error);
        });
  },
  methods: {

  }
}
</script>

<style scoped>
.docDetailView{
  overflow: auto;
  margin: 0 20px 0 20px;
  background: #f6eeee;
  min-height: 800px;
  box-shadow: 0 10px 10px #718361; /* 添加一个向下的白色阴影 */
}
</style>