package buckets_answer;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
* 計量ゲームの実行クラスのテスト
*/
public class BucketTest {
    private Bucket bucket;

    //テスト用バケツ容量
    private final int TEST_BUCKET_CAPACITY = 5;
    private final int TEST_TO_BUCKET_CAPACITY = 7;

    @Before
    public void initialize() throws Exception {
        //テスト用バケツ容量セット
        bucket = new Bucket(TEST_BUCKET_CAPACITY);
    }

    @Test
    public void 初期値の設定が正しく行われること() {
        assertThat(bucket.getAmount(), is(0));
        assertThat(bucket.getCapacity(), is(TEST_BUCKET_CAPACITY));
    }

    @Test
    public void fullInを呼び出した場合バケツが満タンになること() {
        bucket.fullIn();

        assertThat(bucket.getAmount(), is(TEST_BUCKET_CAPACITY));
    }

    @Test
    public void emptyを呼び出した場合バケツが空になること() {
        bucket.empty();

        assertThat(bucket.getAmount(), is(0));
    }

    @Test
    public void 注ぎ先バケツの空容量が注ぎ元バケツの容量よりも多い状態でmoveを呼び出した場合注ぎ先と注ぎ元それぞれの量が正しく設定されていること() {
        //注ぎ元バケツの設定
        bucket.setAmount(2);

        //注ぎ先バケツの設定
        Bucket toBucket = new Bucket(TEST_TO_BUCKET_CAPACITY);
        toBucket.setAmount(1);

        //実行
        int moveAmount = bucket.move(toBucket);

        //移動量・注ぎ元バケツ容量の確認
        assertThat(moveAmount, is(2));
        assertThat(bucket.getAmount(), is(0));
    }

    @Test
    public void 注ぎ先バケツの空容量が注ぎ元バケツの容量よりも少ない状態でmoveを呼び出した場合注ぎ先と注ぎ元それぞれの量が正しく設定されていること() {
        //注ぎ元バケツの設定
        bucket.setAmount(4);

        //注ぎ先バケツの設定
        Bucket toBucket = new Bucket(TEST_TO_BUCKET_CAPACITY);
        toBucket.setAmount(6);

        //実行
        int moveAmount = bucket.move(toBucket);

        //移動量・注ぎ元バケツ容量の確認
        assertThat(moveAmount, is(1));
        assertThat(bucket.getAmount(), is(3));
    }

    @Test
    public void 注ぎ先バケツの空容量と注ぎ元バケツの容量が等しい状態でmoveを呼び出した場合注ぎ先と注ぎ元それぞれの量が正しく設定されていること() {
        //注ぎ元バケツの設定
        bucket.setAmount(3);

        //注ぎ先バケツの設定
        Bucket toBucket = new Bucket(TEST_TO_BUCKET_CAPACITY);
        toBucket.setAmount(4);

        //実行
        int moveAmount = bucket.move(toBucket);

        //移動量・注ぎ元バケツ容量の確認
        assertThat(moveAmount, is(3));
        assertThat(bucket.getAmount(), is(0));
    }



}

