package highandlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * High and Low ゲームクラス
 */
class HighAndLow {
    public static void main(String[] args) {
        final GameController controller = newGameController();
        controller.start();
        controller.inputUserAnswer();
        controller.showResult();
        controller.finish();
    }

    private static GameController newGameController() {
        final Game game = new Game(makeRandomNumber(), makeRandomNumber());
        return new GameController(game);
    }

    static int makeRandomNumber() {
        return (int) (Math.random() * 10 + 1);
    }

    static class GameController {

        private static final Pattern inputPattern = Pattern.compile("^[12]$");

        private final Game game;

        private Optional<Answer> answer = Optional.empty();

        public GameController(final Game game) {
            this.game = game;
        }

        /**
         * 開始
         */
        public void start() {
            System.out.println("High and Low ゲームをはじめます。");
        }

        /**
         * 入力
         */
        public void inputUserAnswer() {
            System.out.println(game.getFirstNumber() + "! 次の値は High? Low?");
            System.out.println("1: High   2: Low");
            this.answer = getInputUserAnswer();
        }

        private static Optional<Answer> getInputUserAnswer() {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                while (true) {
                    final String input = br.readLine();
                    if (inputPattern.matcher(input).matches()) {
                        final Answer answer = input.equals("1") ? Answer.HIGH : Answer.LOW;
                        return Optional.of(answer);
                    }

                    System.out.println("入力された値が不正です。正しい値を入力してください。");
                }
            } catch (IOException e) {
                return Optional.empty();
            }
        }

        /**
         * 結果表示
         */
        public void showResult() {
            final Answer answer = this.answer.orElseThrow(IllegalStateException::new);
            System.out.println(game.getSecondNumber() + "がでました！");
            final Result result = game.go(answer);
            System.out.println(getResultMessage(result));
        }

        private String getResultMessage(final Result result) {
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

        /**
         * 終了
         */
        public void finish() {
            System.out.println("ゲームを終わります。");
        }

        public Optional<Answer> getAnswer() {
            return answer;
        }
    }

    enum Answer {
        HIGH, LOW
    }

    enum Result {
        WIN, LOSE, DRAW
    }

    static class Game {

        private final int firstNumber;

        private final int secondNumber;

        public Game(final int firstNumber, final int secondNumber) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
        }

        public int getFirstNumber() {
            return firstNumber;
        }

        public int getSecondNumber() {
            return secondNumber;
        }

        public Result go(final Answer answer) {
            final int got = Integer.compare(firstNumber, secondNumber);
            if (got == 0) return Result.DRAW;
            if (got < 0 && answer == Answer.HIGH) return Result.WIN;
            if (got > 0 && answer == Answer.LOW) return Result.WIN;
            return Result.LOSE;
        }
    }
}



