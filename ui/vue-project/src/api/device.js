import myAxios from '@/axios/base';


export function addDevice(paramsList) {
    return myAxios({
        url: '/api/devices/add',
        method: 'post',
        data: paramsList
    })
}

export function findDeviceByGroup(group) {
    return myAxios({
        url: '/api/devices/list/' + group,
        method: 'get'
    })
}

export function groupList() {
    return myAxios({
        url: '/api/devices/group',
        method: 'get'
    })
}

export function deviceList() {
    return myAxios({
        url: '/api/devices/list',
        method: 'get'
    })
}

export function getModel(deviceId) {
    return myAxios({
        url: '/api/devices/model/' + deviceId,
        method: 'get'
    })
}

export function deleteInfoByName(deviceName) {
    return myAxios({
        url: '/api/devices/' + deviceName,
        method: 'delete'
    })
}

export function offline(deviceName) {
    return myAxios({
        url: '/api/emqx/offline/' + deviceName,
        method: 'delete'
    })
}

export function edit(paramsList) {
    return myAxios({
        url: '/api/devices/edit',
        method: 'post',
        data: paramsList
    })
}