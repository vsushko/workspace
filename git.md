## API 
https://developer.github.com/v3/issues/#list-issues-for-a-repository

Changing Your Committer Name & Email Globally:
```
git config --global user.name "John Doe"
git config --global user.email "john@doe.org"
```
How do i update a github forked repository:<br>
https://stackoverflow.com/questions/7244321/how-do-i-update-a-github-forked-repository

server certificate verification failed. CAfile: /etc/ssl/certs/ca-certificates.crt CRLfile: none:
https://stackoverflow.com/questions/21181231/server-certificate-verification-failed-cafile-etc-ssl-certs-ca-certificates-c
```
export GIT_SSL_NO_VERIFY=1
#or
git config --global http.sslverify false
```
hide untracked filed:
```
git config --global status.showUntrackedFiles no
```
list remote branches:
```
git remote show origin
```
add local repo
```
git remote add <NAME> <PATH>
```
starting git bisect command:
```
git bisect start HEAD <commit>
```
comparing two branches:
```
git diff dev..master
```
or:
```
git diff --stat origin/master
```
generate new key:
```
ssh-keygen -t ed25519 -C "your_email@example.com"
```
Always use the "git" user:
```
ssh -T GITHUB-USERNAME@github.com
```
You should verify your connection by typing:
```
ssh -T git@github.com
```
will get you back 1 commit:
```
git reset --hard HEAD~1
```
revert specified file:
```
git checkout @ -- myfile.ext
```
```
git checkout -- myfile.ext
```
to undo a merge that was already pushed:
```
git revert -m 1 commit_hash
```
assuming your local master was not ahead of origin/master, you should be able to do:
```
git reset --hard origin/master
```
drops concrete record with changes from the stash list:
```
git stash drop stash@{0}
```
install latest git version on ubuntu:
```
sudo add-apt-repository ppa:git-core/ppa
sudo apt-get update
sudo apt-get install git
```
restore deleted but not commited file:
```
git ls-files --deleted
git checkout -- <file>
```
make file executable:
```
git update-index --chmod=+x filename
```
Commits per author:
```
git shortlog -s -n
```
