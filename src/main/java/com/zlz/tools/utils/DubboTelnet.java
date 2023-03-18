package com.zlz.tools.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.tomcat.util.buf.ByteBufferUtils;
import org.springframework.util.StringUtils;

public class DubboTelnet {
    private TelnetClient telnet = new TelnetClient();
    private InputStream in;
    private PrintStream out;
    // 普通用户结束
    public DubboTelnet(String ip, int port) {
        try {
        	if(isConnected()) {
        		telnet.disconnect();
        	}
            telnet.connect(ip, port);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /** * 读取分析结果 * * @param pattern * @return */
    public String readUntil(String pattern) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedInputStream bi = new BufferedInputStream(in);
            while (true) {
                byte[] buffer = new byte[1024];
                int len = bi.read(buffer);
                if (len <= -1) {
                    break;
                }

                String msg = new String(buffer, 0, len, "UTF-8");
                sb.append(msg);
                if (msg.endsWith(pattern) || sb.indexOf(pattern) != -1) {
                    break;
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /** * 写操作 * * @param value */
    public void write(String value) {
        try {
            out.println(value);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** * 向目标发送命令字符串 * * @param command * @return */
    public String sendCommand(String command) {
        try {
            write(command);
            Thread.sleep(1000);
            return readUntil("dubbo>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /** * 连接状态 */
    public boolean isConnected() {
        return telnet.isConnected();
    }
}
