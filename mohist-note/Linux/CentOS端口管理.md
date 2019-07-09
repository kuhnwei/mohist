```shell
# 查看zones
firewall-cmd --get-active-zones

# 打开端口
firewall-cmd --zone=public --add-port=8161/tcp --permanent

# 重启防火墙
firewall-cmd --reload

# 查看端口情况
firewall-cmd --query-port=8161/tcp
```

