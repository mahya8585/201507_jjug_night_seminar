package buckets;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
* 計量ゲームの実行クラスのテスト
*/
public class CreateDisplayWordsTest {
    private CreateDisplayWords createDisplayWords;


    @Before
    public void initialize() throws Exception {
        createDisplayWords = new CreateDisplayWords();
    }

    //表示させるだけのテスト(実装時の確認用コンソール目視テスト)
    @Test
    public void 初期値の設定が正しく表示されること() {
        createDisplayWords.writeStart();
    }

    //表示させるだけのテスト(実装時の確認用コンソール目視テスト)
    @Test
    public void 手順分岐を正しく行い文字列の表示が行われること(){
        //順番通りに表示されたらOK
        Status testData = new Status();

        testData.addHistory(createStatusAmounts(3, 0, Const.BucketActions.SMALL_FULLIn));
        testData.addHistory(createStatusAmounts(0, 3, Const.BucketActions.SMALL_MOVE));
        testData.addHistory(createStatusAmounts(3, 0, Const.BucketActions.LARGE_MOVE));
        testData.addHistory(createStatusAmounts(3, 8, Const.BucketActions.LARGE_FULLIN));
        testData.addHistory(createStatusAmounts(0, 8, Const.BucketActions.SMALL_EMPTY));
        testData.addHistory(createStatusAmounts(0, 0, Const.BucketActions.LARGE_EMPTY));

        createDisplayWords.writeAnswerProcess(testData);

    }

    /**
     * Statusオブジェクトの容量データ登録を行うテスト補助メソッド
     * @param smallBucketAmount 小さいバケツの容量
     * @param largeBucketAmount 大きいバケツの容量
     * @param actionName 手順名
     * @return  パラメータが設定されたステータスオブジェクト
     */
    private Status createStatusAmounts(int smallBucketAmount, int largeBucketAmount, Const.BucketActions actionName) {
        Status result = new Status();
        result.setSmallBucketAmount(smallBucketAmount);
        result.setLargeBucketAmount(largeBucketAmount);
        result.setActionName(actionName);

        return result;
    }




}

