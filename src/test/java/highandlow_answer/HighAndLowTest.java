package highandlow_answer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * HighAndLowクラスのテスト
 */
@RunWith(Enclosed.class)
public class HighAndLowTest {

    public static class GameControllerTest {

        private final HighAndLow.Game game = mock(HighAndLow.Game.class);

        private final HighAndLow.GameController sut = new HighAndLow.GameController(game);

        private final ExtendInputStream inputStream = new ExtendInputStream();

        @Before
        public void setUp() throws Exception {
            System.setIn(inputStream);
        }

        @After
        public void tearDown() {
            System.setIn(null);
        }

        @Test
        public void ユーザが正しい値を入力した場合入力した数値が返却されること() {
            inputStream.inputString("1");

            sut.inputUserAnswer();

            assertThat(sut.getAnswer(), is(Optional.of(HighAndLow.Answer.HIGH)));
        }

        @Test
        public void ユーザが空文字を入力した場合もう一度入力を求められること() {
            inputStream.inputString("");
            try {
                sut.inputUserAnswer();
            } catch (NullPointerException nullPoint) {
                assertTrue(true);
            }
            assertFalse(false);
        }

        @Test
        public void ユーザが数値以外の値を入力した場合もう一度入力を求められること() {
            inputStream.inputString("あああ");
            try {
                sut.inputUserAnswer();
            } catch (NullPointerException nullPoint) {
                assertTrue(true);
            }
            assertFalse(false);
        }

        @Test
        public void ユーザが想定外の数値を入力した場合もう一度入力を求められること() {
            inputStream.inputString("3");
            try {
                sut.inputUserAnswer();
            } catch (NullPointerException nullPoint) {
                assertTrue(true);
            }
            assertFalse(false);
        }
    }

    public static class GameTest {
        @Test
        public void ユーザが正解していた場合WINが返却されること() {
            final HighAndLow.Game sut = new HighAndLow.Game(1, 2);
            final HighAndLow.Result result = sut.go(HighAndLow.Answer.HIGH);
            assertThat(result, is(HighAndLow.Result.WIN));
        }

        @Test
        public void ユーザが不正解の場合LOSEが返却されること() {
            final HighAndLow.Game sut = new HighAndLow.Game(3, 2);
            final HighAndLow.Result result = sut.go(HighAndLow.Answer.HIGH);
            assertThat(result, is(HighAndLow.Result.LOSE));
        }

        @Test
        public void 引き分けだった場合3が返却されること() {
            final HighAndLow.Game sut = new HighAndLow.Game(3, 3);
            final HighAndLow.Result result = sut.go(HighAndLow.Answer.HIGH);
            assertThat(result, is(HighAndLow.Result.DRAW));
        }

    }

    public static class HigAndLowTest {
        /**
         * makeRandomNumber()メソッドの乱数分布テスト
         */
        @Test
        public void ランダム出力した数値の分布を確認する() {
            int runCount = 1000;

            Map<Integer, Integer> resultNumbers = new HashMap<>();
            for (int roopCount = 0; roopCount < runCount; roopCount++) {
                int result = HighAndLow.makeRandomNumber();

                //すでに集計Map内にキーが存在する場合は集計値を+1する
                Integer totaling = 0;
                if (resultNumbers.containsKey(result)) {
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
}

