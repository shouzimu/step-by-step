Centos7安装单节点kubernetes

官方教程链接：https://kubernetes.io/zh/docs/tasks/tools/install-kubectl/



## 安装 kubectl

1、配置使用阿里云的源

```shell
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
```



2、安装kubectl

```shell
 yum install -y kubectl
```



### 安装 Minikube



1、下载minikube

可以下载并使用一个单节点二进制文件。

```shell
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 \
  && chmod +x minikube
```

不过由于googleapis不能访问，我们使用如下命令从github上下载，具体版本号可以到github上查看

```
 curl -Lo minikube https://github.com/kubernetes/minikube/releases/download/v1.10.0-beta.1/minikube-linux-amd64   && chmod +x minikube
```



2、将 Minikube 可执行文件添加至 path

```shell
sudo mkdir -p /usr/local/bin/
sudo install minikube /usr/local/bin/
```



3、启动

```shell
[root@iZm5e7y9qoyl006b96mwaaZ k8s]#  minikube start --driver=none
* minikube v1.10.0-beta.1 on Centos 7.7.1908
* Using the none driver based on user configuration
X Sorry, Kubernetes v1.18.0 requires conntrack to be installed in root's path
```

发现报了一个错误

安装conntrack

```shell
yum install -y conntrack-tools
```

再次执行进入漫长的下载

```
[root@iZm5e7y9qoyl006b96mwaaZ k8s]#  minikube start --driver=none
* minikube v1.10.0-beta.1 on Centos 7.7.1908
* Using the none driver based on user configuration
* Starting control plane node minikube in cluster minikube
* Running on localhost (CPUs=2, Memory=3789MB, Disk=40183MB) ...
* OS release is CentOS Linux 7 (Core)
* Preparing Kubernetes v1.18.0 on Docker 1.13.1 ...
    > kubectl.sha256: 65 B / 65 B [--------------------------] 100.00% ? p/s 0s
    > kubelet.sha256: 65 B / 65 B [--------------------------] 100.00% ? p/s 0s
! This bare metal machine is having trouble accessing https://k8s.gcr.io
* To pull new external images, you may need to configure a proxy: https://minikube.sigs.k8s.io/docs/reference/networking/proxy/
    > kubeadm.sha256: 65 B / 65 B [--------------------------] 100.00% ? p/s 0s
    > kubectl: 41.98 MiB / 41.98 MiB [---------------] 100.00% 11.29 MiB p/s 4s
    > kubeadm: 37.96 MiB / 37.96 MiB [----------------] 100.00% 7.99 MiB p/s 5s
    > kubelet: 2.35 MiB / 108.01 MiB [>_______] 2.17% 11.00 KiB p/s ETA 2h44m0s
```

可以在启动命令上追加

```shell
--registry-mirror=https://替换为阿里云加速.mirror.aliyuncs.com
minikube start --driver=none --image-repository='registry.aliyuncs.com/google_containers'
```

经过漫长等待出现下面输出

```shell
! kubectl and minikube configuration will be stored in /root
! To use kubectl or minikube commands as your own user, you may need to relocate them. For example, to overwrite your own settings, run:
* 
  - sudo mv /root/.kube /root/.minikube $HOME
  - sudo chown -R $USER $HOME/.kube $HOME/.minikube
* 
* This can also be done automatically by setting the env var CHANGE_MINIKUBE_NONE_USER=true
* Enabled addons: default-storageclass, storage-provisioner
* Done! kubectl is now configured to use "minikube"
```

说明启动成功

```shell
[root@iZm5e7y9qoyl006b96mwaaZ k8s]# minikube status
minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured

[root@iZm5e7y9qoyl006b96mwaaZ k8s]# kubectl get nodes
NAME                      STATUS   ROLES    AGE     VERSION
izm5e7y9qoyl006b96mwaaz   Ready    master   3m22s   v1.18.0

[root@iZm5e7y9qoyl006b96mwaaZ k8s]# kubectl  cluster-info
Kubernetes master is running at https://172.31.202.153:8443
KubeDNS is running at https://172.31.202.153:8443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.
```





### 测试

官网链接：https://kubernetes.io/docs/setup/learning-environment/minikube/

1、使用名为 `echoserver` 的镜像创建一个 Kubernetes Deployment，并使用 `--port` 在端口 8080 上暴露服务。`echoserver` 是一个简单的 HTTP 服务器。

```shell
kubectl run hello-minikube --image=registry.aliyuncs.com/google_containers/echoserver:1.10 --port=8080

上面这个网站描述输出的是pod，跟预期的deployment不符
pod/hello-minikube created

换成下面的命令试一下
kubectl create deployment hello-minikube --image=registry.aliyuncs.com/google_containers/echoserver:1.10
输出
deployment.apps/hello-minikube created
```



2、查看pod状态

```shell
[root@iZm5e7y9qoyl006b96mwaaZ k8s]# kubectl get pod
NAME             READY   STATUS    RESTARTS   AGE
hello-minikube   1/1     Running   0          6m18s
```



3、将`hello-minikube` Deployment，需要将其作为 Service 公开

```
kubectl expose deployment hello-minikube --type=NodePort --port=8080

service/hello-minikube exposed
```



4、获取暴露 Service 的 URL 以查看 Service 的详细信息：

```
minikube service hello-minikube --url
```

