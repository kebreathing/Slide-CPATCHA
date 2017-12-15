/**
 * author: Chris Long
 * Created at 2017/12/12 22:30
 * For Assignment of ISE
 */

var MAX_LOG_ITEMS = 10000;
var mouselistener = new Array();
var thumbnails_onoff = new Array(false, false, false, false);
var drag_status = false;

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
    var date = new Date()
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
  })
}

function dragListener() {
  $('#puzzle').draggable({
    containment: '#container',
    start: function(){
      console.log('Drag start')
    },
    drag: function(){
      console.log('drag...')
    },
    stop: function(){
      console.log('stop...')
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
