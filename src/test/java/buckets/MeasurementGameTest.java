package buckets;

import buckets.MeasurementGame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
* 計量ゲームの実行クラスのテスト
*/
@RunWith(Enclosed.class)

class MeasurementGameTest {
    private MeasurementGame measurementGame;

    @Before
    public void initialize() throws Exception {
        measurementGame = new MeasurementGame();
    }


    @Test
    public void ユーザが正しい値を入力した場合入力した数値が返却されること(){
        inputStream.inputString("1");
        assertThat(highAndLow.inputUserAnswer(), is(1));
    }

}

