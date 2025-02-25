package console.inputResult;

import java.util.concurrent.ConcurrentSkipListMap;

public class InputResultOp extends InputResult {
    private ConcurrentSkipListMap<String, String> params;

    public InputResultOp(boolean cont, ConcurrentSkipListMap<String, String> params, InputResult.Types type) {
        super(cont);
        this.params = params;
        this.type = type;
    }

    public ConcurrentSkipListMap<String, String> getParams() { return params; }
}

