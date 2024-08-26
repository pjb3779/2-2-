<template>
  <div class="homeBack">
  <div class="top" ref="top" id="top">
  <nav>
    <div style="display: flex;" class="topline1">
    <div class="logo">
      <router-link to="/home"><img src=".\assets\Ydera2.svg" alt="Logo" /></router-link>
    </div>
      <button @click="toggleElements" class="expandButton">
        <svg v-if="!isExpanded" t="1716301298052" class="icon1" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3701" width="20" height="20"><path d="M512 712.106667a35.84 35.84 0 0 1-26.453333-11.52L264.533333 465.066667a35.84 35.84 0 1 1 52.48-49.066667L512 623.786667l194.56-207.36a35.84 35.84 0 1 1 52.48 49.066666l-221.44 235.52a35.84 35.84 0 0 1-25.6 11.093334zM512 0a512 512 0 1 0 512 512A512 512 0 0 0 512 0z m0 72.106667a439.466667 439.466667 0 0 1 170.666667 844.373333A439.466667 439.466667 0 0 1 200.533333 200.533333a438.613333 438.613333 0 0 1 311.04-128z" fill="#5E5C5C" p-id="3702"></path></svg>
        <svg v-if="isExpanded" t="1716304326263" class="icon2" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4675" width="20" height="20"><path d="M512 318.72a35.84 35.84 0 0 0-26.453333 11.52L264.533333 565.76a35.84 35.84 0 1 0 52.48 49.066667L512 407.466667l194.56 207.36a35.84 35.84 0 0 0 52.48-49.066667L537.6 330.24a35.84 35.84 0 0 0-25.6-11.52zM512 0a512 512 0 1 0 512 512A512 512 0 0 0 512 0z m0 72.106667a439.466667 439.466667 0 0 1 170.666667 844.373333A439.466667 439.466667 0 0 1 200.533333 200.533333a438.613333 438.613333 0 0 1 311.04-128z" fill="#5E5C5C" p-id="4676"></path></svg>
      </button>
    <div style="display: flex;" class="topline2">
      <router-link to="/home" @click="adjustPaddingTop" ><div >
        <svg t="1716019237814" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6861" width="20" height="20"><path d="M992.7 493.1L554.3 54.8c-11.6-11.6-27-16.9-42.3-16.3-15.3-0.6-30.7 4.7-42.3 16.3L31.3 493.1c-22.1 22-22.1 58.1 0 80.2 22 22.1 58.1 22.1 80.2 0L512 172.8l400.5 400.5c22.1 22.1 58.1 22.1 80.2 0 22-22.1 22-58.1 0-80.2z" fill="#515151" p-id="6862"></path><path d="M144.4 604.1V941c0 27.6 18.4 44.5 46 44.5h193V725.1c0-31.3 23.1-53.6 54.5-53.6H584c31.3 0 59.4 22.3 59.4 53.6v260.4h188.1c27.6 0 46.9-16.9 46.9-44.5V595.4l-362.6-362-371.4 370.7z" fill="#272536" p-id="6863"></path></svg>
        &#9;H&#9;o&#9;m&#9;e
      </div></router-link>
      <router-link v-if="isExpanded" to="/about" @click="adjustPaddingTop" ><div>
        <svg t="1716021447650" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="7893" width="20" height="20"><path d="M600.76158 68.191078 245.714238 68.191078c-49.040926 0-88.317465 39.720653-88.317465 88.76158l-0.444115 710.09366c0 49.040926 39.277562 88.76158 88.317465 88.76158l533.014616 0c49.040926 0 88.76158-39.720653 88.76158-88.76158L867.046319 334.476841 600.76158 68.191078zM689.523159 778.284739 334.475817 778.284739l0-88.76158 355.047342 0L689.523159 778.284739zM689.523159 600.76158 334.475817 600.76158l0-88.76158 355.047342 0L689.523159 600.76158zM556.38079 378.857631 556.38079 134.762263 800.475134 378.857631 556.38079 378.857631z" fill="#272536" p-id="7894"></path></svg>
        D&#9;o&#9;c&#9;s&#9;
      </div> </router-link>
      <router-link v-if="isExpanded" to="/hot" @click="adjustPaddingTop"><div>
        <svg t="1716021634655" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="10072" width="20" height="20"><path d="M831.98135 222.495164c-141.644987 40.035749-167.647866 153.418157-158.468196 225.513659-101.125291-117.707473-96.992242-253.056024-96.992242-448.001994-324.092873 121.073151-248.729495 470.01547-258.436995 575.995335-81.516442-66.087548-96.945123-223.975759-96.945123-223.975759-86.082596 43.833075-129.242912 160.963889-129.242912 255.937855 0 229.793191 188.120238 416.040569 420.076816 416.040569 232.003697 0 420.123935-186.247378 420.123935-416.040569C932.049269 471.511279 830.682768 408.452015 831.98135 222.495164L831.98135 222.495164 831.98135 222.495164z" fill="#272536" p-id="10073"></path></svg>
        &#9;H&#9;o&#9;t</div></router-link>
      <router-link v-if="isExpanded" to="/centerview" class="create_link"><div>
        <svg t="1716023015740" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="27867" width="20" height="20"><path d="M330.459834 819.767313c-17.019391 17.019391-34.038781 22.692521-56.731302 28.365651L114.880886 853.806094c-28.365651-5.67313-51.058172-28.365651-56.731302-56.731302l5.67313-158.847645c0-22.692521 11.34626-45.385042 28.365651-56.731302l329.041551-334.714681c28.365651-34.038781 79.423823-34.038781 113.462604 0l130.481994 130.481994c28.365651 34.038781 28.365651 79.423823 0 113.462604L330.459834 819.767313z" p-id="27868" fill="#272536"></path><path d="M699.213296 320.531856l-130.481994-130.481994c-28.365651-34.038781-28.365651-79.423823 0-113.462604l45.385042-51.058172c28.365651-34.038781 79.423823-34.038781 113.462604 0l130.481994 130.481994c28.365651 34.038781 28.365651 79.423823 0 113.462604l-45.385042 51.058172C784.310249 348.897507 733.252078 348.897507 699.213296 320.531856z" p-id="27869" fill="#272536"></path><path d="M852.387812 1024 120.554017 1024C86.515235 1024 58.149584 1001.307479 58.149584 967.268698l0 0c0-34.038781 28.365651-56.731302 62.404432-56.731302L852.387812 910.537396c34.038781 0 62.404432 22.692521 62.404432 56.731302l0 0C914.792244 1001.307479 886.426593 1024 852.387812 1024z" p-id="27870" fill="#272536"></path></svg>
        &#9;C&#9;r&#9;e&#9;a&#9;t&#9;e</div></router-link>
      <router-link v-if="isExpanded" to="/personview" class="person_link"><div>
        <svg t="1716021505763" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8879" width="20" height="20"><path d="M535.6 511.8c95.1-10.5 172.1-87.5 182.6-182.6C732 204 634.4 98.2 512 98.2c-114.6 0-207.5 92.8-207.5 207.4v0.1c0 122.4 105.9 219.9 231.1 206.1zM512 572.4c-138.5 0-415 79.4-415 237.1v88.9c0 16.4 13.3 29.6 29.6 29.6h770.7c16.4 0 29.6-13.3 29.6-29.6v-88.9C927 651.9 650.5 572.4 512 572.4z" fill="#272536" p-id="8880"></path></svg>
        &#9;P&#9;e&#9;r&#9;s&#9;o&#9;n&#9;a&#9;l</div></router-link>
      <div style="display: flex;" class="topline3" id="topSearch">
        <input type="text" v-model="searchTerm" placeholder="Search..." class="searchFrame" />
        <button @click="search" class="searchButton">
          <svg  t="1716008678130" class="search_icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1454" width="30" height="30">
            <path d="M441.66 783.66c-44.57 0-88.1-8.23-129.47-24.48-44.12-17.33-83.8-43.06-117.95-76.46-34.14-33.4-60.73-72.51-79.03-116.25-17.66-42.22-26.87-86.93-27.38-132.88s7.73-90.85 24.46-133.45c17.33-44.12 43.06-83.8 76.46-117.95 33.4-34.14 72.51-60.73 116.25-79.03 42.22-17.66 86.93-26.87 132.88-27.38 45.93-0.51 90.85 7.73 133.45 24.46 44.12 17.33 83.8 43.06 117.95 76.46 34.14 33.4 60.73 72.51 79.03 116.24 17.66 42.22 26.87 86.93 27.38 132.88 0.5 45.95-7.73 90.85-24.46 133.45-17.33 44.12-43.06 83.8-76.46 117.95-33.4 34.14-72.51 60.73-116.25 79.03-42.22 17.66-86.93 26.87-132.88 27.38-1.33 0.02-2.65 0.03-3.98 0.03z m0.13-637.83c-73.78 0-147.5 28.49-203.05 85.26-109.52 111.94-107.54 292.12 4.4 401.64 54.23 53.05 125.88 81.8 201.73 80.98 75.86-0.83 146.85-31.15 199.91-85.38 53.05-54.23 81.81-125.87 80.98-201.73s-31.16-146.85-85.38-199.9c-55.17-53.97-126.91-80.87-198.59-80.87z" fill="#231815" p-id="1455"></path><path d="M238.81 529.87c-13.44 0-25.45-9.46-28.18-23.14-9.09-45.46-5.95-93.84 9.07-139.9 14.92-45.74 41.33-88.45 76.38-123.51 11.24-11.24 29.45-11.24 40.69-0.01 11.24 11.23 11.24 29.45 0.01 40.69-58.44 58.46-84.5 137.5-69.72 211.44 3.12 15.58-6.99 30.74-22.57 33.86-1.91 0.39-3.81 0.57-5.68 0.57z" fill="#F7B52C" p-id="1456"></path><path d="M860.4 874.25c-8.82 0-17.64-3.32-24.45-9.97l-178.56-174.7c-13.8-13.5-14.04-35.64-0.54-49.44 13.5-13.8 35.64-14.04 49.44-0.54l178.56 174.7c13.8 13.5 14.04 35.64 0.54 49.44a34.9 34.9 0 0 1-24.99 10.51z" fill="#231815" p-id="1457"></path>
          </svg>
        </button>
      </div>
    </div>
    <div class="create">
      <router-link to="/centerview" class="createButton"><button>
        <svg t="1716023015740" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="27867" width="20" height="20"><path d="M330.459834 819.767313c-17.019391 17.019391-34.038781 22.692521-56.731302 28.365651L114.880886 853.806094c-28.365651-5.67313-51.058172-28.365651-56.731302-56.731302l5.67313-158.847645c0-22.692521 11.34626-45.385042 28.365651-56.731302l329.041551-334.714681c28.365651-34.038781 79.423823-34.038781 113.462604 0l130.481994 130.481994c28.365651 34.038781 28.365651 79.423823 0 113.462604L330.459834 819.767313z" p-id="27868" fill="#272536"></path><path d="M699.213296 320.531856l-130.481994-130.481994c-28.365651-34.038781-28.365651-79.423823 0-113.462604l45.385042-51.058172c28.365651-34.038781 79.423823-34.038781 113.462604 0l130.481994 130.481994c28.365651 34.038781 28.365651 79.423823 0 113.462604l-45.385042 51.058172C784.310249 348.897507 733.252078 348.897507 699.213296 320.531856z" p-id="27869" fill="#272536"></path><path d="M852.387812 1024 120.554017 1024C86.515235 1024 58.149584 1001.307479 58.149584 967.268698l0 0c0-34.038781 28.365651-56.731302 62.404432-56.731302L852.387812 910.537396c34.038781 0 62.404432 22.692521 62.404432 56.731302l0 0C914.792244 1001.307479 886.426593 1024 852.387812 1024z" p-id="27870" fill="#272536"></path></svg>
        <span>创作中心</span></button></router-link>
    </div>
      <div class="personal" v-on:mouseover="isHovered = true" v-on:mouseout="isHovered = false">
        <div class="avatarDiv" v-bind:class="{ hover: isHovered }">
          <router-link to="/personview">
            <img  class="avatar" ref="avatar" :src="avatarurl" alt="Avatar" />
          </router-link>
        </div>
        <div class="floatWindow_bottom" ref="floatWindow">
          <div class="floatWindow_center">
            <div class="floatWindowPersonal">
              <div v-if="isLoggedIn" @click="this.$router.push('/personview')"><p>{{ mul_user.userName }}</p></div>
              <div v-if="isLoggedIn" @click="this.$router.push('/personview')"><p>个人主页</p></div>
              <div @click="navigateToLogin"><p>{{ LogState }}</p></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </nav>
  </div>
  <div class="bili-header__banner" data-report="banner.taper_test">
    <div class="animated-banner" ref="banner" id="banner" >
      <div class="layer"><img src="./assets/1.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/2.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/3.webp" data-height="204" data-width="2000" height="250" width="2400">
      </div>
      <div class="layer"><img src="./assets/4.webp" data-height="240" data-width="2000" height="240" width="2200">
      </div>
      <div class="layer"><img src="./assets/5.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/6.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/7.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/8.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/9.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/10.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/11.webp" data-height="224" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/12.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/13.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/14.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/15.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/16.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/17.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/18.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/19.webp" data-height="187" data-width="2000" height="187" width="1800"></div>
      <div class="layer"><img src="./assets/20.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><img src="./assets/21.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      <div class="layer"><video loop muted="muted" src="./assets/fileName.webm" playsinline="" width="180" height="100" data-height="100"
                                data-width="180"></video></div>
      <div class="layer"><img src="./assets/22.webp" data-height="187" data-width="2000" height="205" width="2200"></div>
      <div class="layer"><img src="./assets/23.webp" data-height="187" data-width="2000" height="187" width="2000"></div>
      </div>
  </div>

  <div  ref="home" @scroll="handleScroll"><router-view /></div>
    <el-backtop></el-backtop>
    <div  class="bottom" id="bottom" ><div>关于我们</div></div>
  </div>
</template>
<script>
import {mapActions, mapState} from "vuex";
import { getCurrentInstance } from 'vue';
import {useRoute} from "vue-router";
import {ElNotification} from 'element-plus'
export default {
  data() {
    return {
      //这个是用户头像
      avatarurl:
          'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      styleMap: {
        0: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 0,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: null,
          element: null
        },
        1: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 0,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'y',
            scale: 10
          },
          element: null
        },
        2: {
          initialStyle: {
            height: '187px',
            width: '2400px',
            translateX: 300,
            translateY: 34,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: null,
          element: null
        },
        3: {
          initialStyle: {
            height: '240.7px',
            width: '2200px',
            translateX: 330,
            translateY: 33,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 50
          },
          element: null
        },
        4: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: null,
          element: null
        },
        5: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 10
          },
          element: null
        },
        6: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 10
          },
          element: null
        },
        7: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 2
          },
          element: null
        },
        8: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 10
          },
          element: null
        },
        9: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: null,
          element: null
        },
        10: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 50
          },
          element: null
        },
        11: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 10
          },
          element: null
        },
        12: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 30
          },
          element: null
        },
        13: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 30
          },
          element: null
        },
        14: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 40,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 10
          },
          element: null
        },
        15: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 20
          },
          element: null
        },
        16: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 10
          },
          element: null
        },
        17: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: -100,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 20
          },
          element: null
        },
        18: {
          initialStyle: {
            height: '187.3px',
            width: '1800px',
            translateX: -90,
            translateY: 0,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 400
          },
          element: null
        },
        19: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 0,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 10
          },
          element: null
        },
        20: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 0,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 10
          },
          element: null
        },
        21: {
          initialStyle: {
            height: '100px',
            width: '180px',
            translateX: -245,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1,
            objectFit: 'cover'
          },
          style: null,
          element: null
        },
        22: {
          initialStyle: {
            height: '205.7px',
            width: '2200px',
            translateX: 0,
            translateY: 0,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 200
          },
          element: null
        },
        23: {
          initialStyle: {
            height: '187px',
            width: '2000px',
            translateX: 0,
            translateY: 15,
            rotate: 0,
            scale: 1,
            opacity: 1
          },
          style: {
            direction: 'x',
            scale: 200
          },
          element: null
        },
      },
      searchTerm: '',
      atTop: false,
      atBottom: false,
      isHovered: false,
      initMouseLeft: 0,
      bannerWidth: 0,
      bannerLeft: 0,
      banner: null,
      isExpanded:false,
      windowWidth: window.innerWidth,
      isLoggedIn:false,
      LogState: '退出',
    }
  },
  computed: {
    screenWidth() {
      return window.innerWidth;
    },
    ...mapState(['mul_isLoggedIn', 'mul_user'])
  },
  created() {
    //将vuex状态存储到sessionStorage中
    if (sessionStorage.getItem('store')) {
      this.$store.replaceState(
          Object.assign(
              {},
              this.$store.state,
              JSON.parse(sessionStorage.getItem('store'))
          )
      )
       console.log(JSON.parse(sessionStorage.getItem('store')))  // 打印
    }

    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    // beforeunload事件在页面刷新时先触发
    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('store', JSON.stringify(this.$store.state))
       console.log(JSON.parse(sessionStorage.getItem('store')))  // 打印
    })

  },
  watch: {
    windowWidth(newWidth) {
      this.isExpanded = newWidth > 1000;
      this.$nextTick(() => {
        this.adjustPaddingTop();
        this.hanleBannerTop();
      });
    },
    mul_isLoggedIn(newValue, oldValue) {
      this.updateLogState();
    },

  },
  updated() {
    this.updateUser();

  },
  mounted() {
    this.banner = document.querySelector('.animated-banner');
    this.bannerLeft = this.banner.offsetLeft;
    this.bannerWidth = this.banner.offsetWidth;
    this.$el.addEventListener('scroll', this.handleScroll);
    this.handleFloatWin();
    window.addEventListener('resize', this.handleFloatWin);
    this.updateScreenWidth();
    window.addEventListener('resize', this.updateScreenWidth);
    this.handleExpand();
    window.addEventListener('resize', this.handleExpand);
    Object.keys(this.styleMap).forEach((key) => {
      this.styleMap[key].element = this.banner.querySelector(`.layer:nth-child(${parseInt(key) + 1})`).querySelector('img');
    });
    this.init();
    this.playVideo();
    this.banner.addEventListener('mouseenter', (event) => {
      event.stopPropagation();
      this.initMouseLeft = event.pageX - this.bannerLeft;
      this.startListener();
    });
    this.banner.addEventListener('mouseleave', (event) => {
      event.stopPropagation();
      this.clearListener(event);
    });


  },
  methods: {
    ...mapActions(['logout']),
    navigateToLogin() {
      this.logout();
      console.log(this.mul_isLoggedIn)
      this.$router.push('/login');
    },
    updateUser(){
      this.isLoggedIn=this.mul_isLoggedIn;
      if(this.mul_isLoggedIn){
        this.Login='退出';
      }else {
        this.Login='登录';
      }
    },
    updateLogState(){
      if(this.mul_isLoggedIn){
          this.LogState='退出';
      }else {
          this.LogState='登录';
      }
    },
    search() {
      console.log('Searching for: ' + this.searchTerm);
      //执行搜索逻辑，搜索的逻辑发送给搜索页面
      if(this.searchTerm!==''){
        this.$router.push({
          path: '/searchview',
          query: {
            searchTerm: this.searchTerm
          }
        });
      }else {
        ElNotification.error({
          title: 'Error',
          message: 'Input SearchTerm!',
        })
      }

    },
    toggleElements() {
      this.isExpanded = !this.isExpanded;
      this.$nextTick(() => {
        this.adjustPaddingTop();
        this.hanleBannerTop();
      });
    },
    adjustPaddingTop() {
      if (this.$refs.top && this.$refs.home &&
      this.$route.name !== 'login'){
        const frame = this.$refs.top;
        const home = this.$refs.home;
        const height = frame.offsetHeight-5;
        home.style.paddingTop = `${height}px`;
      }
    },
    handleLoginTop(){
      if (this.$refs.home ){
        const home = this.$refs.home;
        home.style.paddingTop = `0px`;
      }
    },
    handleExpand() {
      this.adjustPaddingTop();
      this.hanleBannerTop();
    },
    handleScroll(e) {
      const { target } = e;
      // 检查是否滚动到顶部
      if (target.scrollTop === 0) {
        console.log('滚动到顶部了！');
        this.atTop = true;
      } else {
        this.atTop = false;
      }
      // 检查是否滚动到底部
      if (target.scrollHeight - target.scrollTop === target.clientHeight) {
        console.log('滚动到底部了！');
        this.atBottom = true;
        // 阻止事件的默认行为
        e.preventDefault();
      } else {
        this.atBottom = false;
      }
    },
    hanleBannerTop(){
      if (this.$refs.top && this.$refs.banner){
        const frame = this.$refs.top;
        const banner = this.$refs.banner;
        const height = frame.offsetHeight-20;
        banner.style.top = `${height}px`;
      }
    },
    handleFloatWin(){
      if(this.$refs.avatar && this.$refs.floatWindow){
        const avatar = this.$refs.avatar;
        const floatWin = this.$refs.floatWindow;
        const positionX = avatar.getBoundingClientRect().x-30;
        const centerX = avatar.getBoundingClientRect().x + avatar.width;
        const halfwidth = centerX - positionX-10;
        floatWin.style.left = `${positionX}px`;
        floatWin.style.width = `${2*halfwidth}px`;
      }
    },
    updateScreenWidth() {
      this.isExpanded = window.innerWidth >= 1001;
    },
    init() {
      Object.keys(this.styleMap).forEach((item) => {
        const current = this.styleMap[item];
        const initStyle = current.initialStyle;
        if(current && initStyle && current.element){
          current.element.style = `height: ${initStyle.height}; width: ${initStyle.width}; transform: translate(${initStyle.translateX}px, ${initStyle.translateY}px) rotate(${initStyle.rotate}deg) scale(${initStyle.scale}); opacity: ${initStyle.opacity}; object-fit: ${initStyle.objectFit}`;
        }
      });
    },
    playVideo() {
      const video = document.querySelector('video');
      video.play();
    },
    calcutedPosition(mouseLeft, scale) {
      return -(mouseLeft - this.initMouseLeft) * scale / this.bannerWidth;
    },
    startListener() {
      this.banner.addEventListener('mousemove', (event) => {
        event.stopPropagation();
        const mouseLeft = event.pageX - this.bannerLeft;
        Object.keys(this.styleMap).forEach((item) => {
          const current = this.styleMap[item];
          if (current.style) {
            const initStyle = current.initialStyle;
            const style = current.style;
            const element = current.element;
            const offset = this.calcutedPosition(mouseLeft, style.scale);
            let styleResult = `height: ${initStyle.height}; width: ${initStyle.width}; opacity: ${initStyle.opacity}; object-fit: ${initStyle.objectFit};`;

            if (style.direction === 'y') {
              styleResult += `transform: translate(${initStyle.translateX}px, ${initStyle.translateY - offset}px) rotate(${initStyle.rotate}deg) scale(${initStyle.scale});`;
            }
            else {
              styleResult += `transform: translate(${initStyle.translateX - offset}px, ${initStyle.translateY}px) rotate(${initStyle.rotate}deg) scale(${initStyle.scale});`;
            }
            element.style = styleResult;
          }
        });
      });
    },
    clearListener() {
      const mouseLeft = event.pageX - this.bannerLeft;

      Object.keys(this.styleMap).forEach((item) => {
        const current = this.styleMap[item];
        const style = current.style;
        const initStyle = current.initialStyle;
        const element = current.element;

        if (current.style) {
          let startValue = this.calcutedPosition(mouseLeft, style.scale);
          let endValue = 0;
          let duration = 150;
          let interval = 10;
          let steps = duration / interval;
          let stepValue = (startValue - endValue) / steps;

          let currentValue = startValue;

          let timer = setInterval(() => {
            currentValue -= stepValue;
            let styleResult = `height: ${initStyle.height}; width: ${initStyle.width}; opacity: ${initStyle.opacity}; object-fit: ${initStyle.objectFit};`;

            if (style.direction === 'y') {
              styleResult += `transform: translate(${initStyle.translateX}px, ${initStyle.translateY - currentValue}px) rotate(${initStyle.rotate}deg) scale(${initStyle.scale});`;
            } else {
              styleResult += `transform: translate(${initStyle.translateX - currentValue}px, ${initStyle.translateY}px) rotate(${initStyle.rotate}deg) scale(${initStyle.scale});`;
            }

            if (Math.abs(currentValue - endValue) < Math.abs(stepValue)) {
              clearInterval(timer);
              styleResult = `transform: translate(${initStyle.translateX}px, ${initStyle.translateY}px) rotate(${initStyle.rotate}deg) scale(${initStyle.scale});`;
            }
            element.style = styleResult;
          }, interval);
        }
      });
    },
    beforeRouteLeave(to, from, next) {
      if (to.path === '/login') {
        this.handleLoginTop();
      }
      next();
    }
  }
}

</script>
<style>

@import url("https://fonts.googleapis.com/css2?family=Quicksand:wght@300&display=swap");

@font-face {
  font-family: "NewFont";
  src: url("assets/AlimamaFangYuanTiVF-Thin.ttf");
}
.homeBack {
  background: #dbf1af;
  transition: 0.5s ease-in-out;
}

@media (min-width: 1800px) {
  .homeBack {
    position: absolute;
    max-width: 1800px;
    left: 50%;
    transform: translate(-50%, 0%);
    box-shadow: -8px 0 8px -8px rgb(167, 172, 176), 8px 0 8px -8px rgb(167, 172, 176);
  }
}

#app {
  font-family: "Quicksand", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

::-webkit-scrollbar {
  width: 10px; /* 滚动条的宽度 */
}
::-webkit-scrollbar-track {
  background-color: #dbf1af; /* 轨道的背景色 */
}
::-webkit-scrollbar-thumb {
  background-color: #bdbebc; /* 滑块的颜色 */
  border-radius: 5px; /* 滑块的圆角 */
}
::-webkit-scrollbar-thumb:hover {
  background-color: #999; /* 滑块在 hover 时的颜色 */
}

body {
  justify-content: center;
  min-height: 100vh;

  width: 100%;
  margin: 0 0 0 0;
}

.top {
  position: fixed;
  max-width: 1802px;
  min-height: 60px;
  width: 100%;
  margin: 0px 0 0px 0;
  padding: 0 0 0 0;
  box-shadow: 5px 1px 5px 1px rgba(189, 179, 179, 0.75);
  background: linear-gradient(to top, #dff3b8, #b5d96c, #b5d96c, #dff3b8) 0% 100%;
  background-size: 100% 600%;
  border-radius:0 0 5px 5px;
  top: 0;
  z-index: 999;
  font-family: "Quicksand", sans-serif;
}



@media (min-width: 1001px) {
  nav {
    padding: 0px;
    margin: 0px;
  }


  .animated-banner {
    bottom: 0;
    left: 0;
    right: 0;
    overflow: hidden;
    min-width: 1000px;
    min-height: 187px;
    max-height: 187px;
    height: 9.375vw;
    z-index: 100;
    position: relative;
    border-radius: 0 0 8px 8px;
    box-shadow: 3px 1px 2px #797777;
  }

  .animated-banner>.layer {
    position: absolute;
    left: 0;
    top: 0;
    height: 100%;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    background-size: cover;
    overflow: hidden;
  }

  .topline1 {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin: 0px 5% 0px 5%;
  }

  .logo {
    flex: 1;
    margin-top: 6px;
    margin-right: 6px;
  }



  .topline2 {
    flex: 8;
    display: flex;
    text-align: center;
    width: 100%;
    margin: 0;
    padding-top: 0;

  }



  .topline2 a:not(.topline3) {
    flex: 1.5; /* 让每个div平均分配空间 */
    text-align: center; /* 让文字居中 */
    border-style: solid;
    border-color:transparent;
    outline: none;
    font-size: 20px;
    text-decoration: none;
    padding-top: 2.3% ;
    background: linear-gradient(to top, #dff3b8, #b5d96c, #b5d96c, #dff3b8) 0% 100%;
    /* 背景尺寸 */
    background-size: 100% 600%;

    /* 动画 */
    transition: background-position 1s ease;

  }

  .topline2 a:hover:not(.topline3){
    flex: 1.5; /* 让每个div平均分配空间 */
    text-align: center; /* 让文字居中 */
    border-style:solid ;
    border-color: transparent;
    outline: none;
    font-size: 20px;
    padding-top: 2.3% ;
    background-position: 0% 0%;
  }

  .expandButton {
    display: none;
  }



  .create_link,.person_link {
    display: none;
  }

  .topline3 {
    flex:2.8;
    margin: 10px 2% 10px 10px;

  }

  .searchFrame {

    font-family: "Quicksand", sans-serif;
    margin: 0 0 0px 0;
    text-align: center;
    border-radius: 17px 0 0 17px;
    border-width: 1px 0 1px 1px;
    border-color: transparent ;
    outline: none;
    background: #eceeee;
    width: 80%;
    transition-duration: 0.4s;
  }

  .searchFrame:hover {
    font-family: "Quicksand", sans-serif;
    margin: 0 0 0px 0;
    text-align: center;
    border-radius: 17px 0 0 17px;
    border-width: 1px 0 1px 1px;
    border-style: solid;
    border-color: #445067;
    outline: none;
    background: #eceeee;
    width: 80%;
  }

  .searchButton svg {
    margin-top: 2px;
  }
  .searchButton {
    margin: 0px 0 0px 0;
    padding-left: 1.5%;
    text-align: center;
    border-radius: 0 17px 17px 0;
    background: rgba(149, 218, 106, 0.71);
    border-width: 1px 1px 1px 0;
    border-color: transparent ;
    transition-duration: 0.4s;
    transition: transform 0.3s ease-in-out;
  }

  .searchButton:hover {
    margin: 0px 0 0px 0;
    padding-left: 1.5%;
    text-align: center;
    border-radius: 2px 17px 17px 2px;
    background: rgba(129, 218, 73, 0.72);
    border-width: 1px 1px 1px 0;
    border-color: transparent ;
    transform: scale(1.1);
  }
  .searchButton:active {
    margin: 0px 0 0px 0;
    padding-left: 1.5%;
    text-align: center;
    border-radius: 2px 17px 17px 2px;
    background: rgba(129, 218, 73, 0.72);
    border-width: 1px 1px 1px 0;
    border-color: transparent ;
    transform: scale(1);
    transition: 0.1s;
  }


  .searchFrame:hover + .searchButton {
    border-width: 1px 1px 1px 0;
    border-style: solid;
    border-color: #445067;
  }

  nav a {
    font-weight: bold;
    color: #2c3e50;
  }

  a:hover {
    color: #ffeeee;
  }

  nav a.router-link-exact-active {
    color: #000000;
  }

  .create {
    flex: 1.2;
    font-size: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .create a {
    margin-top:3% ;
    padding: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 30px;
    text-decoration: none;

  }


  .create a button {
    padding: 7% 10% 4% 10%;
    border-radius: 10px;
    border: none;
    color: #070707;
    text-align: center;
    text-decoration: none;
    font-size: 20px;
    background: radial-gradient( #d5f594, #d5f594,#b0ef52, #b4f33a, #98f608,#d5f594);
    transition-duration: 0.4s; /* Transition effect duration */
    background-size: 600% 600%;
    animation: gradientBG 5s ease infinite;
    box-shadow: 2px 2px 2px #b4b7a3;
    cursor: pointer; /* Change mouse cursor on hover */
  }
  @keyframes gradientBG {
    0% {
      background-position: 0% 50%;
    }
    50% {
      background-position: 100% 50%;
    }
    100% {
      background-position: 0% 50%;
    }
  }
  .create a button:hover {
    transform: scale(1.05);
    border: none;
    color: #000000;
  }

  .create a button svg {
    transition-duration: 1s;
  }

  .create a button span {

  }

  @media screen and (max-width: 1470px ) {
    .create a button span{
      display: none;
    }
    .create a button svg{
      transform: scale(1.4);
    }
  }

  @media screen and (min-width: 1471px) {
    .create a button svg{
      /* 在屏幕宽度大不产生动画 */
      transition: none;
    }
  }

  @keyframes animated-border {
    0% {
      box-shadow: 0 0 0 0 #888c88;
    }
    100% {
      box-shadow: 0 0 0 8px rgba(255,255,255,0);
    }
  }
  .avatarDiv {
    margin-left: 30px;
    margin-top: 0.3%;
    border-radius: 50%;
    height: 40px;
    width: 40px;
    background: #6e6a6a;
    border: 3px solid #e4fad0;
    padding: 2px;
    animation: animated-border 1.5s infinite;
    transform: translateY(0) scale(1);
    transition: transform 0.5s ease-out;
  }

  .avatarDiv:hover, .personal:hover .avatarDiv {
    transform: translateY(35px) scale(1.5);
  }



  .avatar {
    clip-path: circle(49% at center);
    height: 45px;
    width: 45px;
    margin: -2.3px 0 0 -2.5px;
  }

  .floatWindow_bottom {
    position: absolute;
    display: none;

  }
  .floatWindowPersonal div p {
    margin: 5px 0 0 0;
  }




  @keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
  }
  .avatarDiv:hover + .floatWindow_bottom, .floatWindow_bottom:hover {
    transform: translate3d(-13%,0px,0);
    display: inline-block;
    position: absolute;
    top: 75px;
    animation: fadeIn 0.5s ease-in-out;
    z-index: -1;
    background: #dbf1af;
    box-shadow: 1px 1px 1px 0px #b7b4b4;
    border: #f8f6f6 1px solid;
    border-radius: 8px;
  }



  .floatWindow_center {
    width: 100%;
    position: relative;
    margin-top: 30px;

  }

  .floatWindowPersonal {
    position: relative;
    margin: 10px;
    display: flex;
    flex-direction: column;
  }

  .floatWindowPersonal div {
    margin: 5px 0 5px 0;
  }



}

@media (max-width: 1000px) {

  .floatWindow_bottom {
    display: none;
  }

  .animated-banner {
    top:135px;
    bottom: 0;
    left: 0;
    right: 0;
    overflow: hidden;
    min-height: 150px;
    height: 4.6825vw;
    z-index: 0;
    position: relative;
    border-radius: 0 0 8px 8px;
    box-shadow: 3px 1px 2px #797777;
  }

  .animated-banner>.layer {
    position: absolute;
    left: 0;
    height: 100%;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    background-size: cover;
  }

  nav {
    display: flex;
    flex-direction: column;
    text-align: left;

  }

  .logo {
    text-align: center;
    margin: 0px 0 0 0;
    padding: 5px 0 5px 0;
    background: #dff3b8;
  }


  .topline1 {
    display: flex;
    flex-direction: column;
  }

  .topline2 {
    display: flex;
    flex-direction: column;
  }

  .expandButton {
    position: absolute;
    display: inline-flex;
    margin: 0 0 0 90% ;
    top: 65px;
    background: transparent;
    border: none;
  }
  .expandButton svg {
    transition: transform 0.3s ease-in-out;
  }

  .expandButton svg:hover {
    transform: scale(1.1);
  }


  .topline2 a:not(.topline3) {
    font-size: 20px;
    background-color: #ffffff; /* 设置背景色 */
    padding: 10px 0 4px 20px;
    box-shadow: 5px 1px 5px 1px rgba(189, 179, 179, 0.75);
    text-decoration: none;
    transition-duration: 0.4s;
  }

  .topline2 a:not(.topline3):hover {
    font-size: 20px;
    background-color: #edf3f3; /* 设置背景色 */
    padding: 10px 0 4px 20px;
  }

  .topline3 {
    background: white;
    margin: 0px 0 0px 0;
    box-shadow: 0px 0px 0px 0px #000;
  }

  .searchButton {
    margin: 10px 0 10px 0;
    padding-left: 1.5%;
    text-align: center;
    border-radius: 0 17px 17px 0;
    background: rgba(149, 218, 106, 0.71);
    border-width: 1px 1px 1px 0;
    border-color: transparent ;
    transition-duration: 0.4s;
    transition: transform 0.3s ease-in-out;
  }



  .search_icon {
    padding: 5px 0 0 0;
    margin-right: 5px;
  }

  .searchButton:hover {
    margin: 10px 0 10px 0;
    padding-left: 1.5%;
    text-align: center;
    border-radius: 2px 17px 17px 2px;
    background: rgba(129, 218, 73, 0.72);
    border-width: 1px 1px 1px 0;
    border-color: transparent ;
    transform: scale(1.1);
  }

  .searchButton:active {
    margin: 10px 0 10px 0;
    padding-left: 1.5%;
    text-align: center;
    border-radius: 2px 17px 17px 2px;
    background: rgba(129, 218, 73, 0.72);
    border-width: 1px 1px 1px 0;
    border-color: transparent ;
    transform: scale(1);
    transition-duration: 0.1s;
  }

  .searchFrame {
    font-family: "Quicksand", sans-serif;
    margin: 10px 0 10px 25%;
    text-align: center;
    border-radius: 17px 0 0 17px;
    border-width: 1px 0 1px 1px;
    border-color: transparent ;
    outline: none;
    background: #eceeee;
    width: 45%;
    transition-duration: 0.4s;
  }

  .searchFrame:hover {
    height: 100%;
    font-family: "Quicksand", sans-serif;
    margin: 10px 0px 10px 25%;
    text-align: center;
    border-radius: 17px 0 0 17px;
    border-width: 1px 0 1px 1px;
    border-style: solid;
    border-color: #445067;
    outline: none;
    background: #eceeee;
    width: 45%;
  }

  .searchFrame:hover + .searchButton {
    border-width: 1px 1px 1px 0;
    border-style: solid;
    border-color: #445067;
  }

  .create {
    display: none;
  }

  .avatarDiv {
    display: none;
  }
}

.bottom {
  width: 100%;
  font-family: "NewFont", sans-serif;
  font-size: 25px;
  color: #d3dede;
  height: 150px;
  background: #1e1d1d;
  display: flex;
  justify-items: center;
  justify-content: space-around;
}
.bottom div {
  margin-top: 30px;
}



</style>
