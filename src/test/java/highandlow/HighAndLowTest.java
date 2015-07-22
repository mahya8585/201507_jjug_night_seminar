package highandlow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
* HighAndLowクラスのテスト
*/
public class HighAndLowTest{
    private HighAndLow highAndLow;
    private ExtendInputStream inputStream = new ExtendInputStream();

    @Before
    public void initialize() throws Exception {
        highAndLow = new HighAndLow();
        System.setIn(inputStream);
    }

    @After
    public void after(){
        System.setIn(null);
    }

    @Test
    public void ユーザが正しい値を入力した場合入力した数値が返却されること(){
        inputStream.inputString("1");
        assertThat(highAndLow.input_user_answer(), is(1));
    }

    @Test
    public void ユーザが空文字を入力した場合もう一度入力を求められること(){
        inputStream.inputString("");
        try{
            highAndLow.input_user_answer();
        } catch (NullPointerException nullPoint) {
            assertTrue(true);
        }
        assertFalse(false);
    }

    @Test
    public void ユーザが数値以外の値を入力した場合もう一度入力を求められること(){
        inputStream.inputString("あああ");
        try{
            highAndLow.input_user_answer();
        } catch (NullPointerException nullPoint) {
            assertTrue(true);
        }
        assertFalse(false);
    }

    @Test
    public void ユーザが想定外の数値を入力した場合もう一度入力を求められること(){
        inputStream.inputString("3");
        try{
            highAndLow.input_user_answer();
        } catch (NullPointerException nullPoint) {
            assertTrue(true);
        }
        assertFalse(false);
    }

    @Test
    public void ユーザが正解していた場合1が返却されること(){
        int result = highAndLow.compareNumber(9, 10, 1);
        assertThat(result, is(1));

        result = highAndLow.compareNumber(10, 9, 2);
        assertThat(result, is(1));
    }

    @Test
    public void ユーザが不正解の場合2が返却されること(){
        int result = highAndLow.compareNumber(9, 10, 2);
        assertThat(result, is(2));

        result = highAndLow.compareNumber(10, 9, 1);
        assertThat(result, is(2));
    }

    @Test
    public void 引き分けだった場合3が返却されること(){
        int result = highAndLow.compareNumber(1, 1, 1);
        assertThat(result, is(3));

        result = highAndLow.compareNumber(1, 1, 2);
        assertThat(result, is(3));
    }

    /**
     * makeRandomNumber()メソッドの乱数分布テスト
     */
    @Test
    public void ランダム出力した数値の分布を確認する() {
        int runCount = 1000;

        Map<Integer,Integer> resultNumbers = new HashMap<>();
        for (int roopCount = 0; roopCount < runCount; roopCount++) {
            int result = (int) (Math.random() * 10 + 1);

            //すでに集計Map内にキーが存在する場合は集計値を+1する
            Integer totaling = 0;
            if(resultNumbers.containsKey(result)) {
                totaling = resultNumbers.get(result) + 1;
            } else {
                totaling = 1;
            }

            resultNumbers.put(result, totaling);
        }

        //結果表示
        assertThat(resultNumbers.size(), is(10));
        System.out.println(resultNumbers.entrySet());

    }
}

