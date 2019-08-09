get oracle version:
```
SELECT * FROM v$version; 
```
check current running session:
```
select a.SID, a.SERIAL#, c.OBJECT_NAME 
  from v$session a, v$locked_object b, user_objects c
where a.SID=b.SESSION_ID and b.OBJECT_ID=c.OBJECT_ID
```

SELECT PLAN_TABLE_OUTPUT FROM TABLE(DBMS_XPLAN.DISPLAY());

Difference between fast full index scan and range scan:
http://www.dbaref.com/home/dba-routine-tasks/whatisfastfullindexscanvsindexrangescan
https://docs.oracle.com/cd/B19306_01/server.102/b14211/ex_plan.htm#i26038

```
EXPLAIN PLAN 
  SET statement_id = 'ex_plan2' FOR
SELECT last_name FROM employees
 WHERE last_name LIKE 'Pe%';

SELECT PLAN_TABLE_OUTPUT 
  FROM TABLE(DBMS_XPLAN.DISPLAY(NULL, 'ex_plan2','BASIC'));
```
