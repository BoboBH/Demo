@echo off&setlocal enabledelayedexpansion
for /f "tokens=1* delims=: " %%a in ('schtasks /query /fo list') do (
  set "name=%%b"
  if "%%a"=="任务名" if /i "!name:~,8!"=="Del_File" schtasks /delete /tn "%%b" /f
)
pause