# -*- coding: UTF-8 -*-
"""slip URL Configuration
 网址入口，关联到对应的views.py中的一个函数（或者generic类），访问网址就对应一个函数。
The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
# from django.contrib import admin
# from . import view, search, post

from slipser import views as slipser_views

urlpatterns = [
    #url(r'^$',view.index),
    # url(r'^search-form$', search.search_form),
    # url(r'^search$', search.search),
    # url(r'^search-post$', post.search_post),
    #将APP中的视图函数对应到网站
    url(r'^Slide-CPATCHA$', slipser_views.home, name='home'),
    #学习调试用
    #url(r'^admin/', admin.site.urls),
    #url(r'^$',slipser_views.index),
]
