source ~/.bash_profile
b=9g
c=3
d=40g

t4=$(/app/hadoop/bin/yarn application -list | grep spark-thrift01 | wc -l)
if [ $t4 = 0 ]; then

  pid=$(ps aux | grep "org.apache.spark.sql.hive.thriftserver.HiveThriftServer2" | grep -v grep | awk '{print $2}')
  if [ -z "$pid" ]; then
    echo "restart"
  else
    kill -9 $pid
  fi
  sleep 2s

  /app/spark/sbin/start-thriftserver.sh \
    --master yarn \
    --name spark-thrift01 \
    --queue spark \
    --properties-file /app/spark/conf/spark-thrift-sparkconf.conf \
    --deploy-mode client \
    --executor-memory $b \
    --executor-cores $c \
    --driver-memory $d

else

  metircs=$(curl -s http://pdtlrp01:8881/metrics | grep -y "^java_lang_Memory_HeapMemoryUsage")
  echo $metircs | while read line; do
    t2=$(echo ${line} | awk -F ' ' '{print $6}' | awk '{print $0*1}')
    t1=$(echo ${line} | awk -F ' ' '{print $8}' | awk '{print $0*1}')
    t3=$(awk 'BEGIN{printf "%.0f\n",('$t1'/'$t2')*100}')
    echo $t3

    if [ $t3 -gt 70 ]; then

      pid=$(ps aux | grep "org.apache.spark.sql.hive.thriftserver.HiveThriftServer2" | grep -v grep | awk '{print $2}')
      if [ -z "$pid" ]; then
        echo "restart"
      else
        kill -9 $pid
      fi
      sleep 2s

      /app/spark/sbin/start-thriftserver.sh \
        --master yarn \
        --name spark-thrift01 \
        --queue spark \
        --properties-file /app/spark/conf/spark-thrift-sparkconf.conf \
        --deploy-mode client \
        --executor-memory $b \
        --executor-cores $c \
        --driver-memory $d

    fi

  done

fi
