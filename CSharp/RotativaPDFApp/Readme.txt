1.Rotativa使用wkhtmltopdf/image来完成html到pdf的转换；
2.Windows环境下，将wkhtmltopdf.exe,hkhtmltoimage.exe复制到wwwroot\Rotativa目录下；
3.Linux下需要安装xvfb,libfontconfig wkhtmltopdf组建，并将wkhtmltopdf/image复制打wwwroot/Rotativa目录
  PS:Ubuntu下apt-get install安装是，debian中的镜像有bug，会报could not connect to display.
     3.1需要下载wkhtmltopdf的源代码并编译安装；
	 3.2或者增加头运行 xvfb-run wkhtmltopdf https://www.baidu.com /home/currentuser/baidu.pdf