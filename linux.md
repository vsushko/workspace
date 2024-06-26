#### How to check os version in Linux command line
1. Type any one of the following command to find os name and version in Linux:
```
cat /etc/os-release
lsb_release -a
hostnamectl
```
2. Type the following command to find Linux kernel version:
`uname -r`
run command by specifyed user:
`sudo -u myuser <command>`
change user:
`sudo su - user`
extract tar to the specified directory:
```
mkdir ~/kafka && cd ~/kafka
tar -xvzf ~/Downloads/kafka.tgz --strip 1
# The -J is the flag that specifically deals with .xz files.
tar -xJf file.pkg.tar.xz
```
ssh to remote server with pem:
```
ssh -i <pem-path>file.pem user@ip
```

Load key «~/.ssh/id_rsa»: bad permissions:
```
chmod 400 ~/.ssh/id_rsa
chmod 0400 ~/.ssh/id_rsa
```

prints existing ssh keys:
```
ls -al ~/.ssh
```
ssh localhost without password:
```
1. ssh-keygen -t rsa
Press enter for each line 
# id_rsa.pub!!!!!!!
2. cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
3. chmod og-wx ~/.ssh/authorized_keys
```
zip all files in directory:
```
zip -r myarch.zip mydir
```
shows bash history:
```
~/.bash_history
```
change hostname (without reboot):
```
hostname -f
sudo vim /etc/hosts
127.0.1.1 new-host-name
```
unset var i.e. http_proxy from export:
```
unset http_proxy
```
identify processes using files or sockets (fuser):
``` 
sudo fuser -k 2181/tcp
```
 shows files which opened by which process:
```
sudo lsof -i | grep 8080
```
kill process by listening port:
```
lsof -i :8080 | awk {'print $2'} | tail -1 | xargs kill -9
```
curl –no-check-certificate option like wget command
```
curl -k url
```
vim deletes all file content:
```
ggdG
```
generates 1GB file:
```
openssl rand -out sample.txt -base64 $(( 2**30 * 3/4 ))^C
```
mv to cur dir:
```
mv <path> .
```
check directory's folders size:
```
du -sh *
```
print path + file: 
```
echo `pwd`/file
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
netstat for checking who hang up on 8123
```
sudo netstat -tulpn | grep LISTEN | grep 8123
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
tail logs:
```
sudo tail -f -n 30 /var/log/app/mylog.log
```
make script executable:
```
chmod +x myscript.sh
```
add rights for executing:
```
chmod u+x filename
```
ubuntu screenshot utils:
```
sudo apt install mate-utils
# using
mate-screenshot (Key Bindings p)
mate-screenshot -w (Key Bindings w)
mate-screenshot -a (Key Bindings a)
```
lsblk - lists information about all available or the specified block devices:
```
lsblk
```
df displays the amount of disk space available on the file system containing each file name argument (-h --human-readable)
```
df -h 
```
create symbolic link:
```
ln -s kafka_2.13-2.6.0 kafka
```

Add alias for ssh:
```
vim ~/.ssh/config

Host <some.alias>
  HostName 192.168.0.1
  Port 22
  User <username>
``` 
Application
Installing a tar.gz on linux
```
https://stackoverflow.com/questions/33033538/installing-a-tar-gz-on-linux
```

generate id_rsa.pub from private key:
https://blog.tinned-software.net/generate-public-ssh-key-from-private-ssh-key/

Отключение systemd-timesyncd:
```
sudo timedatectl set-ntp no
```
“insufficient memory for the Java Runtime Environment ”
https://stackoverflow.com/questions/22805552/insufficient-memory-for-the-java-runtime-environment-message-in-eclipse

send 100 concurrent requests to server:
```
ab -n 100 -c 100 http://localhost:9091/mvc
```
expose local web server:
```
nc -v <IP_ADDRESS> 3000
```
How to Install Minikube on Ubuntu 22.04 LTS:
```
https://www.fosstechnix.com/how-to-install-minikube-on-ubuntu-22-04-lts/
```
Apache HTTP Server:
```
https://httpd.apache.org/docs/2.4/getting-started.html
```
