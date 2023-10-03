import myAxios from '@/axios/base';

export function notionList() {
    return myAxios({
        url: '/api/notion/list',
        method: 'get'
    })
}

export function findNotionByGroup(group) {
    return myAxios({
        url: '/api/notion/list/' + group,
        method: 'get'
    })
}

export function operateById(value) {
    return myAxios({
        url: '/api/notion/operate/' + value,
        method: 'delete'
    })
}

export function deleteById(value) {
    return myAxios({
        url: '/api/notion/' + value,
        method: 'delete'
    })
}