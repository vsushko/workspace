useful commands:

zip all files in directory:
```
zip -r myarch.zip mydir
```
shows bash history:
```
~/.bash_history
```
identify identify processes using files or sockets (fuser):
``` 
sudo fuser -k 2181/tcp
```
 shows files which opened by which process:
```
sudo lsof -i | grep 8080
```
reports a snapshot of the current processes on the system using BSD syntax:
```
ps aux | grep 11111
```
kill the process by pid (Similarly ‘kill -9 PID‘ is similar to ‘kill -SIGKILL PID‘ and vice-versa):
```
kill -9 11111
```
shows files with specific text:

```
grep -Ril "text-to-find-here" /

1. i stands for ignore case (optional in your case)
2. stands for recursive
3. stands for "show the file name, not the result itself"
4. / stands for starting at the root of your machine
```
tool to inspect classpath:
```
jdeps -cp my.jar com.test.MyMainClass
```
How to SSH on a port other than 22:
```
https://askubuntu.com/questions/264046/how-to-ssh-on-a-port-other-than-22
```
