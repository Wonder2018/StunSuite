# Stun Suite（Stun 套件）

本项目包含一套使用 Stun 的服务器与客户端，用于查询当前网络的 Nat 类型。使用的 Stun 协议版本为 [rfc5389](https://tools.ietf.org/html/rfc5389)。项目采用 Java 实现，所用 jdk 版本为 jdk1.8.0_181。使用 Maven 构建。

---

项目模块有

- [ ] client \- Stun 客户端

- [ ] server \- Stun 服务器

- [x] common \- Stun 通用模块（包括通用自定义异常和 Stun 数据包实体）

- [ ] util \- Stun 工具类

---

项目仍在施工中。目前已完成通用模块，可以正常生成二进制的 Stun 数据包或识别 Stun 数据包并将其转换为可操作的实体对象。
