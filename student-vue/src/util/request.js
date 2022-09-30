import axios from "axios";
export function request(config){
    //1.创建axios的实例
    const instance = axios.create({
        baseURL: '/api',
        timeout:5000
    })
    instance.interceptors.request.use(config => {
        // console.log(config);
        // 1.比如config中的一些信息不符合服务器的要求
        //2.比如每次发送网络请求时，都希望在界面中显示一个请求图标
        //3.某写网络请求（token）必须携带一些特殊的信息
        return config
    },err =>{
        console.log(err);
    });
    //2.2响应式拦截
    instance.interceptors.response.use(res => {
        // console.log(res);
        return res.data
    },err => {
        console.log(err);
    })
    return instance(config)
}