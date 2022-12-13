# Bank-Api

## Description

The project is an example of a banking API that contains all the functions related to storing data and providing an API
to work with it.

## Installation

1. Download the source project from [GitHub](https://github.com/VadzimMatsiushonak/Bank-Api.git)
2. Run command to build the project ```./gradlew build clean```

## Usage

1. Start the project by running BankApiApplication main method or using ```./gradlew bootRun```
2. Open [Swagger](http://localhost:8080/swagger-ui/) to access Api GUI

## Useful links

- [Notion](https://www.notion.so/BankApi-4cd52d55c7204482a6f364f1b7687c5e)
- [Github](https://github.com/VadzimMatsiushonak/Bank-Api)
- [Gitlab](https://gitlab.com/vadzim.matsiushonak/bank-api)
- [ClickUp](https://app.clickup.com/43295014/v/b/6-386791473-2)
- [Toggl timer](https://track.toggl.com/timer)
- [BankApi model](https://app.sqldbm.com/MySQL/Edit/p237658/)

## Available Requests flow

1. Initiate payment

## Task Flow

1. Create/Get task in ClickUp
2. Assign it to yourself
3. Change the status to In Progress
4. Track time with ClickUp
5. Connect GitHub PR to ClickUp task
6. Merge the PR and change status to 'In Test' or 'Solved' (if tests are provided in the task)

## Git Flow

1. PR should have at least 1 reviewer
2. Use only squash and merge

## Naming

- PR
    - Title should have (tag) [task ID] task title
    - Example: (feat) [BA-1] Setup project structure
- Commits
    - The same rules as PR
- Task IDs
    - [taskType taskID]
    - current task types: BA - Backend, FE - Frontend, AD - Android
- Tags
    - feature, bug, tests, docs

## Api flows

### CRUD

1. CRUD request available on [Swagger](http://localhost:8080/swagger-ui/) with Admin role privileges

### Auth

### Payment

1. Payment initiation _**/api/v1/payments/initiatePayment**_

### Account