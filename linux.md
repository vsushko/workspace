extract tar to the specified directory
```
mkdir ~/kafka && cd ~/kafka
tar -xvzf ~/Downloads/kafka.tgz --strip 1
```
prints existing ssh keys:
```
ls -al ~/.ssh
```
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
ps wlax:
```
ps wlax | grep ssh
```
reports a snapshot of the current processes on the system using BSD syntax:
```
ps aux | grep 11111
```
selects all processes and filters by jsvc:
```
ps -AH | grep jsvc
```
to see every process on the system using standard syntax:
```
ps -ef | grep kafka
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
post curl:
```
curl - k--header "Content-Type: application/json"--request POST--data '{"some":"json"}' https: //localhost:8080/url
```
ll:
```
vim ~/.bash_profile
alias ll='ls -lGaf'
```
do ps -ef in infinite loop:
```
while (true); do ps -ef | grep zookeeper; sleep 1; done
```
