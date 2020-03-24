# 关于指引
本指引旨在帮助开发人员快速上手，更详细内容请访问 [开发指引](https://developer.91asl.com/guide/)。
- 当前指引的版本：`quick-start-0.0.2`

# 工程说明
本工程基于SpringBoot，可以做为单体应用独立运行，亦可作为微服务注册并运行在微服务环境中。建议开发阶段以单体应用模式进行调试，测试部署阶段再切换为微服务模式，详见[应用模式切换章节](#应用模式切换)。

# 快速开始
## 开发环境配置
修改maven的配置文件(windows机器一般在maven安装目录的conf/settings.xml，mac/linux一般在`~/.m2/settings.xml`)
1. 在`<servers></servers>`标签中添加`server`子节点：
```xml
        <server>
            <id>maven-public</id>
            <username>用户名</username>
            <password>密码</password>
        </server>
```
> 注: 用户名和密码请向融先配管索取。

2. 在`<mirrors></mirrors>`标签中添加`mirror`子节点：
```xml
        <mirror>
            <id>maven-public</id>
            <mirrorOf>maven-public</mirrorOf>
            <url>https://repo.91asl.com:9443/nexus/repository/maven-public/</url>
        </mirror>
```

3. 在`<profiles></profiles>`标签中添加`profile`子节点：
```
        <profile>
            <id>longshare-public</id>
            <repositories>
                <repository>
                    <id>maven-public</id>
                    <name>maven-public</name>
                    <url>https://repo.91asl.com:9443/nexus/repository/maven-public/</url>
                    <layout>default</layout>
                    <!--<snapshotPolicy>always</snapshotPolicy>-->
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </repository>
            </repositories>
        </profile>
```

4. 在`<activeProfiles></activeProfiles>`标签中添加`activeProfile`子节点：
```
        <activeProfile>longshare-public</activeProfile>
```

> 如不能联网，则需要将repostory下面的mavne依赖包全部拷贝到本地仓库目录。

## 新建工程
### 1. 初始化工程
有两种方式：
- 使用[initialization-msgen](https://developer.91asl.com/initialization/msgen/unix/latest/msgen)（目前暂只支持linux）
  ```
  curl -L https://developer.91asl.com/initialization/msgen/unix/latest/msgen -o /usr/local/bin/msgen
  chmod a+x /usr/local/bin/msgen
  groupId="com.longshare.microservice.demo" artifactId="crude" msgen
  ```
  
- 下载[初始工程](https://developer.91asl.com/initialization/initial-project/rest-initial-project.zip)进行修改
  1. 工程级别替换如下变量（注意替换顺序）：
  ```
  com.longshare.microservice.demo.restinitialdemo -> 基础包名
  com.longshare.microservice.demo -> groupId
  rest-initial-demo -> artifactId
  ```
  
  2. 移动java包与基础包名一致
  
  3. 修改两处数据库连接，一处是application.yml，另一处是generator-config.xml

### 2. 在idea或者eclipse以maven方式导入工程即可

## 生成基础代码
1. 按[数据库设计规范](#数据库设计规范)设计好数据库模型，并将生成的sql执行到数据库后，在src/test/resources/generator-config.xml的<context></context>节点内部追加如下需要生成的数据库表:
```
        <!--TODO 要生成的表配置-->
        <table tableName="staff">
            <generatedKey column="id" sqlStatement="Mysql"/>
        </table>
```

2. 运行src/test包下的Generator.java

3. 查看编译生成的代码
```
├── src
│   ├── main
│   │   ├── asciidoc
│   │   ├── java
│   │   └── resources
│   │       ├── application.yml
│   │       ├── mapper
│   │       └── ui-component
│   └── test
│       ├── java
│       └── resources
│           ├── application.yml
│           ├── generator-config.xml
├── target
│   ├── generated-doc-sources
│   │   └── rests
│   │       ├── api-guide.adoc
│   │       ├── index.adoc
│   │       └── swagger-index.adoc
│   ├── generated-sources
│   │   └── rests
│   ├── generated-test-sources
│   │   └── rests
```
- 生成的代码：target/generated-sources/rests，确认无误后移动到src/main/java
- 生成的测试用例：target/generated-test-sources/rests，确认无误后移动到src/test/java下
- 生成的文档：target/generated-doc-sources/rests，确认无误后移动到src/main/asciidoc下

## 开发调试
按照[开发规范](#开发规范)的要求编写业务代码、单元测试、接口文档。也可以直接运行Application的main方法启动应用。然后用浏览器直接测试：
- HAL-browser：  
`http://localhost:8080/crude/browser/index.html`
- Swagger-UI：  
`http://localhost:8080/crude/swagger-ui.html`

> 注意：以上URL基于默认端口和Context，如果有修改的请自行替换。

# 规范

## 数据库设计规范
* 在MySQL数据库中将Character Set设置为utf8、将Collation设置为utf8_bin，并在数据库配置文件中设置lower_case_table_names=1
* 数据库、表、字段等所有名称的可用字符范围为：a-z， 0-9 和_下划线，长度要严格控制在30个字符以内。
* 数据库、表、字段等所有名称均使用英文单词或英文短语或相应缩写，均使用单数名，禁止使用汉语拼音。
* 数据库、表、字段等名称统一使用小写，单词间用_下划线分隔。
* 数据库表名统一加模块或者微服务前缀（不超过6个字母）
* 表主键统一命名为id，主键统一使用UUID，类型统一为varchar(36)。
* 所有表增加is_delete、creator、created_at、updater、modified_at字段
* 所有的表字段中，除外键，其它字段名都无需刻意加前后缀，也不要在字段名前出现表名。
* 表（广义）外键建议命名为：主表名_字段名，类型和主表中字段类型一样。如果一个表中有多个外键（字段）同时引用（对应）一张表的同一个字段，再根据实际情况加前后缀区分
* 对于字典字段，编码字段后面跟code后缀，文本字段跟text后缀
* 表示日期时间的字段，都要有后缀，如果只精确到天则以date为后缀，如果要精确到时分秒那就用time作后缀，使用bitint型时间戳（从1970年1月1日08时整开始的秒数）。
* 是否注销、是否成功等类似的布尔型字段，名称前统一加is前缀，比如是否成功（is_success）、是否注销（is_active）、是否显示（is_display）等。
* 外键约束以fk做前缀，后跟从表名称和主表名称：fk_从表名_主表名。 

## 开发规范

### 接口规范
严格执行Restful服务接口成熟度模型Level2规范，有条件的情况下推荐实践Level3规范。

#### Restful服务接口成熟度模型Level2规范概要
- 引入资源概念，每个资源有对应的标识符和表达。
- 使用不同的 HTTP 方法来进行不同的操作。
- 使用 HTTP 状态码来表示不同的结果。

基于以上规范，一个确定的接口有以下三部分组成：
1. 资源URI
2. HTTP方法
3. 入参和出参

#### HTTP方法
    |HTTP Operation |描述           |
    |---            |---            |
    |GET            |获取，查找     |
    |POST           |新增创建       |
    |PUT            |更新           |
    |PATCH          |部分更新       |
    |DELETE         |删除           |

#### 资源URI
    
1. 模块名称  
    一般为系统名称或者某一个微服务名称

2. 模块版本  
    加入系统版本能使提升接口可用性（上下兼容）和降低重构代价。  
    因为系统版本号放在uri的最前面，可以通过代理路到不同的接口实现，进而使新老版本共存直至平缓过渡后停掉老系统。  
    一般如下几种情况需要变更版本号：
    * 接口名称变更导致新老接口路由冲突  
    例如：v1/orders?user_id=1 --> v2/:user_id/orders
    * 接口入参或出参变更导致新老接口冲突

3. 资源名称  
    假设模块地址为[module-uri]
    * 资源型
    ```
    GET [module-uri]/orders            获取订单列表
    GET [module-uri]/orders/:id        根据id获取单个订单
    POST [module-uri]/orders           创建订单
    PUT [module-uri]/orders/:id        根据id更新订单
    PATCH [module-uri]/orders/:id      根据id部分更新订单
    DELETE [module-uri]/orders/:id     根据id删除订单
    ```
    
    * 服务型
    ```
    GET [module-uri]/services/search    搜索服务
    ```
    
    * 系统设置类
    ```
    PUT [module-uri]/settings/langueage          设置系统语言
    ```
    
    * 其他更复杂场景的定义原则  
    根据返回实体确定属于哪个资源，如果是关联关系，则定义为关系名称。  
    比如:staff->role->permission
    ```
    GET/POST/PATCH/DELETE /staffs 获取、新增、修改、删除员工，员工属性可以有roles、permissions
    GET/POST/PATCH/DELETE /roles 获取、新增、修改、删除角色，角色属性可以有permissions、staffs
    GET/POST/PATCH/DELETE /perssions 获取、新增、修改、删除权限，权限属性可以有roles、staffs
    GET/POST/PATCH/DELETE /staff_role_relations或者authorizations
    ```
    
#### 入参出参

##### 数据格式
前后端之间交互数据格式采用[hal+json](https://tools.ietf.org/html/draft-kelly-json-hal-08)规范。  
微服务之间交互数据格式采用JAVA简单对象POJO对应的json格式。  
针对JSON的六种数据类型，因为Number、Boolean和Null在不同编程语言会有不确定性，所以本规范只使用String、Array、Object。
- [ ] Number：整数或浮点数
- [x] String：字符串
- [ ] Boolean：true 或 false
- [x] Array：数组包含在方括号[]中
- [x] Object：对象包含在大括号{}中
- [ ] Null：空类型
    
各数据类型转String注意事项：
    
- Number型数据（特别是double\flout）：
    - 格式化为字符串  
    因为c和java的精度处理不一致，导致在数据转换时会有精度丢失。
    - 数据不要做变相处理  
    比如5%这个数据，要么返回“0.05”，要么返回“5%”，不能返回5。再比如10000000这个数据，要么返回“10000000”，要么返回“10,000,000”，要么返回“1000万”，不能返回“1000”。
    
- date类型  
转换为long（时间戳）：c和object-c对日期序列化和java是可能不一致的，导致日期解析不出来，app使用的时区不一致，可能导致日期错误。
        
- Boolean类型  
转换为char，0为false，1为true

##### 错误码
采用[problem+json - RFC 7807](https://tools.ietf.org/html/rfc7807)规范。并将title作为错误码。  
错误码定义采用枚举类，见自动生成的`ProblemCode.java`类，其中枚举变量名转小写即为错误码title。如：`LINK_CREATED_FAILED("创建连接失败")`的错误码title即为`link_created_failed`，错误信息detail即为`创建连接失败`。  
错误码申明只需要在具体错误发生处抛出即可，抛出类型如下：
- 客户端错误，对应HTTP状态码4xx。此类错误是客户端请求非法，需要客户端或者客户处理的；代码示例：
```
throw ProblemSolver.server(ProblemCode.LINK_CREATED_FAILED)
    //.withStatus(Status.NOT_IMPLEMENTED) //因为默认HTTP状态为500，这里可以定义更具体的5xxHTTP状态
    .build();
```

- 服务端错误，对应HTTP状态码5xx。此类错误是服务端处理异常，一般为bug或者业务错误，需要程序员或者业务员处理；
```
throw ProblemSolver.client(ProblemCode.LINK_CREATED_FAILED)
    //.withStatus(Status.METHOD_NOT_ALLOWED) //因为默认HTTP状态为400，这里可以定义更具体的4xxHTTP状态
    .build();
```

### 多数据源定义规范

#### 读写分离依据
1. 根据Service实现类的方法名自动判断，规则如下：
如方法名包含`"insert", "create", "delete", "remove", "modify", "update", "change", "patch"`关键字，则使用主库，否则使用从库
2. 在Service实现类的方法名上加注解`@WriteDataSource`

#### 读写分离yml配置
```
rest.datasource:
  enable: false #动态数据源开关，false时将使用spring数据源
  slaveRetryInterval: 10000 #从库宕机重试间隔（毫秒），宕机期间自动切换主库，从库上线后恢复
  defaultDataSource: #默认数据源，未配置的数据源tag将使用该数据源
    url: 
    username:
    password:
  dynamicDataSources:
    localhost: #数据源tag
      master: #主库，必须配置
        url: 
        username:
        password:
      slave0: #从库集群，后缀从0开始累加，不配置将全部使用主库
        url: 
        username:
        password:
      slave1:
        url: 
        username:
        password:
```

### java代码规范
对于本文档未定义的，均参考[阿里巴巴JAVA开发手册](https://github.com/alibaba/p3c/blob/master/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%EF%BC%88%E8%AF%A6%E5%B0%BD%E7%89%88%EF%BC%89.pdf)

### 版本定义规范
版本定义遵循[语义化版本](https://semver.org/lang/zh-CN/#spec-item-4)规范

## 单元测试规范
测试驱动开发，先写测试用例，在写接口，单元测试需要覆盖所有Restful接口。

## 文档规范
文档采用asciidoc格式，文档目录在`src/main/asciidoc`，生成后文档目录为`target/generated-docs/`。

### Swagger规范
Swagger文档根据java代码中的Swagger注解自动生成，所以要求所有的Restful的Controller接口、接口出入参都增加Swagger注解。生成的文档路径：`target/generated-docs/swagger-index.html`

### RestDocs规范
RestDocs文档与单元测试绑定，提倡测试用例和文档一起完成，以测试用例来保障文档的有效性。分两种场景：
1. 具体接口交互，具体要求同单元测试规范。
2. 业务开发指引，这部分主要是结合具体业务场景将各个接口串起来，不光需要根据该场景写场景化的测试用例，还需要在`src/main/asciidoc/api-guide.adoc`文档中增加文字描述。

## 应用模式切换
单体应用模式切换为微服务应用模式步骤：
1. 启动注册中心
```
java -jar registry-center.jar
```

2. 修改工程pom
单体应用模式和微服务应用模式切换只需要修改pom中的parent节点配置即可：

- 单体应用模式pom
```
    <parent>
        <groupId>com.longshare</groupId>
        <artifactId>rest-parent</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
```

- 微服务应用模式pom
```
    <parent>
        <groupId>com.longshare</groupId>
        <artifactId>ms-cloud-rest-parent</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
```

> 微服务启动依赖注册中心，所以确保注册中心启动，并且 application.yml增加了注册中心相关配置。

# 部署
1. 编译打包
```bash
mvn packge
```

2. 环境配置
拷贝步骤1打包的jar包（路径：`target/crude.jar`，注意文件名后缀是".jar"，不是".jar.original"），配置文件（路径：`src/main/resources/bootstrap.yml`、`src/main/resources/application.yml`）到运行目录并对配置进行修改，拷贝后的路径如下：
```
├── crude.jar
├── bootstrap.yml
├── application.yml
```

3. 运行

- 简单运行，关闭命令窗口程序就被关闭
```bash
java -jar crude.jar
```

- 后台运行(linux)，关闭shell窗口，程序不会被关闭
```bash
nohup java -Xmx400m -jar crude.jar >/dev/null 2>&1 &
```
