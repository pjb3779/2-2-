<template>
  <div class="docComponent2" @click="jumpToDocPre">
    <el-image  @click="jumpToDocPre" class="img"  :src="url" :fit="'fill'" />
    <div id="span">
      <div class="Title"><p>{{ title }}</p></div>
      <div class="Author"><p>{{ this.author.userName }}</p></div>
      <div class="Tags">
        <el-tag type="primary">{{ tags }}</el-tag>
      </div>
      <div class="Summary">{{ summary }}</div>
    </div>
  </div>
</template>

<script>
import image from "@/assets/testDocImg.png";
import http from "@/http";

export default {
  name: "DocComponent2",
  props : {
    docid:Number,
  },
  data(){
    return {
      url:"",
      summary:'',
      title:'',
      tags:'',
      authorID:'',
      author:{},
      ImgData:"",
    }
  },
  watch: {

  },
  mounted(){
    http.get('/Doc/getDocById/'+this.docid)
        .then(response => {
          console.log(response.data[0]);
          this.summary=response.data[0].summary;
          this.title=response.data[0].title;
          this.tags=response.data[0].tags;
          this.authorID=response.data[0].AuthorID;
          if(response.data[0].cover!==null){
            this.ImgData=response.data[0].cover;
            this.url="data:image/png;base64,"+this.ImgData;
          }else {
            this.url=image;
          }
          http.get('/getUsrByID/'+this.authorID)
              .then(response => {
                this.author=response.data[0];
              })
        })
        .catch(error => {
          console.error(error);
        });
  },
  methods: {
    jumpToDocPre(){
      this.$router.push({ path: '/docpreview', query: { data: this.docid }});
    },
  },
}
</script>

<style scoped>
@font-face {
  font-family: "cha";
  src: url("../assets/TsangerYuYangT_W02_W02.ttf");
}
.docComponent2 {
  font-family: cha,serif;
  background-color: #f6eeee;
  display: flex;
  flex-direction: row;
  border-width: 1px 0 1px 0;
  border-style: solid;
  border-color: transparent;
  transition: 0.4s ease-out;
  padding: 5px 0 5px 0;
}
.docComponent2:hover {
  background-color: #ece3e3;
  border-color: #d2cbcb;
  transform: translateY(-2%);
  box-shadow: 1px 1px 10px 2px #CCC;
}
#span {
  flex: 10;
  margin-left: 15px;
  margin-right: 10px;
  display:flex;
  flex-direction: column;
}
.img {
  flex: 1;
  margin-left: 25px;
  border-radius: 3px;
}
.Title {
  font-weight: 600;
}
.Summary {
  margin-top: 10px;
}
.Author {
  margin-top: -25px;
  font-size: 15px;
}
.Tags el-tag {
  min-width: 100%;
  width: 100%;
}

</style>