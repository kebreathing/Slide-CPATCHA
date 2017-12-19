# coding:utf-8
from django.shortcuts import render
from django.http import HttpResponse


# def index(request):
#     return HttpResponse(u"欢迎光临 自强学堂!")
# Create your views here.

def home(request):
    #所有网页都可以继承的字符串
    # 在html中用 {{ string }} 继承
    #string = u"我在自强学堂学习Django，用它来建网站"
    #return render(request, 'base.html',{'string':string})
    return render(request, 'puzzle.html')


# def add(request):
#     a = request.GET['a']
#     b = request.GET['b']
#     a = int(a)
#     b = int(b)
#     return HttpResponse(str(a+b))