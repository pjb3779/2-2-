import axios from "axios";
const http = axios.create({
    baseURL: 'http://localhost:8081',  // 设置基础 URL
    // 其他配置...
})
export default  http;