public class Circle 
{
	static private int stepLength;
	static private int height;
	static private int width;

 	private int radio;
	private int x;
	private int y;	
}
public boolean CheckOverlap(Circle c)
	{
		if (Math.sqrt((c.x-x)*(c.x-x)+(c.y-y)*(c.y-y))>c.radio+radio)
			return true;
		return false;
	}
	
	public boolean AllCirCheck(ArrayList <Circle> cirList)
	{
		boolean re = true;
		for(int i = 0 ; i < cirList.size();i++)
			if ((re = CheckOverlap(cirList.get(i)))==false)
				break;
		return re;
	}
	
	public static Circle getBlockCir()
	{
		Circle c = new Circle((int)(Math.random()*width) , (int)(Math.random()*height) , 0 );
		return c;
	}
	
	public static void insertNRandomBlock(ArrayList <Circle> cirList,int n)
	{
		for (int count = 0 ;count <n;count++)
			cirList.add(getBlockCir());
	}

class GraphicPanel extends JPanel
{
	private int WIDTH ;
	private int HEIGHT ;
	GraphicPanel(int width,int height)
	{
		super();
		WIDTH = width;
		HEIGHT = height;
	}
	protected void paintComponent(Graphics g)
	{
		BufferedImage image= (BufferedImage) this.createImage(WIDTH, HEIGHT);
		Graphics imageGra = image.getGraphics();
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int stepLength =sc.nextInt(); 
		int blockNum = sc.nextInt();
		super.paintComponent(imageGra);
		Circle.setStepLength(stepLength);
		Circle.setHeight(HEIGHT);
		Circle.setWidth(WIDTH);
		ArrayList <Circle> cirList = new ArrayList <Circle>();
		Circle.insertNRandomBlock(cirList, blockNum);//插入障碍点
		imageGra.setColor(Color.RED);
		for(int count = 0;count <blockNum;count++)
			blockPainter(imageGra,cirList.get(count));//绘制障碍点
		imageGra.setColor(Color.BLUE);
		int radio = WIDTH/2;
		long count=0;
		while(cirList.size()<n+blockNum)//在圆的数量未满足要求的时候一直执行
		{
			for(int nextX = 1;nextX<WIDTH;nextX+=stepLength)
			{
				if ((nextX-radio)<0||(nextX+radio)>WIDTH)
					continue;
				for(int nextY = 1;nextY<HEIGHT;nextY+=stepLength)
				{
					if(cirList.size()>=n+blockNum)
						break;
					if((nextY-radio)<0||(nextY+radio)>HEIGHT)
						continue;
					int pointColor=image.getRGB(nextX, nextY);
					if(Color.BLUE.getRGB()==pointColor)
						{count++;
						continue;}
					Circle c = new Circle(nextX,nextY,radio);
					if (!c.AllCirCheck(cirList))
						continue;
					cirList.add(c);
					circlePainter(imageGra,c);
				}
			}
			radio -=stepLength;//半径持续递减
		if (radio == 0)
				break;
		}
		g.drawI	mage(image, 0, 0, WIDTH, HEIGHT, 0, 0, WIDTH, HEIGHT, this);
