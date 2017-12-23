/**
* author: Chris Long
* Created at 2017/12/12 22:30
* For Assignment of ISE
*/

var imgPath = './img'

// Add Even Listener to Button
function bindEvent(){
  console.log("Function Binding.")
  // Get all the instances of buttons
  $("button").click(function() {
    console.log(this.id)
    var imgId = 'img' + (this.id).substring(3, this.id.length)
    var src = $('#' + imgId).attr('src')
    var path = src.split("/")
    var imgname = path[path.length-1]
    window.location.href = './puzzle/' + imgname
  });

  $("#warning404").click(function(){
    // 如果网络出现问题，会出现此框
    // 重新发出请求后，成功则消失，失败则继续出现
    var display = $("#warning404").css('display')
    if(display == "block"){
      $("#block").hide()
      // TODO: Request the images again.
    }
  })
}

// Set the default Image
function defaultBKG(){
  var defaultImgs = new Array();
  for(var i = 0; i < 6; i++){
    defaultImgs[i] = imgPath + '/pexels-photo-00000' + (i+1) + ".jpeg";
  }
  // 设置默认拼图图片
  var imgs = $('img');
  for(var i  = 0; i < imgs.length; i++){
    $('#' + imgs[i].id).attr('src', defaultImgs[i]);
  }
}

// 点击消失错误提示
function disappear404(){

}

$(document).ready(function(){
  bindEvent();
  //defaultBKG();
});
