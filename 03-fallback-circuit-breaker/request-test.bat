@echo off

@REM while true; do sleep 1; curl http://localhost:8081/hello?m=mymessage; echo -e "\n"; done;

:loop
curl http://localhost:8080/hello?m=mymessage
echo ""
timeout /t 1 > nul
goto loop
