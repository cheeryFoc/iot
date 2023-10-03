import myAxios from '@/axios/base';


export function userInfoByName(userName) {
  return myAxios({
    url: '/api/users/info/' + userName,
    method: 'get'
  })
}

export function getUser(userName) {
  return myAxios({
    url: '/api/users/' + userName,
    method: 'get'
  })
}

export function register(paramsList) {
  return myAxios({
    url: '/api/users/register',
    method: 'post',
    data: paramsList
  })
}


export function deleteInfoByName(userName) {
  return myAxios({
    url: '/api/users/' + userName,
    method: 'delete'
  })
}

export function deleteUser(userName) {
  return myAxios({
    url: '/api/users/anyone/' + userName,
    method: 'delete'
  })
}


export function updatePwd(paramsList) {
  return myAxios({
    url: '/api/users/updatePwd',
    method: 'post',
    data: paramsList
  })
}

export function updateInfo(paramsList) {
  return myAxios({
    url: '/api/users/updateInfo',
    method: 'post',
    data: paramsList
  })
}
