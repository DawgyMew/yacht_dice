package yacht_dice;

public class Die {
    private int roll;
    public Die(){
        roll = 0;
    }
    // Get a random number from 1 to 6 //
    public void rollDie(){
        roll = (int)(Math.random() * 6) + 1;
    }
    public int getRoll(){
        return roll;
    }
}
