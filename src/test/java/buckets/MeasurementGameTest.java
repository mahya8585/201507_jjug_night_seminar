package buckets;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static buckets.TestUtil.*;

/**
* 計量ゲームの実行クラスのテスト
*/
public class MeasurementGameTest {
    private MeasurementGame measurementGame;
    private TestUtil util;

    @Before
    public void initialize() throws Exception {
        util = new TestUtil();
    }


    /**
     * 本当は値チェックのテストはymlとかでデータセットもたせた方がかっこいい
     * が、今回はテストには気合いれてないのでそのような運用考慮はまた今度・・・
     */

    @Test
    public void 初期値の設定が正しく行われること() {
        measurementGame = new MeasurementGame();

        measurementGame.makeDefaultValue();

        assertThat(measurementGame.smallBucket.getCapacity(), is(Const.SMALL_BUCKET_CAPACITY));
        assertThat(measurementGame.smallBucket.getAmount(), is(0));

        assertThat(measurementGame.largeBucket.getCapacity(), is(Const.LARGE_BUCKET_CAPACITY));
        assertThat(measurementGame.largeBucket.getAmount(), is(0));

        assertFalse(measurementGame.queue.isEmpty());
        assertTrue(measurementGame.judgmentMap[0][0]);
    }

    @Test
    public void LARGE_EMPTYをパラメータに設定した場合返却ステータスのlargeBucket容量が0になっていること() {
        measurementGame = new MeasurementGame();

        //リクエスト値の作成
        Status requestParam = createStatusAmounts(1, 5, null);

        //実行
        Status result = measurementGame.executeBucketAction(Const.BucketActions.LARGE_EMPTY, requestParam);

        assertThat(result.getLargeBucketAmount(), is(0));
        assertThat(result.getSmallBucketAmount(), is(1));
    }

    @Test
    public void LARGE_FULLINをパラメータに設定した場合返却ステータスのlargeBucket容量が満タンになっていること() {
        measurementGame = new MeasurementGame();

        //リクエスト値の作成
        Status requestParam = createStatusAmounts(2, 4, null);

        //実行
        Status result = measurementGame.executeBucketAction(Const.BucketActions.LARGE_FULLIN, requestParam);

        assertThat(result.getLargeBucketAmount(), is(Const.LARGE_BUCKET_CAPACITY));
        assertThat(result.getSmallBucketAmount(), is(2));
    }

    @Test
    public void LARGE_MOVEをパラメータに設定した場合返却ステータスのsmallBucketの空容量分largeBucket容量が減っていること() {
        measurementGame = new MeasurementGame();

        //リクエスト値の作成
        Status requestParam = createStatusAmounts(0, 2, null);

        //実行
        Status result = measurementGame.executeBucketAction(Const.BucketActions.LARGE_MOVE, requestParam);

        //MOVEの処理そのものの分岐テストはBucketTestクラスで実施。ここでは本クラス内の分岐のテストのみ実施
        assertThat(result.getLargeBucketAmount(), is(0));
        assertThat(result.getSmallBucketAmount(), is(2));
    }

    @Test
    public void SMALL_EMPTYをパラメータに設定した場合返却ステータスのsmallBucket容量が0になっていること() {
        measurementGame = new MeasurementGame();

        //リクエスト値の作成
        Status requestParam = createStatusAmounts(2, 3, null);

        //実行
        Status result = measurementGame.executeBucketAction(Const.BucketActions.SMALL_EMPTY, requestParam);

        assertThat(result.getLargeBucketAmount(), is(3));
        assertThat(result.getSmallBucketAmount(), is(0));
    }

    @Test
    public void SMALL_FULLINをパラメータに設定した場合返却ステータスのsmallBucket容量が満タンになっていること() {
        measurementGame = new MeasurementGame();

        //リクエスト値の作成
        Status requestParam = createStatusAmounts(1, 5, null);

        //実行
        Status result = measurementGame.executeBucketAction(Const.BucketActions.SMALL_FULLIN, requestParam);

        assertThat(result.getLargeBucketAmount(), is(5));
        assertThat(result.getSmallBucketAmount(), is(Const.SMALL_BUCKET_CAPACITY));
    }

    @Test
    public void SMALL_MOVEをパラメータに設定した場合返却ステータスのlargeBucketの空容量分smallBucket容量が減っていること() {
        measurementGame = new MeasurementGame();

        //リクエスト値の作成
        Status requestParam = createStatusAmounts(2, 3, null);

        //実行
        Status result = measurementGame.executeBucketAction(Const.BucketActions.SMALL_MOVE, requestParam);

        assertThat(result.getLargeBucketAmount(), is(5));
        assertThat(result.getSmallBucketAmount(), is(0));
    }

    @Test
    public void 初期ステータスを保持したデータをパラメータに設定した場合queueに2件登録がされていること() {
        measurementGame = new MeasurementGame();

        measurementGame.makeDefaultValue();
        assertThat(measurementGame.queue.size(), is(1));
        Status result = measurementGame.find(measurementGame.queue.poll());

        assertThat(measurementGame.queue.size(), is(2));

        Status queueStatus = measurementGame.queue.poll();
        assertNotNull(queueStatus.getProcessText());
        assertThat(queueStatus.getSmallBucketAmount(), is(Const.SMALL_BUCKET_CAPACITY));
        assertThat(queueStatus.getLargeBucketAmount(), is(0));

        queueStatus = measurementGame.queue.poll();
        assertNotNull(queueStatus.getProcessText());
        assertThat(queueStatus.getSmallBucketAmount(), is(0));
        assertThat(queueStatus.getLargeBucketAmount(), is(Const.LARGE_BUCKET_CAPACITY));

        assertTrue(measurementGame.judgmentMap[Const.SMALL_BUCKET_CAPACITY][0]);
        assertTrue(measurementGame.judgmentMap[0][Const.LARGE_BUCKET_CAPACITY]);
    }
    @Test
    public void 計量に成功したパターンが発生した場合ステータス情報が返却されること() {
        measurementGame = new MeasurementGame();

        Status param = new Status();
        param.setSmallBucketAmount(0);
        param.setLargeBucketAmount(1);

        Status result = measurementGame.find(param);
        assertNotNull(result);
    }
    @Test
    public void 計量に成功したパターンが存在しなかった場合nullが返却されること() {
        measurementGame = new MeasurementGame();

        Status param = new Status();
        param.setSmallBucketAmount(0);
        param.setLargeBucketAmount(0);

        Status result = measurementGame.find(param);
        assertNull(result);
    }









}

