import { createStore } from 'vuex'

export default createStore({
  state: {
    mul_isLoggedIn: false,
    /*
    Email:
    creatDate:
    passWords:
    salt:
    userID:
    userName:
     */
    mul_user: null,
  },
  getters: {
    mul_user: state => state.mul_user,
    mul_isLoggedIn: state => state.mul_isLoggedIn,
  },
  mutations: {
    // 定义状态修改方法
    updateLoginStatus(state, isLoggedIn) {
      state.mul_isLoggedIn = isLoggedIn;
    },
    updateUser(state, user) {
      state.mul_user = user;
    },
    logout(state) {
      state.mul_isLoggedIn = false;
      state.mul_user = { name:'',id: '' };
      // 清除其他用户信息
    },
  },
  actions: {
    login({commit},login_user) {
      // 执行登录逻辑
      // 根据登录结果调用相关mutation
      commit('updateLoginStatus', true);
      commit('updateUser', login_user);
      console.log(this.state.mul_isLoggedIn+ this.state.mul_user);
    },
    logout({ commit }) {
      // 在登出完成后，调用mutations来更新状态和清除用户信息
      commit('logout');
    }
  },
  modules: {
  },
})
