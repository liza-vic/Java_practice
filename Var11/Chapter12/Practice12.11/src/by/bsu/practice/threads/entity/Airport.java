package by.bsu.practice.threads.entity;

public class Airport {
    private static final Airport instance = new Airport();

    public static final int TERMINALS = 5;
    public static final int LADDERS = 7;
    public static final int PLANES = 10;

    private Ladder[] ladders;
    private Terminal[] terminals;
    private Plane[] planes;

    public static Airport getInstance() {
        return instance;
    }

    public Ladder[] getLadders() {
        return ladders;
    }

    public Terminal[] getTerminals() {
        return terminals;
    }

    public Plane[] getPlanes() {
        return planes;
    }


    private Airport(){
        this.ladders = new Ladder[LADDERS];
        this.terminals = new Terminal[TERMINALS];
        this.planes = new Plane[PLANES];

        for(int i = 0;i < LADDERS; i++){
            ladders[i] = new Ladder();
        }

        for(int i = 0;i < TERMINALS; i++){
                terminals[i] = new Terminal();
        }

        for (int i = 0;i < PLANES / 2;i++){
            planes[i] = new Plane(i,(int) ((Math.random() * 11) + 5),RangeZone.values()[0]);
        }

        for (int i = 0;i < PLANES / 2;i++){
            planes[i + PLANES / 2] = new Plane(i + PLANES / 2, (int) ((Math.random() * 11) + 5),RangeZone.values()[1]);
        }



    }
}
