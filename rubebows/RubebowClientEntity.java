package rubebows;

import java.util.ArrayList;

import envEff.Util.Graphics.EnumColors;
import envEff.Util.Graphics.Node;
import envEff.Util.PlugInCreation.EnvEntityClient;
import envEff.Util.PlugInCreation.EnvEntityServer;

public class RubebowClientEntity extends EnvEntityClient {

	private static int innerRadius = 80;
	private static int bandSpacing = 2;
	private static int resolution = 30;
	private static int baseAplha = 0;
	public ArrayList<Node[]> secArray;// = new ArrayList<Node[]>();
	public ArrayList<ArrayList<Node[]>> bands;// = new ArrayList<ArrayList<Node[]>>();

	private static EnumColors[] colors = new EnumColors[] { EnumColors.RED,
			EnumColors.ORANGE, EnumColors.YELLOW, EnumColors.GREEN,
			EnumColors.BLUE, EnumColors.VIOLET, EnumColors.RASPBERRY };

	public Node[] mainArray;

	public RubebowClientEntity(float x, float y, float z, long time) {
		super(x, y, z, time);
		
		
		
	}

	public RubebowClientEntity(EnvEntityServer s) {		
		super(s);
		
	}

	@Override
	protected void generate() {
		populateRainbow();
		//this.nodeList.add(mainArray);

	}
	
	private EnumColors getColor(int i, boolean boo){
		if(boo){
			return colors[colors.length - 1 - i];
		}
		else return colors[i];
	}
	private void populateRainbow() {
		secArray = new ArrayList<Node[]>();
		bands = new ArrayList<ArrayList<Node[]>>();
		for(int k = 0; k <= resolution ; k ++){
			Node[] nArray = new Node[7];
			float angle = 360/resolution * k;
			float addX = (float) Math.cos(Math.toRadians(angle))
					* bandSpacing;
			float addY = (float) Math.sin(Math.toRadians(angle))
					* bandSpacing;

			float baseX = (float) Math
					.cos(Math.toRadians(angle)) * innerRadius;
			float baseY = (float) Math
					.sin(Math.toRadians(angle)) * innerRadius;
			for(int i = 0; i < 7 ; i++){
				nArray[i] = new Node(0,
						i * addY + baseY, i * addX + baseX, 0, getColor(i, true), RubebowMain.baseAl);
			}
			//System.out.println(nodeList);
			this.nodeList.add(nArray);
			
			
		}
		bands.add(nodeList);
		
		if(posX > 0){
			
			for(int k = 0; k <= resolution ; k ++){
				Node[] nArray = new Node[7];
				float angle = 360/resolution * k;
				float addX = (float) Math.cos(Math.toRadians(angle))
						* bandSpacing/2;
				float addY = (float) Math.sin(Math.toRadians(angle))
						* bandSpacing/2;

				float baseX = (float) Math
						.cos(Math.toRadians(angle)) * (innerRadius + bandSpacing*20);
				float baseY = (float) Math
						.sin(Math.toRadians(angle)) * (innerRadius + bandSpacing*20);
				for(int i = 0; i < 7 ; i++){
					nArray[i] = new Node(0,
							i * addY + baseY, i * addX + baseX, 0, getColor(i, false), RubebowMain.baseAl/2);
				}				
				this.secArray.add(nArray);	

				
			}
			bands.add(secArray);
			System.out.println(bands.size());
		}
		
		
	}
	
	

	public int getResolution() {
		return resolution;
	}

}
