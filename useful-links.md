Fedora:

1. DNF system upgrade : https://fedoraproject.org/wiki/DNF_system_upgrade

Update your system using dnf:

```dnf upgrade --refresh```

Install dnf-plugin-system-upgrade package:

```dnf install ndf-plugin-system-upgrade```

Download the updated packages:

```dnf system-upgrade download --refresh --releasever=25 --allowerasing```

Trigger the upgrade process:

```dnf system-upgrade reboot```

IDE:

`https://plugins.jetbrains.com/plugin/7973-sonarlint`

`http://www.sonarlint.org/index.html`

docker-compose:

https://docs.docker.com/compose/install

docker-machine:

https://docs.docker.com/machine/install-machine/#installing-machine-directly

```
curl -L https://github.com/docker/machine/releases/download/v0.10.0/docker-machine-`uname -s`-`uname -m` >/tmp/docker-machine &&
  chmod +x /tmp/docker-machine &&
  sudo cp /tmp/docker-machine /usr/local/bin/docker-machine
  ```
virtualbox for docker-machine:

```
https://docs.docker.com/machine/drivers/virtualbox
https://www.if-not-true-then-false.com/2010/install-virtualbox-with-yum-on-fedora-centos-red-hat-rhel/
https://www.virtualbox.org/wiki/Downloads  
https://www.youtube.com/watch?v=xiqZYvu7gdE
```
java decompiler:
```
(http://jd.benow.ca/)
```
audio recorder:
```
http://ubuntuhandbook.org/index.php/2016/02/install-audio-recorder-in-ubuntu-16-04-15-10/
```

