import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.sql.Date;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;



import com.lti.civil.*;
import com.lti.civil.awt.AWTImageConverter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class webcam  extends JApplet implements CaptureObserver,Serializable

{
	
	
	 JButton upload=null;
	 JButton save=null;
	 JPanel cpanel=null;
	JPanel crpdpanel=null;
	ImageLabel picLabel=null;
	ImageLabel iLabel=null;
	
	ImageLabel picLabel2=null;
	ImageLabel iLabel2=null;

	JPanel panel=null;
	BufferedImage bfimg=null;
    JButton start = null;
    JButton shot = null;
    JButton stop = null;
    CaptureStream captureStream = null;
    boolean takeShot=false;
    boolean panelFlag=false;
    boolean crpPanelFlag=false;
 
    private CaptureSystem system;
    private volatile boolean disposing = false;
    private CaptureSystemFactory factory;
  
    public void run(String arg0) {

        new webcam().setVisible(false);
        repaint();
    	
    }
  
  
  
    public webcam(CaptureSystemFactory factory)
    {  

        this.factory = factory;
  
    }
  
  
    public void init()
    {

	 
 
    
    		this.setSize(1200, 600);
    		this.setLayout(null);
      
        	
        	
        	factory = DefaultCaptureSystemFactorySingleton.instance();
            CaptureSystem system;
            try {
            system = factory.createCaptureSystem();
            system.init();
            List list = system.getCaptureDeviceInfoList();
          
            for (int i = 0; i < list.size(); ++i) {
            CaptureDeviceInfo info = (CaptureDeviceInfo) list.get(i);
            System.out.println((new StringBuilder()).append("Device ID ").append(i).append(": ").append(info.getDeviceID()).toString());
            System.out.println((new StringBuilder()).append("Description ").append(i).append(": ").append(info.getDescription()).toString());
            captureStream = system.openCaptureDeviceStream(info.getDeviceID());
            captureStream.setObserver(webcam.this);
            break;
            }
            } catch (CaptureException ex) {
            ex.printStackTrace();
            }
          
             panel = new JPanel();
             panel.setLayout(null);
             panel.setBackground(Color.white);
          
            this.setContentPane(panel);
          
            start = new JButton("Start Stream");
            start.setBounds(new Rectangle(0,0, 110, 30));
            start.setBackground(Color.lightGray);
            stop = new JButton("Stop Stream");
            stop.setBounds(new Rectangle(200, 0, 110, 30));
            stop.setBackground(Color.lightGray);
            shot = new JButton("Image");
            shot.setBounds(new Rectangle(0,45, 325, 325));
            shot.setBackground(Color.white);
            panel.add(start);
            panel.add(stop);
            panel.add(shot);
            panel.revalidate();
            start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            try {
            captureStream.start();
            } catch (CaptureException ex) {
            ex.printStackTrace();
            }
            }
            });
            stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            try {
            captureStream.stop();
            } catch (CaptureException ex) {
            ex.printStackTrace();
            }
            }
            });
            shot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            takeShot=true;
            }
            });

      
    }
  
 
    public void stop()
    {
    	 try {
	            captureStream.stop();
	    	    File directory = new File (System.getProperty("java.io.tmpdir")+"JarClassLoader");
	    		 // Get all files in directory
	    		    File[] files = directory.listFiles();
	    		    for (File file : files)
	    		    {
	    		    	
	    		    	// Delete each file
	    		    	if (!file.delete())
	    		    	{	
	    		    		// Failed to delete file
	    		    }
	    	    }
	    		    
	           } 
    	 		catch (CaptureException ex) {
	            ex.printStackTrace();
	            }
	            

    }
    
    
    
    
    public void destroy()
    
    
    {
    	
    	try {
            captureStream.stop();
    	    File directory = new File (System.getProperty("java.io.tmpdir")+"JarClassLoader");
    		 // Get all files in directory
    		    File[] files = directory.listFiles();
    		    for (File file : files)
    		    {
    		    	
    		    	// Delete each file
    		    	if (!file.delete())
    		    	{	
    		    		// Failed to delete file
    		    }
    	    }
    		    
           } 
	 		catch (CaptureException ex) {
            ex.printStackTrace();
            }
            
	
    	
    	
    }
    
    
    
    
    public void start()
	{
		

		
	}
    
    
    
    
    
    
    
    
    public webcam() {
        init();
        
               }


    class MyCaptureObserver implements CaptureObserver
    {

        public void onError(CaptureStream sender, CaptureException e)
        {  
            e.printStackTrace();
        }


        public void onNewImage(CaptureStream sender, com.lti.civil.Image image)
        {  
            if (disposing)
                return;
            try
            {
                //setImage(AWTImageConverter.toBufferedImage(image));
            }
            catch (Throwable t)
            {    t.printStackTrace();
            }
        }
      
    }
 
 
  
  
  
  
  
    public  BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg  = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
  
  
   
    public void  CaptureImage(BufferedImage image)
    {
     	
     	if(crpPanelFlag)
     		
     	{
     		
     		panel.remove(crpdpanel);	
     		
     	}
     		
     	
    	
    	if(panelFlag)
    	{
    		
    		panel.remove(picLabel);
    		panelFlag=false;
    	}
    	
        takeShot=false;
        byte bytes[] = null;
        
        
        try {
        	
        	 ByteArrayOutputStream os = new ByteArrayOutputStream();
             ImageIO.write( image, "jpg", os );
            bytes= os.toByteArray();
            os.close();
            
        if (bytes == null) {
        bytes = null;
        return;
        }
      
        //ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        //File file = new File("/img" + Calendar.getInstance().getTimeInMillis() + ".jpg");
       // FileOutputStream fos = new FileOutputStream(file);
       // fos.write(bytes);
       // fos.close();
       // BufferedImage myImage = ImageIO.read(file);
        //shot.setText("");
        //shot.setIcon(new ImageIcon(myImage));
       // shot.revalidate();
        } catch (IOException ex) {
        ex.printStackTrace();
        }
        
        //picLabel = new JLabel(new ImageIcon(image));
        //picLabel.setBounds(0,500, 200, 200);
        picLabel = new ImageLabel(image);
        picLabel.updateUI();
        picLabel.repaint();
        picLabel.revalidate();
        
        ClipMover mover = new ClipMover(picLabel);
        picLabel.addMouseListener(mover);
        picLabel.addMouseMotionListener(mover);
		
        
        iLabel=picLabel.getPanel();
        iLabel.setBounds(350,45, 335, 335);
        iLabel.setBorder(new LineBorder(Color.red,1));
        iLabel.setBorder(BorderFactory.createTitledBorder("Static Image"));
       
        
        iLabel.repaint();
        iLabel.revalidate();
        iLabel.updateUI();
       
        
        if(!panelFlag)
        {
        	// picLabel.setIcon(img);
        	iLabel.repaint();
             
        panel.add(iLabel);
        panel.getRootPane().revalidate();
        panel.updateUI();
        panelFlag=true;
        }
        
       
        panel.repaint();
        panel.revalidate();
        
    }
   
   
   
 
    public void onNewImage(CaptureStream sender, com.lti.civil.Image image)
    {  
      
          
        if (disposing)
            return;
        try
        {
        
           
           
          
            //takeShot=false;
        
           
           
           
           
        //BufferedImage myImage =    (AWTImageConverter.toBufferedImage(image));
 
       
        byte bytes[] = null;
        try {
        if (image == null) {
        bytes = null;
        return;
        }
        try {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(os);
        jpeg.encode(AWTImageConverter.toBufferedImage(image));
        os.close();
        bytes = os.toByteArray();
        
      
        
        } catch (IOException e) {
        e.printStackTrace();
        bytes = null;
        } catch (Throwable t) {
        t.printStackTrace();
        bytes = null;
        }
        if (bytes == null) {
        return;
        }
       
        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage myImage=ImageIO.read(is);
       
        bfimg= resize(myImage,250,250); 
        is.close();
        
        if(takeShot) 
        {
            
            CaptureImage(bfimg);
           
        }
        
        
        shot.setBorder(new LineBorder(Color.red,1));
        shot.setBorder(BorderFactory.createTitledBorder("Live Streaming"));
        shot.setText("");
        shot.setIcon(new ImageIcon(bfimg));
        shot.revalidate();
       
        } catch (IOException ex) {
        ex.printStackTrace();
        }
 
         
       
       
     
      
     
       shot.setToolTipText("Capture Image");
        shot.setText("");
       // shot.setBounds(0, 0, 200, 200);
        //ImageIcon img=new ImageIcon();
        //img.setImage(myImage);
       
        //shot.setIcon(img);
        //shot.revalidate();
     
        }
        catch (Throwable t)
        {    t.printStackTrace();
        }
    }
  
  
  
 
  private Icon ImageIcon(BufferedImage myImage) {
        // TODO Auto-generated method stub
        return null;
    }




        public void onError(CaptureStream stream, CaptureException ce) {
        System.out.println("Error!");
        }

 
 
        public static void main(String args[])
        throws Exception {
        webcam test = new webcam();

        }
        
        
        
        
        
        
        
        
        
        
        
        
        class ImageLabel extends JLabel
        {
        	Image cimg;
        	BufferedImage imageb;
        	Dimension size;
        	public Rectangle clip;
        	boolean showClip,clipedImg;

        	public boolean isShowClip() {
        		return showClip;
        	}


        	JSlider slider1 = new JSlider(SwingConstants.HORIZONTAL, 80, 160, 80);
        	
        	 ClipedPanel clipedPanel;
        			
        	ImageLabel(BufferedImage image)
        	{ 
        		this.imageb=  image;
        		size = new Dimension(imageb.getWidth(), imageb.getHeight());
        	    showClip = true;
        				
        	}
        	

        	public Image getCimg()
    		{ 
    			return cimg;
    		}
        
        	protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                int x = (getWidth() - size.width)/2;
                int y = (getHeight() - size.height)/2;
                
             /*   int x = (getWidth() - size.width)/2;
                int y = (getHeight() - size.height)/2;*/
                
                g2.drawImage(imageb, x, y, this);
               if(showClip)
                {
                   if(clip == null)
                   createClip(110,110);
                   g2.setPaint(Color.red);
                    g2.draw(clip);
                }
            }
        	
        	
        	
   		 public void setClip(int x, int y)
		    {
		        // keep clip within raster
		        int x0 = (getWidth() - size.width)/2;
		        int y0 = (getHeight() - size.height)/2;
		        
		        if(x < x0 || x + clip.width  > x0 + size.width ||
		           y < y0 || y + clip.height > y0 + size.height)
		            return;
		        
		        clip.setLocation(x, y);
		        repaint();
		        
		        clipImage();
     		repaint();
		    }
		 
		    public Dimension getPreferredSize()
		    {
		        return size;
		    }
		 
		    private void createClip(int sx,int sy)
		    {
	            clip = new Rectangle(sx, sy);
		     
		        clip.x = (getWidth() - clip.width)/2;
		        clip.y = (getHeight() - clip.height)/2;
		    }
		 
		    public JPanel getCroppedPanel()
		    {
		    	
		    	ClipedPanel cpanel = this.getClippedImg();
		    	 
/*		    	 upload = new JButton("Upload Image");
		    	 upload.setBackground(Color.lightGray);
		    	 upload.addActionListener(new ActionListener()
		        {

					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
				       	JOptionPane.showMessageDialog(null, "Server Settings need to be Configured", "Upload Image",1);		
						if(clipedImg)
		            	{
		            		//saveImg();
		            		repaint();
		            		 if(getClippedImg()!=null)
					                panel.remove(getClippedImg());
		            	}
		            	
						
						
						 Image image=getCimg();
						 
			        
						 
						 
						 
						 BufferedImage bufferedImage=new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
						 bufferedImage.createGraphics().drawImage(image, 0, 0, null);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						try {
							ImageIO.write(bufferedImage, "png", baos);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						InputStream is = new ByteArrayInputStream(baos.toByteArray());
						
							
					}
					
		        }
		        
		        );
*/		        
		    	 save = new JButton("Save Image");
		    	 save.setBackground(Color.lightGray);
		    	 save.addActionListener(new ActionListener()
		    	 {

						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
					
							saveImg();
							
							
								
						}
						
			        }
			        
			        );
		    	
		     
		    	//cpanel.add(upload);
		    	cpanel.add(save);
		     	cpanel.revalidate();
		        return cpanel;
		    }
		   
		    
		    
		    
		    public void saveImg()
			 {
				 //Image image=getImage();
				 Image image=getCimg();
				 
				 BufferedImage bufferedImage=new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
				 bufferedImage.createGraphics().drawImage(image, 0, 0, null);
				 JFileChooser chooser=new JFileChooser();
						 
				 /**/
				 String e[] = ImageIO.getWriterFormatNames();
					for (int i=0; i<e.length; i++)
						chooser.addChoosableFileFilter(null);
					int result=chooser.showSaveDialog(this);
					if (result==JFileChooser.APPROVE_OPTION)
					{ 
						//String ext="pdf";
						String ext="JPG";
						//String ext=chooser.getFileFilter().getDescription();
						//ext=ext.substring(0, ext.indexOf(' ')).toLowerCase();
						File file=chooser.getSelectedFile();
						String name=file.getName();
						if (!name.endsWith(ext))
							file=new File(file.getParentFile(), name+"."+ext);
						try 
						{
							ImageIO.write(bufferedImage, ext, file);
							
							

						}
						
						
						
						
						
						catch (IOException e1) 
						{
							e1.printStackTrace();
						} 
							
							
						
					}
			 }
		    
		    

		    
		    
		    
		    
		    
		    
		    
		    
		    private void clipImage()
		  //  public ClipedPanel clipImage()
		    {
		        BufferedImage clipped = null;
		        try
		        {
		            int w = clip.width;
		            int h = clip.height;
		            int x0 = (getWidth()  - size.width)/2;
		            int y0 = (getHeight() - size.height)/2;
		            int x = clip.x - x0;
		            int y = clip.y - y0;
		            clipped = imageb.getSubimage(x, y, w, h);
		            
		            //Image cimg = clipped;
		            cimg = clipped;
		            
		            clipedPanel = new  ClipedPanel( cimg);
		            
		           // ClipMover cm = new ClipMover( clipedPanel);
		           
		             crpdpanel = picLabel.getCroppedPanel();
		   	        	            
					
					crpdpanel.setBounds(700,50, 200, 250);
					
					
					crpdpanel.repaint();
					
					crpdpanel.setBorder(new LineBorder(Color.red,1));
					crpdpanel.setBorder(BorderFactory.createTitledBorder("Cropped Image"));
					crpdpanel.setBackground(Color.white);
					
					panel.add(crpdpanel);
					crpPanelFlag=true;
					panel.repaint();
					panel.revalidate();
				            
		        }
		        catch(RasterFormatException rfe)
		        {
		            System.out.println("raster format error: " + rfe.getMessage());
		       //     return;
		        }
		       // JLabel label = new JLabel(new ImageIcon(clipped));
		      //  JOptionPane.showMessageDialog(this, label, "clipped image",JOptionPane.PLAIN_MESSAGE);
		      // return clipedPanel;   
		    }
		    
		    public ClipedPanel getClippedImg()
		    {
		    	return clipedPanel;
		    }
        	
        	
        	
        	
	
        	public ImageLabel getPanel()	
        	{
        		return this;	
        	}
        	
        	
        }
        
        
        
        
        
        
    	class ClipMover extends MouseInputAdapter
    	{
    		ImageLabel cropping;
    	    Point offset;
    	    boolean dragging;
    	       
    	    
    	    public ClipMover(ImageLabel c)
    	    {
    	        cropping = c;
    	        offset = new Point();
    	        dragging = false;
    	    }
    	 
    	    public void mousePressed(MouseEvent e)
    	    {
    	    	
    	        Point p = e.getPoint();
    	       
    	        
    	        if(cropping.clip.contains(p))
    	        {
    	            offset.x = p.x - cropping.clip.x;
    	            offset.y = p.y - cropping.clip.y;
    	            dragging = true;
    	        }
    	    }
    	 
    	    public void mouseReleased(MouseEvent e)
    	    {
    	        dragging = false;
    	    }
    	 
    	    public void mouseDragged(MouseEvent e)
    	    {
    	    	
    	        if(dragging)
    	        {
    	        	 ClipedPanel xcpanel = cropping.getClippedImg(); 
    			//	 System.out.println("xcpanel - "+xcpanel);
    				 if(xcpanel!=null)
    				 {
    		                  panel.remove(xcpanel);
    		             //   System.out.println("xcpanel - "+xcpanel);
    				 }
    	        
    	        	
    	            int x = e.getX() - offset.x;
    	            int y = e.getY() - offset.y;
    	            
    	            
    	            
    	            if( cropping.isShowClip())
    	            	            cropping.setClip(x, y);
    	         
    	       }
    	    }
    	    
    	    
    	}    
        
        
}





class ClipedPanel extends JPanel
{
	private Image image;
	int imageWidth;
	int imageHeight;
	
	BufferedImage imageb;
	Dimension size;
	Rectangle clip;
	boolean showClip;

	ClipedPanel(Image image)
	{ 
		this.image=image;
		this.imageWidth=image.getWidth(null);
		this.imageHeight=image.getHeight(null);
	//	this.setBounds(new Rectangle(0, 60,500, 500));
		
		//this.imageb= new BufferedImage(this.image.getWidth(null),this.image.getWidth(null),BufferedImage.TYPE_INT_RGB);
		
		this.imageb= (BufferedImage) image;
		size = new Dimension(imageb.getWidth(), imageb.getHeight());
	    showClip = false;
	}
	
  	
	
	protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int x = (getWidth() - size.width)/2;
        int y = (getHeight() - size.height)/2;
        
       
        
        
        g2.drawImage(imageb, x, y, this);
        
    }

	 		

	public ClipedPanel getPanel()	{			return this;	}
}
