import axios from "axios";
const http = axios.create({
    baseURL: 'http://localhost:8080',  // 设置基础 URL
    // 其他配置...
})
export default  http;