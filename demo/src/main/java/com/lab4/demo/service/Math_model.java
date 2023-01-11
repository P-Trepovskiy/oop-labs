package com.lab4.demo.service;

import com.lab4.demo.data_access.Logs_writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public abstract class Math_model {
    public static final Logger logger = LoggerFactory.getLogger(Math_model.class);
    static void getCofactor(List<List<Double>> mat, List<List<Double>> temp,
                            int p, int q, int n)
    {
        int i = 0, j = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp.get(i).set(j++, mat.get(row).get(col));

                    if (j == mat.size() - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }
    static double determinantOfMatrix(List<List<Double>> mat, int n)
    {
        double D = 0;

        if (n == 1)
            return mat.get(0).get(0);

        List<List<Double>> temp = new ArrayList<>();
        for (int i = 0; i < mat.size(); i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < mat.size(); j++) {
                row.add(0.0);
            }
            temp.add(row);
        }

        int sign = 1;

        for (int f = 0; f < n; f++) {
            getCofactor(mat, temp, 0, f, n);
            D += sign * mat.get(0).get(f)
                    * determinantOfMatrix(temp, n - 1);
            sign = -sign;
        }

        return D;
    }
    static List<List<Double>> angleCofactor(List<List<Double>> mat, int n) {
        List<List<Double>> ret = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(mat.get(i).get(j));
            }
            ret.add(row);
        }
        return ret;
    }
    public static void calculate() throws Exception {
        Matrix mat = Parse_json.input();
        List<Double> determinants = new ArrayList<>();
        for (int i = 1; i <= mat.getMatrix().size(); i++) {
            determinants.add(determinantOfMatrix(angleCofactor(mat.getMatrix(), i), i));
        }
        boolean is_pos = true;
        boolean is_neg = determinants.get(0) <= 0;
        StringBuilder log = new StringBuilder();
        if (!mat.isResult_only()) log.append("Determinants:");
        for (Double determinant : determinants) {
            if (!mat.isResult_only()) log.append(" det").
                    append(determinants.indexOf(determinant) + 1).
                    append(" = ").append(determinant);
        }
        log.append("\n");
        for (Double determinant : determinants) {
            if (determinant < 0) {
                if (!mat.isResult_only()) log.append(" det").
                        append(determinants.indexOf(determinant) + 1).
                        append(" is less than zero, so form is not positive.")
                        .append("\n");
                is_pos = false;
                break;
            }
        }
        if (!is_pos && is_neg) {
            for (int i = 1; i < determinants.size(); i++) {
                if (i % 2 == 0 && determinants.get(i) < 0) {
                    if (!mat.isResult_only()) log.
                            append("det of odd index ")
                            .append(i)
                            .append(" is less than zero, so form is not negative.")
                            .append("\n");
                    is_neg = false;
                    break;
                }
            }
        }
        String result = "";
        if (is_pos && !is_neg) {
            if (!mat.isResult_only()) log.append(". None of the determinants is less than zero, so form is positive.")
                    .append("\n");
            result = "Positive. ";
        }
        if (is_neg && !is_pos) {
            if (!mat.isResult_only()) log.append("Odd index determinants are positive numbers, " +
                    "even index determinants are negative numbers, so form is negative.");
            result = "Negative. ";
        }
        if (!is_neg && !is_pos) {
            if (!mat.isResult_only()) log.append("Form is neither positive or negative, so it is alternating.").
                    append("\n");
            result = "Altering. ";
        }
        if (is_neg && is_pos) {
            if (!mat.isResult_only()) log.append("All of the determinants are equal to zero, so form is undefined");
            result = "Undefined. ";
        }
        Logs_writer.write_log(log.toString(), mat.getMatrix(), UUID.randomUUID().toString(), result);
        logger.info("logs written successfully, check logs.json for info.");
    }
}
