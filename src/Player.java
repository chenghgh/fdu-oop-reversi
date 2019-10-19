public class Player {
    private String name;
    private int color;//1表示黑，-1表示白
    private char sign;//表示图标

    public Player(){}

    public Player(String name,int color){
        this.name = name;
        this.color = color;
    }

    public Player(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public char getSign() {
        return sign;
    }

    public void setSign(char sign) {
        this.sign = sign;
    }
}
