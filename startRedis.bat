@echo off
title redis-server
cd /d "C:\Program Files\Redis-x64-5.0.10"
redis-server.exe redis.windows.conf
pause