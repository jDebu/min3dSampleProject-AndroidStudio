package min3d.sampleProject1;

/**
 * Created by Glup on 29/12/15.
 */
import min3d.Shared;
import min3d.Utils;
import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import min3d.vos.LightType;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AndroidRobot3DView extends RendererActivity implements View.OnClickListener {

    private Object3dContainer androidRobot3DObject;
    private int materialIndex = 0;
    private int rotationDirection = 1;
    private Light myLight = new Light();


    /** Called when the activity is first created. */
    @Override
    public void initScene()
    {
        myLight.position.setAll(300,150,150);

        myLight = new Light();
        myLight.type(LightType.DIRECTIONAL);

        scene.lights().add(myLight);
        scene.backgroundColor().setAll(255,255,255,1);

        IParser myParser = Parser.createParser(Parser.Type.MAX_3DS, getResources(), "min3d.sampleProject1:raw/squared_robot_3ds", false);
        myParser.parse();

        androidRobot3DObject = myParser.getParsedObject();
        androidRobot3DObject.position().x = androidRobot3DObject.position().y = androidRobot3DObject.position().z = 0;


        // We scale the robot, check this value if you want to target another scale
        androidRobot3DObject.scale().x = androidRobot3DObject.scale().y = androidRobot3DObject.scale().z = 1f;
        scene.addChild(androidRobot3DObject);

        // Preloading the textures with the textureManager()
        Bitmap b;
        b = Utils.makeBitmapFromResourceId(R.drawable.squared_robot_body_business);
        Shared.textureManager().addTextureId(b, "squared_robot_body_business");
        b.recycle();

        b = Utils.makeBitmapFromResourceId(R.drawable.squared_robot_body);
        Shared.textureManager().addTextureId(b, "squared_robot_body");
        b.recycle();


        b = Utils.makeBitmapFromResourceId(R.drawable.squared_robot_arm);
        Shared.textureManager().addTextureId(b,"squared_robot_arm");
        b.recycle();

        b = Utils.makeBitmapFromResourceId(R.drawable.squared_robot_foot);
        Shared.textureManager().addTextureId(b,"squared_robot_foot");
        b.recycle();


        b = Utils.makeBitmapFromResourceId(R.drawable.squared_robot_antenna);
        Shared.textureManager().addTextureId(b,"squared_robot_antenna");
        b.recycle();

        b = Utils.makeBitmapFromResourceId(R.drawable.squared_robot_head);
        Shared.textureManager().addTextureId(b,"squared_robot_head");
        b.recycle();

        b = Utils.makeBitmapFromResourceId(R.drawable.squared_robot_head_business);
        Shared.textureManager().addTextureId(b,"squared_robot_head_business");
        b.recycle();

        loadAllTextures();

    }
    /** Updates child and their textures with the textures to be loaded */
    public void loadAllTextures()
    {
        int numChildren = androidRobot3DObject.numChildren();
        for(int i = 0;i<numChildren;i++)
        {
            String name = androidRobot3DObject.getChildAt(i).name();

            Log.d("min3d", "Texture name: " + name);

            // The name is either extracted from the _mtl file
            // or directly from the *.3ds file
            // The name can be given directly into Blender
            if(name.indexOf("body")!=-1)
            {
                androidRobot3DObject.getChildAt(i).textures().clear();
                androidRobot3DObject.getChildAt(i).textures().addById("squared_robot_body");
            }

            if(name.indexOf("head")!=-1)
            {
                androidRobot3DObject.getChildAt(i).textures().clear();
                androidRobot3DObject.getChildAt(i).textures().addById("squared_robot_head");
            }

            if(name.indexOf("foot")!=-1)
            {
                androidRobot3DObject.getChildAt(i).textures().clear();
                androidRobot3DObject.getChildAt(i).textures().addById("squared_robot_foot");
            }


            if(name.indexOf("arm")!=-1)
            {
                androidRobot3DObject.getChildAt(i).textures().clear();
                androidRobot3DObject.getChildAt(i).textures().addById("squared_robot_arm");
            }

            if(name.indexOf("antenna")!=-1)
            {
                androidRobot3DObject.getChildAt(i).textures().clear();
                androidRobot3DObject.getChildAt(i).textures().addById("squared_robot_antenna");
            }
        }

    }


    /** Changing the body texture of the robot */
    public void changeBodyTexture(String aTextureName)
    {
        // If you want, you can target a specific part of the robot
        // and only change the texture of this part by using the getChildByName("")
    	/* Passing through all the children of the robot
    	 * and applying a new texture on them */
        for(int i = 0;i<androidRobot3DObject.numChildren();i++)
        {
            String name = androidRobot3DObject.getChildAt(i).name();
            Log.d("min3d", "Texture name: " + name);

            if(name.indexOf("body")!=-1)
            {
                androidRobot3DObject.getChildAt(i).textures().clear();
                androidRobot3DObject.getChildAt(i).textures().addById(aTextureName);
            }
        }
    }

    /** Changes the texture of the head */
    public void changeHeadTexture(String aTextureName)
    {
        if(androidRobot3DObject.getChildByName("head")!=null)
        {
            androidRobot3DObject.getChildByName("head").textures().removeAll();
            androidRobot3DObject.getChildByName("head").textures().addById(aTextureName);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        {
            if(event.getAction() == MotionEvent.ACTION_UP)
            {
                if(materialIndex == 0)
                {
                    changeBodyTexture("squared_robot_body_business");
                    changeHeadTexture("squared_robot_head_business");
                    rotationDirection  = -1;
                    materialIndex= 1;
                }
                else if(materialIndex == 1)
                {
                    changeBodyTexture("squared_robot_body");
                    changeHeadTexture("squared_robot_head");
                    rotationDirection = 1;
                    materialIndex= 0;
                }

            }}
        return true;

    }

    @Override
    public void updateScene() {

        androidRobot3DObject.rotation().y += rotationDirection;
        myLight.position.setZ(myLight.position.getZ()+1);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }


}