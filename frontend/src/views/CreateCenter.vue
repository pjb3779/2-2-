<template>
  <div class="container">
    <div class="bottom_center">
      <LeftContent class="fix" id="left" @changeToMainPage="toMainPage"
      @changeToPublishPage="toPublishPage"    
      @changeToCreatePage="toCreatePage"   
      @changeToDraftBox="toDraftBox"  
      @changeToDocManage="toDocManage"
      />
      <component 
      :is="tabComponent" 
      ></component>
      
    </div>
  </div>

</template>

<script>
import TopContent from "../components/TopContent.vue";
import LeftContent from "../components/LeftContent.vue"
import MainPage from "../components/MainPage.vue"
import PublishContent from "../components/PublishContent.vue"
import CreatePage from "../components/CreatePage.vue"
import DraftBox from "../components/DraftBox.vue"
import DocManage from "../components/DocManage.vue"


export default {
  data(){
    return {
      tabComponent:MainPage ,
      draftID:0,
      draftContent:'',
    }
  },

  components:{
    TopContent,
    LeftContent,
    MainPage,
    PublishContent,
    CreatePage,
    DraftBox,
    DocManage,
  },

  methods:{
    toMainPage(){
      this.tabComponent=MainPage;
      console.log("111");
    },
    toPublishPage(){
      this.tabComponent=PublishContent;
    },
    toCreatePage(){
      this.tabComponent=CreatePage;
    },
    toDraftBox(){
      this.tabComponent=DraftBox;
    },
    toDocManage(){
      this.tabComponent=DocManage;
    }
  },
}

</script>

<style scoped>

  .container {
    overflow: auto;
    background: #f6eeee;
    min-height: 856px;
    box-shadow: 0 10px 10px #718361; /* 添加一个向下的白色阴影 */
    text-align: left;
  }


  .bottom_center {
    flex: 1; /* 占据剩余空间的全部 */
    display: flex;
  }

  .right {
    flex: 1; /* 占据剩余空间的一半 */
  }
  swiperDemo{
    position: absolute;
  }
</style>