package util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoIterable;
import org.apache.commons.lang3.StringUtils;

import java.net.UnknownHostException;

/**
 * @ClassName MongoConectionUtil
 * @Description: TODO
 * @Author lxc
 * @Date 2020/8/17 15:22
 * @Version V1.0
 **/
public class MongoConectionUtil {

    /**
     * 创建 MongoClient
     *
     * @param ip
     * @param port
     * @param username
     * @param password
     * @return
     */
    public static MongoClient getMongoClient(String ip, int port, String username, String password) throws UnknownHostException {
        StringBuilder mongoURI = new StringBuilder();
        mongoURI.append("mongodb://");
        if (StringUtils.isNotBlank(username) || StringUtils.isNotBlank(password)) {
            mongoURI.append(username).append(":").append(password).append("@").append(ip).append(":").append(port).append("/admin?authSource=admin&authMechanism=SCRAM-SHA-1");
        } else {
            mongoURI.append(ip).append(":").append(port).append("/admin&authMechanism=SCRAM-SHA-1");
        }
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.connectTimeout(500);
        build.serverSelectionTimeout(500);
        MongoClientURI mongoClientURI = new MongoClientURI(mongoURI.toString(), build);
        return new MongoClient(mongoClientURI);
    }


    /**
     * @param ip
     * @param port
     * @param username
     * @param password
     * @return true:连接成功，false:连接失败
     */
    public static MongoClient getMongoConnStatus(String ip, int port, String username, String password) throws Exception {
        MongoClient client = getMongoClient(ip, port, username, password);
        try {
            MongoIterable<String> names = client.listDatabaseNames();
            names.first();
            return client;
        } catch (Exception e) {
            throw new Exception("该用户不存在!");
        }
    }
}
