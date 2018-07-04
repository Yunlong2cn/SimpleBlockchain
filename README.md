# SimpleBlockchain
区块链入门

## 执行
```
java -jar web-0.0.1-SNAPSHOT.jar
```

## 说明

1、每个节点均可做为节点服务器，用于其他节点的注册功能，以便于区块数据同步

2、执行 jar 包运行 web 程序，通过访问 http://localhost:8080 ，可以查看区块数据，通过配置 application.yml ，blockchain.node-server 设置节点注册服务器，用于同步区块数据

3、通过访问 http://localhost:8080/?data=这是数据，添加区块
