给普通用户sudu权限

如果没有sudu权限，使用sudo会有如下输出
```
[server@instance-nol4mhou ~]$ sudo ls

我们信任您已经从系统管理员那里了解了日常注意事项。
总结起来无外乎这三点：

    #1) 尊重别人的隐私。
    #2) 输入前要先考虑(后果和风险)。
    #3) 权力越大，责任越大。

[sudo] server 的密码：
server 不在 sudoers 文件中。此事将被报告。
```

切换到root用户，编辑/etc/sudoers
```
vim /etc/sudoers
```
添加一行，假如你的用户叫server
```$xslt
server  ALL=(ALL)       ALL
```
保存退出就可以了

注意：``提升权限的密码就是server的密码``


