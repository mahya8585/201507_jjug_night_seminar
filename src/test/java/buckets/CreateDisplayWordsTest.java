package buckets;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
* 計量ゲームの実行クラスのテスト
*/
public class CreateDisplayWordsTest {
    private CreateDisplayWords createDisplayWords;
    private TestUtil util;


    @Before
    public void initialize() throws Exception {
        createDisplayWords = new CreateDisplayWords();
        util = new TestUtil();
    }

    //表示させるだけのテスト(実装時の確認用コンソール目視テスト)
    @Test
    public void 初期値の設定が正しく表示されること() {
        System.out.println("★初期値の設定が正しく表示されること↓");
        createDisplayWords.displayStart();
    }

    //表示させるだけのテスト(実装時の確認用コンソール目視テスト)
    @Test
    public void 正しい順番で文字列の表示が行われること(){
        System.out.println("★正しい順番で文字列の表示が行われること↓");
        //順番通りに表示されたらOK
        Status testData = new Status();

        //数値だけ表示されます
        List<Status> statusList = new ArrayList<>();
        statusList.add(util.createStatusAmounts(3, 0, Const.BucketActions.SMALL_FULLIN));
        statusList.add(util.createStatusAmounts(0, 3, Const.BucketActions.SMALL_MOVE));
        statusList.add(util.createStatusAmounts(3, 0, Const.BucketActions.LARGE_MOVE));
        statusList.add(util.createStatusAmounts(3, 8, Const.BucketActions.LARGE_FULLIN));
        statusList.add(util.createStatusAmounts(0, 8, Const.BucketActions.SMALL_EMPTY));
        statusList.add(util.createStatusAmounts(0, 0, Const.BucketActions.LARGE_EMPTY));
        testData.setHistory(statusList);

        createDisplayWords.displayAnswerProcess(testData);

    }

    //表示させるだけのテスト
    @Test
    public void 解のないステータスが設定された場合の文字列が正しく表示されること() {
        System.out.println("★解のないステータスが設定された場合の文字列が正しく表示されること↓");
        createDisplayWords.displayAnswerProcess(null);
    }

    @Test
    public void 手順名nullの場合にnullが返却されること(){
        String result = createDisplayWords.createProcessText(null, new Status());
        assertNull(result);
    }

    @Test
    public void 手順名LARGE_EMPTYの場合に指定文言が返却されること(){
        Status status = util.createStatusAmounts(1,1, null);
        String result = createDisplayWords.createProcessText(Const.BucketActions.LARGE_EMPTY, status);

        String expectation = util.createActionText(Const.BucketActions.LARGE_EMPTY) + " : (1 , 1)";
        assertThat(result, is(expectation));
    }

    @Test
    public void 手順名LARGE_FULLINの場合に指定文言が返却されること(){
        Status status = util.createStatusAmounts(1,1, null);
        String result = createDisplayWords.createProcessText(Const.BucketActions.LARGE_FULLIN, status);

        String expectation = util.createActionText(Const.BucketActions.LARGE_FULLIN) + " : (1 , 1)";
        assertThat(result, is(expectation));
    }

    @Test
    public void 手順名LARGE_MOVEの場合に指定文言が返却されること(){
        Status status = util.createStatusAmounts(1,1, null);
        String result = createDisplayWords.createProcessText(Const.BucketActions.LARGE_MOVE, status);

        String expectation = util.createActionText(Const.BucketActions.LARGE_MOVE) + " : (1 , 1)";
        assertThat(result, is(expectation));
    }

    @Test
    public void 手順名SMALL_EMPTYの場合に指定文言が返却されること(){
        Status status = util.createStatusAmounts(1,1, null);
        String result = createDisplayWords.createProcessText(Const.BucketActions.SMALL_EMPTY, status);

        String expectation = util.createActionText(Const.BucketActions.SMALL_EMPTY) + " : (1 , 1)";
        assertThat(result, is(expectation));
    }

    @Test
    public void 手順名SMALL_FULLINの場合に指定文言が返却されること(){
        Status status = util.createStatusAmounts(1,1, null);
        String result = createDisplayWords.createProcessText(Const.BucketActions.SMALL_FULLIN, status);

        String expectation = util.createActionText(Const.BucketActions.SMALL_FULLIN) + " : (1 , 1)";
        assertThat(result, is(expectation));
    }

    @Test
    public void 手順名SMALL_MOVEの場合に指定文言が返却されること(){
        Status status = util.createStatusAmounts(1,1, null);
        String result = createDisplayWords.createProcessText(Const.BucketActions.SMALL_MOVE, status);

        String expectation = util.createActionText(Const.BucketActions.SMALL_MOVE) + " : (1 , 1)";
        assertThat(result, is(expectation));
    }
}

