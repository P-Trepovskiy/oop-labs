import java.util.Arrays;
import java.util.Objects;

public class Matrix implements matrix_interface {
    private final int rows_size;
    private final int columns_size;
    private double[][] matrix;

    public Matrix(int m, int n) {
        rows_size = m;
        columns_size = n;
        matrix = new double[rows_size][columns_size];
    }

    public int getRows_size() {
        return rows_size;
    }

    public int getColumns_size() {
        return columns_size;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public int get_size() {
        return columns_size * rows_size;
    }

    public Matrix zero_matrix() {
        for(int i = 0; i < columns_size; i++) {
            for(int j = 0; j < columns_size; j++) {
                matrix[i][j] = 0;
            }
        }

        return this;
    }

    public Matrix copy(Matrix matrix_to_copy) {
        Matrix ret = new Matrix(matrix_to_copy.rows_size, matrix_to_copy.columns_size);
        ret.matrix = matrix_to_copy.matrix.clone();
        return ret;
    }

    public Matrix fill(double[][] values) throws Exception {
        Matrix ret = new Matrix(rows_size, columns_size);
        if (rows_size * columns_size != values.length * values[0].length) {
            throw new Exception("Not enough values to fill");
        } else {
            ret.matrix = values.clone();
            return ret;
        }
    }

    public double get_el(int row, int column) throws Exception {
        try {
            return matrix[row][column];
        } catch (Exception ex) {
            throw new Exception("Index out of bounds");
        }
    }

    public double[] get_col(int column) throws Exception {
        try {
            double[] ret_value = new double[columns_size];
            for(int i = 0; i < columns_size; i++) {
                ret_value[i] = matrix[i][column];
            }
            return ret_value;
        } catch (Exception ex) {
            throw new Exception("Index out of bounds");
        }
    }

    public double[] get_row(int row) throws Exception {
        try {
            return matrix[row];
        } catch (Exception ex) {
            throw new Exception("Index out of bounds");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Matrix matrix1)) {
            return false;
        } else {
            return getRows_size() == matrix1.getRows_size() &&
                    getColumns_size() == matrix1.getColumns_size() &&
                    Arrays.deepEquals(getMatrix(), matrix1.getMatrix());
        }
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getRows_size(), getColumns_size());
        result = 31 * result + Arrays.deepHashCode(getMatrix());
        return result;
    }

    public Matrix multiply(Matrix mat1) throws Exception {
        if (mat1.columns_size != rows_size) {
            throw new Exception("Cannot multiply Inconsistent matrix.");
        } else {
            Matrix result = (new Matrix(mat1.rows_size, columns_size)).zero_matrix();

            for(int i = 0; i < rows_size; i++) {
                for(int j = 0; j < rows_size; j++) {
                    result.matrix[i][j] = 0.0;

                    for(int k = 0; k < rows_size; k++) {
                        double[] mat = result.matrix[i];
                        mat[j] += matrix[i][k] * mat1.matrix[k][j];
                    }
                }
            }

            return result;
        }
    }
}

interface matrix_interface {
    int getRows_size();

    int getColumns_size();

    double[][] getMatrix();

    Matrix zero_matrix();

    static Matrix unit_matrix(int rows_size, int columns_size) throws Exception {
        double[][] mat = new double[rows_size][columns_size];
        for (double[] doubles : mat) {
            Arrays.fill(doubles, 1.0);
        }
        return (new Matrix(rows_size, columns_size)).fill(mat);
    }

    Matrix copy(Matrix matrix_to_copy);

    Matrix fill(double[][] values) throws Exception;

    double get_el(int row, int col) throws Exception;

    double[] get_col(int col) throws Exception;

    double[] get_row(int row) throws Exception;

    boolean equals(Object mat);

    int hashCode();

    Matrix multiply(Matrix mat1) throws Exception;
}