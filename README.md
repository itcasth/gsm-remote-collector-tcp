GSM 远程取号器的接口


## 接口约束 ##
传输地址：
``` c
#define SERVICE_IP_ADDR	0xC0A80604
#define SERVICE_TCP_PORT	10080
```
传输协议：
TCP
消息格式：
``` c
#define SS_DEV_MSG_BODY_SIZE  0x300
typedef struct SS_DEV_MSG
{
    		unsigned short     unCode;              /* 消息种别码msgType */
    		unsigned int     unBodyLen;             /* 消息参数体长度（单位：字节）*/
    		unsigned char    uchBody[SS_DEV_MSG _BODY_SIZE];    /* 消息参数体 */
} SS_DEV_MSG;
#define SS_DEV_MSG_HDR_SIZE	(sizeof(SS_DEV_MSG) - SS_DEV_MSG _BODY_SIZE)
```

## 消息约束： ##
（1）消息和数据采用网络字节序，大端模式发送。

（2）实际传输长度为SS_DEV_MSG_HDR_SIZE + unBodyLen（消息的实际长度）。
