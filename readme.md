## 如何启动本项目

1. 本地安装 Redis
2. 本地数据库



## 目录结构

```bash
java.com.juliajiang.exportdemo
├── util													
│   ├── ExcelExportUtil.java
│   ├── DozerUtil.java
│   ├── GsonUtil.java
│   └── IDGeneratorUtil.java
├── config
│   ├── MybatisPlusConfig.java
│   └── RedissonConfig.java
├── student
│   ├── dto
│   │   ├── StudentReq.java
│   │   └── StudentDTO.java
│   ├── repository
│   │   ├── impl
│   │   │   └── StudentRepositoryImpl.java
│   │   └── StudentRepository.java
│   ├── controller
│   │   └── StudentController.java
│   ├── service
│   │   └── StudentService.java
│   └── domain
│       ├── mapper
│       │   └── StudentMapper.java
│       └── storage
│           └── Student.java
├── common
│   ├── JsonResult.java
│   ├── BaseAssembler.java
│   ├── BaseReq.java
│   ├── BaseReqDTO.java
│   └── BaseController.java
├── asyn
│   ├── QueueNameEnum.java
│   ├── EventHandler.java
│   ├── EventConsumer.java
│   ├── EventProducer.java
│   ├── QueueInfo.java
│   ├── EventType.java
│   ├── export
│   │   ├── handler
│   │   │   ├── StudentHandle.java
│   │   │   ├── ExportHandler.java
│   │   │   └── ExportTemplate.java
│   │   ├── ExportEnum.java
│   │   └── queue
│   │       └── ExportQueueInfo.java
│   └── EventModel.java
├── ExportDemoApplication.java
└── MybatisPlusGenerator.java
```