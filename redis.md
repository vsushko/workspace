Redis stack:
```
https://redis.io/docs/stack/
```
Distributed Locks with Redis:
```
https://redis.io/docs/manual/patterns/distributed-locks/
```
Commands:
```
https://redis.io/commands
```
rbook:
```
https://rbook.could
```
Local installation:
```
brew tap redis-stack/redis-stack
brew install redis-stack
redis-stack-server
```
To connect to your local Redis server and execute commands, run redis-cli

Design considerations:
```
1. What type of data are we storing?
2. Should we be concerned about the size of data?
3. Do we need to expire this data?
4. What will the key naming policy be for this data?
5. Any business-logic concerns?
```

Reasons to Store as Hash:
- The record has many attributes
- A collections of these records have to be stored many different ways
- Often need to access a single record at a time

Don’t use Hashes when…:
- The record is only for counting or enforcing uniqueness
- Record stores only one or two attributes
- Used only for creating relations between different records
- The record is only used for time series data

Set Use Cases
- enforcing uniqueness of any value
- creating a relationship between different records
- finding common attributes between different things
- general list of elements where order doesn't matter

Sorted Set Use Cases:
- tabulating 'the most' or 'the least' of a collection of hasshes
- creating relationships between records, sorted byy some criteria

HyperLogsLogs:
- algorithm for approximately counting the number of unique values
- similar to a set, but doesn't store the elements

Lists:
- store an ordered list of stirngs
- not an array
- implemented as doubly-linked list
- often used for time-series data

Lists Use Cases:
- append-only or prepend-only data (temperature readings, stsock values)
- when you only need the last/first N values of something
- your data has no sort order besides the order it was inserted

Not to use Lists:
- if you have many items
- you need to apply some filtering criteria
- your data is sorted by some attribute

Options for Handling Concurrency
- use an atomic update command (like HINCRBY or HSETNX)
- use a transaction with the 'WATCH' command
- use a lock
- use a custom LUA update script

When to use Lua scripts:
- limiting the amount of data exchanged between sserver + redis
- solving some concurrency issues
- minimizing the number of round trips between server + redis

Scripts down sides:
- keys must be known ahead of time
- tough to test scripts
- loss of language features (e.g. type checking with typescripts)
- another language to deal with (Lua)

Creating and Using an Index:
```
HSET cars#a1 name 'fast car' color red year 1960 
HSET cars#b1 name 'car' color red year 1960 
HSET cars#c1 name 'old car' color blue year 1960 
HSET cars#d1 name 'new car' color red year 1960 

FT.CREATE idx:cars on hash prefix 1 cars#
        schema name text year numeric color tag

FT.SEARCH idx:cars '@name:(fast car)'
FT.SEARCH idx:cars '@color:{blue}'
FT.SEARCH idx:cars '@year:[1960 1980]'
```
Queries with EXPLAIN:
```
FT.EXPLAINCLI idx:items 'chair'
```
Query performance with PROFILE:
```
FT.PROFILE idx:items SEARCH QUERY 'chairs' LIMIT 0 0
```
