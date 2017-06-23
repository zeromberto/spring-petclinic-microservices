#!/bin/bash
id=`docker ps | grep "customers-service" | head -n 1 | awk '{print $1}'`
sleep 560
docker stop $id
