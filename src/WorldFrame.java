import javax.swing.JFrame;
public class WorldFrame extends JFrame implements Runnable {
    private WorldPanel p;
    private Thread windowThread;

    public WorldFrame(String display){
        super(display);
        int frameWidth = 1050;
        int frameHeight = 1025;
        p = new WorldPanel();
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(600, 100);
        this.setVisible(true);
        startThread();
    }

    public void startThread() {
        windowThread = new Thread(this);
        windowThread.start();
    }

    public void run(){
        while (true){
            p.repaint();
        }
    }
}
