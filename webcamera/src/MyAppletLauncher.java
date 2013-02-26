
import javax.swing.JApplet;

public class MyAppletLauncher extends JApplet {

    private JarClassLoader jcl;
    
    @Override
    public void init() {
        jcl = new JarClassLoader();
        try {
            jcl.initApplet("webcam", this);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void start() {
        jcl.startApplet();
    }
    
    @Override
    public void stop() {
        jcl.stopApplet();
    }
    
    @Override
    public void destroy() {
        jcl.destroyApplet();
    }
    
} // class MyAppletLauncher

