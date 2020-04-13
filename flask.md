installation:
```
virtualenv -p /usr/bin/python3 flaskenv
source flaskenv/bin/activate
pip install Flask
```
export var:
```
export FLASK_APP=./flask.py
```
then:
```
flask run
```
externally visible server:
```
flask run --host=0.0.0.0
```
