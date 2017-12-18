/**
 * author: Chris Long
 * Created at 2017/12/12 22:30
 * For Assignment of ISE
 */

var MAX_LOG_ITEMS = 10000;
var mouselistener = new Array();
var thumbnails_onoff = new Array(false, false, false, false);
var drag_status = false;

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

// 鼠标日志监听
function MouseListener(type, date, detailed) {
  var json = {
    'type': type,
    'date': date,
    'detailed': detailed
  }

  while (mouselistener.length >= MAX_LOG_ITEMS) {
    mouselistener.pop()
  }
  mouselistener.push(json)
}

// 子拼图缩略图选择监听
function ThumbnailsListener() {
  $('.thumbnails-default').click(function() {
    if(drag_status == true) {
      // 拖动过程不可能更换图片
      // 为了程序逻辑的完整性，还是要检查一下的
      return;
    }
    var date = new Date().Format('hh:mm:ss')
    var type = 'SelectThumbnails'
    var detailed = '';

    // clicker
    var tid = this.id
    var nid = tid.charAt(tid.length - 1)
    detailed += this.id + ' has been selected '

    if (thumbnails_onoff[nid - 1] == false) {
      for (var i = 1; i <= 4; i++) {
        $('#selected' + i).hide();
        thumbnails_onoff[i - 1] = false;
      }

      thumbnails_onoff[nid - 1] = true;
      $('#selected' + nid).show();
      detailed += '[showed] and other [closed].'

      // TODO: 改变大图的显示图像
      $('#puzzle').attr('src', $('#thumbnail' + nid).attr('src'))
    } else {
      thumbnails_onoff[nid - 1] = false;
      detailed += '[closed].'
      for (var i = 1; i <= 4; i++) {
        $('#selected' + i).hide();
      }
    }

    MouseListener(type, date, detailed)
    textAppender(date, type, detailed)
  })
}

function textAppender(date, etype, detailed){
  $("#textShower").append('<label>'
            +'<font size="1">[' + date + '] '
            + etype + ': '
            + detailed
            + '</font></label></br>');
}

function dragListener() {
  $('#puzzle').draggable({
    containment: '#container',
    start: function(){
      drag_status = true;
      textAppender((new Date()).Format('hh:mm:ss'), 'MouseDown', 'Drag start.')
      // console.log('Drag start')
    },
    drag: function(){
      console.log('drag...')
      // TODO: 记录鼠标具体事件变化
    },
    stop: function(){
      drag_status = false;
      textAppender((new Date()).Format('hh:mm:ss'), 'MouseRelease', 'Drag End.')
      // TODO: 记录鼠标结束事件，并且向服务器发送认证请求
      alert('You released the mouse to verified whether your are robot.')
      window.location.reload()
    }
  });
}

function buttonListener(){
  $('#resetBtn').click(function(){
    window.location.reload()
  })
}

// 查看日志
function checkLog() {
  for (var i = 0; i < mouselistener.length; i++) {
    console.log(mouselistener[i])
  }
}


$(document).ready(function() {
  dragListener();
  buttonListener();
  ThumbnailsListener();
})
