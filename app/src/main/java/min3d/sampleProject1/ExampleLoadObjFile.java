package min3d.sampleProject1;

import android.graphics.Bitmap;
import android.util.Log;

import min3d.Shared;
import min3d.Utils;
import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import min3d.vos.TextureVo;

/**
 * How to load a model from a .obj file
 * 
 * @author dennis.ippel
 * 
 */
public class ExampleLoadObjFile extends RendererActivity {
	private Object3dContainer objModel;
	private  int count;
	private TextureVo texture;
	@Override
	public void initScene() {
		
		scene.lights().add(new Light());
		
		IParser parser = Parser.createParser(Parser.Type.OBJ,
				getResources(), "min3d.sampleProject1:raw/m_b_short_standar_corta_cadera_11_obj", true);
		parser.parse();

		objModel = parser.getParsedObject();
		objModel.scale().x = objModel.scale().y = objModel.scale().z = 2.0f;
		scene.addChild(objModel);


		Bitmap b = Utils.makeBitmapFromResourceId(R.drawable.sam_0811__texturaprueba);
		Shared.textureManager().addTextureId(b, "sam_0811__texturaprueba");
		b.recycle();
		//texture = new TextureVo("sam_0811__texturaprueba");
		//objModel.textures().clear();
		//objModel.textures().add(texture);

		loadAllTextures();

		count=0;
	}

	private void loadAllTextures() {
		int numChildren = objModel.numChildren();
		Log.e("NumHijos:", numChildren + "");
		objModel.getChildAt(0).textures().clear();
		objModel.getChildAt(0).pointSmoothing(true);
		objModel.getChildAt(0).doubleSidedEnabled(true);
		objModel.getChildAt(0).textures().addById("sam_0811__texturaprueba");
	}

	@Override
	public void updateScene() {
		/*count++;
		// Assign a different texture to the two objects
		// every second or so
		if (count % 240 == 0) {
			objModel.textures().clear(); // ie, no texture
		}
		else if (count % 240 == 60) {
			objModel.textures().addReplace(texture);
		}
		else if (count % 240 == 120) {
			objModel.textures().addReplace(texture);
		}
		else if (count % 240 == 180) {
			objModel.textures().addReplace(texture);
		}*/

		objModel.rotation().x++;
		objModel.rotation().z++;
	}
}
