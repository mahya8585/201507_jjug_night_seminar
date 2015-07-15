package buckets;

import java.util.List;

import static buckets.Const.*;


/**
 * ステータスオブジェクトデータの設定をするメソッド軍
 * Created by maaya_ishida on 2015/06/19.
 */
public class CreateStatusData {
    /**
     * ステートオブジェクトの作成(histoyのみ）
     * @param sourceStatus 上記パラメータを追加する元になるステータスオブジェクト
     * @return パラムを登録したステータス情報
     */
    Status makeHistoryInStatus(Status sourceStatus) {
        List<Status> historyList = sourceStatus.getHistory();
        historyList.add(sourceStatus);
        sourceStatus.setHistory(historyList);

        return sourceStatus;
    }

    /**
     * ステートオブジェクトの作成(historyを除く）
     * @param smallBucketAmount 小さいバケツに現在入っている量
     * @param largeBucketAmount 大きいバケツに現在入っている量
     * @param actionName 行動名
     * @param sourceStatus 上記パラメータを追加する元になるステータスオブジェクト
     * @return パラムを登録したステータス情報
     */
    Status makeAmountAndTextInStatus(int smallBucketAmount, int largeBucketAmount, BucketActions actionName, Status sourceStatus) {
        sourceStatus.setSmallBucketAmount(smallBucketAmount);
        sourceStatus.setLargeBucketAmount(largeBucketAmount);
        sourceStatus.setProcessText(
                new CreateDisplayWords().createProcessText(actionName, sourceStatus)
        );

        return sourceStatus;
    }
}
