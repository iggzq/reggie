function changeUserInfor(data){
    return $axios.put({
        'url': 'http://localhost:8080/user',
        data
    })
}