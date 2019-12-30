echo test for expression
for /d %%i in (*) do echo %%i
cls
echo off
rem list all shedule tasks
for /f "tokens=2* delims=: " %%a in ('schtasks /query /fo list') do echo %%a
echo on
