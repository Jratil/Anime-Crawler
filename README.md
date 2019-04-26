## Anime Crawler  
简介： 此项目为爬虫项目，用于爬取某个网站上的图片，提供api接口。使用开源框架webmagic，springboot, mybatis, Redis等进行网站图片
的爬取，对外提供api访问接口。  
  
1. 使用统一异常处理来对爬取过程中可能产生的异常进行处，对400和500系列错误进行了统一处理   
  
2. 对api的访问次数进行限制  
  
3. 设置了每日定时爬取，自动爬取图片链接到数据库，对api进行访问时优先从数据库进行查询  
  
### 主要api:  
1. /api: 查看具体的api;  

2. /api/gallery/{pageNum}/{galleryNum}： 直接获取图片列表  
`eg: /api/gallery/1/1`
  
3. /api/pageUrl/{pageNum}：获取页数，配合下面获取图片使用，一般用于分页  
`eg: /api/page/1`
  
4. /api/picUrl/{picNum}：获取图片，配合上面获取页数使用，picNum为获取页数返回的列表中的一项  
`eg: /api/page/76844`

[Demo地址](http://120.79.172.32:8080/api)
