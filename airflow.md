Dags location:
```
cd $AIRFLOW_HOME/dags/
```

### Commands
list users:
```
airflow list_users
```
simple DAG:
```python
import airflow
from airflow.models import DAG
from airflow.operators.bash_operator import BashOperator
from airflow.operators.dummy_operator import DummyOperator
from airflow.utils import timezone
from datetime import datetime, timedelta

args = {
    "owner": "airflow",
    "start_date": timezone.utcnow() - timedelta(hours=1)
}

dag = DAG(
    dag_id="vasiliy_sushko_lab01",
    default_args=args,
    schedule_interval="0 * * * *",
    dagrun_timeout=timedelta(minutes=60),
)

start = DummyOperator(
    task_id="start",
    dag=dag,
)

step0 = DummyOperator(
    task_id="task2",
    dag=dag,
)

start >> step0
```
