Mockito 2 limitations:

- Can't mock static methods
- Can't mock constructors
- Can't mock equals(), hashCode()
- Can't mock final methods
- Can't mock private methods

We usually use when we want stub void methods these bucnh of methods or sometimes we using spies (familiy methods):
- doReturn()
- doThrow()
- doAnswer()
- doNothing()
- doCallRealMethod()
