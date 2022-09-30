import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "@/views/Index";
import Login from "@/views/Login/Login";
import Register from "@/views/Register";
import LoginByPassword from "@/views/Login/templates/LoginByPassword";
import LoginByEmail from "@/views/Login/templates/LoginByEmail";
import Home from "@/views/Home";
import Student from "@/views/Student/Student";
import Clazz from "@/views/Clazz/Clazz";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Index',
        component: Index,
        meta: {
            title: '学生管理系统 - 首页'
        },
        children: [
            {
                path: '',
                name: "Home",
                component: Home
            },
            {
                path: 'student',
                name: "student",
                component: Student
            },
            {
                path: 'class',
                name: "class",
                component: Clazz
            }
        ]
    },
    {
        path: '/Login',
        name: 'Login',
        component: Login,
        meta: {
            title: '学生管理系统 - 登录'
        },
        children: [
            {
                path: '',
                name: "LoginByPassword",
                component: LoginByPassword
            },
            {
                path: 'LoginByEmail',
                name: "LoginByEmail",
                component: LoginByEmail
            }
        ]
    },
    {
        path: '/Register',
        name: 'Register',
        component: Register,
        meta: {
            title: '学生管理系统 - 注册'
        },
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
let routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return routerPush.call(this, location).catch(err => err)
}
