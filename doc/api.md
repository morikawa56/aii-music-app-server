# 音乐推荐系统API

## 一、用户相关接口

### 1. 注册

#### 1.1 基本信息

> 请求路径：/api/user/signup
>
> 请求方式：POST
>
> 接口描述：该接口用于注册新用户

#### 1.2 请求参数

请求参数格式：x-www-form-urlencoded

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注                        |
| -------- | ------ | ------ | -------- |---------------------------|
| username | 用户名 | string | 是       | 6~20位非空字符（无汉字以及除下划线外特殊字符） |
| password | 密码   | string | 是       | 8~25位非空字符（至少包含大小写以及数字）    |

请求数据样例：

```shell
username=zhangsan&password=Ajm564237
```

#### 1.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称    | 类型   | 是否必须 | 默认值 | 备注                   | 其他信息 |
| ------- | ------ | -------- | ------ | ---------------------- | -------- |
| code    | number | 必须     |        | 响应码，0-成功，1-失败 |          |
| status  | number | 必须     |        | HTTP状态码             |          |
| message | string | 非必须   |        | 提示信息               |          |
| data    | object | 非必须   |        | 返回的数据             |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": null
}
```

### 2. 登录

#### 2.1 基本信息

> 请求路径：/api/user/login
>
> 请求方式：POST
>
> 接口描述：该接口用于登录

#### 2.2 请求参数

请求参数格式：x-www-form-urlencoded

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注                                     |
| -------- | ------ | ------ | -------- | ---------------------------------------- |
| username | 用户名 | string | 是       | 6~20位非空字符（无汉字以及特殊字符）     |
| password | 密码   | string | 是       | 8~25位非空字符（至少包含大小写以及数字） |

请求数据样例：

```shell
username=zhangsan&password=Ajm564237
```

#### 2.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称    | 类型   | 是否必须 | 默认值 | 备注                   | 其他信息 |
| ------- | ------ | -------- | ------ | ---------------------- | -------- |
| code    | number | 必须     |        | 响应码，0-成功，1-失败 |          |
| status  | number | 必须     |        | HTTP状态码             |          |
| message | string | 非必须   |        | 提示信息               |          |
| data    | object | 必须     |        | 返回的数据，jwt令牌    |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjUsInVzZXJuYW1lIjoid2FuZ2JhIn0sImV4cCI6MTY5MzcxNTk3OH0.pE_RATcoF7Nm9KEp9eC3CzcBbKWAFOL0IsuMNjnZ95M"
}
```

#### 2.4 备注说明

> 用户登录成功后，系统会自动下发JWT令牌，然后在后续的每次请求中，浏览器都需要在请求头header中携带到服务端，请求头的名称为 Authorization，值为 登录时下发的JWT令牌。
>
> 如果检测到用户未登录，则http响应状态码为401

### 3. 获取用户详细信息

#### 3.1 基本信息

> 请求路径：/api/user
>
> 请求方式：GET
>
> 接口描述：该接口用于获取当前已登录用户的详细信息

#### 3.2 请求参数

无

#### 3.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称            | 类型   | 是否必须 | 默认值 | 备注                  | 其他信息 |
| --------------- | ------ | -------- | ------ | --------------------- | -------- |
| code            | number | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status          | number | 必须     |        | HTTP状态码            |          |
| message         | string | 非必须   |        | 提示信息              |          |
| data            | object | 必须     |        | 返回的数据            |          |
| \|-uid          | number | 非必须   |        | 主键ID                |          |
| \|-username     | srting | 非必须   |        | 用户名                |          |
| \|-nickname     | string | 非必须   |        | 昵称                  |          |
| \|-emailAddress | string | 非必须   |        | 电子邮件地址          |          |
| \|-phoneNumber  | string | 非必须   |        | 电话号码              |          |
| \|-avatarUrl    | string | 非必须   |        | 用户头像URL           |          |
| \|-permission   | string | 非必须   |        | 用户权限组            |          |
| \|-profile      | string | 非必须   |        | 个人简介等其它信息    | JSON格式 |
| \|-createdTime  | number | 非必须   |        | 创建账户时间          |          |
| \|-updatedTime  | number | 非必须   |        | 更新账户信息时间      |          |
| \|-isBanned     | number | 非必须   |        | 是否被封禁            |          |
| \|-bannedStart  | number | 非必须   |        | 封禁开始时间          |          |
| \|-bannedEnd    | number | 非必须   |        | 封禁结束时间          |          |

响应数据样例：

```json
{
    "code": 0,
    "data": {
        "avatarUrl": "",
        "bannedEnd": null,
        "bannedStart": null,
        "createdTime": "2024-03-31 20:36:32",
        "emailAddress": "",
        "isBanned": false,
        "nickname": "morikawa56",
        "permission": "admin",
        "phoneNumber": "",
        "profile": "{}",
        "uid": 2,
        "updatedTime": "2024-03-31 20:36:32",
        "username": "morikawa56"
    },
    "message": "操作成功",
    "status": 200
}
```

### 4. 更新用户信息

#### 4.1 基本信息

> 请求路径：/api/user
>
> 请求方式：PUT
>
> 接口描述：该接口用于更新用户的信息

#### 4.2 请求参数

请求参数格式：application/json

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注           |
| -------- | ------ | ------ | -------- | -------------- |
| uid      | 主键ID | number | 是       |                |
| nickname | 昵称   | string | 是       | 6~20位非空字符 |
| emailAddress | 电子邮件地址 | string | 是       | 满足邮箱的格式 |
| phoneNumber  | 电话号码           | string | 是 | 20位以内仅数字 |
| profile      | 个人简介等其它信息 | string | 是 | json格式 |

请求数据样例：

```json
{
    "uid":5,
    "nickname":"wb",
    "emailAddress":"wb@itcast.cn",
    "phoneNumber":"11122223333",
    "profile":"{}"
}
```

#### 4.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称    | 类型   | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ------- | ------ | -------- | ------ | --------------------- | -------- |
| code    | number | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status  | number | 必须     |        | HTTP状态码            |          |
| message | string | 非必须   |        | 提示信息              |          |
| data    | object | 必须     |        | 返回的数据            |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": null
}
```

### 5. 更新用户头像

#### 5.1 基本信息

> 请求路径：/api/user/avatar
>
> 请求方式：PATCH
>
> 接口描述：该接口用于更新已登录用户的头像

#### 5.2 请求参数

请求参数格式：queryString

请求参数说明：

| 参数名称  | 说明     | 类型   | 是否必须 | 备注    |
| --------- | -------- | ------ | -------- | ------- |
| avatarUrl | 头像地址 | string | 是       | url地址 |

请求数据样例：

```shell
avatarUrl=
https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png
```

#### 5.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称    | 类型   | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ------- | ------ | -------- | ------ | --------------------- | -------- |
| code    | number | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status  | number | 必须     |        | HTTP状态码            |          |
| message | string | 非必须   |        | 提示信息              |          |
| data    | object | 非必须   |        | 返回的数据            |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": null
}
```

### 6. 更新用户密码

#### 6.1 基本信息

> 请求路径：/api/user/password
>
> 请求方式：PATCH
>
> 接口描述：该接口用于更新已登录用户的密码

#### 6.2 请求参数

请求参数格式：application/json

请求参数说明：

| 参数名称 | 说明       | 类型   | 是否必须 | 备注                                     |
| -------- | ---------- | ------ | -------- | ---------------------------------------- |
| old_pwd  | 原密码     | string | 是       |                                          |
| new_pwd  | 新密码     | string | 是       | 8~25位非空字符（至少包含大小写以及数字） |
| re_pwd   | 确认新密码 | string | 是       | 8~25位非空字符（至少包含大小写以及数字） |

请求数据样例：

```json
{
    "old_pwd":"123456",
    "new_pwd":"234567",
    "re_pwd":"234567"
}
```

#### 6.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称    | 类型   | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ------- | ------ | -------- | ------ | --------------------- | -------- |
| code    | number | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status  | number | 必须     |        | HTTP状态码            |          |
| message | string | 非必须   |        | 提示信息              |          |
| data    | object | 非必须   |        | 返回的数据            |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": null
}
```

### 7. 用户权限申请

#### 7.1 基本信息

> 请求路径：/api/user/permission
>
> 请求方式：PATCH
>
> 接口描述：该接口用于申请更新用户权限组，将发送到管理员待处理列表等待通过

#### 7.2 请求参数

请求参数格式：x-www-form-urlencoded

请求参数说明：

| 参数名称     | 说明           | 类型   | 是否必须 | 备注 |
| ------------ | -------------- | ------ | -------- | ---- |
| newPermisson | 申请新的权限组 | string | 是       |      |

请求数据样例：

```shell
newPermisson=creator
```

#### 7.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称    | 类型   | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ------- | ------ | -------- | ------ | --------------------- | -------- |
| code    | number | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status  | number | 必须     |        | HTTP状态码            |          |
| message | string | 非必须   |        | 提示信息              |          |
| data    | object | 非必须   |        | 返回的数据            |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": null
}
```

### 8. 封禁用户申请

#### 8.1 基本信息

> 请求路径：/api/user/ban
>
> 请求方式：PATCH
>
> 接口描述：该接口用于申请封禁或自我暂时停用用户，发送到管理员待处理列表等待通过

#### 8.2 请求参数

请求参数格式：x-www-form-urlencoded

请求参数说明：

| 参数名称 | 说明         | 类型   | 是否必须 | 备注         |
| -------- | ------------ | ------ | -------- | ------------ |
| uid      | 用户主键uid  | number | 是       |              |
| isBanned | 设定是否封禁 | number | 是       | 0或1一位数字 |

请求数据样例：

```shell
uid=6&isBanned=1
```

#### 8.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称    | 类型   | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ------- | ------ | -------- | ------ | --------------------- | -------- |
| code    | number | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status  | number | 必须     |        | HTTP状态码            |          |
| message | string | 非必须   |        | 提示信息              |          |
| data    | object | 非必须   |        | 返回的数据            |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": null
}
```

### 9. 登出

#### 9.1 基本信息

> 请求路径：/api/user/logout
>
> 请求方式：POST
>
> 接口描述：该接口用于登出当前账户

#### 9.2 请求参数

无

#### 9.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称    | 类型   | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ------- | ------ | -------- | ------ | --------------------- | -------- |
| code    | number | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status  | number | 必须     |        | HTTP状态码            |          |
| message | string | 非必须   |        | 提示信息              |          |
| data    | object | 非必须   |        | 返回的数据            |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": null
}
```
