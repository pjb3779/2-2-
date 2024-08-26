<template>
  <!-- 本组件用于显示“个人创作中心”的顶部部分 -->
    <div class="header">
      <!-- 顶部容器 -->
      <div class="left-section">
        <!-- 左侧部分，包含标题和子标题 -->
        <img src="../images/title.png" id="title" />
        <img src="../images/logo.jpg" id="logo" />
        <a class="subtitle" href="">返回主页</a> <!-- 子标题 -->

      </div>

      <div class="right-section">
        <!-- 右侧部分，包含通知图标、发布按钮和个人资料图标 -->
        <img class="profile-icon" /> <!-- 个人资料图标 -->
        <div id="day">在悦读纪大陆的第{{ days }}天</div>
        <div id="split"></div>
        <img src="../images/msg.png" id="msgbox"/>
      </div>
    </div>
</template>
  
<script>
  export default {
    name: 'TopBar',// 组件名
    data() {
      return {
        days:0
      }
    }
  }
</script>
  
<style scoped>

  .header {
    position: fixed;
    display: flex; /* 使用flex布局 */
    width:100%;
    justify-content: space-between; /* 两端对齐 */
    align-items: center; /* 垂直居中 */
    background-color: #d5fde4;
    padding: 12px; /* 内边距 */
    margin: 0;
    border-bottom: 1px solid #92f1ac;
    z-index: 999;
  }
  
  .left-section{
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
    margin-left: 15px;
  }
  
  .left-section .subtitle {
    font-size: 16px; /* 子标题字体大小 */
    color: #808080; /* 灰色字体 */
    text-decoration: none;
    margin-left: 8px;
  }
  
  .left-section .subtitle:hover{
    font-size: 16px; /* 子标题字体大小 */
    color: #5b5a5a; /* 灰色字体 */
    text-decoration: none;
    margin-left: 8px;
  }
  #title {
    width: 240px; /* Logo宽度 */
    height: auto; /* Logo高度自适应 */
    margin-right: 8px; /* 与标题的间距 */
  }
  #logo{
    width: 22px;
    height: auto;
  }
  .right-section {
    display: flex; /* 使用flex布局 */
    align-items: center; /* 垂直居中 */
    justify-content: center; /* 水平居中 */
    margin-right: 80px;
  }
  .profile-icon {
    width: 40px; /* 宽度 */
    height: 40px; /* 高度 */
    background-color: #ccc; /* 灰色背景 */
    border-radius: 50%; /* 圆形 */
    margin-right: 30px;
  }

  #day{
    padding: 5px 30px;
    font-size: 1rem;
    color: rgb(111, 234, 185);
    margin-right: 40px;
    border-style: solid;
    border-color: #0ce1a5;
    border-width: 1px;
    border-radius: 20px;
    background-image: linear-gradient(to right, #cff7eb 0%, #d0f8f4 99%, #e2f9f7 100%);
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: transform 0.3s ease;
  }
  
  #split{
    width: 1px;
    height: 16px;
    background: #e7e7e7;
    margin-right: 32px;
  }
  

  #msgbox{
    height: 19px;
    width: 25px;
  }

  #msgbox:hover{
    transform: translateY(2px);
  }
  
</style>
  