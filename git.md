Changing Your Committer Name & Email Globally:
```
git config --global user.name "John Doe"
git config --global user.email "john@doe.org"
```
How do i update a github forked repository:<br>
https://stackoverflow.com/questions/7244321/how-do-i-update-a-github-forked-repository

hide untracked filed:
```
git config --global status.showUntrackedFiles no
```
list remote branches:
```
git remote show origin
```
comparing two branches:
```
git diff dev..master
```
will get you back 1 commit:
```
git reset --hard HEAD~1
```
to undo a merge that was already pushed:
```
git revert -m 1 commit_hash
```
assuming your local master was not ahead of origin/master, you should be able to do:
```
git reset --hard origin/master
```
