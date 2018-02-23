## Getting Started
Spring boot project with consul implementation.

## Features
* Rxjava2
* [Consul](https://www.consul.io/)
* Feign
* Hystrix
* Swagger2

## Prerequisites
* consul

## Installing
* Install first the consul agent in your local machine, you can download it [here](https://www.consul.io/downloads.html).

## Running Consul Agent
```
consul agent -server -bootstrap-expect=1 -data-dir=consul-data -ui -bind=192.168.6.1(ip address of local machine)
```
You can check in http://localhost:8500/ui/ to determine if consul is up and running.
Note: Before running the project ensure first that consul is running.

## Configuration
* Because of incompatibility in consul version 1 onwards added some configuration.
```
#disable management endpoint provided by actuator module
management:
  security:
    enabled: false

#disable health check indicator of consul.
#compatibilty issue in consul 1.0 version.
  health:
    consul:
      enabled: false
```
