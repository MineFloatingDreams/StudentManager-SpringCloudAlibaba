import {request} from "@/util/request";

export function registerRequest(username, password, email, code) {
    return request({
        url: '/user/register',
        params: {
            username,
            password,
            email,
            code
        },
        method: 'post'
    })
}