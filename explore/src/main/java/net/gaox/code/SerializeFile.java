package net.gaox.code;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.gaox.base.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <p> 序列化、反序列 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-03 16:36
 */
@Slf4j
public class SerializeFile {

    public static void main(String[] args) {
        String filePath = "file/user.dat";
        serialize2file(filePath);
        deserializeFromFile(filePath);
    }

    /**
     * 序列化到文件
     *
     * @param filePath 文件路径
     */
    public static void serialize2file(String filePath) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filePath)));
            User user = new User("高羲之", 21, "北京", "男", "gaoxizhi");
            oos.writeObject(user);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从文件反序列化
     *
     * @param filePath 文件路径
     */
    public static void deserializeFromFile(String filePath) {
        try {
            ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filePath)));
            User user = (User) ois.readObject();
            log.info("read user: {}", JSONObject.toJSONString(user));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
