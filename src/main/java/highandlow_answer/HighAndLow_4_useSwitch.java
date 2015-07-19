package highandlow_answer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * High and Low ゲームクラス
 */
class HighAndLow_4_useSwitch {
    public static void main(String[] args) {
        // スタート文言の表示
        System.out.println("High and Low ゲームをはじめます。");
        // 基準値の生成
        int first = makeRandomNumber();
        // ユーザの入力
        System.out.println(first + "! 次の値は High? Low?");
        int userAnswer = inputUserAnswer();
        // 勝負値の生成
        int second = makeRandomNumber();
        System.out.println(second + "がでました！");
        // 結果判定
        showResult(first, second, userAnswer);
        System.out.println("ゲームを終わります。");
    }

    /**
     * ランダム数の生成を行います。
     * 最大値・最小値の指定も今回はこのメソッド内で設定しています。
     * 最小値：1 最大値：10
     * @return int 乱数
     */
    static int makeRandomNumber(){
        return (int)(Math.random() * 10 + 1);
    }

    /**
     * ユーザの答えの入力を制御します。
     * 1: High
     * 2: Low
     * 入力チェックも合わせて実施します。
     * @return
     */
    static int inputUserAnswer(){
        String choices = "1: High   2: Low";
        System.out.println(choices);

        String userAnswer = "";

        try(InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is)) {

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
        }

        return Integer.parseInt(userAnswer);
    }

    /**
     * 選択肢の入力チェックを行います。
     * - 数値型であること
     * - 1か2の値であること（1:High 2:Low)
     * @param targetChoice 入力チェック対象
     * @return
     */
    static boolean isChoice(String targetChoice) {
        //必須チェック
        if (targetChoice.isEmpty()) {
            return false;
        }

        //数値型チェック
        Pattern pattern = Pattern.compile("^[12]$");
        Matcher matcher = pattern.matcher(targetChoice);
        if (!matcher.find()) {
            return false;
        }

        return true;
    }

    /**
     * ユーザの選択肢の正誤を確認します
     * @param first 基準値
     * @param second 勝負値
     * @param userAnswer ユーザが入力した答え(1:High 2:Low)
     * @return int 判定結果(1:正解 2:はずれ 3:引き分け）
     */
    static void showResult(int first, int second, int userAnswer){
        System.out.println(second + "がでました！");
        final Result result = go(first, second, userAnswer);
        System.out.println(getResultMessage(result));
    }

    static Result go(int first, int second, int userAnswer) {
        final int got = Integer.compare(first, second);
        if (got == 0) return Result.DRAW;
        if (got < 0 && userAnswer == 1) return Result.WIN;
        if (got > 0 && userAnswer == 2) return Result.WIN;
        return Result.LOSE;
    }

    static String getResultMessage(final Result result) {
        switch (result) {
            case WIN:
                return "あたり！ﾜ━ヽ(*´Д｀*)ﾉ━ｨ!!!";
            case LOSE:
                return "はずれ！(´・ω・｀)";
            case DRAW:
                return "引き分け！";
        }
        throw new IllegalArgumentException();
    }

    enum Result {
        WIN, LOSE, DRAW
    }
}



