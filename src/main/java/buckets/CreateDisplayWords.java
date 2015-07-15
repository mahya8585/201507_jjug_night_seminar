package buckets;

import static buckets.Const.*;

/**
 * コンソールに表示する文言の作成及び表示を行います。
 * Created by maaya_ishida on 2015/07/13.
 */
public class CreateDisplayWords {

    /**
     * 初期情報の表示と開始の文言を表示する
     */
    void displayStart() {
        StringBuilder start = new StringBuilder();
        start.append(SMALL_BUCKET_CAPACITY)
                .append("L のバケツと ")
                .append(LARGE_BUCKET_CAPACITY)
                .append("L のバケツを使って ")
                .append(ANSWER)
                .append("L の水を計量します。");

        System.out.println(start.toString());
    }



    /**
     * 検索した手順の書き出しを行う。
     * @param answer 結果出力時のデータセット
     */
    void displayAnswerProcess(Status answer) {
        if (answer != null) {
            System.out.println(answer.getHistory().size() + " 回で計量することができました。");

            for (Status process : answer.getHistory()) {
                //書き出し
                System.out.println(process.getProcessText());
            }

        } else {
            //解なしの場合の表示
            System.out.println("答えが出ませんでした。。。");
        }
    }

    /**
     * 結果表示に使用する手順文言の作成を行い、返却します。
     * @param actionName 手順
     * @param status ステータスデータ
     * @return 手順文言
     */
    String createProcessText(BucketActions actionName, Status status) {
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

        processText.append(" : (")
                    .append(status.getSmallBucketAmount())
                    .append(" , ")
                    .append(status.getLargeBucketAmount())
                    .append(")");

        return processText.toString();
    }
}
