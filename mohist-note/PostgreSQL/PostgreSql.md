## Ubuntu 部署配置 PostgreSQL

 ```shell
sudo apt-get install postgresql postgresql-client

sudo /etc/init.d/postgresql start
sudo /etc/init.d/postgresql stop
sudo /etc/init.d/postgresql restart

# 切换用户
su postgres
# 登录数据库
psql
# 修改 postgres 登录密码, 注意：密码要用引号引起来，命令最后要加分号(;)结尾
ALTER USER postgres WITH PASSWORD 'postgres';

# 修改配置文件，允许远程连接
vim /etc/postgresql/9.X/main/postgresql.conf

listen_address = '*'
password_encryption = on

vim /etc/postgresql/9.x/main/pg_hba.conf

host all all 0.0.0.0/0 md5

# 重启
sudo /etc/init.d/postgresql restart

 ```

## 双机热备

1. 修改postgresql.conf

   ```shell
   vim /etc/postgresql/9.x/main/postgresql.conf
   ```

   ```
   checkpoint_segments = 500
   checkpoint_timeout = 5min
   archive_mode = on
   wal_level = hot_standby
   max_wal_senders = 1
   wal_keep_segments = 500
   hot_standby = on
   logging_collector = on
   log_directory = 'pg_log'
   log_filename = 'postgresql-%Y-%m-%d_%H%M%S.log'
   ```

2. 创建备份用户

   ```shell
   psql -U postgres -h 127.0.0.1
   CREATE USER standby SUPERUSER LOGIN PASSWORD 'standby';
   ```

3. 配置 pg_hba.conf

   ```
   host  replication  standby  0.0.0.0/0  md5
   ```

   

## windows10 postgreSQL 配置允许远程访问

`C:\Program Files\PostgreSQL\9.6\data`

- postgresql.conf

  ```
  listen_addresses = '*'
  ```

  

- pg_hba.conf

  ```
  # 主要是新增
  host    all             all             192.168.1.188/32        md5
  ```

  ```
  # TYPE  DATABASE        USER            ADDRESS                 METHOD
  # IPv4 local connections:
  host    all             all             127.0.0.1/32            md5
  host    all             all             192.168.1.188/32        md5
  # IPv6 local connections:
  host    all             all             ::1/128                 md5
  # Allow replication connections from localhost, by a user with the
  # replication privilege.
  #host    replication     postgres        127.0.0.1/32            md5
  #host    replication     postgres        ::1/128                 md5
  ```

重启服务 `postgresql-x64-9.6`

