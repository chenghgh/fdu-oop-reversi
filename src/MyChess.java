import java.util.Scanner;

public class MyChess {

    public static void main(String[] args){
        //输入初始化
        Player human = new Player("human");
        Player cpt = new Player("computer");

        Scanner in = new Scanner(System.in);
        int dimension = 0;
        do{
            System.out.print("Enter the board dimension: ");
            dimension = in.nextInt();
        }while (dimension > 26 || dimension < 4);

        Chess chess = new Chess(dimension);
        System.out.print("Computer plays(X/O): ");

        //设置黑白棋与计算机人的对应
        char a = in.next().charAt(0);
        if(a == 'X'){ ;
            cpt.setColor(1);
            cpt.setSign('X');
            human.setColor(-1);
            human.setSign('O');
        }else {
            cpt.setColor(-1);
            cpt.setSign('O');
            human.setColor(1);
            human.setSign('X');
        }
        chess.printBoard();

        //开始记录相关信息
        Record record = new Record();
        record.setStart(chess.getBoard(),human,cpt);

        while(true){
            if(cpt.getColor() == 1){//电脑先走
                chess.cptFindPlaceAndMove(cpt);
                chess.printBoard();
                if(chess.checkBoard() == 1){//检查一遍游戏是否结束
                    record.record[5] = chess.gameScore();
                    record.writeCsv();
                    break;
                }
                int[] rcscore = chess.findPlace(human);
                if(rcscore[2] == 0){//rcscore[0] is row,rcscore[1] is col,rcscore[2] is score
                    System.out.println(human.getSign()+"player has no valid move.");
                    continue;
                }
                System.out.print("Enter move for "+human.getSign()+"(Row/Col):");
                String s = in.next();
                String legalMove = chess.humanMove(s,human);
                if(legalMove != null){
                    record.record[5] = "Human give up";
                    record.writeCsv();
                    break;
                }
                chess.printBoard();
            }else{
                int[] rcscore = chess.findPlace(human);
                if(rcscore[2] == 0){
                    System.out.println(human.getSign()+"player has no valid move.");
                    continue;
                }
                System.out.print("Enter move for "+human.getSign()+"(Row/Col):");
                String s = in.next();
                String legalMove = chess.humanMove(s,human);
                if(legalMove != null){
                    record.record[5] = "Human give up";
                    record.writeCsv();
                    break;
                }
                chess.printBoard();
                if(chess.checkBoard() == 1){
                    record.record[5] = chess.gameScore();
                    record.writeCsv();
                    break;
                }
                chess.cptFindPlaceAndMove(cpt);
                chess.printBoard();
            }
            if(chess.checkBoard() == 1){
                record.record[5] = chess.gameScore();
                record.writeCsv();
                break;
            }
        }
    }
}
