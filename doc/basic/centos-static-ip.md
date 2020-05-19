### centos配置静态ip

此处环境为vmwork station 15+centos7，网络使用nat的方式

目标ip: 192.168.1.131，当前ip192.168.31.133

先查看ip地址

```shell
[root@localhost ~]# ip addr
.
.
2: ens33: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    inet 192.168.31.133/24 brd 192.168.31.255 scope global noprefixroute dynamic ens33
.
.
```

当前的ip为192.168.31.133，对应使用的网络为ens33

编辑network-scripts下对应的文件

```shell
vi /etc/sysconfig/network-scripts/ifcfg-ens33 
TYPE="Ethernet"
PROXY_METHOD="none"
BROWSER_ONLY="no"
BOOTPROTO="dhcp"
DEFROUTE="yes"
IPV4_FAILURE_FATAL="no"
IPV6INIT="yes"
IPV6_AUTOCONF="yes"
IPV6_DEFROUTE="yes"
IPV6_FAILURE_FATAL="no"
IPV6_ADDR_GEN_MODE="stable-privacy"
NAME="ens33"
DEVICE="ens33"
ONBOOT="yes"

修改为其中几项如下
TYPE="Ethernet"
BOOTPROTO="static"
IPADDR=192.168.31.131
GATEWAY=192.168.31.2
DNS1=DNS1
DNS2=DNS2
NETMASK=255.255.255.0
```



重启网络就可以看到新的ip了

```
[root@localhost ~]# service network restart
Restarting network (via systemctl):                        [  确定  ]
[root@localhost ~]# ip addr
.
.
2: ens33: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
.
.
inet 192.168.31.131/24 brd 192.168.31.255 scope global noprefixroute ens33
```

