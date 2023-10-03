import myAxios from '@/axios/base';


export function userList() {
  return myAxios({
    url: '/api/manage/list',
    method: 'get'
  })
}

export function resetPassword(userName) {
  return myAxios({
    url: '/api/manage/resetPassword/' + userName,
    method: 'get'
  })
}

export function changeIdentity(userName) {
  return myAxios({
    url: '/api/manage/changeIdentity/' + userName,
    method: 'get'
  })
}

export function downgrading(userName) {
  return myAxios({
    url: '/api/manage/downgrading/' + userName,
    method: 'get'
  })
}
