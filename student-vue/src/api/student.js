import {request} from "@/util/request";


export function getStudentInfoRequest(token, context, tags, currentPage, pageSize, sort, asc) {
    return request({
        url: '/student/showStudent',
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

export function deleteStudentRequest(token, ids) {
    return request({
        url: '/student/deleteStudent?' + ids,
        headers: {
            token
        },
        method: 'get'
    })
}

export function addStudentRequest(token, id, name, age, sex, clazzId) {
    return request({
        url: '/student/addStudent',
        headers: {
            token
        },
        params: {
            id,
            name,
            age,
            sex,
            clazzId
        },
        method: 'post'
    })
}

export function updateStudentRequest(token, id, name, age, sex, clazzId) {
    return request({
        url: '/student/updateStudent',
        headers: {
            token
        },
        params: {
            id,
            name,
            age,
            sex,
            clazzId
        },
        method: 'post'
    })
}