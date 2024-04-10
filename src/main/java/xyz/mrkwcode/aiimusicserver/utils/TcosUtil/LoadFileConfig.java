package xyz.mrkwcode.aiimusicserver.utils.TcosUtil;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class LoadFileConfig {
    public static Map<String, Object> readSecretInfo(InputStream inputStream) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Map<String, Object>> data = yaml.load(inputStream);
        // 处理 YAML 数据
        return data.get("secret-info");
    }
}
