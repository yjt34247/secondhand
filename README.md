# secondhand
# 二手交易发布系统 - 文档说明

## 📋 项目概述

这是一个基于Java Web的二手交易发布系统，采用标准的MVC架构设计，实现了二手物品的发布、搜索、编辑和删除等功能。

## 🏗️ 系统架构设计

### 项目结构

```
secondhand/
├── src/main/java/com/example/secondhand/
│   ├── entity/                    # 实体类层
│   │   ├── User.java             # 用户实体类
│   │   └── Item.java             # 二手物品实体类
│   │
│   ├── dao/                      # 数据访问层
│   │   ├── JdbcHelper.java       # JDBC工具类（封装驱动加载、连接、语句创建）
│   │   ├── UserDao.java          # 用户DAO接口
│   │   ├── UserDaoImpl.java      # 用户DAO实现（使用泛型和函数式编程）
│   │   ├── ItemDao.java          # 物品DAO接口
│   │   └── ItemDaoImpl.java      # 物品DAO实现（使用泛型和函数式编程）
│   │
│   ├── service/                  # 业务逻辑层
│   │   ├── UserService.java      # 用户服务接口
│   │   ├── UserServiceImpl.java  # 用户服务实现（密码加密存储）
│   │   ├── ItemService.java      # 物品服务接口
│   │   └── ItemServiceImpl.java  # 物品服务实现（模糊搜索逻辑）
│   │
│   └── controller/               # 控制层
│       ├── UserServlet.java      # 用户相关请求处理
│       ├── ItemServlet.java      # 物品相关请求处理
│       ├── SearchServlet.java    # 搜索请求处理
│       └── IndexServlet.java     # 首页请求处理
│
└── webapp/                       # Web资源
    ├── WEB-INF/
    │   └── web.xml              # Web应用配置
    ├── user/                    # 用户相关页面
    │   ├── login.jsp           # 登录页面
    │   └── register.jsp        # 注册页面
    ├── item/                    # 物品相关页面
    │   ├── detail.jsp          # 物品详情页面
    │   ├── publish.jsp         # 发布物品页面
    │   └── edit.jsp            # 编辑物品页面
    ├── search/                  # 搜索相关页面
    │   └── result.jsp          # 搜索结果页面
    ├── index.jsp               # 系统首页
    └── error.jsp               # 错误页面
```
## 📝 各层详细说明

### 1. **实体类层 (entity)**
- **User.java** - 用户实体类
  - 属性：id、username、password、email、phone、createdAt
  - 使用Lombok注解简化代码

- **Item.java** - 二手物品实体类
  - 属性：id、userId、title、description、price、category、status、createdAt、updatedAt、username
  - 包含关联的用户信息

### 2. **数据访问层 (dao)**
- **JdbcHelper.java** - JDBC核心工具类
  - 封装：驱动加载、建立连接、创建PreparedStatement、资源关闭
  - 使用泛型和函数式编程简化数据访问

- **UserDao.java / ItemDao.java** - 数据访问接口
  - 定义CRUD操作方法
  - 声明业务所需的数据查询方法

- **UserDaoImpl.java / ItemDaoImpl.java** - 数据访问实现
  - 使用JdbcHelper执行SQL
  - 使用`Function<ResultSet, T>`进行行映射
  - 实现接口定义的所有方法

### 3. **业务逻辑层 (service)**
- **UserService.java / ItemService.java** - 业务服务接口
  - 定义业务操作方法
  - 声明系统核心功能

- **UserServiceImpl.java / ItemServiceImpl.java** - 业务服务实现
  - 调用DAO层进行数据操作
  - 实现业务逻辑：密码加密、模糊搜索等
  - 进行数据验证和业务规则处理

### 4. **控制层 (controller)**
- **UserServlet.java** - 用户控制器
  - 处理用户注册、登录、注销请求
  - URL映射：`/user/register`, `/user/login`, `/user/logout`

- **ItemServlet.java** - 物品控制器
  - 处理物品发布、编辑、删除、查看请求
  - URL映射：`/item/publish`, `/item/edit`, `/item/update`, `/item/delete`, `/item/detail`

- **SearchServlet.java** - 搜索控制器
  - 处理物品搜索请求
  - URL映射：`/search`

- **IndexServlet.java** - 首页控制器
  - 处理首页请求
  - URL映射：`/index`, `/`

### 5. **视图层 (webapp)**
- **user/login.jsp** - 用户登录页面
  - 提供用户名和密码输入表单
  - 包含登录验证和错误提示

- **user/register.jsp** - 用户注册页面
  - 提供用户注册信息表单
  - 包含基本的表单验证

- **item/publish.jsp** - 发布物品页面
  - 提供物品信息输入表单
  - 包含分类选择等UI元素

- **item/edit.jsp** - 编辑物品页面
  - 提供物品信息编辑表单
  - 包含删除功能

- **item/detail.jsp** - 物品详情页面
  - 显示物品详细信息
  - 提供编辑和删除操作入口

- **search/result.jsp** - 搜索结果页面
  - 显示搜索结果列表
  - 支持分页显示

- **index.jsp** - 系统首页
  - 显示最新物品列表
  - 提供搜索功能和导航

- **error.jsp** - 错误处理页面
  - 显示系统错误信息
  - 提供返回首页链接
 
## 🔗 各层依赖关系
1. **请求流向**：用户 → JSP → Servlet → Service → DAO → Database
2. **数据流向**：Database → DAO → Service → Servlet → JSP → 用户
3. **异常处理**：各层捕获异常，统一在Controller层处理并跳转到error.jsp

### 技术架构特点

1. **严格MVC分层**
   - Controller: 处理HTTP请求，调用Service
   - Service: 实现业务逻辑（密码加密、模糊搜索）
   - DAO: 数据访问，使用泛型和函数式编程
   - Entity: 数据实体对象

2. **JDBC封装**
   - `JdbcHelper.java` 封装了：
     - 驱动加载（静态代码块）
     - 建立连接（`getConnection()`）
     - 创建语句（`prepareStatement()`）
     - 资源关闭（`close()`）

3. **安全特性**
   - 用户密码使用BCrypt加密存储
   - SQL使用PreparedStatement防止注入
   - 登录验证后才能访问核心功能


   
## 🗄️ 数据库结构说明

### 数据库配置
- **数据库名**: `secondhand_db`

### 表结构

#### 1. users表（用户表）
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,                -- 用户ID，主键，自增长
    username VARCHAR(50) UNIQUE NOT NULL,             -- 用户名，唯一，不能为空
    password VARCHAR(255) NOT NULL,                   -- 密码（BCrypt加密存储）
    email VARCHAR(100),                               -- 邮箱地址
    phone VARCHAR(20),                                -- 联系电话
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP    -- 注册时间，默认当前时间
);
```

#### 2. items表（二手物品表）
```sql
CREATE TABLE items (
    id INT PRIMARY KEY AUTO_INCREMENT,                              -- 物品ID，主键，自增长
    user_id INT NOT NULL,                                           -- 发布者ID，外键关联users表
    title VARCHAR(200) NOT NULL,                                    -- 物品标题，最大200字符
    description TEXT,                                               -- 物品详细描述
    price DECIMAL(10, 2) NOT NULL,                                  -- 物品价格（10位整数，2位小数）
    category VARCHAR(50),                                           -- 物品分类
    status VARCHAR(20) DEFAULT '在售',                              -- 物品状态：在售/已售/下架
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                 -- 发布时间，默认当前时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 最后更新时间
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE    -- 外键约束，级联删除
);
```

## 🚀 使用方法

### 环境要求
- JDK 17+
- Apache Tomcat 10+
- MySQL 8.0+
- Maven 3.6+

## 🔑 测试账号信息

### 默认测试用户
- **用户名**: 叶君韬
- **密码**: 123456（已用BCrypt加密存储）

## 🎯 核心功能

### 1. 用户管理
- ✅ 用户注册（密码加密存储）
- ✅ 用户登录
- ✅ 用户信息验证

### 2. 二手物品管理（需登录）
- ✅ 发布二手物品
- ✅ 编辑自己的物品
- ✅ 删除自己的物品
- ✅ 查看物品详情

### 3. 搜索功能
- ✅ 关键词模糊搜索（标题、描述、分类）
- ✅ 分类筛选

### 4. 权限控制
- ✅ 未登录只能看到登录/注册页面
- ✅ 登录后才能查看和搜索物品
- ✅ 只能编辑和删除自己发布的物品

## 🔧 技术特性
### 1. 数据访问层特性
- **泛型查询**: 使用 `Function<ResultSet, T>` 进行类型安全的映射
- **函数式编程**: 使用Lambda表达式简化代码
- **连接池管理**: 手动连接管理，确保资源释放

### 2. 业务层特性
- **密码安全**: BCrypt加密算法存储密码
- **输入验证**: 必要的字段验证
- **模糊搜索**: 在Service层实现搜索逻辑

### 3. 表示层特性
- **JSP页面**: 使用JSTL和EL表达式
- **响应式设计**: 基本的HTML表格布局
- **用户体验**: 清晰的导航和操作提示

## 📝 开发规范

### 代码规范
1. 所有数据库操作使用 `PreparedStatement`
2. 资源使用后必须关闭
3. 异常处理要具体，避免捕获过于宽泛的异常
4. 遵循MVC分层，每层职责明确

### 安全规范
1. 用户密码必须加密存储
2. SQL语句使用参数化查询
3. 用户输入必须验证和清理
4. 敏感操作需要权限验证

**项目维护者**: 叶君韬  
**最后更新**: 2025年  
**版本**: 1.0-SNAPSHOT  




