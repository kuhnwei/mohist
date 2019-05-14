# Ubuntu配置snmp服务

```shell
apt-get install snmp
# apt-get install snmpd

service snmpd start

apt-get install sysv-rc-conf

alias chkconfig=sysv-rc-conf  #设置别名

chkconfig --list 

chkconfig snmpd on #设置服务开机自动启动

vim /etc/snmp/snmpd.conf

# 修改监听地址
agentAddress upd:127.0.0.1:161

# 添加：view 定义了可以查看哪些节点设备的信息。
# view systemview included .1 表示可以查看.1节点下的所有设备信息。
# Third, create a view for us to let the group have rights to:
# Make at least snmpwalk -v 1 localhost -c public system fast again.
# name incl/excl subtree mask(optional)
view systemview included .1
view systemview included .1.3.6.1.2.1.1
view systemview included .1.3.6.1.2.1.25.1.1

#取消以下配置的注释
# proc mountd
# proc ntalkd 4
# proc sendmail 10 1

# exec echotest /bin/echo hello world

# disk / 10000

# load 12 14 14

#验证配置的SNMP: 输入命令
snmpwalk -v 2c -c public localhost 1.3.6.1.4.1.2021.11.11.0
#返回 iso.3.6.1.4.1.2021.11.11.0 = INTEGER: 96
```

## Windows10配置SNMP服务

```
# 1.打开“程序和功能”；
# 2.打开“启用或关闭Windows功能”
# 3.找到“简单网络管理协议(SNMP)-WMI SNMP 提供程序”，并启用
# 4.打开服务 "cmd -> services.msc"
# 5.找到"SNMP Service"，并选择重启
# 6.右键"SNMP Service" 选择属性，在“安全”选项卡添加"public"社区，并设置主机IP
```



java 程序获得服务器状态 需要以下几个步骤：

1. 创建Snmp对象；
2. 创建CommunityTarget对象，并制定community，version，address，timeout，retry等参数
3. 创建PDU对象，并制定操作类型（GET/GETNEXT/GETBULK/SET），添加VariableBinding(也就是代操作的OID，每一种监控信息对应一个oid，如输出字节数对应的oid为1.3.6.1.2.1.31.1.1.1.10)，如果是GETBULK操作，还可以制定MaxRepetitions和NonRepeaters。（注意：一定要制定MaxRepetitions，默认值是0，那样不会返回任何结果。）
4. 利用snmp.send(pdu, target)方法，发送请求请返回结果。
5. 需要引入jar包org.snmp

```java
package site.mohist;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

/**
 * @author Kuhn, email@kuhnwei.com
 * @version 2019/2/25 14:38
 **/
public class SnmpTest {
    private Snmp snmp;
    private Target target;
    private PDU pdu;

    private String host;
    private int port;
    private int version;
    private String community;
    private int retries;
    private long timeout;
    private int type;

    public void snmpGet(HashMap<String, String> result, Collection<String> oids, Snmp snmp, PDU pdu, CommunityTarget target) {
        pdu.setType(PDU.GET);
        for (String oid : oids) {
            pdu.add(new VariableBinding(new OID(oid)));
        }

        ResponseEvent responseEvent;
        try {
            responseEvent = snmp.send(pdu, target);
            PDU response = responseEvent.getResponse();
            if (response != null) {
                if (response.getErrorIndex() == PDU.noError && response.getErrorStatus() == PDU.noError) {
                    Vector<VariableBinding> vector = (Vector<VariableBinding>) response.getVariableBindings();
                    for (VariableBinding v : vector) {
                        result.put(v.getOid().toString(), v.getVariable().toString());
                    }
                } else {
                    // TODO throw new SnmpGetException(response.getErrorStatusText());
                }
            } else {
                // TODO throw  new SnmpGetException("no  response");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> snmpGet(Collection<String> oids) {
        HashMap<String, String> result = new HashMap<>();
        snmpGet(result, oids, this.snmp, this.pdu, (CommunityTarget) this.target);
        return result;
    }

    public Map<String, String> snmpGet(String oid) {
        List<String> list = new ArrayList<>();
        list.add(oid);
        return snmpGet(list);
    }

    public Snmp createSnmp() {
        if (this.snmp == null) {
            try {
                TransportMapping transport;
                transport = new DefaultUdpTransportMapping();
                Snmp snmp = new Snmp(transport);
                snmp.setTimeoutModel(new DefaultTimeoutModel());
                snmp.listen();
                return snmp;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.snmp;
    }

    public Target createTarget() {
        Target target = new CommunityTarget();
        Address address = GenericAddress.parse("udp:" + this.host + "/" + this.port);
        target.setAddress(address);
        target.setRetries(this.retries);
        target.setTimeout(this.timeout);
        target.setVersion(this.version);
        target.setSecurityName(new OctetString(this.community));
        return target;
    }

    public PDU createPDU() {
        PDU pdu = new PDU();
        pdu.setType(this.type);
        return pdu;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello SNMP !!!");
        String snmpHost = "192.168.68.132";
        if (InetAddress.getByName(snmpHost).isReachable(3000)) {
            SnmpTest snmpTest = new SnmpTest();
            snmpTest.host = snmpHost;
            snmpTest.port = 161;
            snmpTest.community = "public";
            snmpTest.retries = 3;
            snmpTest.timeout = 10000;
            snmpTest.version = 1;
            snmpTest.type = PDU.GET;

            snmpTest.snmp = snmpTest.createSnmp();
            snmpTest.target = snmpTest.createTarget();
            snmpTest.pdu = snmpTest.createPDU();

            Map<String, String> map = snmpTest.snmpGet("1.3.6.1.4.1.2021.4.3.0");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "     " + entry.getValue());
            }
        }
        System.out.println("-------------------------");
    }
}
// 1.3.6.1.4.1.2021.4.3.0     1046524
```

