import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './styles/base.css'
import VueKinesis from 'vue-kinesis'

Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.use(VueKinesis)
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
