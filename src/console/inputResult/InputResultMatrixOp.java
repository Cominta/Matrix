package console.inputResult;

import java.util.concurrent.ConcurrentSkipListMap;

public class InputResultMatrixOp extends InputResult {
    // 0 - * n k
    // 1 - / n k
    // 2 - + n k multiplier
    // 3 - - n k multiplier
    // 4 - swap n k
    private ConcurrentSkipListMap<String, Integer> params;

    public InputResultMatrixOp(boolean cont, ConcurrentSkipListMap<String, Integer> params) {
        super(cont);
        this.params = params;
        this.type = InputResult.Types.MATRIX_OP;
    }

    public ConcurrentSkipListMap<String, Integer> getParams() { return params; }
}
