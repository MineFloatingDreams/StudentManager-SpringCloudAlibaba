import {request} from "@/util/request";

export function loginByPasswordRequest(username, password) {
    return request({
        url: '/user/loginByPassword',
        params: {
            username,
            password
        },
        method: 'post'
    })
}
export function loginByEmailRequest(email,code){
    return request({
        url: '/user/loginByEmail',
        params: {
            email,
            code
        },
        method: 'post'
    })
}