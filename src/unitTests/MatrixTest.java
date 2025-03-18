package unitTests;

import console.CInput;
import matrix.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MatrixTest {
    final int matrixCountZ = 4;

    @DisplayName("Test line sum (Z numbers)")
    @Test
    public void testLineSumZ() {
        ArrayList<Matrix> matrixs = new ArrayList<>();

        for (int z = 0; z <= matrixCountZ; z++) {
            matrixs.add(CInput.readNewMatrix().getMatrix());
        }

        
    }
}
