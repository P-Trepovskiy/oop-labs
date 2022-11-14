import java.util.Arrays;
import java.util.Objects;

public final class Matrix_imm implements matrix_imm_interface {
    private final int rows_size;
    private final int columns_size;
    private final double[][] matrix;

    public Matrix_imm(int m, int n, double[][] mat) throws Exception {
        rows_size = m;
        columns_size = n;
        if (mat.length * mat[0].length == m * n) {
            matrix = mat;
        } else {
            throw new Exception("Failed to construct matrix object.");
        }
    }

    public int getRows_size() {
        return rows_size;
    }

    public int getColumns_size() {
        return columns_size;
    }

    public double[][] getMatrix() {
        double[][] ret = new double[rows_size][columns_size];

        for(int i = 0; i < rows_size; i++) {
            System.arraycopy(matrix[i], 0, ret[i], 0, columns_size);
        }

        return ret;
    }

    public int get_size() {
        return columns_size * rows_size;
    }

    public Matrix_imm zero_matrix() {
        for(int i = 0; i < columns_size; i++) {
            for(int j = 0; j < columns_size; j++) {
                matrix[i][j] = 0.0;
            }
        }

        return this;
    }

    public Matrix_imm copy(Matrix_imm matrix_to_copy) throws Exception {
        try {
            return new Matrix_imm(matrix_to_copy.rows_size, matrix_to_copy.columns_size, matrix_to_copy.matrix);
        } catch (Exception ex) {
            throw new Exception("Copied matrix doesn't exist");
        }
    }

    public Matrix_imm fill(double[][] values) throws Exception {
        if (rows_size * columns_size != values.length * values[1].length) {
            throw new Exception("Not enough values to fill");
        } else {
            try {
                return new Matrix_imm(rows_size, columns_size, values);
            } catch (Exception ex) {
                throw new Exception("Cannot fill values.");
            }
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

    public Matrix_imm multiply(Matrix_imm mat1) throws Exception {
        if (getColumns_size() != mat1.getRows_size()) {
            throw new Exception("Cannot multiply Inconsistent matrix.");
        } else {
            double[][] mat = new double[getRows_size()][mat1.getColumns_size()];
            for(int i = 0; i < rows_size; i++) {
                for(int j = 0; j < rows_size; j++) {
                    mat[i][j] = 0;
                    for(int k = 0; k < rows_size; k++) {
                        mat[i][j] += matrix[i][k] * mat1.getMatrix()[k][j];
                    }
                }
            }
            return new Matrix_imm(getRows_size(), mat1.getColumns_size(), mat);
        }
    }
}

interface matrix_imm_interface {
    int getRows_size();

    int getColumns_size();

    double[][] getMatrix();

    Matrix_imm zero_matrix();

    static Matrix_imm unit_matrix(int rows_size, int columns_size) throws Exception {
        double[][] mat = new double[rows_size][columns_size];

        for (double[] doubles : mat) {
            Arrays.fill(doubles, 1.0);
        }

        return new Matrix_imm(rows_size, columns_size, mat);
    }

    Matrix_imm copy(Matrix_imm matrix_to_copy) throws Exception;

    Matrix_imm fill(double[][] values) throws Exception;

    double get_el(int row, int col) throws Exception;

    double[] get_col(int col) throws Exception;

    double[] get_row(int row) throws Exception;

    boolean equals(Object mat);

    int hashCode();
}