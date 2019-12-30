echo "%1" "%2"
echo need 2 params, params[1]=Branch, params[2]=version
echo please make sure you provide 2 params;
echo make sure the auto_build.bat folder is under d:\git\auto_build\
pause
d:
cd\
if not exist git exist 
cd git\auto_build\
if exist rel rd  rel /S /Q
echo create "rel" folder
mkdir rel
cd rel
del *.* /Q
cd\
cd git
if exist git_pub rd git_pub /S /Q
mkdir git_pub
git init git_pub
cd git_pub
echo pull source code
git pull http://10.9.19.245/ruifu.wm/ROSS %1
pause start to build project
cd ROSSWcfService
if exist bin rd bin /S /Q
echo build WCF service
msbuild ROSSWcfService.csproj /t:rebuild
cd bin
dir
copy *.* d:\git\auto_build\rel\
cd\
cd d:\git\auto_build\rel\
del *.config /Q
del *.xml /Q
del *.p12 /Q
cd ..\
if exist release_package_wcf rd release_package_wcf /S /Q
mkdir release_package_wcf
cd rel
WinRAR.exe a ..\release_package_wcf\rwcf_%2.zip *.*
d:
cd\
cd git\auto_build\
