import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单的HTTP服务器，用于模拟后端API响应
 */
public class SimpleServer {
    
    private static final int PORT = 8080;
    
    public static void main(String[] args) throws IOException {
        // 创建ServerSocket并绑定到所有网络接口
        ServerSocket serverSocket = new ServerSocket(PORT, 0, InetAddress.getByName("0.0.0.0"));
        System.out.println("Server started on port " + PORT);
        System.out.println("Listening for requests on all network interfaces...");
        System.out.println("Server address: " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress());
            
            // 处理客户端请求
            handleRequest(clientSocket);
        }
    }
    
    private static void handleRequest(Socket clientSocket) throws IOException {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream outputStream = clientSocket.getOutputStream();
        ) {
            // 读取请求行
            String requestLine = in.readLine();
            if (requestLine == null) {
                return;
            }
            
            System.out.println("Request: " + requestLine);
            
            // 读取请求头
            String headerLine;
            Map<String, String> headers = new HashMap<>();
            while (!(headerLine = in.readLine()).isEmpty()) {
                System.out.println("Header: " + headerLine);
                String[] parts = headerLine.split(": ", 2);
                if (parts.length == 2) {
                    headers.put(parts[0], parts[1]);
                }
            }
            
            // 读取请求体
            StringBuilder requestBody = new StringBuilder();
            if (headers.containsKey("Content-Length")) {
                int contentLength = Integer.parseInt(headers.get("Content-Length"));
                char[] buffer = new char[contentLength];
                in.read(buffer, 0, contentLength);
                requestBody.append(buffer);
                System.out.println("Body: " + requestBody.toString());
            }
            
            // 解析请求路径
            String[] requestParts = requestLine.split(" ");
            String fullPath = requestParts[1];
            // 分离路径和查询参数
            String[] pathParts = fullPath.split("\\?");
            String path = pathParts[0];
            
            // 解析查询参数
            Map<String, String> queryParams = new HashMap<>();
            if (pathParts.length > 1) {
                String queryString = pathParts[1];
                String[] paramPairs = queryString.split("&");
                for (String pair : paramPairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length == 2) {
                        queryParams.put(keyValue[0], keyValue[1]);
                    }
                }
            }
            
            // 打印调试信息
            System.out.println("Request path: " + path + " (full: " + fullPath + ")");
            System.out.println("Path length: " + path.length());
            System.out.println("Query params: " + queryParams);
            
            // 处理不同的API请求
            String loginPath = "/Users/Login";
            String homePath = "/Home/Data";
            System.out.println("Comparing with loginPath: " + loginPath + " (equals: " + path.equals(loginPath) + ")");
            System.out.println("Comparing with homePath: " + homePath + " (equals: " + path.equals(homePath) + ")");
            
            if (path.equals(loginPath)) {
                // 处理登录请求
                handleLoginRequest(requestBody.toString(), outputStream);
            } else if (path.equals(homePath)) {
                // 处理首页数据请求
                handleHomeDataRequest(outputStream);
            } else if (path.equals("/Devices/Datas")) {
                // 处理设备数据请求，忽略devIds参数，返回模拟数据
                handleDeviceDataRequest(outputStream);
            } else {
                // 处理其他请求
                System.out.println("Unknown path: " + path);
                sendResponse(outputStream, 404, "Not Found", "{\"status\": 1, \"statusCode\": 0, \"msg\": \"API not found\"}");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clientSocket.close();
        }
    }
    
    private static void handleLoginRequest(String requestBody, OutputStream outputStream) throws IOException {
        // 解析登录请求（使用简单的字符串操作）
        String account = extractValue(requestBody, "Account");
        String password = extractValue(requestBody, "Password");
        
        System.out.println("Account: " + account);
        System.out.println("Password: " + password);
        
        // 模拟登录验证
        if ("15283747024".equals(account) && "123456".equals(password)) {
            // 登录成功
            String responseBody = "{\"status\": 0, \"statusCode\": 1, \"msg\": \"登录成功\", \"resultObj\": {\"userId\": 1, \"account\": \"15283747024\", \"telephone\": \"15283747024\", \"accessToken\": \"fake_token_123456\", \"roleName\": \"admin\", \"roleId\": 1}}";
            sendResponse(outputStream, 200, "OK", responseBody);
        } else {
            // 登录失败
            String responseBody = "{\"status\": 1, \"statusCode\": 0, \"msg\": \"用户名或密码错误\"}";
            sendResponse(outputStream, 200, "OK", responseBody);
        }
    }
    
    /**
     * 处理首页数据请求
     */
    private static void handleHomeDataRequest(OutputStream outputStream) throws IOException {
        // 模拟首页数据
        String responseBody = "{\"status\": 0, \"statusCode\": 1, \"msg\": \"获取成功\", \"resultObj\": {\"onlineDevices\": 12, \"currentAlerts\": 3, \"uptime\": \"7 days 12:34:56\", \"temperature\": 23.5, \"humidity\": 45.2}}";
        sendResponse(outputStream, 200, "OK", responseBody);
    }
    
    /**
     * 处理设备数据请求
     */
    private static void handleDeviceDataRequest(OutputStream outputStream) throws IOException {
        // 模拟设备数据
        String responseBody = "{\"status\": 0, \"statusCode\": 1, \"msg\": \"获取成功\", \"resultObj\": [" +
                "{\"deviceID\": 1, \"name\": \"机房设备1\", \"datas\": [" +
                    "{\"apiTag\": \"temperature\", \"value\": 23.5, \"recordTime\": \"2026-01-07 15:30:00\"}, " +
                    "{\"apiTag\": \"humidity\", \"value\": 45.2, \"recordTime\": \"2026-01-07 15:30:00\"}, " +
                    "{\"apiTag\": \"light\", \"value\": 850, \"recordTime\": \"2026-01-07 15:30:00\"}, " +
                    "{\"apiTag\": \"pressure\", \"value\": 101.3, \"recordTime\": \"2026-01-07 15:30:00\"}" +
                "]}, " +
                "{\"deviceID\": 2, \"name\": \"机房设备2\", \"datas\": [" +
                    "{\"apiTag\": \"temperature\", \"value\": 24.1, \"recordTime\": \"2026-01-07 15:30:00\"}, " +
                    "{\"apiTag\": \"humidity\", \"value\": 43.8, \"recordTime\": \"2026-01-07 15:30:00\"}, " +
                    "{\"apiTag\": \"light\", \"value\": 820, \"recordTime\": \"2026-01-07 15:30:00\"}, " +
                    "{\"apiTag\": \"pressure\", \"value\": 101.2, \"recordTime\": \"2026-01-07 15:30:00\"}" +
                "]}, " +
                "{\"deviceID\": 3, \"name\": \"机房设备3\", \"datas\": [" +
                    "{\"apiTag\": \"temperature\", \"value\": 22.9, \"recordTime\": \"2026-01-07 15:30:00\"}, " +
                    "{\"apiTag\": \"humidity\", \"value\": 46.5, \"recordTime\": \"2026-01-07 15:30:00\"}, " +
                    "{\"apiTag\": \"light\", \"value\": 870, \"recordTime\": \"2026-01-07 15:30:00\"}, " +
                    "{\"apiTag\": \"pressure\", \"value\": 101.4, \"recordTime\": \"2026-01-07 15:30:00\"}" +
                "]}" +
            "]}";
        sendResponse(outputStream, 200, "OK", responseBody);
    }
    
    private static String extractValue(String json, String key) {
        // 更健壮的JSON解析，能够处理不同的字段顺序和格式
        System.out.println("Extracting key: " + key + " from json: " + json);
        
        // 简单的字符串处理，找到key和对应的value
        int keyStart = json.indexOf("\"" + key + "\":");
        if (keyStart == -1) {
            System.out.println("Key not found: " + key);
            return null;
        }
        
        // 找到value的开始位置
        int valueStart = json.indexOf(":", keyStart) + 1;
        // 跳过空格
        while (valueStart < json.length() && json.charAt(valueStart) == ' ') {
            valueStart++;
        }
        
        // 确定value的结束位置
        int valueEnd;
        if (json.charAt(valueStart) == '\"') {
            // 字符串类型的值
            valueStart++;
            valueEnd = json.indexOf("\"", valueStart);
            if (valueEnd == -1) {
                System.out.println("String value end not found for key: " + key);
                return null;
            }
        } else if (json.charAt(valueStart) == '{') {
            // 对象类型的值（这里不处理）
            System.out.println("Object value not supported for key: " + key);
            return null;
        } else if (json.charAt(valueStart) == '[') {
            // 数组类型的值（这里不处理）
            System.out.println("Array value not supported for key: " + key);
            return null;
        } else {
            // 数字或布尔类型的值
            valueEnd = json.indexOf(',', valueStart);
            if (valueEnd == -1) {
                // 最后一个字段
                valueEnd = json.indexOf('}', valueStart);
            }
            if (valueEnd == -1) {
                System.out.println("Value end not found for key: " + key);
                return null;
            }
        }
        
        // 提取并返回值
        String value = json.substring(valueStart, valueEnd);
        System.out.println("Extracted value: " + value + " for key: " + key);
        return value;
    }
    
    private static void sendResponse(OutputStream outputStream, int statusCode, String statusMessage, String responseBody) throws IOException {
        // 确保响应体不为null
        if (responseBody == null) {
            responseBody = "";
        }
        
        // 发送完整的HTTP响应，不依赖gzip压缩
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        
        // 发送响应头
        writer.println("HTTP/1.1 " + statusCode + " " + statusMessage);
        writer.println("Content-Type: application/json; charset=UTF-8");
        writer.println("Access-Control-Allow-Origin: *");
        writer.println("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
        writer.println("Access-Control-Allow-Headers: *");
        writer.println("Content-Length: " + responseBody.getBytes("UTF-8").length);
        writer.println("Connection: close");
        writer.println(); // 空行分隔响应头和响应体
        
        // 发送响应体
        writer.println(responseBody);
        
        // 刷新并关闭writer，确保所有数据都发送出去
        writer.flush();
        writer.close();
        
        System.out.println("Response sent successfully");
    }
}