####CentOS 7安装shadowsocks server

#####1、安装 pip
pip是 python 的包管理工具。在本文中将使用 python 版本的 shadowsocks，此版本的 shadowsocks 已发布到 pip 上，因此我们需要通过 pip 命令来安装。

```shell
yum -y install epel-release
yum -y install python-pip
pip install --upgrade pip

```
#####2配置shadowsocks
```
pip install shadowsocks
```
配置/etc/shadowsocks.json
```shell
{
  "server": "0.0.0.0",
  "server_port": 8388,
  "password": "uzon57jd0v869t7w",
  "method": "aes-256-cfb"
}
```
后台启动
```shell
ssserver -c /etc/shadowsocks.json -d start
```
#####2配置防火墙，开放8388端口
```
systemctl start firewalld
firewall-cmd --permanent --add-port=8388/tcp
firewall-cmd --reload

```
