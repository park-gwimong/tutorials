SSH 원격 접속 예제 코드들입니다.

## sshConnect.exp
Expect의 기본 문법인 tcl로 구현한 ssh 로그인 수행 코드입니다.
> 
```bash
#sshConnect.exp [host] [userId] [password]
```

## sshConnect.pl
Perl의 Expect 모듈을 이용하여 구현한 ssh 로그인 수행 코드입니다.
> 
```bash
#sshConnect.pl [host] [userId] [password]
```

## sshConnectUseNetSSHExpect.pl
Perl의 Expect의 확장 모듈인 NetSSHExpect를 이용하여 구현된 SSH 로그인 수행 코드입니다.
[[원문 코드]](https://metacpan.org/pod/distribution/Net-SSH-Expect/lib/Net/SSH/Expect.pod)  
> 
```bash
#sshConnectUseNetSSHExpec.pl
```
