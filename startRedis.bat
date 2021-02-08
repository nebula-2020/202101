@echo off
title redis-server
cd /d "%ProgramFiles%\Redis-x64-5.0.10"
redis-server.exe redis.windows.conf
pause