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


