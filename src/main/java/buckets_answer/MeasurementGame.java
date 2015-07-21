package buckets_answer;


import java.util.LinkedList;
import java.util.Queue;

import static buckets_answer.Const.*;


/**
 * バケツ容量を得るための探索実行クラス
 * Created by maaya_ishida on 2015/06/19.
 */
public class MeasurementGame {
    //小さいバケツ
    static Bucket smallBucket = new Bucket(SMALL_BUCKET_CAPACITY);
    //大きいバケツ
    static Bucket largeBucket = new Bucket(LARGE_BUCKET_CAPACITY);
    //検索Queue
    static Queue<Status> queue = new LinkedList<>();
    //やったことある組み合わせ覚書
    //0Lから容量までの組み合わせを取るため、二次元配列は容量+1で領域を確保する
    static boolean[][] judgmentMap = new boolean[SMALL_BUCKET_CAPACITY + 1][LARGE_BUCKET_CAPACITY + 1];


    /**
     * 実行メソッド
     * バケツの設定と検索処理の実施
     * @param args
     */
    public static void main(String args[]) {
        //スタート値の設定
        makeDefaultValue();
        CreateDisplayWords displayWords = new CreateDisplayWords();
        displayWords.displayStart();

        //幅優先探索
        Status answer = null;
        while (!queue.isEmpty()) {
            answer = find(queue.poll());
        }

        //手順の書き出し（history)
        displayWords.displayAnswerProcess(answer);
    }

    /**
     * 初期データの作成
     */
    static void makeDefaultValue(){
        //スタートステータスを設定する
        CreateStatusData createStatus = new CreateStatusData();
        Status defaultStatus = createStatus.makeAmountAndTextInStatus(0, 0, null, new Status());

        queue.offer(defaultStatus);
        judgmentMap[0][0] = true;
    }

    /**
     * 幅探索処理
     * @param queueStatus pollしたqueueの情報(status情報)
     * @return 正解時のステータス
     */
    static Status find(Status queueStatus) {
        for (BucketActions bucketAction : BucketActions.values()) {
            //検索用ステータスを作成
            Status actionResult = queueStatus.clone();
            actionResult = executeBucketAction(bucketAction, actionResult);
            int sBucketAmount = actionResult.getSmallBucketAmount();
            int lBucketAmount = actionResult.getLargeBucketAmount();

            CreateStatusData createStatus = new CreateStatusData();

            //正解にたどり着いたらその時点でループを抜け、次の処理へ
            if (sBucketAmount + lBucketAmount == ANSWER) {
                //残りのqueueの削除
                queue = new LinkedList<>();

                return createStatus.makeHistoryInStatus(actionResult);
            }

            //過去に判定したことある数値の組み合わせだった場合はそれ以上の計算を行わない(Queueに入れない)
            if (!judgmentMap[sBucketAmount][lBucketAmount]) {
                judgmentMap[sBucketAmount][lBucketAmount] = true;

                //historyリストの作成
                actionResult = createStatus.makeHistoryInStatus(actionResult);
                queue.offer(actionResult);
            }

            //TODO 考えうる組み合わせ全ての網羅が完了してしまっているか(答えの出せない組み合わせであるか）
            // 判定するべきですが、今回は必ず答えが出るバケツ容量の組み合わせを設定しているため省略します・・・
        }

        return null;
    }

    /**
     * バケツ作業を実行しその結果を返却する
     */
    static Status executeBucketAction (BucketActions selectAction, Status queueStatus) {
        //検査用バケツの設定
        smallBucket.setAmount(queueStatus.getSmallBucketAmount());
        largeBucket.setAmount(queueStatus.getLargeBucketAmount());

        // 指定動作の実行
        switch (selectAction) {
            case LARGE_EMPTY:
                largeBucket.empty();
                break;

            case LARGE_FILL:
                largeBucket.fill();
                break;

            case LARGE_MOVE:
                int addAmountToSmallBucket = largeBucket.move(smallBucket);

                smallBucket.setAmount(
                    smallBucket.getAmount() + addAmountToSmallBucket
                );
                break;

            case SMALL_EMPTY:
                smallBucket.empty();
                break;

            case SMALL_FILL:
                smallBucket.fill();
                break;

            case SMALL_MOVE:
                int addAmountToLargeBucket = smallBucket.move(largeBucket);

                largeBucket.setAmount(
                    largeBucket.getAmount() + addAmountToLargeBucket
                );
                break;
        }

        //返却stateの作成
        CreateStatusData createStatus = new CreateStatusData();
        return createStatus.makeAmountAndTextInStatus(smallBucket.getAmount(), largeBucket.getAmount(), selectAction, queueStatus);
    }

}
