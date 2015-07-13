package buckets;


import java.util.LinkedList;
import java.util.Queue;

import static buckets.Const.*;
import static buckets.CreateDisplayWords.writeAnswerProcess;
import static buckets.CreateDisplayWords.writeStart;


/**
 * バケツ容量を得るための探索実行クラス
 * Created by maaya_ishida on 2015/06/19.
 */
public class MeasurementGame {
    //小さいバケツ
    private static Bucket smallBucket;
    //大きいバケツ
    private static Bucket largeBucket;
    //検索Queue
    private static Queue<Status> queue;
    //やったことある組み合わせ覚書
    static boolean[][] judgmentMap = new boolean[SMALL_BUCKET_CAPACITY][LARGE_BUCKET_CAPACITY];


    /**
     * 実行メソッド
     * バケツの設定と検索処理の実施
     * @param args
     */
    public static void main(String args[]) {
        //スタート値の設定
        makeDefaultValue();
        writeStart();

        //幅優先探索
        Status answer = null;
        while (!queue.isEmpty()) {
            answer = find();
        }

        //手順の書き出し（history)
        writeAnswerProcess(answer);
    }

    /**
     * 初期データの作成
     */
    static void makeDefaultValue(){
        //小さいバケツの作成
        smallBucket = new Bucket(SMALL_BUCKET_CAPACITY);
        //大きいバケツの作成
        largeBucket = new Bucket(LARGE_BUCKET_CAPACITY);

        //スタートステータスを設定する
        queue = new LinkedList<>();
        queue.offer(makeStatus(0,0,null));
    }

    /**
     * 幅探索処理
     * @return 正解時のステータス
     */
    static Status find() {
        Status actionResult;
        for (BucketActions bucketAction : BucketActions.values()) {
            //検索用ステータスを作成
            actionResult = executeBucketAction(bucketAction, smallBucket.getAmount(), largeBucket.getAmount());
            int sBucketAmount = actionResult.getSmallBucketAmount();
            int lBucketAmount = actionResult.getLargeBucketAmount();

            //正解にたどり着いたらその時点でループを抜け、次の処理へ
            if (sBucketAmount + lBucketAmount == ANSWER) {
                queue = null;
                return actionResult;
            }

            //過去に判定したことある数値の組み合わせだった場合はそれ以上の計算を行わない(Queueに入れない)
            if (!judgmentMap[sBucketAmount][lBucketAmount]) {
                judgmentMap[sBucketAmount][lBucketAmount] = true;
                queue.offer(makeStatus(sBucketAmount, lBucketAmount, bucketAction));
            }

            //TODO 本来であれば考えうる組み合わせ全ての網羅が完了してしまっているか(答えの出せない組み合わせであるか）
            // 判定するべきですが、今回は必ず答えが出るバケツ容量の組み合わせを設定しているため省略します・・・
        }

        return null;
    }

    /**
     * バケツ作業を実行しその結果を返却する
     */
    static Status executeBucketAction (BucketActions selectAction, int smallBucketAmount, int largeBucketAmount) {
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
     * @param smallBucketAmount 小さいバケツに現在入っている量
     * @param largeBucketAmount 大きいバケツに現在入っている量
     * @param actionName 行動名
     * @return パラムを登録したステータス情報
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
