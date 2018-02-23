# winio-demo
java 调用 winio 的方式，在银行密码控件上输入密码
<br/>
以 [民生银行个人网上银行](https://nper.cmbc.com.cn/pweb/static/login.html) 为例
## IEDriverServer.exe 下载地址
[http://selenium-release.storage.googleapis.com/index.html](http://selenium-release.storage.googleapis.com/index.html)
## 注意事项
- WinIo32.dll、WinIo32.sys 应放入到 Java 的 bin 目录下，如：`D:\Program Files\java\jdk1.8.0_73\bin`
- 将 lib/JNative.jar 加入到依赖中
- IEDriverServer.exe 的版本选择的是 3.8
- `xyz.supermoonie.email.MailSender` 需要配置下邮件发送人