package buckets;


import java.util.*;

/**
 * バケツ容量を得るための探索実行クラス
 * Created by maaya_ishida on 2015/06/19.
 */
public class SearchAnswer {
    //小さいバケツの内容量
    static final int SMALL_BUCKET_CAPACITY = 3;
    //大きいバケツの内容量
    static final int LARGE_BUCKET_CAPACITY = 8;
    //最終的に得たい水の量
    static final int ANSWER = 4;
    //小さいバケツ
    static Bucket smallBucket;
    //大きいバケツ
    static Bucket largeBucket;

    //組み合わせ作成用の代替変数
    static enum BucketActions {
        SMALL_FULLIn,
        SMALL_EMPTY,
        SMALL_MOVE,
        LARGE_FULLIN,
        LARGE_EMPTY,
        LARGE_MOVE
    }

    //やったことある組み合わせ覚書
    static boolean[][] judgmentMap = new boolean[SMALL_BUCKET_CAPACITY][LARGE_BUCKET_CAPACITY];

    public static void main(String args[]) {
        //小さいバケツの作成
        smallBucket = new Bucket(SMALL_BUCKET_CAPACITY);
        //大きいバケツの作成
        largeBucket = new Bucket(LARGE_BUCKET_CAPACITY);

        // 探索と結果表示
        searchExec();
    }

    /**
     * バケツに許された挙動を使って得たい水の量を測るための一番少ない手数を検索します。
     * コンソールに結果表示も行います。
     */
    static void searchExec() {
        Queue<Status> queue = new LinkedList<>();
        //スタートステータスを設定する
        queue.offer(makeStatus(0,0,null));

        //幅優先探索
        for (BucketActions bucketAction : BucketActions.values()){
            //検索用ステータスを作成
            Status actionResult = action(bucketAction, smallBucket.getAmount(), largeBucket.getAmount());
            int sBucketAmount = actionResult.getSmallBucketAmount();
            int lBucketAmount = actionResult.getLargeBucketAmount();

            //正解にたどり着いたらその時点でループを抜け、次の処理へ
            if (sBucketAmount + lBucketAmount == ANSWER) {
                break;
            }

            //過去に判定したことある数値の組み合わせだった場合はそれ以上の計算を行わない(Queueに入れない)
            if (!judgmentMap[sBucketAmount][lBucketAmount]) {
                judgmentMap[sBucketAmount][lBucketAmount] = true;
                queue.offer(makeStatus(sBucketAmount, lBucketAmount, bucketAction));

            } else {
                //TODO Queueに入れない方の処理

            }


            //TODO Queueごとに上記処理を実施する処理
        }

        //TODO 手順の書き出し

    }

    /**
     * バケツ作業を実行しその結果を返却する
     */
    static Status action (BucketActions selectAction, int smallBucketAmount, int largeBucketAmount) {
        //検査用バケツの設定
        Bucket sBucket = new Bucket(SMALL_BUCKET_CAPACITY);
        sBucket.setAmount(smallBucketAmount);
        Bucket lBucket = new Bucket(LARGE_BUCKET_CAPACITY);
        lBucket.setAmount(largeBucketAmount);

        // 指定動作の実行
        switch (selectAction) {
            case LARGE_EMPTY:
                sBucket.empty();
                break;

            case LARGE_FULLIN:
                lBucket.fullIn();
                break;

            case LARGE_MOVE:
                int addAmountToSmallBucket = lBucket.move(sBucket);

                sBucket.setAmount(
                    sBucket.getAmount() + addAmountToSmallBucket
                );
                break;

            case SMALL_EMPTY:
                sBucket.empty();
                break;

            case SMALL_FULLIn:
                sBucket.fullIn();
                break;

            case SMALL_MOVE:
                int addAmountToLargeBucket = sBucket.move(lBucket);

                lBucket.setAmount(
                    lBucket.getAmount() + addAmountToLargeBucket
                );
                break;

            default:
                System.out.println("バケツの行動に予期せぬ行動が設定されました。selectAction = [" + selectAction + "]");
                break;
        }

        //返却stateの作成
        return makeStatus(sBucket.getAmount(), lBucket.getAmount(), selectAction);
    }

    /**
     * ステートオブジェクトの作成
     * @param smallBucketAmount
     * @param largeBucketAmount
     * @param actionName
     * @return
     */
    static Status makeStatus(int smallBucketAmount, int largeBucketAmount, BucketActions actionName) {
        Status resultStatus = new Status();
        resultStatus.setSmallBucketAmount(smallBucketAmount);
        resultStatus.setLargeBucketAmount(largeBucketAmount);
        resultStatus.setActionName(actionName);

        resultStatus.addHistory(resultStatus);

        return resultStatus;
    }

}
