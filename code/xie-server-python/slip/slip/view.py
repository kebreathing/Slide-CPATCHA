# -*- coding: UTF-8 -*-
#处理用户发出的请求，从urls.py中对应过来,
# 通过渲染templates中的网页可以将显示内容
# 比如登陆后的用户名，用户请求的数据，输出到网页。
from django.http import HttpResponse

from django.shortcuts import render



# def index(request):    #接受用户请求
#     return render(request, 'hello.html')