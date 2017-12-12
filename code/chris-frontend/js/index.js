/**
* author: Chris Long
* Created at 2017/12/12 22:30
* For Assignment of ISE
*/

function get_width_of_browser(){
  var vWidth = document.documentElement.clientWidth
  var vHeight = document.documentElement.clientHeight
  console.log("高度：" + vHeight + " 宽度：" + vWidth)
  $('div.container').css({'height':vHeight, 'width': vWidth})
}


get_width_of_browser()
