<h2>Anime Crawler</h2>
简介： 此项目为爬虫项目，用于爬取某个网站上的图片，提供api接口
<h3>主要api为：</h3>
1. /api/gallery/{pageNum}/{galleryNum}： 直接获取图片列表，eg: /api/gallery/1/1；
<br>
2. /api/pageUrl/{pageNum}：获取页数，配合下面获取图片使用，一般用于分页，eg: /api/page/1；
<br>
3. /api/picUrl/{picNum}：获取图片，配合上面获取页数使用，picNum为获取页数返回的列表中的一项，eg: /api/page/76844;
<br>
<strong>[demo地址](http://120.79.172.32:8080/api) </strong>
