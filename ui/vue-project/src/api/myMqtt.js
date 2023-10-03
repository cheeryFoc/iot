import myAxios from '@/axios/base';

export function sendMsg(paramsList) {
    return myAxios({
      url: '/api/mqtt/publishTopic',
      method: 'post',
      data: paramsList
    })
}

export function getData(tag) {
  return myAxios({
    url: '/api/mqtt/init/' + tag,
    method: 'get'
  })
}