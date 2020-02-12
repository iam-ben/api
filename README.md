# About
Scala REST API example

Resources:
  - tasks(id: Int, title: String, description: String)
  - calendars(id: Int, title: String, description: String, date_start: Timestamp, date_end: Timestamp)

##
Supported method: GET, POST, PUT, PATCH, DELETE

Header Content-Type must be application/json

##
Requirements

- PostgreSQL

  ```
  > Docker pull postgres
  > Docker container create --name scala-api-exp -e POSTGRES_PASSWORD=testing -p 35432:5432 postgres
  > Docker start scala-api-exp

- Create database

  ```
  -- Login to PostgreSQL
  > psql -d postgres -p 35432 --username postgres
  
  -- Create user admin:
  > postgres=# CREATE USER dbadmin WITH password 'test_password'
  
  -- Create the database :
  > postgres=# CREATE DATABASE api WITH OWNER=dbadmin
                  LC_COLLATE='en_US.utf8'
                  LC_CTYPE='en_US.utf8'
                  ENCODING='UTF8'
                  TEMPLATE=template0;
  

 - Schema & tables will be handled by Flyway migration.

##
Example

##
Retreive all tasks

```GET /v1/tasks```

##
Retrieve task with id 1

```GET /v1/tasks/1```

##
Create a task with JSON object (all fields are required)

```
{
  "title": "my title",
  "description": "my description"
}

POST /v1/tasks
```

##
Update task id 1 with JSON object (all fields are required)

```
{
  "title": "my updated title",
  "description": "my updated description"
}

PUT /v1/tasks/1
```

##
Update task id 1 with JSON object (update only some fileds, not all fields are required)
```
{
  "title": "my patched title",
}

PATCH /v1/tasks/1
```

##
Delete task id 1

```DELETE /v1/tasks/1```

#
The same operation we can do with calendars resource.
