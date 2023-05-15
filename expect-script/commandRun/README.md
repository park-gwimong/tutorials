
Perl의 Expect 모듈을 이용하여 원격으로 로그인 후 입력된 명령을 수행하는 코드입니다.  
>
```bash
#commandRun.pl -h [host] -u [userId] -p [userPassword] -rp [host's root password] -C [Command String]
```
# Options
  - -F [filePath] : 파일 모드로 수행. filePath는 원격에서 실행할 명령어 파일
  - -C [commandString] : 명령어 모드로 수행. 
  - -S : SSH로 접속
  - -T : Telnet로 접속
  - -P [Port] : 원격 접속할 포트 번호
