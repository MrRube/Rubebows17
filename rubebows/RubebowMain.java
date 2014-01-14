package rubebows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
//import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
//import net.minecraftforge.common.Configuration;
import envEff.EnvironmentalEffect;
import envEff.Util.NetUtils;
import envEff.Util.WorldUtils;
import envEff.Util.Graphics.GLUtil;
import envEff.Util.Graphics.Node;
import envEff.Util.PlugInCreation.EnvEntityClient;
import envEff.Util.PlugInCreation.EnvEntityServer;
import envEff.Util.PlugInCreation.IEffectHandlers;

@Mod(modid = "Rubebow", name = "Rube's Rainbows for Mr. Rube's Environmental Effect API", version = "v1.1.0 for Mc 1.7.2")
public class RubebowMain implements IEffectHandlers {
	public static RubebowMain instance;
	private static boolean canRainbow = false;
	private static boolean rainWatcher = false;
	
	//~~~~~Config Variables~~~~~\\
	private static float offset = 250;	
	public static int baseAl = 64;
	private static int angleRange = 75;
	private static int angleFade = 20;
	private static int fadeTime = 600;
	private static int life = 2400;

	private boolean requestCheck = true;

	private static float sunAngle = 0;
	//public Configuration preInitConfig;
	
	public RubebowMain() {
		instance = this;
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		/*
		preInitConfig = new Configuration(event.getSuggestedConfigurationFile());
		try {
			preInitConfig.load();
			this.setConfig();
			
		} catch (Exception e) {

			FMLLog.log(Level.SEVERE, e,
					"Aurora has a problem loading it's configuration");
		} finally {
			preInitConfig.save();
		}
*/
	}
	private void setConfig() {
		/*
		this.offset = (float)this.preInitConfig.get(Configuration.CATEGORY_GENERAL, "X Offset From Player", offset).getDouble(0);
		this.baseAl = this.preInitConfig.get(Configuration.CATEGORY_GENERAL, "Base Alpha", baseAl).getInt();
		this.angleRange = this.preInitConfig.get(Configuration.CATEGORY_GENERAL, "Player Max Yaw Angle", angleRange).getInt();
		this.angleFade = this.preInitConfig.get(Configuration.CATEGORY_GENERAL, "Angle from Max Yaw Angle Where Fading Starts", angleFade).getInt();
		this.fadeTime = this.preInitConfig.get(Configuration.CATEGORY_GENERAL, "Time to fade in or out", fadeTime).getInt();
		this.life = this.preInitConfig.get(Configuration.CATEGORY_GENERAL, "Life span of the rainbow", this.life).getInt();		
		*/
	}

	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent evt) {
		System.out.println("Registering Rubebows");
		EnvironmentalEffect.registerEffect(instance);
	}

	@Override
	public ArrayList<EnvEntityServer> getActiveServerEntities() {

		return null;
	}

	@Override
	public ArrayList<EnvEntityClient> getActiveClientEntities() {
		// TODO Auto-generated method stub
		return null;
	}
//~~~Not Needed~~~\\
	@Override
	public void commonTickHandler(WorldServer world) {
/*
		if (world.getWorldInfo().isRaining() && !this.rainWatcher) {
			System.out.println("Watching rain");
			this.rainWatcher = true;
		}

		if (this.rainWatcher && !world.getWorldInfo().isRaining()) {
			rainWatcher = false;
			System.out.println("Rainbows Are visable");
			List players = MinecraftServer.getServer()
					.getConfigurationManager().playerEntityList;
			Random rand = new Random();
				int i = rand.nextInt(5);
				if(i > 0){
					System.out.println("Rainbow is a double rainbow OuO");
				}
			RubebowServerEntity ent = new RubebowServerEntity(i, 0, 0,
					System.currentTimeMillis());
			EnvironmentalEffect.registry
					.addToActiveServerEntites(instance, ent);

			for (Object o : players) {
				EntityPlayerMP player = (EntityPlayerMP) o;
				NetUtils.sendPacket((EnvEntityServer) ent, (Player) player);

			}
		}

		if (EnvironmentalEffect.registry.getActiveServerEntites(instance)
				.size() > 0) {
			for(EnvEntityServer ent: EnvironmentalEffect.registry.getActiveServerEntites(instance)){
				ent.onTickInGame();				
			}
			if(((RubebowServerEntity)EnvironmentalEffect.registry.getActiveServerEntites(instance).get(0)).ticksExisted > life){
				System.out.println("Clearing Server Rainbows");
				EnvironmentalEffect.registry.clearActiveServerEntites(instance);
			}
			if (WorldUtils.isTimeOutside(world, 12000, 0)) {
				System.out.println("Clearing Server Rainbows");
				EnvironmentalEffect.registry.clearActiveServerEntites(instance);
			}
		}
*/
	}

	private boolean hasRained(World world) {
		return world.isRaining();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void clientTickHandler(World world) {
		if (world != null) {

			if (world.getWorldInfo().isRaining() && !this.rainWatcher && !(EnvironmentalEffect.registry.getActiveClientEntites(instance).size() > 0)) {
				System.out.println("Watching rain");
				this.rainWatcher = true;
			}

			if (this.rainWatcher && !world.getWorldInfo().isRaining()) {
				rainWatcher = false;
				System.out.println("Rainbows Are visable");

				Random rand = new Random(world.getWorldTime());
					float i = rand.nextFloat()-.75F;
					System.out.println(i);
					if(i > 0){
						System.out.println("Rainbow is a double rainbow OuO");
					}
				RubebowServerEntity ent = new RubebowServerEntity(i, 0, 0,
						System.currentTimeMillis());
				EnvironmentalEffect.registry
						.addToActiveClientEntites(instance, new RubebowClientEntity(ent));

				
			}
			if (this.requestCheck) {
				
				this.requestCheck = false;
			}
			if (EnvironmentalEffect.registry.getActiveClientEntites(instance)
					.size() > 0) {
				sunAngle = MathHelper.wrapAngleTo180_float(WorldUtils
						.getSunAngle(world));
				for (EnvEntityClient ent : EnvironmentalEffect.registry
						.getActiveClientEntites(instance)) {
					ent.onTickInGame();

				}
				despawn(EnvironmentalEffect.registry.getActiveClientEntites(
						instance).get(0), world);
				

			}
		}

	}

	private void despawn(EnvEntityClient ent, World world) {
		if (ent.getTicksExisted() > life) {
			EnvironmentalEffect.registry.clearActiveClientEntites(instance);
		}

	}

	@Override
	public void render(float f) {
		if (EnvironmentalEffect.registry.getActiveClientEntites(instance)
				.size() > 0) {
			if (WorldUtils.isPlayerNotLookingAtSun()) {
				for (EnvEntityClient ent : EnvironmentalEffect.registry
						.getActiveClientEntites(instance)) {
					this.renderRubebow(f, (RubebowClientEntity) ent);
				}
			}
		}

	}

	private static void renderRubebow(float f, RubebowClientEntity ent) {
		Tessellator tess = Tessellator.instance;
		GL11.glPushMatrix();
		int oMod = getOpacityMod(ent);

		GLUtil.translateEntPosistionFixedAxis(f, ent, getRenderDirection(),
				-40F, 0.1F);

		GL11.glScaled(2.25F, 2.25F, 2.25F);

		GLUtil.sexyGlowStart();
		Node[] current;
		Node[] next;
		//System.out.println(ent.bands.size());
		for (ArrayList<Node[]> list : ent.bands) {
			//System.out.println(list);
			for (int i = 0; i < list.size() - 1; i++) {
				current = list.get(i);
				next = list.get(i + 1);

				if (i == list.size() - 2) {
					next = list.get(0);
				}

				for (int inc = 0; inc < current.length - 1; inc++) {
					// int inc = 0;
					int[] c1 = current[inc].color.getColors();
					int[] c2 = next[inc + 1].color.getColors();
					int alpha = current[inc].alpha;
					tess.startDrawing(6);

					tess.setColorRGBA(c1[0], c1[1], c1[2], alpha - oMod);
					tess.addVertex(current[inc].posX, current[inc].posY,
							current[inc].posZ);
					tess.addVertex(next[inc].posX, next[inc].posY,
							next[inc].posZ);
					tess.setColorRGBA(c2[0], c2[1], c2[2], alpha - oMod);
					tess.addVertex(next[inc + 1].posX, next[inc + 1].posY,
							next[inc + 1].posZ);
					tess.addVertex(current[inc + 1].posX,
							current[inc + 1].posY, current[inc + 1].posZ);
					tess.draw();
				}
			}
		}

		GLUtil.sexyGlowStop();
		GL11.glPopMatrix();

	}

	private static int getOpacityMod(RubebowClientEntity ent) {
		float yaw = (float) (Math.abs(WorldUtils.getPlayerViewAngle()[0]));
		int alpha = baseAl;

		if (yaw <= 90 - angleRange + angleFade && yaw >= 90 - angleRange) {
			alpha = baseAl
					- (int) ((yaw - 90 + (float) angleRange)
							/ (float) angleFade * (float) baseAl);
			
		}
		if (yaw >= 90 + angleRange - angleFade && yaw <= 90 + angleRange) {

			alpha = baseAl
					- (int) ((90 + angleRange - yaw) / angleFade * (float) baseAl);
			
		}

		if (yaw > 90 - angleRange + angleFade
				&& yaw < 90 + angleRange - angleFade) {

			alpha = 0;
		}
		if (sunAngle < 5) {
			alpha += baseAl - (int) (((sunAngle) / 5) * (float) baseAl);
			System.out.println(alpha);
		}
		if (sunAngle > 165) {
			alpha += baseAl - (int) (((180 - sunAngle) / 15) * (float) baseAl);
		}
		if (sunAngle > 80 && sunAngle <= 90) {
			alpha += baseAl - (int) (((90 - sunAngle) / 10) * (float) baseAl);
		}
		if (sunAngle <= 100 && sunAngle > 90) {
			alpha += baseAl - (int) (((sunAngle - 90) / 10) * (float) baseAl);
		}
		if (sunAngle < 0) {
			alpha = baseAl;
		}
		if (ent.getTicksExisted() < fadeTime) {
			int fade = baseAl
					- (int) (((float) ent.getTicksExisted() / (float) fadeTime) * (float) baseAl);
			// System.out.println("Fade = " + fade);
			alpha += fade;
		}
		if (ent.getTicksExisted() > life - fadeTime) {
			// System.out.println(ent.getTicksExisted());
			int fade = baseAl
					- (int) (((float) (life - ent.getTicksExisted())
							/ (float) (fadeTime) * (float) baseAl));
			// System.out.println("Fade = " + fade);
			alpha += fade;
		}
		// System.out.println("Alpha = " + alpha);
		return alpha;

	}

	private static float getRenderDirection() {
		float yaw = WorldUtils.getPlayerViewAngle()[0];

		if (yaw >= 0) {
			return -1 * offset;
		}
		return offset;
	}

	private static boolean isRenderable() {
		return false;
	}

	@Override
	public Class<? extends EnvEntityClient> getEntityClientClass() {
		// TODO Auto-generated method stub
		return RubebowClientEntity.class;
	}

	@Override
	public Class<? extends EnvEntityServer> getServerEntityClass() {
		// TODO Auto-generated method stub
		return RubebowServerEntity.class;
	}

	@Override
	public int[] dimensions() {
		// TODO Auto-generated method stub
		return new int[] { 1 };
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Rube's Rainbows";
	}

	@Override
	public Integer getID() {
		// TODO Auto-generated method stub
		return 12345;
	}

	@Override
	public void addEntity(EnvEntityServer server) {
		System.out.println("Adding new entity to client");
		EnvironmentalEffect.registry.addToActiveClientEntites(instance,
				new RubebowClientEntity(server));

	}

	@Override
	public void clearActiveClientEffects() {
		// TODO Auto-generated method stub

	}

}
