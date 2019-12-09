### andaconda installation guide:
https://docs.anaconda.com/anaconda/install/linux/

take column-slices(https://stackoverflow.com/questions/10665889/how-to-take-column-slices-of-dataframe-in-pandas/33997632):
```
# slice from 'foo' to 'cat' by every 2nd column
df.loc[:, 'foo':'cat':2]
# foo quz cat

# slice from the beginning to 'bar'
df.loc[:, :'bar']
# foo bar

# slice from 'quz' to the end by 3
df.loc[:, 'quz'::3]
# quz sat

# attempt from 'sat' to 'bar'
df.loc[:, 'sat':'bar']
# no columns returned

# slice from 'sat' to 'bar'
df.loc[:, 'sat':'bar':-1]
sat cat ant quz bar

# slice notation is syntatic sugar for the slice function
# slice from 'quz' to the end by 2 with slice function
df.loc[:, slice('quz',None, 2)]
# quz cat dat

# select specific columns with a list
# select columns foo, bar and dat
df.loc[:, ['foo','bar','dat']]
# foo bar dat
```
