### 开启服务-通过 mqadmin 创建 Topic。
```sh
$ sh bin/mqadmin updatetopic -n localhost:9876 -t TestTopic -c DefaultCluster
```


### 关闭服务
```sh
$ sh bin/mqshutdown broker
The mqbroker(36695) is running...
Send shutdown request to mqbroker with proxy enable OK(36695)

$ sh bin/mqshutdown namesrv
The mqnamesrv(36664) is running...
Send shutdown request to mqnamesrv(36664) OK
```