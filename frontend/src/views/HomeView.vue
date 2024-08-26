<template>
  <div class="home" ref="home" v-loading.fullscreen="loading"  style="width: 100%"  element-loading-text="Loading..."
       element-loading-background="transparent">
    <div class="homeTop1" style="display: flex;">
      <div class="rotakeDoc" ref="rotakeDoc"  @mouseover="mouseOver" @mouseout="mouseOut">
        <img v-for="(img, index) in imgs" :key="index" :src="img.src" :class="{ 'active': currentIndex === index }" />
        <div id="dotsContainer" ref="dotContainer">
          <div v-for="(dot, index) in dots" :key="index" class="dot" :class="{ 'active': currentIndex === index }" @click="setCurrentIndex(index);this.resetTimer">

          </div>
        </div>
        <button class="prev-button" @click="prevImage" ref="prev">
          <svg t="1716903957137" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4554" width="20" height="20"><path d="M690.8 126.5l0.7 0.7c12.3 12.3 12.3 32.4 0 44.7l-340 340L691.6 852c12.5 12.5 12.5 32.9 0 45.4s-32.9 12.5-45.4 0L283.9 535s-0.1-0.1-0.2-0.1l-0.7-0.7c-12.3-12.3-12.3-32.4 0-44.7l363-363c12.4-12.3 32.5-12.3 44.8 0z" fill="#4D4D4D" p-id="4555"></path></svg>
        </button>
        <button class="next-button" @click="nextImage" ref="next">
          <svg t="1716903909859" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3552" width="20" height="20"><path d="M584.533333 512l-302.933333 302.933333L341.333333 874.666667l302.933334-302.933334 59.733333-59.733333-59.733333-59.733333L341.333333 145.066667 281.6 209.066667l302.933333 302.933333z" fill="#444444" p-id="3553"></path></svg>
        </button>

      </div>
      <div class="todayHot">
        今&#9;日&#9;热&#9;门
      </div>
      <div class="gridHot" ref="gridHot">
        <div v-for="doc in hotDocsContent" :key="doc.DocumentID" >
          <DocComponent :docid="doc.DocumentID" />
        </div>
      </div>
    </div>
    <div class="homeTop2">
      <div class="recommendDoc">
        <div class="recommendText" ref="recommendText">每&#9;日&#9;推&#9;荐</div>
        <svg t="1717059100307" ref="recommendLine" class="recommendLine" viewBox="0 0 18204 256" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3199" data-spm-anchor-id="a313x.search_index.0.i11.2d4d3a814R6auG"  height="50"><path d="M-292.39808159 109.8009592h151.94185968v804.3980816H-292.39808159zM109.8009592-86.82968367h151.94185968v1206.5971224H109.8009592zM520.93775665 109.8009592h151.94185968v804.3980816H520.93775665zM923.13679742-292.39808159h151.94185968v1608.79616318h-151.94185968zM1334.27359487 109.8009592h151.94185967v804.3980816h-151.94185967zM1736.47263566 109.8009592h151.94185969v804.3980816h-151.94185969zM2147.60943309-86.82968367H2299.55129278v1206.5971224h-151.94185969zM2549.8084739-292.39808159h151.94185967v1608.79616318h-151.94185967zM2960.94527134 109.8009592h151.94185967v804.3980816h-151.94185967zM3363.14431211-292.39808159h151.94185968v1608.79616318h-151.94185968zM3774.28110797 109.8009592h151.94186126v804.3980816h-151.94186126zM4176.48014879-292.39808159h151.94186124v1608.79616318H4176.48014879zM4587.61694621 109.8009592h151.94185968v804.3980816h-151.94185968zM4989.815987-292.39808159h151.9418597v1608.79616318h-151.9418597zM5400.95278444-86.82968367h151.94185969v1206.5971224h-151.94185969zM5803.15182525-86.82968367h151.94185967v1206.5971224h-151.94185967zM6214.28862267 109.8009592h151.94185967v804.3980816h-151.94185967zM6616.48766347-292.39808159H6768.42952315v1608.79616318h-151.94185968zM7018.68670426-86.82968367h151.94185968v1206.5971224h-151.94185968zM7429.82350169-292.39808159h151.94185968v1608.79616318h-151.94185968zM7832.02254249 109.8009592h151.94185968v804.3980816h-151.94185968zM8243.15933993-292.39808159h151.94185968v1608.79616318h-151.94185968zM8645.35838071-86.82968367h151.9418597v1206.5971224H8645.35838071zM9056.49517817 109.8009592h151.94185968v804.3980816h-151.94185968zM9458.69421894-86.82968367h151.94185968v1206.5971224h-151.94185968zM9869.83101638-292.39808159h151.94185967v1608.79616318h-151.94185967zM10272.03005717 109.8009592h151.94185967v804.3980816h-151.94185967zM10683.1668546-86.82968367h151.94185969v1206.5971224h-151.94185969zM11085.36589539-292.39808159h151.94185971v1608.79616318h-151.94185971zM11496.50269127-86.82968367h151.94186125v1206.5971224h-151.94186125zM11898.70173205 109.8009592h151.94186126v804.3980816h-151.94186126zM12309.83852948-86.82968367h151.94185969v1206.5971224H12309.83852948zM12712.03757029 109.8009592h151.94185967v804.3980816h-151.94185967zM13123.17436772 109.8009592h151.94185968v804.3980816h-151.94185968zM13525.37340852-292.39808159h151.94185968v1608.79616318h-151.94185968zM13927.5724493-86.82968367h151.94185969v1206.5971224h-151.94185969zM14338.70924675 109.8009592h151.94185968v804.3980816h-151.94185968zM14740.90828754-86.82968367h151.94185968v1206.5971224h-151.94185968zM15152.04508498-292.39808159h151.94185968v1608.79616318h-151.94185968zM15554.24412576 109.8009592h151.94185967v804.3980816h-151.94185967zM15965.3809232-86.82968367h151.94185968v1206.5971224h-151.94185968zM16367.57996398-86.82968367h151.94185971v1206.5971224h-151.94185971zM16778.71676143 109.8009592h151.94185969v804.3980816H16778.71676143zM17180.91580224-292.39808159h151.94185967v1608.79616318h-151.94185967zM17592.05259967 109.8009592h151.94185967v804.3980816h-151.94185967zM17994.25164045-86.82968367h151.94185969v1206.5971224h-151.94185969zM18405.38843788-86.82968367h151.9418597v1206.5971224h-151.9418597zM18807.58747869 109.8009592h151.94185967v804.3980816h-151.94185967zM19218.72427455 109.8009592h151.94186125v804.3980816h-151.94186125zM19620.92331534-86.82968367h151.94186126v1206.5971224h-151.94186126zM20032.06011277-292.39808159h151.9418597v1608.79616318h-151.9418597zM20434.25915358 109.8009592h151.94185967v804.3980816h-151.94185967zM20836.45819437-86.82968367h151.94185969v1206.5971224h-151.94185969zM21247.59499181-292.39808159h151.94185969v1608.79616318H21247.59499181zM21649.79403259-86.82968367h151.94185969v1206.5971224h-151.94185969zM22060.93083002 109.8009592h151.94185969v804.3980816h-151.94185969zM22463.12987083-292.39808159h151.94185968v1608.79616318h-151.94185968zM22874.26666826-86.82968367h151.94185967v1206.5971224h-151.94185967zM23276.46570906 109.8009592h151.94185968v804.3980816h-151.94185968zM23687.60250647-86.82968367h151.94185972v1206.5971224h-151.94185972zM24089.80154727 109.8009592h151.94185967v804.3980816h-151.94185967zM24500.93834473-86.82968367h151.94185966v1206.5971224h-151.94185966zM24903.1373855 109.8009592h151.94185968v804.3980816h-151.94185968zM25314.27418294-86.82968367h151.94185968v1206.5971224h-151.94185968zM25716.47322373 109.8009592h151.94185968v804.3980816H25716.47322373zM26127.61002117-292.39808159h151.94185969v1608.79616318h-151.94185969zM26529.80906195-86.82968367h151.94185968v1206.5971224h-151.94185968zM26940.94585782 109.8009592h151.94186126v804.3980816h-151.94186126zM27343.14489863 109.8009592h151.94186125v804.3980816h-151.94186125zM27745.34393941-86.82968367h151.94186126v1206.5971224h-151.94186126zM28156.48073684 109.8009592h151.94185969v804.3980816h-151.94185969z" fill="#707070" p-id="3200" data-spm-anchor-id="a313x.search_index.0.i8.2d4d3a814R6auG" class="selected"></path></svg>
      </div>
      <div class="recommendGrid">
        <div v-for="doc in recDocuments" :key="doc.DocumentID" >
          <DocComponent :docid="doc.DocumentID" />
        </div>
      </div>
    </div>
    <div class="homeTop3">
      <div class="newDoc">
        <div class="newText" ref="newText">最&#9;新&#9;文&#9;档</div>
        <svg t="1717059100307" ref="hotLine" class="hotLine" viewBox="0 0 18204 256" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3199" data-spm-anchor-id="a313x.search_index.0.i11.2d4d3a814R6auG"  height="50"><path d="M-292.39808159 109.8009592h151.94185968v804.3980816H-292.39808159zM109.8009592-86.82968367h151.94185968v1206.5971224H109.8009592zM520.93775665 109.8009592h151.94185968v804.3980816H520.93775665zM923.13679742-292.39808159h151.94185968v1608.79616318h-151.94185968zM1334.27359487 109.8009592h151.94185967v804.3980816h-151.94185967zM1736.47263566 109.8009592h151.94185969v804.3980816h-151.94185969zM2147.60943309-86.82968367H2299.55129278v1206.5971224h-151.94185969zM2549.8084739-292.39808159h151.94185967v1608.79616318h-151.94185967zM2960.94527134 109.8009592h151.94185967v804.3980816h-151.94185967zM3363.14431211-292.39808159h151.94185968v1608.79616318h-151.94185968zM3774.28110797 109.8009592h151.94186126v804.3980816h-151.94186126zM4176.48014879-292.39808159h151.94186124v1608.79616318H4176.48014879zM4587.61694621 109.8009592h151.94185968v804.3980816h-151.94185968zM4989.815987-292.39808159h151.9418597v1608.79616318h-151.9418597zM5400.95278444-86.82968367h151.94185969v1206.5971224h-151.94185969zM5803.15182525-86.82968367h151.94185967v1206.5971224h-151.94185967zM6214.28862267 109.8009592h151.94185967v804.3980816h-151.94185967zM6616.48766347-292.39808159H6768.42952315v1608.79616318h-151.94185968zM7018.68670426-86.82968367h151.94185968v1206.5971224h-151.94185968zM7429.82350169-292.39808159h151.94185968v1608.79616318h-151.94185968zM7832.02254249 109.8009592h151.94185968v804.3980816h-151.94185968zM8243.15933993-292.39808159h151.94185968v1608.79616318h-151.94185968zM8645.35838071-86.82968367h151.9418597v1206.5971224H8645.35838071zM9056.49517817 109.8009592h151.94185968v804.3980816h-151.94185968zM9458.69421894-86.82968367h151.94185968v1206.5971224h-151.94185968zM9869.83101638-292.39808159h151.94185967v1608.79616318h-151.94185967zM10272.03005717 109.8009592h151.94185967v804.3980816h-151.94185967zM10683.1668546-86.82968367h151.94185969v1206.5971224h-151.94185969zM11085.36589539-292.39808159h151.94185971v1608.79616318h-151.94185971zM11496.50269127-86.82968367h151.94186125v1206.5971224h-151.94186125zM11898.70173205 109.8009592h151.94186126v804.3980816h-151.94186126zM12309.83852948-86.82968367h151.94185969v1206.5971224H12309.83852948zM12712.03757029 109.8009592h151.94185967v804.3980816h-151.94185967zM13123.17436772 109.8009592h151.94185968v804.3980816h-151.94185968zM13525.37340852-292.39808159h151.94185968v1608.79616318h-151.94185968zM13927.5724493-86.82968367h151.94185969v1206.5971224h-151.94185969zM14338.70924675 109.8009592h151.94185968v804.3980816h-151.94185968zM14740.90828754-86.82968367h151.94185968v1206.5971224h-151.94185968zM15152.04508498-292.39808159h151.94185968v1608.79616318h-151.94185968zM15554.24412576 109.8009592h151.94185967v804.3980816h-151.94185967zM15965.3809232-86.82968367h151.94185968v1206.5971224h-151.94185968zM16367.57996398-86.82968367h151.94185971v1206.5971224h-151.94185971zM16778.71676143 109.8009592h151.94185969v804.3980816H16778.71676143zM17180.91580224-292.39808159h151.94185967v1608.79616318h-151.94185967zM17592.05259967 109.8009592h151.94185967v804.3980816h-151.94185967zM17994.25164045-86.82968367h151.94185969v1206.5971224h-151.94185969zM18405.38843788-86.82968367h151.9418597v1206.5971224h-151.9418597zM18807.58747869 109.8009592h151.94185967v804.3980816h-151.94185967zM19218.72427455 109.8009592h151.94186125v804.3980816h-151.94186125zM19620.92331534-86.82968367h151.94186126v1206.5971224h-151.94186126zM20032.06011277-292.39808159h151.9418597v1608.79616318h-151.9418597zM20434.25915358 109.8009592h151.94185967v804.3980816h-151.94185967zM20836.45819437-86.82968367h151.94185969v1206.5971224h-151.94185969zM21247.59499181-292.39808159h151.94185969v1608.79616318H21247.59499181zM21649.79403259-86.82968367h151.94185969v1206.5971224h-151.94185969zM22060.93083002 109.8009592h151.94185969v804.3980816h-151.94185969zM22463.12987083-292.39808159h151.94185968v1608.79616318h-151.94185968zM22874.26666826-86.82968367h151.94185967v1206.5971224h-151.94185967zM23276.46570906 109.8009592h151.94185968v804.3980816h-151.94185968zM23687.60250647-86.82968367h151.94185972v1206.5971224h-151.94185972zM24089.80154727 109.8009592h151.94185967v804.3980816h-151.94185967zM24500.93834473-86.82968367h151.94185966v1206.5971224h-151.94185966zM24903.1373855 109.8009592h151.94185968v804.3980816h-151.94185968zM25314.27418294-86.82968367h151.94185968v1206.5971224h-151.94185968zM25716.47322373 109.8009592h151.94185968v804.3980816H25716.47322373zM26127.61002117-292.39808159h151.94185969v1608.79616318h-151.94185969zM26529.80906195-86.82968367h151.94185968v1206.5971224h-151.94185968zM26940.94585782 109.8009592h151.94186126v804.3980816h-151.94186126zM27343.14489863 109.8009592h151.94186125v804.3980816h-151.94186125zM27745.34393941-86.82968367h151.94186126v1206.5971224h-151.94186126zM28156.48073684 109.8009592h151.94185969v804.3980816h-151.94185969z" fill="#707070" p-id="3200" data-spm-anchor-id="a313x.search_index.0.i8.2d4d3a814R6auG" class="selected"></path></svg>
      </div>
      <div class="newGrid">
        <div v-for="doc in newDocuments" :key="doc.DocumentID" >
          <DocComponent :docid="doc.DocumentID" />
        </div>
      </div>
    </div>
  </div>
</template>Y

<script>
// @ is an alias to /src
import HomeBackground from '@/components/HomeBackground.vue';
import {mapActions, mapState} from "vuex";
import DocComponent from "@/components/DocComponent";
import http from '../http.js';

export default {

  name: 'HomeView',
  components: {
    DocComponent
  },
  data() {
    return {
      text:'',
      imgCount: 0,
      dotContainer: null,
      imgs: [
        {src: require('../assets/homeViewAsserts/3-se.png')},
        {src: require('../assets/homeViewAsserts/5-r.png')},
        {src: require('../assets/homeViewAsserts/4-akli.png')},
      ], // 图片数据
      dots: [],
      currentIndex: 0, // 当前显示的图片索引
      intervalId: null, // 用于存储定时器的 ID
      items: [],
      gridHeight: 0,
      hotDocsContent: [],
      recDocuments:[],
      newDocuments:[],
      loading: false, // 是否正在加载数据
    }
  },
  computed:{
    ...mapState(['mul_isLoggedIn', 'mul_user'])
  },
  mounted() {
    this.loading=true;
    http.get('/Doc/getHotDocsToday')
        .then(response => {
          this.hotDocsContent=response.data;
        })
        .catch(error => {
          console.error(error);
        });
    http.get('/Doc/getNewDocs')
        .then(response => {
          this.newDocuments=response.data;
        })
        .catch(error => {
          console.error(error);
        });
    if(this.mul_isLoggedIn){//已登录
      http.get('/Doc/getRecDocs/' + this.mul_user.userID)
          .then(response => {
            this.recDocuments = response.data;
            this.loading=false;
          })
          .catch(error => {
            console.error(error);
          });
    }else {//未登录
      http.get('/Doc/getRecDocs/' + -1)
          .then(response => {
            this.recDocuments = response.data;
            this.loading=false;
          })
          .catch(error => {
            console.error(error);
          });
    }
    this.dots = Array.from({ length: this.imgs.length });
    this.dotContainer = document.getElementById("dotsContainer");
    this.handelRotake();
    window.addEventListener('resize',this.handelRotake);
    this.intervalId = setInterval(this.nextImage, 2000);
    this.handelDotContainer();
    window.addEventListener('resize',this.handelDotContainer);
    this.$nextTick(() => {
      this.handelWidth();
    });
    window.addEventListener('resize',this.handelWidth);
  },
  methods: {
    getHotDocId(index){
      if (this.hotDocsContent.length > 0 &&
          index < 8) {
        return this.hotDocsContent[index].DocumentID;
      }
      return 0;
    },
    handelWidth(){
      if(this.$refs.recommendText&&this.$refs.home&&this.$refs.recommendLine
      &&this.$refs.hotLine){
        const text = this.$refs.recommendText;
        const home = this.$refs.home;
        const line =this.$refs.recommendLine;
        const hotLine =this.$refs.hotLine;
        line.style.width=`${home.clientWidth-text.clientWidth-60}px`;
        hotLine.style.width = `${home.clientWidth-text.clientWidth-60}px`;
      }
    },
    handelRotake(){
      if(this.$refs.rotakeDoc){
        const rotake = this.$refs.rotakeDoc;
        const hight = rotake.style.width;
        rotake.style.height = `${hight * (3 / 5)}`;
      }
    },
    handelDotContainer(){
      this.$nextTick(()=>{
        if(this.$refs.dotContainer && this.$refs.rotakeDoc &&
        this.$refs.next && this.$refs.prev){
          const rotake = this.$refs.rotakeDoc;
          const dotContainer = this.$refs.dotContainer;
          const x = rotake.getBoundingClientRect().x;
          const width = rotake.getBoundingClientRect().width;
          const width2 = dotContainer.clientWidth;
          const height = dotContainer.clientHeight;
          const height2 = rotake.clientHeight;
          dotContainer.style.left = `${width/2-width2/2-20}px`;
          dotContainer.style.top = `${height2+11}px`;
          const next = this.$refs.next;
          const prev = this.$refs.prev;
          next.style.left = `${width2/2-10}px`;
          prev.style.right = `${width2/2+10}px`;
          console.log(window.innerWidth);
          if(window.innerWidth>=1501){
            next.style.top = `405px`;
            prev.style.top = `405px`;
          }else {
            next.style.top = `5px`;
            prev.style.top = `5px`;
          }

        }
      })
    },
    setCurrentIndex(index) {
      this.currentIndex = index;
    },
    nextImage() {
      this.currentIndex++;
      if (this.currentIndex >= this.imgs.length) {
        this.currentIndex = 0;
      }
      this.resetTimer(); // 重置定时器
    },
    prevImage() {
      this.currentIndex--;
      if (this.currentIndex < 0) {
        this.currentIndex = this.imgs.length - 1;
      }
      this.resetTimer(); // 重置定时器
    },
    resetTimer() {
      clearInterval(this.intervalId);
      this.intervalId = setInterval(this.nextImage, 2000);
    },
  },

}
</script>
<style>

@font-face {
  font-family: "NewFont";
  src: url("../assets/AlimamaFangYuanTiVF-Thin.ttf");
}


.home {

  background: #f6eeee;
  min-height: 800px;
  box-shadow: 0 10px 10px #718361; /* 添加一个向下的白色阴影 */
  display: flex;
  flex-direction: column;
}

.gridHot {
  flex: 16;
  position: relative;
  display: grid;
  margin: 15px 15px 0 2%;
  grid-template-columns: auto auto; /* 创建四列 */
  grid-column-gap: 4%;
  grid-row-gap: 10px;
  border-width: 1px;
  border-color: #6e6a6a;
  border-style: solid;
  border-radius: 8px;
  padding: 25px 0 25px 0;
  background-color: #e7e2e2;
  box-shadow: 1px 1px 1px #bebdbd;
  overflow: auto;
}

.gridHot::-webkit-scrollbar {
  margin: 3px 3px 3px 0;

}
.gridHot::-webkit-scrollbar-track {
  background-color: #e7e2e2;
  margin: 3px 3px 3px 0;
  border-radius: 8px;
}
.gridHot::-webkit-scrollbar-thumb {
  background-color: #bdbebc;

  margin: 3px 3px 3px 0;
}


.gridHot div {
  position: relative;

}
.rotakeDoc img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: fill;
  border-radius: 5px;
  opacity: 0;
  transition: opacity 0.5s ease-in-out;
}
.rotakeDoc img.active {
  opacity: 1;
}

#dotsContainer {
  display: flex;
  flex-direction: row;
  position: absolute;
}

#dotsContainer .dot {
  margin-left: 20px;
  height: 10px;
  width: 10px;
  background: #6e6a6a;
  border-radius: 5px;
  transition: transform 0.3s ease-in-out;
}
#dotsContainer .dot:hover {
  transform: scale(1.1);
}
#dotsContainer .dot.active {
  background: #070707;
}

.next-button,.prev-button {
  position: relative;
  background: transparent;
  border: none;
}
.next-button svg,.prev-button svg {
  transition: transform 0.3s ease-in-out;
}
.next-button svg:hover,.prev-button svg:hover {
  transform: scale(1.2);
}
.recommendGrid div,.newGrid div,.gridHot div {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}

.recommendGrid,.newGrid {
  margin-top: 50px;
  grid-template-columns: repeat(4,auto);
  display: grid;
  grid-row-gap: 20px;
}
@media screen and (min-width: 1750px) {
  .recommendGrid,.newGrid {
    grid-template-columns: repeat(5,auto) ;
  }
}
@media screen and (max-width: 1340px) and (min-width: 1051px) {
  .recommendGrid,.newGrid {
    grid-template-columns: repeat(3,auto) ;
  }
}
@media screen and (max-width: 1050px) and (min-width: 769px) {
  .recommendGrid,.newGrid {
    grid-template-columns: repeat(2,auto) ;
  }
}
@media screen and (max-width: 768px) {
  .recommendGrid,.newGrid {
    grid-template-columns: repeat(1, auto);
  }
}
@media screen and (min-width: 491px) and (max-width: 550px) {
  .recommendGrid,
  .newGrid,
  .gridHot {
    grid-template-columns: repeat(1, auto);
  }
}

@media screen and (min-width: 311px) and (max-width: 490px) {
  .recommendGrid,
  .newGrid,
  .gridHot {
    grid-template-columns: repeat(1, auto);
  }

}

@media screen and (min-width: 100px) and (max-width: 310px) {
  .recommendGrid,
  .newGrid,
  .gridHot {
    grid-template-columns: repeat(1, auto);
  }
}

/*小屏情况*/
@media (max-width: 1000px) {

  .rotakeDoc {
    position: relative;
    margin: 5% 10% 0 10%;
    border: black 1px solid;
    width: 80%;
    padding-top: 50%; /* 设置高度为宽度的 60% (80% * 3 / 5 = 32%) */
    border-radius: 6px;
  }

  .homeTop1 {
    display: flex;
    flex-direction: column;
  }
  .todayHot {
    font-family: NewFont,serif;
    flex: 0.5;
    text-align: center;
    font-size: 50px;
    margin-top: 25px;
  }

  .recommendDoc {
    font-family: NewFont,serif;
    font-size: 50px;
    text-align: left;
    margin: 15px 15px  0 35px;
    display: flex;
    flex-direction: row;
  }
  .recommendText {
    width: 250px;
  }
  .recommendLine {
    display: none;
  }
  .hotLine {
    display: none;
  }


}
/*宽屏情况*/
@media (min-width: 1001px) {
  @media (min-width: 1501px) {
    .rotakeDoc {
      flex: 14;
      position: relative;
      margin: 20px 0 0 20px;
      border: black 1px solid;
      min-height: 400px;
      border-radius: 6px;
    }

  }
  @media (max-width: 1500px) {
    .rotakeDoc {
      flex: 14;
      position: relative;
      margin: 20px 0 0 20px;
      border: black 1px solid;
      padding-top: 25%; /* 设置高度为宽度的 80% (40% * 3 / 5 = 32%) */
      border-radius: 6px;
    }
  }
  .homeTop1 {
    display: flex;
    height: 400px;
  }
  .todayHot {
    font-family: NewFont,serif;
    flex: 0.5;
    text-align: center;
    font-size: 40px;
    text-orientation: upright;
    writing-mode: vertical-rl;
    margin-left: 3%;
    margin-top: 15px;
    border-radius: 2px;
  }

  .recommendDoc {
    font-family: NewFont,serif;
    font-size: 50px;
    text-align: left;
    margin: 15px 15px  0 35px;
    display: flex;
    flex-direction: row;
  }
  .recommendText {
    width: 250px;
  }
  .recommendLine {
    position: absolute;
    margin-top: 5px;
    margin-left: 250px;
  }

  .hotLine{
     position: absolute;
     margin-top: 5px;
     margin-left: 250px;
   }

}
.homeTop2 {
  display: flex;
  flex-direction: column;
  margin: 50px 20px 20px 20px;
}
.homeTop3 {
   margin: 50px 20px 20px 20px;
 }
.newDoc {
  font-family: NewFont,serif;
  font-size: 50px;
  text-align: left;
  margin: 15px 15px  0 35px;
  display: flex;
  flex-direction: row;
}




</style>



