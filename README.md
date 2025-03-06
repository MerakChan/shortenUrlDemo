# URL缩短服务

## 项目概述
这是一个基于Spring Boot实现的URL缩短服务，可以将长URL转换为短URL，便于分享和使用。

## 技术栈
- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2数据库
- Thymeleaf模板引擎
- Maven

## 主要功能
- URL缩短：将长URL转换为短URL
- URL重定向：访问短URL时自动重定向到原始URL
- 友好的Web界面：提供简洁的用户界面
- 数据持久化：使用H2数据库存储URL映射关系

## 核心特性
1. 使用Base62编码生成短URL
2. 支持URL有效期管理
3. 响应式界面设计
4. RESTful API设计

## 项目结构
```
src/
├── main/
│   ├── java/
│   │   └── com/merak/urlshortener/
│   │       ├── controller/    # 控制器层
│   │       ├── model/        # 数据模型
│   │       ├── repository/   # 数据访问层
│   │       ├── service/      # 业务逻辑层
│   │       └── UrlShortenerApplication.java
│   └── resources/
│       ├── templates/    # Thymeleaf模板
│       └── application.properties
```

## 快速开始
1. 克隆项目到本地
2. 确保已安装Java 17和Maven
3. 在项目根目录执行：
   ```bash
   mvn spring-boot:run
   ```
4. 访问 http://localhost:8080

## 使用方法
1. 在首页输入框中粘贴需要缩短的URL
2. 点击"生成短链接"按钮
3. 复制生成的短URL即可使用

## 技术实现细节
### URL缩短算法
使用Base62编码（0-9、a-z、A-Z）将系统时间戳转换为短码，确保生成的短URL：
- 唯一性：使用时间戳作为基础
- 短小：通过Base62编码压缩长度
- 可读性：不包含特殊字符

### 数据存储
使用H2内存数据库存储URL映射关系，包含：
- 原始URL
- 短码
- 创建时间
- 过期时间
