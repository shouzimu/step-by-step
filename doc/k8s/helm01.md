版本为v2.16.6

下载helm

```shell
wget https://get.helm.sh/helm-v2.16.6-linux-amd64.tar.gz

复制可执行文件到/usr/bin
```

下载tiller

```shell
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/tiller:v2.16.6
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/tiller:v2.16.6 gcr.io/kubernetes-helm/tiller:v2.16.6 
```

初始化

```
helm init

执行
helm version
如果报错 error forwarding port 44134 to pod 
yum install socat就可以了
```