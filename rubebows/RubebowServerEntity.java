package rubebows;


import envEff.Util.PlugInCreation.EnvEntityServer;

public class RubebowServerEntity extends EnvEntityServer{
	public long ticksExisted = 0;
	public RubebowServerEntity(float x, float y, float z, long time) {
		super(x, y, z, time);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onTickInGame() {
		ticksExisted ++;
		
	}
	@Override
	public int getID(){
		return RubebowMain.instance.getID();
	}

}
