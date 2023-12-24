install homebrew on mac:
```
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```
install postgress:
```
https://www.codementor.io/engineerapart/getting-started-with-postgresql-on-mac-osx-are8jcopb
```
install maven:
```
brew install maven
```
where is jdk located:
```
ls /Library/Java/JavaVirtualMachines/
```
or
```
echo $(/usr/libexec/java_home)
```
or
```
/usr/libexec/java_home -v
```

instal the latests version of java 8 via cask from third party repo:
```
brew cask install java8
```

alias for ll:
```
alias ll='ls -lG'
```
### installing java:
```
brew install openjdk@17
```
create a link:
```
sudo ln -sfn /usr/local/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```
remove .DS_Store files:
```sh
find . -name .DS_Store -print0 | xargs -0 git rm -f --ignore-unmatch
```
install ports:
```
https://www.macports.org/install.php
```


