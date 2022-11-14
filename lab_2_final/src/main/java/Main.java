import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        Matrix mat = matrix_interface.unit_matrix(3, 3);
        Matrix mat2 = new Matrix(3, 3).fill(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        System.out.println(Arrays.deepToString(mat.multiply(mat2).getMatrix()));
    }
}
