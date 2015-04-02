/* When run, this code will produce a scalable American Flag with the correct dimensions.  
 * The flag will automatically resize to fit the width of the window, and then the height of the flag
 * is determined based on the width to height ratio of 1.9.  Therefore, this scaling method is reasonable because 
 * any size flag is capable of being produced by adjusting the width of the window.
 * In the paint(Graphics g) method, 13 alternating red and white stripes are created, as well as columns of white stars.  
 * The stars themselves are created in the star method which finds the 10 points of the star, puts those coordinates in 
 * an array, and then returns out the star polygon.  This star method is then called upon in the pain(Graphics g) method.
 * *Created by Raymond Li*  
 */

import java.awt.*;

import javax.swing.JFrame;


public class Flag extends JFrame 
{
	int startHeight = 500;
	
	final int heightAdjust = 30;
	final int widthAdjust = 3;
	
	double starDiameter = 0.0616;
	static double starOuterRadOverInnerRad = 2.613; 
	
	double heightFlag = 1.0;
	double widthFlag = 1.9;
	double heightOverWidth = 1.9;
	double heightUnion = .5385;
	double widthUnion = 0.76;
	
	public static void main(String[]args)
	{
		Flag frame = new Flag();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public Flag()
	{
		setSize((int) Math.round(startHeight*widthFlag), (int) Math.round(startHeight/heightFlag));
		setResizable(true);
		repaint();
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0,0,getWidth(),getHeight());
		
		int height = getHeight() - heightAdjust;
		int width = getWidth() - widthAdjust;
		
		//horizontal scaling
		if (getHeight() < getWidth()/heightOverWidth) 
		{
			height = (int) Math.round(getWidth()/heightOverWidth - heightAdjust);
			width = (int) Math.round(getWidth()-widthAdjust);
		}
		//vertical scaling
		else if (getWidth() < getHeight()*heightOverWidth)
		{
			height = (int) Math.round(getWidth()/heightOverWidth-heightAdjust);
   		 	width = (int) Math.round(getWidth() - widthAdjust);
		}
		
		
		//stripes
		for(int i=0;i<13; i++)
		{
			if(i%2==0)
			{
				g.setColor(Color.RED);
			}
			else
			{
				g.setColor(Color.WHITE);
			}
			
			g.fillRect(widthAdjust, Math.round(i*(height/13)+heightAdjust), width, height/13);
		}
		
		//Blue Union Rectangle
		int unionWidth = (int) Math.round(widthUnion * height);
		int unionHeight = (int) Math.round(heightUnion * height);
		
		g.setColor(Color.BLUE);
		g.fillRect(widthAdjust, heightAdjust, unionWidth, unionHeight);
		
		//star columns
		double widthSpace = widthUnion/12;
		double heightSpace = heightUnion/10;
		
		//six columns
		g.setColor(Color.WHITE);
		
		for (int k=0;k<=5;k++)
		{
			//columns of 5 stars
			for (int j=0;j<=4;j++) 
			{
				double x0 = widthSpace*height;
				double y0 = heightSpace*height;
				double xInt = 2*k*height*widthSpace;
				double yInt = 2*j*height*heightSpace;
				int xpos = (int) Math.round(x0+xInt+widthAdjust);
				int ypos = (int) Math.round(y0+yInt+heightAdjust);
			
				g.fillPolygon(star(xpos,ypos,(int)Math.round(starDiameter*height)));
			}
			
			//the four star columns only have five columns
			if(k!=5)
			{
				for(int j=0;j<=3;j++)
				{
					double x0 = 2*widthSpace*height;
					double y0 = 2*heightSpace*height;
					double xInt = 2*k*height*widthSpace;
					double yInt = 2*j*height*heightSpace;
					int xpos = (int) Math.round(xInt+x0+widthAdjust);
					int ypos = (int) Math.round(yInt+y0+heightAdjust);
					
			//call upon the star method and input xpos, ypos, and the scaled star diameter as parameters
					g.fillPolygon(star(xpos, ypos, (int) Math.round(starDiameter*height)));
				}
			}
		}
	}
	
	//method star which creates the stars
	public static Polygon star(int xpos, int ypos, int diameter) 
	{
		//creates arrays of 10 for the 10 points
		int[] xcor = new int[10];
		int[] ycor = new int[10];
		
		double radOut = diameter/2;
		double radIn = radOut/starOuterRadOverInnerRad;
		
		//starting angle
		double angle = 3*Math.PI/2;
		
		for(int i=0;i<10;i++)
	    {
	        if(i%2==0)
	        {
	        	xcor[i] = xpos + (int) Math.round(radOut*Math.cos(angle));
	        	ycor[i] = ypos + (int) Math.round(radOut*Math.sin(angle));
	        }
	        else
	        {
	        	xcor[i] = xpos + (int) Math.round(radIn*Math.cos(angle));
	        	ycor[i] = ypos + (int) Math.round(radIn*Math.sin(angle));
	        }
	        	
	        angle += Math.PI/5;
	    }   
	       return new Polygon(xcor, ycor, 10);
	}
	
	
}
