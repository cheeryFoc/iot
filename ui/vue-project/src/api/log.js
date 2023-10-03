import myAxios from '@/axios/base';


export function getFiles() {
    return myAxios({
        url: '/api/log/list/',
        method: 'get'
    })
}

export function deleteFile(fileName) {
    return myAxios({
        url: '/api/log/' + fileName,
        method: 'delete'
    })
}

export function fileDownLoad(fileName) {
    return myAxios({
        url: '/api/log/download/' + fileName,
        method: 'get',
        responseType: 'blob'
    })
}