echo off
echo backup folder:  %1
set backup_folder=c:\rossbackservice
set wcf_backup_folder=C:\ROSS_WCF\QA01
if %1==dev-02 set backup_folder=c:\DEV_02_ROSSBackService
if %1==stage set backup_folder=c:\stage_ROSSBackService
if %1==dev-02 set wcf_backup_folder=C:\ROSS_WCF\DEV02
if %1==stage set wcf_backup_folder=C:\ROSS_WCF\STG01
set timestampe=%date:~6,4%%date:~0,2%%date:~3,2%%time:~0,2%%time:~3,2%%time:~6,2%
echo timestampe
echo %backup_folder%
cd %backup_folder%
7z a %backup_folder%\bak\%timestampe%.zip %backup_folder%\*.*
cd %wcf_backup_folder%
7z a %wcf_backup_folder%\backup\%timestampe%.zip %wcf_backup_folder%\bin\*.*
cd\
cd git\auto_build
echo on