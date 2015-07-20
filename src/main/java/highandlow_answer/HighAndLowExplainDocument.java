package highandlow_answer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * High and Low ゲームクラス
 */
class HighAndLowExplainDocument {
    public static void main(String[] args) {
        // スタート文言の表示
        System.out.println("High and Low ゲームをはじめます。");
        // 基準値の生成
        // TODO メソッドに切り出す
        // TODO final
        int first = (int) (Math.random() * 10 + 1);
        // ユーザの入力
        System.out.println(first + "! 次の値は High? Low?");
        int userAnswer = input_user_answer();
        // 勝負値の生成
        int second = (int) (Math.random() * 10 + 1);
        System.out.println(second + "がでました！");
        // 結果判定
        compareNumber(first, second, userAnswer);
        System.out.println("ゲームを終わります。");
    }

    /**
     * ユーザの答えの入力を制御します。
     * 1: High
     * 2: Low
     * 入力チェックも合わせて実施します。
     *
     * @return
     */
    // TODO キャメルケースにしよう
    // TODO 公開範囲が無駄に広い
    public static int input_user_answer() {
        String choices = "1: High   2: Low";
        System.out.println(choices);

        String userAnswer = "";

        InputStreamReader is = null;
        BufferedReader br = null;
        try {
            is = new InputStreamReader(System.in);
            br = new BufferedReader(is);

            // ユーザの入力を待ちます
            userAnswer = br.readLine();

            // 正しい値が入力されるまで入力させ続けます。
            while(!isChoice(userAnswer)) {
                System.out.println("入力された値が不正です。正しい値を入力してください。");
                System.out.println(choices);
                userAnswer = br.readLine();
            }
        } catch (IOException ioe) {
            System.out.println("入力値に誤りがあります。ゲームを始めからやりなおしてください。 入力値：" + userAnswer);

        } finally {
            try {
                // TODO try-with-resources
                is.close();
                br.close();
            } catch (IOException ioe) {
                System.out.println("クローズ処理に失敗しました。");
            }
        }

        // TODO 例外の可能性
        return Integer.parseInt(userAnswer);
    }

    /**
     * 選択肢の入力チェックを行います。
     * - 数値型であること
     * - 1か2の値であること（1:High 2:Low)
     *
     * @param targetChoice 入力チェック対象
     * @return
     */
    protected static boolean isChoice(String targetChoice) {
        //必須チェック
        if (targetChoice.isEmpty()) {
            return false;
        }

        //数値型チェック
        // TODO patternはキャッシュしてもOK
        // TODO String#matchesでもOK
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(targetChoice);
        if (!matcher.find()) {
            return false;
        }

        //今回はHigh and Low なので2択
        int selectNumber = Integer.parseInt(targetChoice);
        // TODO !気付きにくいかも
        // TODO -> selectNumber != 1 && selectNumber != 2
        if (!(selectNumber == 1 || selectNumber == 2)) {
            return false;
        }

        // TODO まとめられる
        return true;
    }

    /**
     * ユーザの選択肢の正誤を確認します
     *
     * @param a      基準値
     * @param b     勝負値
     * @param c ユーザが入力した答え(1:High 2:Low)
     * @return int 判定結果(1:正解 2:はずれ 3:引き分け）
     */
    // TODO 引数の名前！名前重要
    // TODO 戻り値、enumのほうがいいかもね
    protected static int compareNumber(int a, int b, int c) {
        //Highが正解か？！Lowが正解か？！
        // TODO やりすぎ条件演算子
        // TODO そもそもanswerいらないかも？
        int answer = (a < b) ? 1 : (a > b) ? 2 : 0;

        if (answer == 0) {
            //1つ目の値と2つ目の値が等しい場合は勝負引き分け
            System.out.println("引き分け！");
            return 3;
        } else if (answer == c) {
            System.out.println("あたり！ﾜ━ヽ(*´Д｀*)ﾉ━ｨ!!!");
            return 1;
        } else {
            System.out.println("はずれ！(´・ω・｀)");
            return 2;
        }
    }
}



