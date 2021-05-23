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

Mockito tries to do injection during to find Mocks:
1) Constructor base injection
2) Setter baseed injection
3) Field based injection

InjectMock doesn't work with:
1) inner classes
2) static classes
3) interfaces 
4) final static fields
