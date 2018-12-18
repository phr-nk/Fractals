import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Frank Lenoci
 */

class myGUI implements ActionListener
{
    private JFrame frame;
    private JButton b1;
    private JButton b2;
    private Fractals new_fract = new Fractals();
    public myGUI(int width, int height)
    {
        //creating frame
        frame = new JFrame("Fractals");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        //creating MenuBar
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Pick which Fractal to generate");
        b1 = new JButton("Mandelbrot set");
        b2 = new JButton("Julia set");
        panel.add(label); // Components Added using Flow Layout
       
        b1.addActionListener(this);
        b2.addActionListener(this);
        panel.add(b1);
        panel.add(b2);

    
        
        
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.setVisible(true);
    }
    public void displayImage(ImageIcon ii)
    {
        JFrame new_f = new JFrame("output");
        new_f.setSize(800,800);
        JPanel np = new JPanel();
        JLabel nl = new JLabel(ii);
        np.add(nl);
        new_f.getContentPane().add(BorderLayout.CENTER, np);
        new_f.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == b1)
        {
            displayImage(new_fract.Mandelbrot(800,800,1000));
        }
        else if(e.getSource() == b2)
        {
            displayImage(new_fract.JuliaSet(800,800,1000));
        }
       
        
    }
}
public class Fractals 
{
    public static ImageIcon Mandelbrot(int w, int h, int max)
    {
        BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        int black = 0;
        int[] colors = new int[max];
        for(int i = 0; i < max; i++)
        {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i * i+8f);
        }
        for (int row = 0; row < h; row++)
        {
            for(int col = 0; col < w; col++)
            {
                double c_re = (col - w/2) * 4.0 /w;
                double c_im  = (row - w/2) *4.0/w;
                double x = 0, y = 0;
                int iterations = 0;
                while(x*x+y*y < 4 && iterations < max)
                {
                    double x_new = x*x-y*y+c_re;
                    y = 2*x*y+c_im;
                    x = x_new;
                    iterations++;
                }
                if(iterations < max )
                {
                    image.setRGB(col,row,colors[iterations]);
                }
                else 
                {
                    image.setRGB(col,row,black);
                }
            }
        }
        ImageIcon ii = new ImageIcon(image);
        return ii;

    }
    public static ImageIcon JuliaSet(int w, int h, int max)
    {
        int maxIter = max;
        double zoom = 1;
        double cY, cX;
        double moveX = 0, moveY = 0;
        double zx, zy;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        cX = -0.7;
        cY = 0.27015;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                zx = 1.5 * (x - w / 2) / (0.5 * zoom * w) + moveX;
                zy = (y - h / 2) / (0.5 * zoom * h) + moveY;
                float i = maxIter;
                while (zx * zx + zy * zy < 4 && i > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    i--;
                }
                int c = Color.HSBtoRGB((maxIter / i) % 1, 1, i > 0 ? 1 : 0);
                image.setRGB(x, y, c);
            }
        }
        ImageIcon ii = new ImageIcon(image);
        return ii;

    }
    

    public static void main(String[] args) throws Exception
    {
        new myGUI(500,500);
        
    }
