package buckets;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
* 計量ゲームの実行クラスのテスト
*/
public class MeasurementGameTest {
    private MeasurementGame measurementGame;

    @Before
    public void initialize() throws Exception {
        measurementGame = new MeasurementGame();
    }

    /**
     * 本当は値チェックのテストはymlとかでデータセットもたせた方がかっこいい
     * が、今回はテストには気合いれてないのでそのような運用考慮はまた今度・・・
     */

    @Test
    public void 初期値の設定が正しく行われること() {
        measurementGame.makeDefaultValue();

        assertThat(measurementGame.smallBucket.getCapacity(), is(Const.SMALL_BUCKET_CAPACITY));
        assertThat(measurementGame.smallBucket.getAmount(), is(0));

        assertThat(measurementGame.largeBucket.getCapacity(), is(Const.LARGE_BUCKET_CAPACITY));
        assertThat(measurementGame.largeBucket.getAmount(), is(0));

        assertThat(measurementGame.queue.size(), is(1));

        Status queue = measurementGame.queue.poll();
        assertThat(queue.getSmallBucketAmount(), is(0));
        assertThat(queue.getLargeBucketAmount(), is(0));
        assertThat(queue.getActionName(), is(nullValue()));

        assertThat(queue.getHistory().size(), is(1));

        Status historyStatus = queue.getHistory().get(0);
        assertThat(historyStatus.getSmallBucketAmount(), is(0));
        assertThat(historyStatus.getLargeBucketAmount(), is(0));
        assertThat(historyStatus.getActionName(), is(nullValue()));
    }

    @Test
    public void ステータスオブジェクトに設定した値が登録されていること() {
        Status result = measurementGame.makeStatus(
                            Const.SMALL_BUCKET_CAPACITY,
                            Const.LARGE_BUCKET_CAPACITY,
                            Const.BucketActions.LARGE_FULLIN,
                            new Status());

        assertThat(result.getSmallBucketAmount(), is(Const.SMALL_BUCKET_CAPACITY));
        assertThat(result.getLargeBucketAmount(), is(Const.LARGE_BUCKET_CAPACITY));
        assertThat(result.getActionName(), is(Const.BucketActions.LARGE_FULLIN));

        assertThat(result.getHistory().size(), is(1));

        Status historyStatus = result.getHistory().get(0);
        assertThat(historyStatus.getSmallBucketAmount(), is(Const.SMALL_BUCKET_CAPACITY));
        assertThat(historyStatus.getLargeBucketAmount(), is(Const.LARGE_BUCKET_CAPACITY));
        assertThat(historyStatus.getActionName(), is(Const.BucketActions.LARGE_FULLIN));
    }

    @Test
    public void ステータスオブジェクトに初期値設定とパラメータ設定値を登録した場合値が正しく登録されていること() {
        measurementGame.makeDefaultValue();
        Status result = measurementGame.makeStatus(
                            3, 6, Const.BucketActions.SMALL_FULLIn, measurementGame.queue.poll()
                        );

        //hisotry以外の値は最新の値が登録されていること
        assertThat(result.getSmallBucketAmount(), is(3));
        assertThat(result.getLargeBucketAmount(), is(6));
        assertThat(result.getActionName(), is(Const.BucketActions.SMALL_FULLIn));

        assertThat(result.getHistory().size(), is(2));

        //1つめにはデフォルト値が設定されていること
        //TODO ここバグってる（うまく値のcloneできてない感じある。3,6,smallfullinが設定されちゃう）
        Status historyStatus = result.getHistory().get(0);
        assertThat(historyStatus.getSmallBucketAmount(), is(0));
        assertThat(historyStatus.getLargeBucketAmount(), is(0));
        assertThat(historyStatus.getActionName(), is(nullValue()));

        //2つめにパラメータで渡した値が設定されていること
        historyStatus = result.getHistory().get(1);
        assertThat(historyStatus.getSmallBucketAmount(), is(3));
        assertThat(historyStatus.getLargeBucketAmount(), is(6));
        assertThat(historyStatus.getActionName(), is(Const.BucketActions.SMALL_FULLIn));
    }

    //TODO executeBucketActionのテストケース作成
    @Test
    public void LARGE_EMPTYをパラメータに設定した場合返却ステータスのlargeBucket容量が0になっていること() {

    }
    @Test
    public void LARGE_FULLINをパラメータに設定した場合返却ステータスのlargeBucket容量が満タンになっていること() {

    }
    @Test
    public void LARGE_MOVEをパラメータに設定した場合返却ステータスのsmallBucketの空容量分largeBucket容量が減っていること() {

    }
    @Test
    public void SMALL_EMPTYをパラメータに設定した場合返却ステータスのsmallBucket容量が0になっていること() {

    }
    @Test
    public void SMALL_FULLINをパラメータに設定した場合返却ステータスのsmallBucket容量が満タンになっていること() {

    }
    @Test
    public void SMALL_MOVEをパラメータに設定した場合返却ステータスのlargeBucketの空容量分smallBucket容量が減っていること() {

    }




    //TODO findのテストケース作成
    @Test
    public void 初期ステータスを保持したデータをパラメータに設定した場合queueに2件登録がされていること() {
        //過去に計算してあるデータはキューに保持されないことを確認する
        //ジャッジマップの値も確認
    }
    @Test
    public void 計量に成功したパターンが発生した場合ステータス情報が返却されること() {

    }
    @Test
    public void 計量に成功したパターンが存在しなかった場合nullが返却されること() {

    }









}

