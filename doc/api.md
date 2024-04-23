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

| 参数名称      | 说明           | 类型   | 是否必须 | 备注 |
| ------------- | -------------- | ------ | -------- | ---- |
| uid           | 用户主键uid    | number | 是       |      |
| newPermission | 申请新的权限组 | string | 是       |      |

请求数据样例：

```shell
uid=3&newPermission=creator
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

| 参数名称 | 说明         | 类型    | 是否必须 | 备注                     |
| -------- | ------------ | ------- | -------- | ------------------------ |
| uid      | 用户主键uid  | number  | 是       | 非管理员只能申请自我封禁 |
| isBanned | 设定是否封禁 | boolean | 是       |                          |

请求数据样例：

```shell
uid=6&isBanned=true
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

## 二、音乐相关接口

### 1. 添加

#### 1.1 基本信息

> 请求路径：/api/music
>
> 请求方式：POST
>
> 接口描述：该接口添加一首歌曲，需要上传歌曲资源以及一些基本信息

#### 1.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称      | 说明     | 类型      | 是否必须 | 备注 |
| ------------- | -------- | --------- | -------- | ---- |
| musicname     | 音乐名称 | string    | 是       |      |
| creator       | 创作者ID | string    | 是       |      |
| album         | 专辑信息 | string    | 是       |      |
| publishedTime | 发行时间 | timestamp | 是       |      |
| res          | 表单中文件请求参数的名字 | file   | 是       |      |      |
| musicAvatar  | 音乐图片                 | file   | 否       |      |      |
| introduction | 音乐介绍                 | string | 否       |      |      |

请求数据样例：

无

#### 1.3 响应数据

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

### 2. 查询歌曲列表（条件分页）

#### 2.1 基本信息

> 请求路径：/api/music
>
> 请求方式：GET
>
> 接口描述：该接口根据筛选条件查询歌曲列表

#### 2.2 请求参数

请求参数格式：x-www-form-urlencoded

请求参数说明：

| 参数名称  | 说明               | 类型    | 是否必须 | 备注        |
| --------- | ------------------ | ------- | -------- | ----------- |
| pageNum   | 当前页码           | number  | 是       |             |
| pageSize  | 每页条数           | number  | 是       |             |
| musicname | 音乐名称           | string  | 否       |             |
| creator   | 创作者名称         | string  | 否       |             |
| mode      | 是否开启仅当前用户 | boolean | 否       | 默认为false |

请求数据样例：

无

#### 2.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称             | 类型      | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ---------------- | --------- | -------- | ------ | --------------------- | -------- |
| code             | number    | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status           | number    | 必须     |        | HTTP状态码            |          |
| message          | string    | 非必须   |        | 提示信息              |          |
| data             | object    | 必须     |        | 返回的数据            | JSON数组 |
| \|-mid           | number    | 非必须   |        | 音乐主键ID            |          |
| \|-musicname     | string    | 非必须   |        | 音乐名称              |          |
| \|-creator       | string    | 非必须   |        | 创作者ID              | JSON     |
| \|-album         | string    | 非必须   |        | 专辑ID                | JSON     |
| \|-introduction  | string    | 非必须   |        | 歌曲介绍              |          |
| \|-musicAvatar   | string    | 非必须   |        | 音乐图片地址          |          |
| \|-lyric         | string    | 非必须   |        | 歌词                  |          |
| \|-resUrl        | string    | 非必须   |        | 资源地址              |          |
| \|-publishedTime | timestamp | 非必须   |        | 发行时间              |          |
| \|-createdTime   | timestamp | 非必须   |        | 创建时间              |          |
| \|-updatedTime   | timestamp | 非必须   |        | 更新时间              |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": [{
        "mid": 22,
        "musicname": "aaaa",
        "creator": [2,25],
        "album": [53],
        "introduction": "bbbbbbbbb...",
        "musicAvatar": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.jpg",
        "lyric": "………………",
        "resUrl": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.mp3",
        "publishedTime": 1435645397,
        "createdTime": 1712634453,
        "updatedTime": 1712645397
    },
    {
        "mid": 2345342,
        "musicname": "eeee",
        "creator": [24,453],
        "album": [5345,4234],
        "introduction": "bbsdfsdfgb...",
        "musicAvatar": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b582231-acc8-4dfs-8234-cf0325391ab.jpg",
        "lyric": "………………",
        "resUrl": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.mp3",
        "publishedTime": 1435645397,
        "createdTime": 1712634452,
        "updatedTime": 1712645397
    }
    ...
    ]
}
```

### 3. 更新歌曲信息

#### 3.1 基本信息

> 请求路径：/api/music
>
> 请求方式：PUT
>
> 接口描述：该接口用于更新歌曲的基本信息

#### 3.2 请求参数

请求参数格式：application/json

请求参数说明：

| 参数名称      | 说明         | 类型   | 是否必须 | 备注     |      |
| ------------- | ------------ | ------ | -------- | -------- | ---- |
| mid           | 主键ID       | number | 是       |          |      |
| musicname     | 音乐名称     | string | 否       |          |      |
| creator       | 创作者ID数组 | string | 否       | JSON格式 |      |
| introduction  | 音乐介绍     | string | 否       |          |      |
| publishedTime | 发布时间     | string | 否       |          |      |
| lyric         | 歌词信息     | string | 否       |          |      |

请求数据样例：

```json
{
    "mid":4,
    "musicname": "aaa",
    "creator": "[43,543]",
    "introduction": "bbbbb",
    "publishedTime": 1712645397,
    "lyric": "………………"
}
```

#### 3.3 响应数据

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

### 4. 更新歌曲资源

#### 4.1 基本信息

> 请求路径：/api/music/res
>
> 请求方式：PUT
>
> 接口描述：该接口用于更新歌曲的资源链接

#### 4.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称 | 说明                     | 类型   | 是否必须 | 备注 |      |
| -------- | ------------------------ | ------ | -------- | ---- | ---- |
| mid      | 主键ID                   | number | 是       |      |      |
| file     | 表单中文件请求参数的名字 | file   | 是       |      |      |

请求数据样例：

无

#### 4.3 响应数据

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
    "data": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.mp3"
}
```

### 5. 更新歌曲图片

#### 5.1 基本信息

> 请求路径：/api/music/avatar
>
> 请求方式：PUT
>
> 接口描述：该接口用于更新歌曲的封面图片

#### 5.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称 | 说明                     | 类型   | 是否必须 | 备注 |      |
| -------- | ------------------------ | ------ | -------- | ---- | ---- |
| mid      | 主键ID                   | number | 是       |      |      |
| file     | 表单中文件请求参数的名字 | file   | 是       |      |      |

请求数据样例：

无

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
    "data": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.jpg"
}
```

### 6. 下架歌曲

#### 6.1 基本信息

> 请求路径：/api/music/unshelve
>
> 请求方式：DELETE
>
> 接口描述：该接口用于临时或彻底删除某个歌曲

#### 6.2 请求参数

请求参数格式：application/json

请求参数说明：

| 参数名称 | 说明     | 类型    | 是否必须 | 备注                                                         |      |
| -------- | -------- | ------- | -------- | ------------------------------------------------------------ | ---- |
| mid      | 主键ID   | number  | 是       |                                                              |      |
| type     | 下架类型 | boolean | 是       | false=永久下架（删除所有信息）\|true=临时下架（禁止用户访问） |      |

请求数据样例：

```json
{
    "mid": 5,
    "type": true
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

### 7. 重新上架歌曲

#### 7.1 基本信息

> 请求路径：/api/music/shelve
>
> 请求方式：DELETE
>
> 接口描述：该接口用于重新上架被临时下架的歌曲

#### 7.2 请求参数

请求参数格式：x-www-form-urlencoded

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注 |      |
| -------- | ------ | ------ | -------- | ---- | ---- |
| mid      | 主键ID | number | 是       |      |      |

请求数据样例：

```shell
mid=5
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

### 8. 添加歌曲到歌单

#### 8.1 基本信息

> 请求路径：/api/music/listed
>
> 请求方式：POST
>
> 接口描述：该接口添加一首歌曲到歌单中

#### 8.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注 |      |
| -------- | ------ | ------ | -------- | ---- | ---- |
| mid      | 音乐ID | number | 是       |      |      |
| mlid     | 歌单ID | number | 是       |      |      |

请求数据样例：

```shell
mid=5&mlid=20
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

### 9. 收藏歌曲

#### 9.1 基本信息

> 请求路径：/api/music/fav
>
> 请求方式：PUT
>
> 接口描述：该接口收藏一首歌曲

#### 9.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注 |      |
| -------- | ------ | ------ | -------- | ---- | ---- |
| mid      | 歌曲ID | number | 是       |      |      |

请求数据样例：

```shell
mid=20
```

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

### 10. 删除歌单歌曲

#### 10.1 基本信息

> 请求路径：/api/music/listed
>
> 请求方式：DELETE
>
> 接口描述：该接口删除歌单指定歌曲

#### 10.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注 |      |
| -------- | ------ | ------ | -------- | ---- | ---- |
| mid      | 音乐ID | number | 是       |      |      |
| mlid     | 歌单ID | number | 是       |      |      |

请求数据样例：

```shell
mid=5&mlid=20
```

#### 10.3 响应数据

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

### 11. 取消收藏歌曲

#### 11.1 基本信息

> 请求路径：/api/music/fav
>
> 请求方式：DELETE
>
> 接口描述：该接口取消收藏一首歌曲

#### 11.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注 |      |
| -------- | ------ | ------ | -------- | ---- | ---- |
| mid      | 歌曲ID | number | 是       |      |      |

请求数据样例：

```shell
mid=20
```

#### 11.3 响应数据

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

## 三、歌单相关接口

### 1. 添加歌单

#### 1.1 基本信息

> 请求路径：/api/musiclist
>
> 请求方式：POST
>
> 接口描述：该接口建立一个新歌单

#### 1.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称      | 说明   | 类型   | 是否必须 | 备注 |      |
| ------------- | ------ | ------ | -------- | ---- | ---- |
| musiclistname | 歌单名 | string | 是       |      |      |

请求数据样例：

```shell
musiclistname=喜欢的歌曲
```

#### 1.3 响应数据

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

### 2. 修改歌单信息

#### 2.1 基本信息

> 请求路径：/api/musiclist
>
> 请求方式：PUT
>
> 接口描述：该接口修改歌单信息的基本信息

#### 2.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称        | 说明     | 类型   | 是否必须 | 备注 |      |
| --------------- | -------- | ------ | -------- | ---- | ---- |
| mlid            | 歌单ID   | number | 是       |      |      |
| musiclistAvatar | 歌单封面 | file   | 否       |      |      |
| introduction    | 歌单介绍 | string | 否       |      |      |
| style           | 歌单风格 | string | 否       |      |      |

请求数据样例：

无

#### 2.3 响应数据

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

### 3. 修改歌单名称

#### 3.1 基本信息

> 请求路径：/api/musiclist/name
>
> 请求方式：PUT
>
> 接口描述：该接口修改歌单信息的基本信息

#### 3.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称      | 说明   | 类型   | 是否必须 | 备注 |      |
| ------------- | ------ | ------ | -------- | ---- | ---- |
| mlid          | 歌单ID | number | 是       |      |      |
| musiclistname | 歌单名 | string | 是       |      |      |

请求数据样例：

````shell
mlid=5&musiclistname=hahaha
````



#### 3.3 响应数据

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


### 4. 导出歌单列表

#### 4.1 基本信息

> 请求路径：/api/musiclist
>
> 请求方式：GET
>
> 接口描述：该接口导出根据条件筛选的歌单列表

#### 4.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称      | 说明     | 类型   | 是否必须 | 备注 |      |
| ------------- | -------- | ------ | -------- | ---- | ---- |
| pageNum   | 当前页码           | number  | 是       |             ||
| pageSize  | 每页条数           | number  | 否      | 默认为10 ||
| mlid          | 歌单ID   | number | 否       |      |      |
| uid | 用户ID | number | 否 | 用于判断是否为用户创建列表，如果启用筛选用户歌单却没有填写本字段则默认输出本用户歌单 | |
| musiclistname | 歌单名   | string | 否       |      |      |
| style         | 歌单风格 | string | 否       |      |      |
| mode | 模式 | boolean | 否 | 默认为否，返回全局歌单，是为指定用户歌单 | |

请求数据样例：

````shell
pageNum=1&pageSize=10&mode=false&mlid=5

or

musiclistname=hahaha

or

style=anime
````



#### 4.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称    | 类型   | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ------- | ------ | -------- | ------ | --------------------- | -------- |
| code    | number | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status  | number | 必须     |        | HTTP状态码            |          |
| message | string | 非必须   |        | 提示信息              |          |
| data    | object | 非必须   |        | 返回的数据            |          |
| \|-mlid          | number    | 非必须   |        | 歌单ID       |          |
| \|-musiclistname | string    | 非必须   |        | 歌单名称       |          |
| \|-uid | number | 非必须 | | 创建用户ID | |
| \|-musiclist_pic | string    | 非必须   |        | 歌单图片地址          |          |
| \|-introduction  | string    | 非必须   |        | 歌单介绍              |          |
| \|-style         | string | 非必须 | | 歌单风格              | |
| \|-createdTime   | timestamp | 非必须   |        | 创建时间              |          |
| \|-updatedTime   | timestamp | 非必须   |        | 更新时间              |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": [
        {
            "mlid": 5,
            "musiclistname": "hahaha",
            "uid": 10,
            "musiclist_pic": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.jpg",
            "introduction": ".....",
            "style": "...",
            "created_time": 1435645397,
            "updated_time": 1435645397
        },
        ......
    ]
}
```

### 5. 获得歌单内容列表

#### 5.1 基本信息

> 请求路径：/api/musiclist/detail
>
> 请求方式：GET
>
> 接口描述：该接口导出歌单中的歌曲清单

#### 5.2 请求参数

请求参数格式：x-www-form-urlencoded

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注 |      |
| -------- | ------ | ------ | -------- | ---- | ---- |
| pageNum   | 当前页码           | number  | 是       |             ||
| pageSize  | 每页条数           | number  | 否     | 默认为20 ||
| mlid     | 歌单ID | number | 是      |      |      |

请求数据样例：

````shell
pageNum=1&pageSize=10&mlid=3
````

#### 5.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称             | 类型      | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ---------------- | --------- | -------- | ------ | --------------------- | -------- |
| code             | number    | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status           | number    | 必须     |        | HTTP状态码            |          |
| message          | string    | 非必须   |        | 提示信息              |          |
| data             | object    | 非必须   |        | 返回的数据            | JSON数组 |
| \|-mid           | number    | 非必须   |        | 音乐主键ID            |          |
| \|-musicname     | string    | 非必须   |        | 音乐名称              |          |
| \|-creator       | string    | 非必须   |        | 创作者ID              | JSON     |
| \|-album         | string    | 非必须   |        | 专辑ID                | JSON     |
| \|-introduction  | string    | 非必须   |        | 歌曲介绍              |          |
| \|-musicAvatar   | string    | 非必须   |        | 音乐图片地址          |          |
| \|-lyric         | string    | 非必须   |        | 歌词                  |          |
| \|-resUrl        | string    | 非必须   |        | 资源地址              |          |
| \|-publishedTime | timestamp | 非必须   |        | 发行时间              |          |
| \|-createdTime   | timestamp | 非必须   |        | 创建时间              |          |
| \|-updatedTime   | timestamp | 非必须   |        | 更新时间              |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": [
        {
            "mid": 22,
            "musicname": "aaaa",
            "creator": [2,25],
            "album": [53],
            "introduction": "bbbbbbbbb...",
            "musicAvatar": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.jpg",
            "lyric": "………………",
            "resUrl": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.mp3",
            "publishedTime": 1435645397,
            "createdTime": 1712634453,
            "updatedTime": 1712645397
        },
        {
            "mid": 2345342,
            "musicname": "eeee",
            "creator": [24,453],
            "album": [5345,4234],
            "introduction": "bbsdfsdfgb...",
            "musicAvatar": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b582231-acc8-4dfs-8234-cf0325391ab.jpg",
            "lyric": "………………",
            "resUrl": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.mp3",
            "publishedTime": 1435645397,
            "createdTime": 1712634452,
            "updatedTime": 1712645397
        }
        ...
    ]
}
```



### 6. 获得推荐歌单列表

#### 6.1 基本信息

> 请求路径：/api/musiclist/recommend/list
>
> 请求方式：GET
>
> 接口描述：该接口导出你的个人推荐歌单列表

#### 6.2 请求参数

请求参数格式：x-www-form-urlencoded

请求参数说明：

无

请求数据样例：

无

#### 6.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称             | 类型      | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ------- | ------ | -------- | ------ | --------------------- | -------- |
| code             | number    | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status           | number    | 必须     |        | HTTP状态码            |          |
| message          | string    | 非必须   |        | 提示信息              |          |
| data             | object    | 非必须   |        | 返回的数据            |          |
| \|-mlid          | number    | 非必须   |        | 歌单ID                |          |
| \|-musiclistname | string    | 非必须   |        | 歌单名称              |          |
| \|-uid           | number    | 非必须   |        | 创建用户ID            |          |
| \|-musiclist_pic | string    | 非必须   |        | 歌单图片地址          |          |
| \|-introduction  | string    | 非必须   |        | 歌单介绍              |          |
| \|-style         | string    | 非必须   |        | 歌单风格              |          |
| \|-createdTime   | timestamp | 非必须   |        | 创建时间              |          |
| \|-updatedTime   | timestamp | 非必须   |        | 更新时间              |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": [
        {
            "mlid": 5,
            "musiclistname": "hahaha",
            "uid": 10,
            "musiclist_pic": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.jpg",
            "introduction": ".....",
            "style": "...",
            "created_time": 1435645397,
            "updated_time": 1435645397
        },
        ......
    ]
}
```

### 7. 收藏歌单

#### 7.1 基本信息

> 请求路径：/api/musiclist/fav
>
> 请求方式：POST
>
> 接口描述：该接口收藏一个歌单

#### 7.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注 |      |
| -------- | ------ | ------ | -------- | ---- | ---- |
| mlid     | 歌单ID | number | 是       |      |      |

请求数据样例：

```shell
mlid=20
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

### 8. 删除歌单

#### 8.1 基本信息

> 请求路径：/api/musiclist
>
> 请求方式：DELETE
>
> 接口描述：该接口删除一个歌单

#### 8.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注 |      |
| -------- | ------ | ------ | -------- | ---- | ---- |
| mlid     | 歌单ID | number | 是       |      |      |

请求数据样例：

```shell
mlid=20
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

### 9. 获得推荐歌单

#### 9.1 基本信息

> 请求路径：/api/musiclist/recommend
>
> 请求方式：GET
>
> 接口描述：该接口导出推荐歌单歌曲内容

#### 9.2 请求参数

请求参数格式：x-www-form-urlencoded

请求参数说明：

无

请求数据样例：

无

#### 9.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称             | 类型      | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ---------------- | --------- | -------- | ------ | --------------------- | -------- |
| code             | number    | 必须     |        | 响应码, 0-成功,1-失败 |          |
| status           | number    | 必须     |        | HTTP状态码            |          |
| message          | string    | 非必须   |        | 提示信息              |          |
| data             | object    | 非必须   |        | 返回的数据            | JSON数组 |
| \|-mid           | number    | 非必须   |        | 音乐主键ID            |          |
| \|-musicname     | string    | 非必须   |        | 音乐名称              |          |
| \|-creator       | string    | 非必须   |        | 创作者ID              | JSON     |
| \|-album         | string    | 非必须   |        | 专辑ID                | JSON     |
| \|-introduction  | string    | 非必须   |        | 歌曲介绍              |          |
| \|-musicAvatar   | string    | 非必须   |        | 音乐图片地址          |          |
| \|-lyric         | string    | 非必须   |        | 歌词                  |          |
| \|-resUrl        | string    | 非必须   |        | 资源地址              |          |
| \|-publishedTime | timestamp | 非必须   |        | 发行时间              |          |
| \|-createdTime   | timestamp | 非必须   |        | 创建时间              |          |
| \|-updatedTime   | timestamp | 非必须   |        | 更新时间              |          |

响应数据样例：

```json
{
    "code": 0,
    "status": 200,
    "message": "操作成功",
    "data": [
        {
            "mid": 22,
            "musicname": "aaaa",
            "creator": [2,25],
            "album": [53],
            "introduction": "bbbbbbbbb...",
            "musicAvatar": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.jpg",
            "lyric": "………………",
            "resUrl": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.mp3",
            "publishedTime": 1435645397,
            "createdTime": 1712634453,
            "updatedTime": 1712645397
        },
        {
            "mid": 2345342,
            "musicname": "eeee",
            "creator": [24,453],
            "album": [5345,4234],
            "introduction": "bbsdfsdfgb...",
            "musicAvatar": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b582231-acc8-4dfs-8234-cf0325391ab.jpg",
            "lyric": "………………",
            "resUrl": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/b5811871-acc8-4583-8399-cf0dc73591ab.mp3",
            "publishedTime": 1435645397,
            "createdTime": 1712634452,
            "updatedTime": 1712645397
        }
        ...
    ]
}
```



## 四、其它接口

### 1. 查看任务列表

#### 1.1 基本信息

> 请求路径：/api/univer/tasklist
>
> 请求方式：GET
>
> 接口描述：该接口查看本用户所有任务（管理员或者创作者）

#### 1.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注 |      |
| -------- | ------ | ------ | -------- | ---- | ---- |
| uid      | 用户ID | number | 是       |      |      |

请求数据样例：

````shell
uid=3
````



#### 1.3 响应数据

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
    "data": [
        {
        	"taskId": 2,
        	"perimission": "admin",
            "operator": null,
            "beOperator": 3,
            "detail": {
                "created_time":1712054883000,
                "updated_time":null,
                "finished_time":null,
                "operation":"updatePermission",
                "status":"TOC",
                "update-to":"admin"
            }
    	},
        ...
    ]
}
```

### 2. 批准封禁用户

#### 2.1 基本信息

> 请求路径：/api/univer/banuser
>
> 请求方式：PATCH
>
> 接口描述：该接口批准用户的封禁申请

#### 2.2 请求参数

请求参数格式：multipart/form-data

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注 |      |
| -------- | ------ | ------ | -------- | ---- | ---- |
| taskId   | 任务ID | number | 是       |      |      |

请求数据样例：

````shell
taskId=3
````



#### 2.3 响应数据

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


