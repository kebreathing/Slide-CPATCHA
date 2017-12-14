/**
* author: Chris Long
* Created at 2017/12/12 22:30
* For Assignment of ISE
*/

var ROOTURL = '127.0.0.1'
var API_INDEX = '/index'
var API_GET_PUZZLE = '/get/puzzle'

// 进入页面就向服务器发送请求
function api_index(){
  $.get(ROOTURL + API_INDEX, function(data, status){
    if(status == 200){
      console.log('Successfully connect to python server.')
      // TODO: need to know the structure of the images and id
      // Python Server need to record the certain image id instead of
      // Frontend. 指定框内的ID应该由后台服务器记录，前端不用记录
    }
  })
}

/**
* 根据图片ID获取对应的拼图信息
* @PARAM: imgID 拼图在前端页面的ID号（指image的id）
*/
function api_get_puzzle(imgID){
  $.get(ROOTURL + API_GET_PUZZLE + '/id=' + imgID, function(data, status){
    if(status == 200){
      console.log('Successfully connect to python server.')
      // TODO: need to know the structure of the image.
      // 需要知道服务器如何返回信息给前端
    }
  })
}
