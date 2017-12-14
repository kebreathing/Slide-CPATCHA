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

$(document).ready(function(){
  bindEvent();
  //defaultBKG();
});
