package xyz.mrkwcode.aiimusicserver.validations;

import com.alibaba.fastjson.JSON;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import xyz.mrkwcode.aiimusicserver.annos.Permission;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
public class PermissionValidation implements ConstraintValidator<Permission, String> {

    private File jsonFile = new File("../configs/json/permission.json");
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 提供校验规则
        if(s == null) {
            return false;
        }
        try {
            String permissionJson = jsonReader(jsonFile);
            Map map = JSON.parseObject(permissionJson, Map.class);
            ArrayList<String> permissionArray = (ArrayList<String>) map.get("permissions-group");
            for (String ele: permissionArray) {
                if(ele.equals(s)) return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static String jsonReader(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        Reader reader = new InputStreamReader(new FileInputStream(file), "Utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        String jsonStr = sb.toString();
        return jsonStr;
    }
}
