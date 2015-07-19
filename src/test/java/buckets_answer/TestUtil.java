package buckets_answer;

import static buckets_answer.Const.LARGE_BUCKET_CAPACITY;
import static buckets_answer.Const.SMALL_BUCKET_CAPACITY;

/**
 * Testデータ作成のための使い回しクラス
 * Created by maaya_ishida on 2015/07/14.
 */
public class TestUtil {
    /**
     * Statusオブジェクトの容量データ登録を行うテスト補助メソッド
     * @param smallBucketAmount 小さいバケツの容量
     * @param largeBucketAmount 大きいバケツの容量
     * @param actionName 手順名
     * @return  パラメータが設定されたステータスオブジェクト
     */
    public static Status createStatusAmounts(int smallBucketAmount, int largeBucketAmount, Const.BucketActions actionName) {
        Status result = new Status();
        result.setSmallBucketAmount(smallBucketAmount);
        result.setLargeBucketAmount(largeBucketAmount);

        if (actionName != null) {
            StringBuilder processText = new StringBuilder();
            processText.append(" : (")
                    .append(smallBucketAmount)
                    .append(" , ")
                    .append(largeBucketAmount)
                    .append(")");
            result.setProcessText(processText.toString());

        } else {
            result.setProcessText(null);
        }

        return result;
    }

    /**
     * 手順文言の分岐部分を作成します
     * @param actionName アクション名
     * @return 分岐分手順文言
     */
    public String createActionText(Const.BucketActions actionName){
        StringBuilder processText = new StringBuilder();
        if(actionName == null) {
            return null;
        }

        switch (actionName){
            case LARGE_EMPTY:
                processText.append(LARGE_BUCKET_CAPACITY).append("L のバケツを空にする");
                break;

            case LARGE_FULLIN:
                processText.append(LARGE_BUCKET_CAPACITY).append("L のバケツを満たす");
                break;

            case LARGE_MOVE:
                processText.append(LARGE_BUCKET_CAPACITY)
                        .append("L のバケツから")
                        .append(SMALL_BUCKET_CAPACITY)
                        .append("L のバケツへ移動する");
                break;

            case SMALL_EMPTY:
                processText.append(SMALL_BUCKET_CAPACITY).append("L のバケツを空にする");
                break;

            case SMALL_FULLIN:
                processText.append(SMALL_BUCKET_CAPACITY).append("L のバケツを満たす");
                break;

            case SMALL_MOVE:
                processText.append(SMALL_BUCKET_CAPACITY)
                        .append("L のバケツから")
                        .append(LARGE_BUCKET_CAPACITY)
                        .append("L のバケツへ移動する");
                break;
        }

        return processText.toString();
    }
}

