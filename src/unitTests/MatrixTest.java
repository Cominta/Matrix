package unitTests;

import console.CInput;
import console.InputHandler;
import console.inputResult.InputResult;
import matrix.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class MatrixTest {
    final int matrixCountZ = 4;

    @DisplayName("Test line sum (Z numbers)")
    @Test
    public void testLineSumZ() throws FileNotFoundException, CloneNotSupportedException {
        final int countOfResults = 1;
        ArrayList<Matrix> matrixs = new ArrayList<>();
        ArrayList<Matrix> matrixResults = new ArrayList<>();

        for (int z = 0; z <= matrixCountZ; z++) {
            matrixs.add(CInput.readNewMatrixFromFile("Z/m" + z + ".txt").getMatrix());
        }

        for (int z = 1; z <= matrixCountZ; z++) {
            matrixResults.add(CInput.readNewMatrixFromFile("testLineSumZ/result" + z + ".txt").getMatrix());
        }

        CInput.stream = new FileInputStream("testLineSumZ/commands.txt");
        InputResult result = new InputResult(true);
        int currentIndex = -1;
        Matrix currentMatrix = null;

        while (result.isCont()) {
            result = CInput.readCommand();

            //InputHandler.handleResult(result, matrixs, currentIndex, currentMatrix);
        }
    }
}
