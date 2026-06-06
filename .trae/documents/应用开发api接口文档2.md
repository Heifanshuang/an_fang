# 应用开发API接口文档

## 1. 接口概述

本文档描述了物联网云平台提供的API接口，包括用户认证、项目管理、设备管理、传感器管理和数据采集等功能模块。所有接口均采用RESTful设计风格，支持JWT认证，便于第三方应用集成。

## 2. 技术规范

### 2.1 认证机制

- 所有非登录接口均需要在请求头中携带JWT令牌
- 令牌格式：`Authorization: Bearer {token}`
- 令牌有效期：1小时

### 2.2 响应格式

所有API接口返回统一的JSON格式：

```json
{
  "ResultObj": {},        // 响应结果数据
  "Status": 0,            // 状态码：0-成功，1-失败
  "StatusCode": 1,        // 业务状态码
  "Msg": "成功",           // 响应消息
  "ErrorObj": {}          // 错误信息
}
```

### 2.3 分页响应格式

```json
{
  "ResultObj": {
    "PageSet": [],        // 分页数据列表
    "PageCount": 1,       // 总页数
    "PageIndex": 1,       // 当前页码
    "PageSize": 10,       // 每页条数
    "RecordCount": 100    // 总记录数
  },
  "Status": 0,
  "StatusCode": 1,
  "Msg": "成功",
  "ErrorObj": {}
}
```

## 3. API接口列表

### 3.1 用户认证接口

| 接口名称 | 请求方法 | URL | 功能描述 |
| --- | --- | --- | --- |
| 用户登录 | POST | /Users/Login | 用户登录，返回JWT令牌 |
| 查询APIKey | GET | /Users/ApiKey | 查询用户APIKey |
| 更新APIKey | PUT | /Users/ApiKey | 更新用户APIKey |

### 3.2 项目管理接口

| 接口名称 | 请求方法 | URL | 功能描述 |
| --- | --- | --- | --- |
| 查询单个项目 | GET | /Projects/{projectId} | 查询单个项目详情 |
| 模糊查询项目 | GET | /Projects | 模糊查询项目列表，支持分页 |
| 新增项目 | POST | /Projects | 创建新项目 |
| 更新项目 | PUT | /Projects/{projectId} | 更新项目信息 |
| 删除项目 | DELETE | /Projects | 批量删除项目 |
| 查询项目传感器 | GET | /Projects/{projectId}/Sensors | 查询项目下所有设备的传感器 |
| 获取项目传感器实时数据 | GET | /Projects/{projectId}/SensorsRealTimeData | 获取项目下所有设备的传感器实时数据 |

### 3.3 设备管理接口

| 接口名称 | 请求方法 | URL | 功能描述 |
| --- | --- | --- | --- |
| 批量查询设备最新数据 | GET | /Devices/Datas | 批量查询设备最新数据 |
| 批量查询设备在线状态 | GET | /Devices/Status | 批量查询设备在线状态 |
| 查询单个设备 | GET | /Devices/{deviceId} | 查询单个设备详情 |
| 模糊查询设备列表 | GET | /Devices | 模糊查询设备列表，支持分页 |
| 添加设备 | POST | /Devices | 添加新设备 |
| 更新设备 | PUT | /Devices/{deviceId} | 更新设备信息 |
| 删除设备 | DELETE | /Devices/{deviceId} | 删除单个设备 |

### 3.4 传感器管理接口

| 接口名称 | 请求方法 | URL | 功能描述 |
| --- | --- | --- | --- |
| 查询单个传感器 | GET | /devices/{deviceId}/Sensors/{apiTag} | 查询单个传感器详情 |
| 模糊查询传感器列表 | GET | /devices/{deviceId}/Sensors | 模糊查询传感器列表 |
| 添加传感器 | POST | /devices/{deviceId}/Sensors | 添加新传感器 |
| 更新传感器 | PUT | /devices/{deviceId}/Sensors/{apiTag} | 更新传感器信息 |
| 删除传感器 | DELETE | /devices/{deviceId}/Sensors/{apiTag} | 删除传感器 |

### 3.5 数据采集接口

| 接口名称 | 请求方法 | URL | 功能描述 |
| --- | --- | --- | --- |
| 聚合查询数据 | GET | /devices/{deviceId}/Datas/Grouping | 按API标签聚合查询传感数据 |
| 模糊查询数据 | GET | /devices/{deviceId}/Datas | 模糊查询传感数据，支持分页 |
| 上传传感数据 | POST | /devices/{deviceId}/Datas | 上传传感数据 |

### 3.6 命令控制接口

| 接口名称 | 请求方法 | URL | 功能描述 |
| --- | --- | --- | --- |
| 发送命令/控制设备 | POST | /Cmds | 发送命令控制设备 |

### 3.7 告警管理接口

| 接口名称 | 请求方法 | URL | 功能描述 |
| --- | --- | --- | --- |
| 获取告警历史列表 | GET | /Projects/{projectId}/Alarms/History | 获取告警历史列表 |
| 获取告警详情 | GET | /Projects/{projectId}/Alarms/History/{alarmId} | 获取告警详情 |
| 标记告警为已处理 | POST | /Projects/{projectId}/Alarms/History/{alarmId}/Handle | 标记告警为已处理 |
| 查询策略执行记录 | GET | /Strategys/Records | **告警历史子菜单专用接口**，查询策略执行记录，支持分页和筛选。每一个执行成功的策略作为一个告警历史条目，用于告警历史子菜单的数据显示 |

### 3.8 策略管理接口

| 接口名称 | 请求方法 | URL | 功能描述 |
| --- | --- | --- | --- |
| 查询单个策略 | GET | /Strategys/{strategyId} | 查询单个策略详情 |
| 查询策略列表 | GET | /Strategys | 查询策略列表，支持分页 |
| 新增策略 | POST | /Strategys | 创建新策略 |
| 更新策略 | PUT | /Strategys/{strategyId} | 更新策略信息 |
| 删除策略 | DELETE | /Strategys | 批量删除策略 |

### 3.9 系统配置接口

| 接口名称 | 请求方法 | URL | 功能描述 |
| --- | --- | --- | --- |
| 获取系统配置 | GET | /System/Config | 获取系统配置 |
| 更新系统配置 | POST | /System/Config/{configId} | 更新系统配置 |

## 4. 接口详情

### 4.1 用户登录

**请求URL**：`POST /Users/Login`

**请求体**：
```json
{
  "Account": "string",
  "Password": "string",
  "IsRememberMe": false
}
```

**响应示例**：
```json
{
  "ResultObj": {
    "AccessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "UserId": 123
  },
  "Status": 0,
  "StatusCode": 1,
  "Msg": "成功",
  "ErrorObj": {}
}
```

### 4.2 批量查询设备最新数据

**请求URL**：`GET /Devices/Datas`

**请求参数**：
- `devIds`：设备ID列表，多个ID用逗号分隔

**响应示例**：
```json
{
  "ResultObj": {
    "Items": [
      {
        "DeviceId": 1001,
        "Datas": {
          "temperature": 25.5,
          "humidity": 60.0
        }
      }
    ]
  },
  "Status": 0,
  "StatusCode": 1,
  "Msg": "成功",
  "ErrorObj": {}
}
```

### 4.3 查询项目下所有设备的传感器

**请求URL**：`GET /Projects/{projectId}/Sensors`

**请求参数**：
- `projectId`：项目ID

**响应示例**：
```json
{
  "ResultObj": [
    {
      "DeviceId": 1001,
      "ApiTag": "temperature",
      "Name": "温度传感器",
      "Value": "25.5",
      "Unit": "°C"
    }
  ],
  "Status": 0,
  "StatusCode": 1,
  "Msg": "成功",
  "ErrorObj": {}
}
```

### 4.4 发送命令/控制设备

**请求URL**：`POST /Cmds`

**请求参数**：
- `deviceId`：设备ID
- `apiTag`：命令标签

**请求体**：
```json
1
```

**响应示例**：
```json
{
  "ResultObj": 12345,
  "Status": 0,
  "StatusCode": 1,
  "Msg": "成功",
  "ErrorObj": {}
}
```

### 4.5 获取告警历史列表

**请求URL**：`GET /Projects/{projectId}/Alarms/History`

**请求参数**：
- `projectId`：项目ID
- `pageIndex`：当前页码
- `pageSize`：每页条数

**响应示例**：
```json
{
  "ResultObj": {
    "Items": [
      {
        "AlarmId": "1001",
        "AlarmType": "温湿度异常",
        "AlarmLevel": 1,
        "AlarmMsg": "温湿度超过阈值",
        "StartTime": "2026-01-09 14:30:25",
        "Status": 0
      }
    ],
    "TotalCount": 1
  },
  "Status": 0,
  "StatusCode": 1,
  "Msg": "成功",
  "ErrorObj": {}
}
```

### 4.6 查询策略执行记录

**接口说明**：该接口用于查询策略执行记录，支持分页和筛选。每一个执行成功的策略将作为一个告警历史记录，为告警历史子菜单提供数据支撑。

**请求URL**：`GET /Strategys/Records`

**请求参数**：
| 参数名 | 类型 | 描述 | 其他 |
| --- | --- | --- | --- |
| projectId | integer | 项目ID（必须） | |
| deviceId | integer | 设备ID（可选） | |
| StrategyID | integer | 策略ID（可选） | |
| PageSize | integer | 指定每页要显示的数据个数，默认20，最多100 | |
| StartDate | string | 起始时间（可选，包括当天，格式YYYY-MM-DD） | |
| EndDate | string | 结束时间（可选，包括当天，格式YYYY-MM-DD） | |
| PageIndex | integer | 指定页码 | |

**响应示例**：
```json
{
  "ResultObj": {
    "PageSet": [
      {
        "CD_StrategyRecordActions": null,
        "RecordID": 1,
        "StrategyId": 2,
        "GatewayID": 3,
        "GatewayName": "sample string 4",
        "Kind": 64,
        "GatewayKindCn": "sample string 6",
        "Condition": "sample string 7",
        "ConditionCn": "sample string 8",
        "RunTimeID": 9,
        "Period": 64,
        "Day": 1,
        "Time": "2020-10-14T15:22:35.4361202+08:00",
        "ExecTimeHTML": "sample string 11",
        "RecordActionHTML": "sample string 12",
        "Action": "sample string 13",
        "CreateDate": "2020-10-14T15:22:35.4361202+08:00",
        "CreateUserID": 15,
        "ProjectID": 16
      }
    ],
    "PageCount": 1,
    "PageIndex": 2,
    "PageSize": 3,
    "RecordCount": 4
  },
  "Status": 0,
  "StatusCode": 1,
  "Msg": "sample string 2",
  "ErrorObj": {}
}
```

**接口关系**：该接口返回的策略执行记录将作为告警历史的数据支撑，每一条执行成功的策略记录对应一个告警历史条目。

## 5. 错误码说明

| 错误码 | 描述 |
| --- | --- |
| 1 | 成功 |
| 0 | 失败 |
| 400 | 请求参数错误 |
| 401 | 未授权访问 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 6. 调用示例

### 使用Java调用示例

```java
// 1. 登录获取Token
LoginRequest loginRequest = new LoginRequest();
loginRequest.setAccount("admin");
loginRequest.setPassword("123456");

Call<LoginResponse> loginCall = apiService.login(loginRequest);
loginCall.enqueue(new Callback<LoginResponse>() {
    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
            String token = response.body().getResultObj().getAccessToken();
            // 2. 使用Token调用其他接口
            ApiClient.setAccessToken(token);
            // 3. 查询设备数据
            Call<DeviceDataResponse> deviceDataCall = ApiClient.getApiService().getDeviceData("1001");
            // 处理响应...
        }
    }
    
    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        // 处理失败...
    }
});
```

## 7. 注意事项

1. 所有接口均支持HTTPS协议，建议生产环境使用HTTPS
2. 接口请求频率限制：默认每分钟100次
3. 数据上传接口支持批量上传，建议每次不超过100条记录
4. 命令控制接口响应时间可能受设备网络影响，建议设置合理的超时时间
5. 生产环境中请勿使用默认的JWT密钥，应配置复杂的密钥

## 8. 文档更新记录

| 版本 | 更新日期 | 更新内容 |
| --- | --- | --- |
| 1.0 | 2026-01-10 | 初始版本，包含基础API接口 |
| 1.1 | 2026-01-10 | 添加告警管理接口和系统配置接口 |

---
| 查看传感器数据操作顺序：                                                                                                      | <br />       | <br />                  | <br /> |
| 第一步，使用账号API用post请求（API地址<http://api.nlecloud.com/users/login>                                                     | <br />       | <br />                  | <br /> |
| ）实现用户登录（同时返回AccessToken）。                                                                                         | <br />       | <br />                  | <br /> |
| 第二步，使用项目API用get请求（API地址<http://api.nlecloud.com/projects）模糊查询项目获取到项目id。>                                          | <br />       | <br />                  | <br /> |
| 第三步，使用项目API用get请求（API地址<http://api.nlecloud.com/projects/{projectid}）查询单个项目。>                                     | <br />       | <br />                  | <br /> |
| 第四步，使用项目API用get请求（API地址<http://api.nlecloud.com/Projects/{projectId}/Sensors）获取到该项目底下的全部设备传感器id>                  | <br />       | <br />                  | <br /> |
| 第五步，使用设备传感器API用get请求（API地址<http://api.nlecloud.com/Projects/{projectId}/SensorsRealTimeData）获取到该项目底下的全部设备传感器实时数据> | <br />       | <br />                  | <br /> |
| {projectId}                                                                                                       | <br />       | <br />                  | <br /> |
| {                                                                                                                 | <br />       | <br />                  | <br /> |

```
"ResultObj":[
    {
        "ApiTag":"yanwubai",
        "Name":"烟雾报警灯白",
        "DeviceId":1377547,
        "Groups":2,
        "ProjectID":1310244,
        "Value":"0",
        "GatewayDeviceID":9594507,
        "RecordTime":"2026-01-07T19:37:56"
    },
    {
        "ApiTag":"yanwu",
        "Name":"烟雾传感器",
        "DeviceId":1377547,
        "Groups":1,
        "ProjectID":1310244,
        "Value":"0",
        "GatewayDeviceID":9594508,
        "RecordTime":"2026-01-07T19:37:56"
    },
    {
        "ApiTag":"lvse",
        "Name":"非法闯入警示灯绿",
        "DeviceId":1377547,
        "Groups":2,
        "ProjectID":1310244,
        "Value":"0",
        "GatewayDeviceID":9594509,
        "RecordTime":"2026-01-07T19:37:57"
    },
    {
        "ApiTag":"red",
        "Name":"温湿度异常指示灯红",
        "DeviceId":1400880,
        "Groups":2,
        "ProjectID":1310244,
        "Value":"0",
        "GatewayDeviceID":9594510,
        "RecordTime":"2026-01-07T19:37:57"
    },
    {
        "ApiTag":"loushuiyellow",
        "Name":"漏水指示灯黄",
        "DeviceId":1400880,
        "Groups":2,
        "ProjectID":1310244,
        "Value":"0",
        "GatewayDeviceID":9594511,
        "RecordTime":"2026-01-07T19:37:57"
    },
    {
        "ApiTag":"shuijin",
        "Name":"水浸",
        "DeviceId":1400880,
        "Groups":1,
        "ProjectID":1310244,
        "Value":"0",
        "GatewayDeviceID":9594512,
        "RecordTime":"2026-01-07T19:37:55"
    },
    {
        "ApiTag":"wendu",
        "Name":"多合一温度",
        "DeviceId":1400880,
        "Groups":1,
        "ProjectID":1310244,
        "Value":"13",
        "GatewayDeviceID":9594513,
        "RecordTime":"2026-01-07T19:37:56"
    },
    {
        "ApiTag":"shidu",
        "Name":"多合一湿度",
        "DeviceId":1400880,
        "Groups":1,
        "ProjectID":1310244,
        "Value":"48",
        "GatewayDeviceID":9594514,
        "RecordTime":"2026-01-07T19:37:56"
    }
],
"Status":0,
"StatusCode":0,
"Msg":null,
"ErrorObj":null