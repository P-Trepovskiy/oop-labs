package com.lab4.demo.presentation;

import com.lab4.demo.data_access.Logs_reader;
import com.lab4.demo.service.Math_model;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = "/main")
public class Endpoints {
    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public String return_logs() throws IOException {
        return Logs_reader.read();
    }

    @RequestMapping(value = "/startcalc")
    public static String start_calculations(@RequestBody String json_file) throws IOException {
        JSONObject request_body = new JSONObject(json_file);
        try (FileWriter out = new FileWriter("json_files\\input.json")) {
            out.write(request_body.toString());
        }
        try {
            Math_model.calculate();
            String str = new String(Files.readAllBytes(Paths.get("json_files\\input.json")));
            JSONObject json = new JSONObject(str);
            return "calculation started with matrix = " + json.get("matrix")
                    + ". Results only: " + json.getBoolean("result_only");
        }
        catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
