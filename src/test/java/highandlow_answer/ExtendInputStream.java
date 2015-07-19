package highandlow_answer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by maaya_ishida on 2015/06/17.
 */
class ExtendInputStream extends InputStream {
    private StringBuilder builder = new StringBuilder();

    @Override
    public int read() throws IOException {
        if (builder.length() == 0) {
            return -1;
        }
        int result = builder.charAt(0);
        builder.deleteCharAt(0);
        return result;
    }

    /**
     * 文字列を入力する。
     * @param str 入力文字列
     */
    public void inputString(String str) {
        builder.append(str);
    }
}