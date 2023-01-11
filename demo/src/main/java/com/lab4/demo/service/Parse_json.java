package com.lab4.demo.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Matrix {
    private final boolean result_only;
    private final List<List<Double>> matrix;

    Matrix(boolean result_only, List<List<Double>> matrix) {
        this.result_only = result_only;
        this.matrix = matrix;
    }

    public boolean isResult_only() {
        return result_only;
    }

    public List<List<Double>> getMatrix() {
        return matrix;
    }
}

@Service
public abstract class Parse_json {
    public static final Logger logger = LoggerFactory.getLogger(Parse_json.class);
    public static Matrix input() throws IOException {
        JsonParser parser = new JsonParser() {
            @Override
            public Map<String, Object> parseMap(String json) throws JsonParseException {
                return null;
            }

            @Override
            public List<Object> parseList(String json) throws JsonParseException {
                StringBuilder new_json = new StringBuilder(json.replaceAll("]", "").
                        replaceAll("\\[", "").replaceAll(" ", ""));

                List<List<Double>> matrix = new ArrayList<>();
                try {
                    ArrayList<String> matrix_arr = new ArrayList<>(List.of(new_json.toString().split(",")));
                    int matrix_len = (int) Math.sqrt(matrix_arr.size());
                    for (int i = 0; i < matrix_len; i++) {
                        ArrayList<Double> row = new ArrayList<>();
                        for (int j = 0; j < matrix_len; j++) {
                            row.add(Double.parseDouble(matrix_arr.get(j)));
                        }
                        matrix.add(row);
                        matrix_arr = new ArrayList<>(matrix_arr.subList(matrix_len, matrix_arr.size()));
                    }
                    try {
                        ArrayList<ArrayList<Double>> transposed = new ArrayList<>();
                        for (int i = 0; i < matrix_len; i++) {
                            ArrayList<Double> row = new ArrayList<>();
                            for (int j = 0; j < matrix_len; j++) {
                                row.add(matrix.get(j).get(i));
                            }
                            transposed.add(row);
                        }
                        if (!matrix.equals(transposed)) {
                            throw new Exception();
                        }
                    }
                    catch (Exception e) {
                        throw new Exception("Given matrix is not quadratic form (means not symmetric). " +
                                "Please change input data in json_files\\input.json and restart the program.");
                    }
                    return Collections.singletonList(matrix);
                }
                catch (Exception ex) {
                    try {
                        throw new Exception(ex.getMessage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        String str = new String(Files.readAllBytes(Paths.get("json_files\\input.json")));
        JSONObject json = new JSONObject(str);
        List<Object> obj = parser.parseList(json.get("matrix").toString());
        ArrayList<Object> ret = new ArrayList<>(obj);
        logger.info("JSON Parsed successfully, matrix={}", ret.get(0));
        return new Matrix(json.getBoolean("result_only"), (List<List<Double>>) ret.get(0));
    }

    public static String logs(JSONObject object) {
        StringBuilder ret = new StringBuilder();
        JSONObject cont = new JSONObject(object.get("content").toString());
        ret.append("Input matrix: ").append(cont.get("input matrix").toString()).append("\n");
        ret.append("Result: ").append(cont.get("result")).append("\n");
        try {
            ret.append("Solving: ").append(cont.get("solving")).append("\n");
        }
        catch (Exception ignored){
            ret = new StringBuilder(ret.substring(0, ret.length() - 10));
        }
        return ret.toString();
    }
}