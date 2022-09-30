import {request} from "@/util/request";

export function getForCode(email) {
    return request({
        url: '/code/send/' + email,
        method: 'get'
    })
}