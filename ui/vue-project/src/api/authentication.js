import myAxios from '@/axios/base';


export function login(paramsList) {
  return myAxios({
    url: '/api/auth/login',
    method: 'post',
    data: paramsList
  })
}

export function logout() {
  return myAxios({
    url: '/api/auth/logout',
    method: 'post'
  })
}

export function refresh(paramsList) {
  return myAxios({
    url: '/api/auth/getToken',
    method: 'post',
    data: paramsList
  })
}