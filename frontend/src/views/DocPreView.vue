<template>
  <div class="docPreView" ref="docPreView">
    <h1>{{ title }}</h1>
    <p>{{ docid }}</p>
    <p>{{ summary }}</p>
    <p>{{ tags }}</p>
    <p></p>
    <button @click="jumpToDocDetail">开始</button>
  </div>
</template>

<script>
import http from "@/http";
import {mapActions, mapState} from "vuex";

export default {
  name: "DocPreView",
  data() {
    return{
      docid:0,
      content:"",
      summary:"",
      title: "",
      tags:"",
      showDialog:false,
    }
  },
  computed: {

  },
  mounted() {
    this.docid=this.$route.query.data;
    http.get('/Doc/getDocById/'+this.docid)
        .then(response => {
          this.summary=response.data[0].Summary;
          this.title=response.data[0].Title;
          this.tags=response.data[0].Tags;
        })
        .catch(error => {
          console.error(error);
        });
  },
  methods: {
    jumpToDocDetail(){
      this.$router.push({ path: '/docpreview/docdetailview', query: { data: this.docid }});
    },
  },
}
</script>

<style scoped>
  .docPreView{
    overflow: auto;
    margin: 0 20px 0 20px;
    background: #f6eeee;
    min-height: 800px;
    box-shadow: 0 10px 10px #718361; /* 添加一个向下的白色阴影 */
  }
</style>