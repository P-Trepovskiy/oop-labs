package com.lab4.demo.data_access;


import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.util.List;
import java.util.Objects;

@Repository
public abstract class Logs_writer {

    public static void write_log(String log, List<List<Double>> matrix, String id, String result) throws Exception {
        JSONObject obj = new JSONObject();
        JSONObject content = new JSONObject();
        obj.put("id", id);
        content.put("input matrix", matrix);
        if (!Objects.equals(log, "\n")) content.put("solving", log);
        content.put("result", result);
        obj.put("content", content);
        try (FileWriter out = new FileWriter("json_files\\logs.json")) {
            out.write(obj.toString());
        }

    }
}
