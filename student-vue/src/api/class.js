import {request} from "@/util/request";


export function getClassInfoRequest(token, context, tags, currentPage, pageSize, sort, asc) {
    return request({
        url: '/clazz/showClazz',
        headers: {
            token
        },
        params: {
            context,
            tags,
            currentPage,
            pageSize,
            sort,
            asc
        },
        method: 'post'
    })
}

export function deleteClassRequest(token, ids) {
    return request({
        url: '/clazz/deleteClazz?' + ids,
        headers: {
            token
        },
        method: 'post'

    })
}

export function addClassRequest(token, clazzName) {
    return request({
        url: '/clazz/addClazz',
        headers: {
            token
        },
        params: {
            clazzName
        },
        method: 'post'
    })
}

export function updateClassRequest(token, clazzName, clazzId) {
    return request({
        url: '/clazz/updateClazz',
        headers: {
            token
        },
        params: {
            clazzName,
            clazzId
        },
        method: 'post'
    })
}

export function getClazzRequest(token) {
    return request({
        url: '/clazz/getClazz',
        headers: {
            token
        },
        method: 'get'
    })
}