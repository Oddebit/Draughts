package utils.nn;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Getter
public class Matrix {
    private double[][] data;
    private final int rows;
    private final int cols;

    public Matrix(int rows, int cols) {
        this.data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                this.data[i][j] = (new Random()).nextDouble() * 2.0D - 1.0D;
            }
        }
    }

    public void print() {
        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < this.cols; ++j) {
                System.out.print(this.data[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void add(int term) {
        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < this.cols; ++j) {
                double[] var10000 = this.data[i];
                var10000[j] += term;
            }
        }
    }

    public void add(Matrix m) {
        if (this.cols == m.cols && this.rows == m.rows) {
            for(int i = 0; i < this.rows; ++i) {
                for(int j = 0; j < this.cols; ++j) {
                    double[] var10000 = this.data[i];
                    var10000[j] += m.data[i][j];
                }
            }
        } else {
            System.out.println("Shape Mismatch");
        }
    }

    public static Matrix fromArray(double[][] data) {
        List<double[]> dataList = new LinkedList<>();
        int len = 0;
        for (double[] datum : data) {
            dataList.add(datum.clone());
            if (len < datum.length) len = datum.length;
        }

        Matrix matrix = new Matrix(data.length, len);
        matrix.data = dataList.toArray(new double[0][]);
        return matrix;
    }

    public static Matrix fromArray(double[] array) {
        Matrix matrix = new Matrix(array.length, 1);
        for(int i = 0; i < array.length; ++i) {
            matrix.data[i][0] = array[i];
        }
        return matrix;
    }

    public Double[] toArray() {
        List<Double> list = new ArrayList<>();
        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < this.cols; ++j) {
                list.add(this.data[i][j]);
            }
        }
        return list.toArray(new Double[0]);
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        Matrix matrix = new Matrix(a.rows, a.cols);
        for(int i = 0; i < a.rows; ++i) {
            for(int j = 0; j < a.cols; ++j) {
                matrix.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }
        return matrix;
    }

    public static Matrix transpose(Matrix a) {
        Matrix matrix = new Matrix(a.cols, a.rows);
        for(int i = 0; i < a.rows; ++i) {
            for(int j = 0; j < a.cols; ++j) {
                matrix.data[j][i] = a.data[i][j];
            }
        }
        return matrix;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix matrix = new Matrix(a.rows, b.cols);
        for(int i = 0; i < matrix.rows; ++i) {
            for(int j = 0; j < matrix.cols; ++j) {
                double sum = 0.0;
                for(int k = 0; k < a.cols; ++k) {
                    sum += a.data[i][k] * b.data[k][j];
                }
                matrix.data[i][j] = sum;
            }
        }
        return matrix;
    }

    public void multiply(Matrix a) {
        for(int i = 0; i < a.rows; ++i) {
            for(int j = 0; j < a.cols; ++j) {
                double[] var10000 = this.data[i];
                var10000[j] *= a.data[i][j];
            }
        }
    }

    public void multiply(double a) {
        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < this.cols; ++j) {
                double[] var10000 = this.data[i];
                var10000[j] *= a;
            }
        }
    }

    public void sigmoid() {
        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < this.cols; ++j) {
                this.data[i][j] = 1.0D / (1.0D + Math.exp(-this.data[i][j]));
            }
        }
    }

    public Matrix dSigmoid() {
        Matrix matrix = new Matrix(this.rows, this.cols);
        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0; j < this.cols; ++j) {
                matrix.data[i][j] = this.data[i][j] * (1.0D - this.data[i][j]);
            }
        }
        return matrix;
    }
}
