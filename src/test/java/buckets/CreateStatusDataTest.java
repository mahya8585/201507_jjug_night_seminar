package buckets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static buckets.TestUtil.createStatusAmounts;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
* ステータスデータ作成クラスのテストケース
*/
public class CreateStatusDataTest {
    private CreateStatusData createStatusData;
    private TestUtil util;

    @Before
    public void initialize() throws Exception {
        util = new TestUtil();
    }

    @Test
    public void ステータスオブジェクトに設定した値が登録されていること() {
        createStatusData = new CreateStatusData();

        Status result = createStatusData.makeAmountAndTextInStatus(
                Const.SMALL_BUCKET_CAPACITY,
                Const.LARGE_BUCKET_CAPACITY,
                Const.BucketActions.LARGE_FULLIN,
                new Status());

        assertThat(result.getSmallBucketAmount(), is(Const.SMALL_BUCKET_CAPACITY));
        assertThat(result.getLargeBucketAmount(), is(Const.LARGE_BUCKET_CAPACITY));
        assertNotNull(result.getProcessText());

        assertThat(result.getHistory().size(), is(0));
    }

    @Test
    public void ステータスオブジェクトのhitoryが正しく登録されていること(){
        createStatusData = new CreateStatusData();

        Status param = new Status();
        param.setSmallBucketAmount(Const.SMALL_BUCKET_CAPACITY);
        param.setLargeBucketAmount(Const.LARGE_BUCKET_CAPACITY);
        param.setProcessText("hogehoge");

        Status result = createStatusData.makeHistoryInStatus(param);

        assertThat(result.getSmallBucketAmount(), is(Const.SMALL_BUCKET_CAPACITY));
        assertThat(result.getLargeBucketAmount(), is(Const.LARGE_BUCKET_CAPACITY));
        assertThat(result.getProcessText(), is("hogehoge"));

        assertThat(result.getHistory().size(), is(1));

        Status historyStatus = result.getHistory().get(0);
        assertThat(historyStatus.getSmallBucketAmount(), is(Const.SMALL_BUCKET_CAPACITY));
        assertThat(historyStatus.getLargeBucketAmount(), is(Const.LARGE_BUCKET_CAPACITY));
        assertNotNull(historyStatus.getProcessText());
    }

    @Test
    public void ステータスオブジェクトに初期値設定とパラメータ設定値を登録した場合値が正しく登録されていること() {
        createStatusData = new CreateStatusData();

        MeasurementGame measurementGame = new MeasurementGame();
        measurementGame.makeDefaultValue();
        Status result = createStatusData.makeAmountAndTextInStatus(
                3, 6, Const.BucketActions.SMALL_FULLIN, measurementGame.queue.poll()
        );
        result = createStatusData.makeHistoryInStatus(result);

        //history以外の値は最新の値が登録されていること
        assertThat(result.getSmallBucketAmount(), is(3));
        assertThat(result.getLargeBucketAmount(), is(6));

        String expectation = util.createActionText(Const.BucketActions.SMALL_FULLIN) + " : (3 , 6)";
        assertThat(result.getProcessText(), is(expectation));

        assertThat(result.getHistory().size(), is(1));

        //1つめにパラメータで渡した値が設定されていること
        Status historyStatus = result.getHistory().get(0);

        assertThat(historyStatus.getSmallBucketAmount(), is(3));
        assertThat(historyStatus.getLargeBucketAmount(), is(6));
        assertNotNull(historyStatus.getProcessText());
    }
}

