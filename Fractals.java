import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Fractals
{
    
    public static void Mandelbrot(int w, int h, int max) throws Exception
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
        ImageIO.write(image,"png",new File("mandelbrot.png"));

    }
    public static void JuliaSet(int w, int h, int max) throws Exception
    {
        int maxIter = max;
        double zoom = 5;
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
        ImageIO.write(image,"png",new File("juliaset.png"));

    }
    public static void main(String[] args) throws Exception
    {
        int width = 800, height = 800, max = 1500;
        Mandelbrot(width,height,max);
        JuliaSet(width,height,max);
    }
}
