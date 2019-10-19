public class Chess{
    private int[][] chess;
    private int board;
    private int[][] direction = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};

    public Chess(int b){
        board = b;
        chess = new int[board][board];
        for(int i = 0;i < board;i++){
            for(int j = 0;j < board;j++){
                chess[i][j] = 0;
            }
        }
        chess[board/2-1][board/2-1] = chess[board/2][board/2] = -1;
        chess[board/2-1][board/2] = chess[board/2][board/2-1] = 1;
    }

    public void printBoard(){
        System.out.println();
        System.out.print(" ");
        for(int i = 0;i < board;i++){
            System.out.print((char)(97+i));
        }
        System.out.println();
        for(int i = 0;i < board;i++){
            System.out.print((char)(97+i));
            for(int j = 0;j < board;j++){
                if(chess[i][j] == 0)System.out.print("*");
                if(chess[i][j] == 1)System.out.print("X");
                if(chess[i][j] == -1)System.out.print("O");
            }
            System.out.println();
        }
    }

    public int checkBoard(){//检查是否满足结束条件
        int f1 = 1;  //整个棋盘落满
        int f21 = 1;//无黑棋
        int f22 = 1;//无白棋
        int f3 = 1; //两名玩家无子可落

        for(int i = 0;i < board;i++){
            for(int j = 0;j < board;j++){
                if(chess[i][j] == 0)
                    f1 = 0;
                else if(chess[i][j] == 1)
                    f21 = 0;
                else if(chess[i][j] == -1)
                    f22 = 0;
                if(checkLegal(i,j,1) != 0 || checkLegal(i,j,-1) != 0 ){
                    f3 = 0;
                }
            }
        }
        if(f1 == 1){
            System.out.println("The chess is full.");
            return 1;
        }else if(f21 == 1 || f22 == 1){
            System.out.println("One player is win.");
            return 1;
        }else if(f3 == 1){
            System.out.println("Both players have no valid move.");
            return 1;
        }
        return 0;
    }

    public String humanMove(String s,Player human){//玩家移动，同时可以判断下错位置的情况
        int row = s.charAt(0)-97;
        int col = s.charAt(1)-97;
        //if(row > board || col > board) return null;
        if(checkLegal(row,col,human.getColor()) == 0 || row > board || col > board){
            System.out.println("Invalid Move");
            System.out.println("Game over!");

            return "Invalid Move";
        }else {
            move(row,col,human.getColor());
        }
        return null;
    }

    public int[] findPlace(Player player){//用于遍历棋盘，找到合适位置
        int[] rcscore = new int[3];
        int k = 0;
        for(int i = 0;i < board;i++){
            for(int j = 0;j < board;j++){
                k = checkLegal(i,j,player.getColor());
                if(k > rcscore[2]){
                    rcscore[0] = i;
                    rcscore[1] = j;
                    rcscore[2] = k;
                }
            }
        }
        return rcscore;
    }

    public void cptFindPlaceAndMove(Player cpt){

        int[] rcscore = findPlace(cpt);
        int row = rcscore[0];
        int col = rcscore[1];
        int score = rcscore[2];

        if(score != 0){
            move(row,col,cpt.getColor());
            System.out.print("Computer place "+cpt.getSign()+" at "+(char)(97+row)+(char)(97+col));
        }else {
            System.out.print(cpt.getSign()+"player has no valid move.");
        }

    }

    public int checkLegal(int row,int col,int color){
        int score = 0;
        for(int i = 0;i < 8;i++){
            int zscore = 1;
            while(0 <= (row + (zscore+1)*direction[i][0]) && (row + (zscore+1)*direction[i][0])< board && 0 <= (col + (zscore+1)*direction[i][1]) && (col + (zscore+1)*direction[i][1])< board
                   &&chess[row+(zscore)*direction[i][0]][col+(zscore)*direction[i][1]] == -color && chess[row][col] == 0){
                if(chess[row+(zscore + 1)*direction[i][0]][col+(zscore + 1)*direction[i][1]] == color){
                    score += zscore;
                    break;
                }
                zscore += 1;
            }
        }
        return score;
    }

    private void move(int row,int col,int color){//移动落子，在方法内部检查合法性，然后翻转棋
        if(checkLegal(row,col,color) != 0){
            chess[row][col] = color;
            for(int i = 0;i < 8;i++){
                int z = 1;
                while(0 <= (row + (z+1)*direction[i][0]) && (row + (z+1)*direction[i][0])< board && 0 <= (col + (z+1)*direction[i][1]) && (col + (z+1)*direction[i][1])< board
                        &&chess[row+(z)*direction[i][0]][col+(z)*direction[i][1]] == -color ){
                    z += 1;
                    if(chess[row+z*direction[i][0]][col+z*direction[i][1]] == color){
                        for(int j = 0;j < z;j++){
                            chess[row+j*direction[i][0]][col+j*direction[i][1]] = color;
                        }
                        break;
                    }
                }
            }
        }
    }

    public String gameScore(){
        int x = 0;
        int o = 0;
        for(int i = 0;i < board;i++){
            for(int j = 0;j < board;j++){
                if(chess[i][j] == 1){
                    x++;
                }else if(chess[i][j] == -1){
                    o++;
                }
            }
        }
        String result = "X : O = "+x+" : "+o;
        System.out.println("Game over!");
        System.out.println(result);
        if(x > o){
            System.out.println("X player wins.");
        }else if(x < o){
            System.out.println("O player wins.");
        }

        return result;
    }

    public int[][] getChess() {
        return chess;
    }

    public int getBoard() {
        return board;
    }
}