

https://stackoverflow.com/questions/32019556/matplotlib-crashing-tkinter-application/34109240#34109240


## Virtual environment
Install the virtualenv package:
```
pip install virtualenv
```
Create the virtual environment:
```
virtualenv mypython
```
Create the virtual environment with specifying path:
```
virtualenv -p /usr/bin/python3 mypython
```
Activate the virtual environment:
```
source mypython/bin/activate
```
Deactivate the virtual environment:
```
deactivate
```
Save all the packages in the file:
```
pip freeze > requirements.txt
```
