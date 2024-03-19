# Kоманды


### Захожу в папку 
- cd /usr/local/Cellar/kafka/3.7.0/libexec
### Запускаю zookeeper
- bin/zookeeper-server-start.sh config/zookeeper.properties
- brew services start zookeeper
- brew services stop zookeeper
### Запускаю kafka
- bin/kafka-server-start.sh config/server.properties
- brew services start kafka
- brew services stop kafka
### Подключаю producer
- bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic
### Подключаю consumer
- bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic
### Создаю topic
- kafka-topics --create --topic test --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
- 
- brew services list

