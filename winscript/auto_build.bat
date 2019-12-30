echo "%1" "%2"
echo need 2 params, params[1]=Branch, params[2]=version
echo please make sure you provide 2 params;
 rem pause
c:
cd\
cd git\auto_build\
if exist rel rd  rel /S /Q
echo create "rel" folder
mkdir rel
cd rel
del *.* /Q
cd\
cd git
if not exist %1 (
   echo "need create new folder" %1%
   mkdir %1
   git init %1%
)
cd %1
echo pull source code
git pull http://10.9.19.245/ruifu.wm/ROSS %1
rem pause start to build project
cd ROSSBackendService
if exist bin rd bin /S /Q
echo build backend service process
msbuild ROSSBackendService.csproj /t:rebuild /p:Configuration=Release
cd bin\Release
dir
copy *.* c:\git\auto_build\rel\
cd\
cd c:\git\auto_build\rel\
del *.config /Q
del *.xml /Q
del *.p12 /Q
cd ..\
if exist release_package rd release_package /S /Q
mkdir release_package
cd rel
7z a ..\release_package\rbs_%2.zip *.*
cd c:\git\%1\ROSSWcfService
msbuild ROSSWcfService.csproj /t:rebuild /p:Configuration=Release
cd bin
dir
del c:\git\auto_build\rel\*.* /S /Q
copy *.* c:\git\auto_build\rel\
cd\
cd c:\git\auto_build\rel\
del *.config /Q
del *.xml /Q
del *.p12 /Q
cd ..\
if exist release_package_wcf rd release_package_wcf /S /Q
mkdir release_package_wcf
cd rel
7z a ..\release_package_wcf\rwcf_%2.zip *.*
pause
cd\
cd git\auto_build\
echo "end of auto_build"
