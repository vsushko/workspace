chech current running session:
```
select a.SID, a.SERIAL#, c.OBJECT_NAME 
  from v$session a, v$locked_object b, user_objects c
where a.SID=b.SESSION_ID and b.OBJECT_ID=c.OBJECT_ID
```
