使用dubbo的demo

# 1：docker 安装zookeeper & dubbo

### M1 Mac下使用Docker安装 dubbo-admin

1）从dockerhub拉取dubbo-admin的镜像源
```sh
docker pull apache/dubbo-admin
```
ps：如果使用m1芯片，服务启动后提示：
WARNING: The requested image's platform (linux/amd64) does not match the detected host platform (linux/arm64/v8) and no specific platform was requested
需要使用dubbo-admin官方提供的支持arm64的镜像，此时可通过https://hub.docker.com/r/apache/dubbo-admin/tags
找到支持OS/ARCH：linux/arm64/v8的镜像
这里我使用的是
```sh
docker pull apache/dubbo-admin:0.5.0-SNAPSHOT
```

2）从dockerhub拉取zookeeper的镜像源
```sh
docker pull zookeeper
```
因为dubbo-admin要连接zookeeper，这里我两者都是使用docker安装的，为了保证他们能够正常通信，要将其连接到同一个网络下
3）建立一个桥接网络zk
```sh
docker network create -d bridge zk
```

4）创建zookeeper容器，并指定其网络为zk
```sh
docker run -itd --name zookeeper --network zk -p 2181:2181 -p 2888:2888 -p 3888:3888 zookeeper
```

5）查看zk网络的详细信息
```sh
docker network inspect zk
```

6）创建dubbo-admin容器，指定网络为zk，并设置zookeeper为上述查询出来的地址
```sh
docker run -itd --name dubbo_admin -p 8080:8080 --network zk -e "admin.registry.address=zookeeper://zookeeper:2181" -e "admin.config-center=zookeeper://zookeeper:2181" -e "admin.metadata-report.address=zookeeper://zookeeper:2181" apache/dubbo-admin:0.5.0-SNAPSHOT
```
访问: http://localhost:8080/



### 补充:
dubool rpc框架，可以使用zookeeper也可以使用阿里的Nacos
```sh
docker pull --platform linux/amd64 nacos/nacos-server

// 如果想拉取指定版本，就在命令后面加上版本号，:Tag
// 例如： 
docker pull --platform linux/amd64 nacos/nacos-server:v2.4.1
```

### 启动Nacos：
在终端执行下面的命令，注意：如果 Nacos 版本在2.0及以上，需要把8848、9848和9849三个端口映射出来，否则访问会404失败。Mac苹果电脑，并且在 Docker 上运行某个应用，启动命令需要加入 --platform linux/amd64 ，否则会有警告，影响后面
```sh
docker run --platform linux/amd64  --name nacos --env MODE=standalone -p 8848:8848 -p 9848:9848 -p 9849:9849 -d nacos/nacos-server 
```
这个命令会启动一个名为 Nacos 的容器，并将其绑定到本地机器的 8848 端口，并设置容器模式为 standalone。

### Nacos 详情：
```sh
docker run -d --name nacos \                                            -d 表示运行在后台，--name 指定名称为nacos
--ip 0.0.0.0 \                                                          自定义分配 IP 地址，可忽略
-p 8848:8848 \                                                          前者为暴露给外部访问的端口，后者为nacos容器端口
-p 9848:9848 \                                                          9848是nacos2.0.0版本以上必须要加上端口映射
-p 9849:9849 \                                                          9849是nacos2.0.0版本以上必须要加上端口映射
--env MODE=standalone \                                                 nacos以单机版启动，默认为cluster（集群）
--env NACOS_AUTH_ENABLE=true \                                          如果使用官方镜像，请在启动docker容器时，添加如下环境变量
-v /www/wwwroot/changjing/docker/nacos/conf/:/home/nacos/conf \         nacos 配置文件目录，“:”前为服务器目录，“:”后为nacos容器中的目录
-v /www/wwwroot/changjing/docker/nacos/logs:/home/nacos/logs \          nacos 日志文件目录，“:”前为服务器目录，“:”后为nacos容器中的目录
-v /www/wwwroot/changjing/docker/nacos/data:/home/nacos/data \          nacos 数据文件目录，“:”前为服务器目录，“:”后为nacos容器中的目录
nacos/nacos-server:v2.2.1                                               指定 docker nacos 版本，这里是2.2.1版本
```
### 进入退出容器：
```sh
# 进入容器
docker exec -it nacos /bin/bash
# 退出容器
exit
```
### 配置 Nacos 数据库存储：
默认情况下，Nacos 使用内置的`Derby`数据库进行数据存储。虽然 Derby是一个轻量级的数据库，但当数据量较大时，它可能会导致性能瓶颈和数据丢失的问题。因此，建议将 Nacos 数据库存储改为`MySQL`或 `PostgreSQL`等外部数据库。

```sh
// 只需要新建conf的挂载即可，其他目录的挂载会自动生成目录（如logs）
mkdir ～/nacos/conf

// 把 Nacos 容器中的/home/nacos/conf文件夹拷贝
docker cp nacos:/home/nacos/conf ~/nacos
// 注意：宿主路径填写/nacos，后面不需要写conf，会自动覆盖
```


# 2:修改zookeeper地址

[consumer-application.yml](service-consumer%2Fservice-consumer-core%2Fsrc%2Fmain%2Fresources%2Fapplication.yml)

[provider-application.yml](service-provider%2Fservice-provider-core%2Fsrc%2Fmain%2Fresources%2Fapplication.yml)