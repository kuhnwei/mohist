# Ubuntu部署keepalived

安装keepalived

```shell
sudo apt-get install keepalived
```

安装nginx

```shell
sudo apt-get install nginx
```

查看当前网络配置

```shell
ifconfig
----
ens33     Link encap:Ethernet  HWaddr 00:0c:29:9b:8f:06  
          inet addr:192.168.180.128  Bcast:192.168.180.255  Mask:255.255.255.0
          inet6 addr: fe80::20c:29ff:fe9b:8f06/64 Scope:Link
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:5030 errors:0 dropped:0 overruns:0 frame:0
          TX packets:6596 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:432683 (432.6 KB)  TX bytes:663793 (663.7 KB)

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          inet6 addr: ::1/128 Scope:Host
          UP LOOPBACK RUNNING  MTU:65536  Metric:1
          RX packets:160 errors:0 dropped:0 overruns:0 frame:0
          TX packets:160 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1 
          RX bytes:11840 (11.8 KB)  TX bytes:11840 (11.8 KB)
```

配置keepalived

```shell
sudo vim /etc/keepalived/keepalived.conf
```

*主服务器配置：

```config
# keepalived.conf
global_defs {
	notification_email {
	}
}

vrrp_script chk_nginx {
	script "/etc/keepalived/check_nginx.sh"
	interval 2    # 每2s检查一次
	weight -5     # 检测失败(脚本返回非0)则优先级减少5个值
	fall 3        # 如果连续失败3次，则认为服务器已down
	rise 2        # 如果连续成功2次，则认为服务器已up
}

vrrp_instance VI_1 {       # 实例名称
	state MASTER           # 可以是 MASTER 或 BACKUP, 当其他节点keepalive启动时会自动将priority比较大
	                       # 的节点选举为MASTER
	interface ens33        # 节点固有IP的网卡，用来发VRRP包做心跳检测
	virtual_router_id 51   # 虚拟路由ID，取值0-255，用来区分多个Instance的VRRP组播，同一网段内ID不能重
	                       # 复，主备必须一样
	priority 100           # 权重，主服务器要比备用服务器高
	advert_int 1           # 检测间隔默认为1秒，即1秒进行一次master选举（健康检查时间间隔）
	authentication {       # 认证区域，认证类型有PASS和HA,推荐使用PASS(密码只识别前8位)
		auth_type PASS     # 默认时PASS认证
		auth_pass 650901   # PASS认证密码
	}
	virtual_ipaddress {
		192.168.180.100    # 虚拟VIP地址，允许多个，一行一个
	}
	track_script {          # 引用VRRP脚本，即在vrrp_script部分指定的名字。定期运行，并引发主备切换
		chk_nginx
	}
}
```

*备服务器配置：

```config
# keepalived.conf
global_defs {
	notification_email {
	}
}

vrrp_script chk_nginx {
	script "/etc/keepalived/check_nginx.sh"
	interval 2    # 每2s检查一次
	weight -5     # 检测失败(脚本返回非0)则优先级减少5个值
	fall 3        # 如果连续失败3次，则认为服务器已down
	rise 2        # 如果连续成功2次，则认为服务器已up
}

vrrp_instance VI_1 {       # 实例名称
	state BACKUP           # 可以是 MASTER 或 BACKUP, 当其他节点keepalive启动时会自动将priority比较大
	                       # 的节点选举为MASTER
	interface ens33        # 节点固有IP的网卡，用来发VRRP包做心跳检测
	virtual_router_id 51   # 虚拟路由ID，取值0-255，用来区分多个Instance的VRRP组播，同一网段内ID不能重
	                       # 复，主备必须一样
	priority 50           # 权重，主服务器要比备用服务器高
	advert_int 1           # 检测间隔默认为1秒，即1秒进行一次master选举（健康检查时间间隔）
	authentication {       # 认证区域，认证类型有PASS和HA,推荐使用PASS(密码只识别前8位)
		auth_type PASS     # 默认时PASS认证
		auth_pass 650901   # PASS认证密码
	}
	virtual_ipaddress {
		192.168.180.100    # 虚拟VIP地址，允许多个，一行一个
	}
	track_script {          # 引用VRRP脚本，即在vrrp_script部分指定的名字。定期运行，并引发主备切换
		chk_nginx
	}
}
```

编写脚本：/etc/keepalived/check_nginx.sh

```sh
#!/bin/bash  
#代码一定注意空格，逻辑就是：如果nginx进程不存在则启动nginx,如果nginx无法启动则kill掉keepalived所有进程  
A=`ps -C nginx --no-header | wc -l`  
if [ $A -eq 0 ];then  
  /etc/init.d/nginx start  
  sleep 3  
  if [ `ps -C nginx --no-header | wc -l` -eq 0 ];then  
    killall keepalived  
  fi  
fi 
```

保存后执行

```
sudo /etc/init.d/keepalived start
sudo /etc/init.d/keepalived restart
sudo /etc/init.d/keepalived stop
```

```
sudo /etc/init.d/nginx start
sudo /etc/init.d/nginx restart
sudo /etc/init.d/nginx stop
```

查看运行进程

```shell
ps -e | grep keepalived
------
  4504 ?        00:00:00 keepalived
  4505 ?        00:00:00 keepalived
  5427 ?        00:00:02 keepalived
ps -e | grep nginx
  5533 ?        00:00:00 nginx
  5535 ?        00:00:00 nginx
```

