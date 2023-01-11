package com.lab4.demo.data_access;

import com.lab4.demo.service.Parse_json;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository
public abstract class Logs_reader {
    public static String read() throws IOException {
        String str = new String(Files.readAllBytes(Paths.get("json_files\\logs.json")));
        JSONObject obj = new JSONObject(str);
        return Parse_json.logs(obj);
    }
}
