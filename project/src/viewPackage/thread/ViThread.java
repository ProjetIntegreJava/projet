package viewPackage.thread;

import viewPackage.PanelManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ViThread extends Thread {
    private final ArrayList<BufferedImage> images;
    private final PanelManager panelManager;
    private int currentIndex;
    public static final int NB_IMAGES = 40;
    public static final String BASE_IMAGE_NAME = "ee7c4f28-bb95-46c7-a020-4078188c4378-";
    public ViThread(PanelManager panelManager) {
        this.panelManager = panelManager;
        this.currentIndex = 0;
        this.images = new ArrayList<>();
        for(int i = 0; i < NB_IMAGES; i++) {
            try {
                images.add(ImageIO.read(new File("project/src/viewPackage/thread/resources/images/" + BASE_IMAGE_NAME + i + ".png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void run() {
        while(true) {
            try {
                if(NB_IMAGES - 1 > this.currentIndex) {
                    this.currentIndex++;
                } else {
                    this.currentIndex = 0;
                }
                this.panelManager.right.setCurrImage(this.images.get(this.currentIndex));
                this.panelManager.right.repaint();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
