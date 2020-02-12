# About
Scala REST API example
Resources:
  - tasks(id: Int, title: String, description: String)
  - calendars(id: Int, title: String, description: String, date_start: Timestamp, date_end: Timestamp)

##
Supported method: GET, POST, PUT, PATCH, DELETE

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

PUT /v1/tasks
```

##
Update task id 1 with JSON object (update only some fileds, not all fields are required)
```
{
  "title": "my patched title",
}

PATCH /v1/tasks
```

##
Delete task id 1

```DELETE /v1/tasks/1```

#
The same operation we can do with calendars resource.
