export function setTokenAUTH(tokenKey, token) { // 将token存入localStorage
    return sessionStorage.setItem(tokenKey, token)
}

export function getTokenAUTH(tokenKey) {    //拿到localStorage中的token
    return sessionStorage.getItem(tokenKey)
}

export function removeTokenAUTH(tokenKey) {  //退出登录时调用销毁token
    return sessionStorage.removeItem(tokenKey)
}