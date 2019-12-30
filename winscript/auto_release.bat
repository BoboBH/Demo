--Auto release 
echo "3 parameters is required"
echo "1%:env;2%:branch;3%:version"
echo "such as : auto_release dev-02 ross_4.7 4.7"
echo "Build prorject...."
call auto_build.bat %2 %3%
echo "complete auto_build and try to backup files"
echo "backup files"
cd\
cd git\auto_build
call backup.bat %1
pause
echo "Kill ROSSBackendService.exe..."
taskkill /im ROSSBackendService.exe /F
taskkill /im ROSSBackendService.exe /F

set rbs_folder=c:\rossbackservice
set wcf_folder=C:\ROSS_WCF\QA01\bin
set rbs_zip_file=C:\git\auto_build\release_package\rbs_%3.zip
set wcf_zip_file=C:\git\auto_build\release_package_wcf\rwcf_%3.zip
if %1==dev-02 set rbs_folder=c:\DEV_02_ROSSBackService
if %1==stage set rbs_folder=c:\stage_ROSSBackService
if %1==dev-02 set wcf_folder=C:\ROSS_WCF\DEV02\bin
if %1==stage set wcf_folder=C:\ROSS_WCF\STG01\bin
echo "copy extract files to target folder"
7z e %rbs_zip_file%  -o%rbs_folder% -y
7z e %wcf_zip_file%  -o%wcf_folder% -y

