<template>
  <div class="docComponent" >
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
import image from '../assets/testDocImg.png';
import http from "@/http";
export default {

  name: 'DocComponent',
  props: {
    docid: Number,
  },
  data(){
    return {
      url:"",
      summary:'',
      title:'',
      tags:'',
      authorID:'',
      author:{},
      ImgData:'',
    }
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
            const blob = new Blob([this.ImgData], { type: 'image/png' });
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
  }
}
</script>

<style scoped>
@font-face {
  font-family: "cha";
  src: url("../assets/TsangerYuYangT_W02_W02.ttf");
}
.docComponent {
  width: 300px;
  height: 160px;
  display: flex;
  flex-direction: row;
  background-color: rgb(243, 241, 236);
  transition-duration: 0.6s;
}
.docImg{
  margin: 0;
}
.img {
  width: 40%;
  height: 100%;
  border-radius: 3px;
  margin-right: -2px;
  transition-duration: 0.4s;
  border-color: transparent;
  border-style: solid;
  z-index: 10;
}
.docComponent:hover{
  transform: translateY(-2%);
  box-shadow: 1px 1px 10px 2px #CCC;
}
#span {
  width: 60%;
  height: 100%;
  text-align: center;
  border-radius: 3px 5px 5px 3px ;
  background-color: rgb(243, 241, 236);
  display: flex;
  flex-direction: column;
  column-gap: 5px;
  font-family: cha,serif ;
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