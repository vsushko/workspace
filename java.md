imports order(https://github.com/twitter/commons/blob/master/src/java/com/twitter/common/styleguide.md):

```
:::java
import java.*
import javax.*

import com.*

import net.*

import org.*

import all other

import static *
```
for bytecode and constant pool inspection of any class file:
```
 javap -c -v ClassName
```
memory analyzer mat:
```
https://www.eclipse.org/mat/
```
prints to the console when a gc takes place:
```
-verbose:gc
```
