package console.inputResult;

import java.util.concurrent.ConcurrentSkipListMap;

public class InputResultMatrixOp extends InputResult {
    // 0 - * n k
    // 1 - / n k
    // 2 - + n k multiplier
    // 3 - - n k multiplier
    // 4 - swap n k
    // 5 - m1 + m2
    // 6 - m1 * m2
    private ConcurrentSkipListMap<String, Integer> params;

    public InputResultMatrixOp(boolean cont, ConcurrentSkipListMap<String, Integer> params, InputResult.Types type) {
        super(cont);
        this.params = params;
        this.type = type;
    }

    public ConcurrentSkipListMap<String, Integer> getParams() { return params; }
}
