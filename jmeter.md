install
```
brew install jmeter
```
run:
```
open /usr/local/bin/jmeter
```
jmeter brew location:
```
/usr/local/Cellar/JMeter
```
put jar into lib/ext:
```
https://jmeter-plugins.org/wiki/PluginInstall/
```
for the report install:
```
option > available plugins > jpgc > jpgc Standard Set > apply and restart
```
visual vm download:
```
https://visualvm.github.io/download.html
```

Best Practices:
- Run the test using CLI. Never use GUI.
- Disable all the listeners. They all consume memory.
- "View Results Tree" - is the worst of all listeners in consuming memory.
- Remember - JVM will do optimization in the beginning.
  - JIT/ class loading ...etc
  - After server start, the first few iterations will be slow.
  - So, run a warm up test for few minutes. Ignore the results.
