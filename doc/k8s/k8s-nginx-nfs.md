### 服务端工作

机器ip 192.168.31.131
1、安装相关软件
```shell
yum install nfs-utils rpcbind
```

2、创建共享目录
```shell
 mkdir /nfs-data
```

3、编辑/etc/exports文件添加需要共享目录
```shell
vim /etc/exports
/nfs-data *(ro,sync,insecure,no_root_squash)

exportfs -arv
```

4、启动
```shell
systemctl start nfs.service
```


### k8s中使用nfs

1、创建pv

编辑nfs-pv.yaml
```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: nfs
spec:
  storageClassName: manual
  capacity:
    storage: 5Gi
  accessModes:
    - ReadWriteMany
  nfs:
    server: 192.168.31.131
    path: "/nfs-data"
```

创建pv
```shell
kubectl create -f nfs-pv.yaml 
persistentvolume/nfs created
```
查看pv
```shell
kubectl get pv
```

2、创建pvc
编辑nfs-pvc.yaml
```yaml 
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: nfs
spec:
  accessModes:
    - ReadWriteMany
  storageClassName: manual
  resources:
    requests:
      storage: 1Gi
```
创建pvc
```shell
kubectl create -f nfs-pvc.yaml 
persistentvolumeclaim/nfs created
```
查看pvc
```shell
 kubectl get pvc
```


3、编辑nginx pod文件
```shell
apiVersion: v1
kind: Pod
metadata:
   name: web-frontend
spec:
  containers:
  - name: web
    image: nginx
    ports:
      - name: web
        containerPort: 80
    volumeMounts:
        - name: nfs
          mountPath: "/usr/share/nginx/html"
  volumes:
  - name: nfs
    persistentVolumeClaim:
      claimName: nfs
```

结果pod一直处于pending状态
查看describe返回如下

```
default-scheduler  running "VolumeBinding" filter plugin for pod "web-frontend": pod has unbound immediate PersistentVolumeClaims
```
经过排查发现是nfs-pv.yaml中的path配置不对，原来为 "/" 修改为 "/nfs-data"

再次查看pod
```
MountVolume.SetUp failed for volume "nfs" : mount failed: exit status 32
Output: Running scope as unit run-1681.scope.
mount.nfs: access denied by server while mounting 192.168.31.130:/nfs-data
```
发现是nfs-pv中ip写错了

4、验证
```
进入nginx的pod /usr/share/nginx/html 目录下写入文件
到192.168.31.131 /nfs-data下可以看到
```