package lib.fashion;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;

/**
 * Allows to inject a LayerRenderer to the JustFashion Mod.
 * By default, JustFashion omits all custom renders and only uses vanilla base renders. 
 */
public class LayerInjector {
	
	private static List<LayerRenderer> extraLayers = new ArrayList<LayerRenderer>();

	/**
	 * adds given layer to a list of LayerRenderers that will be added when switching to
	 * 'render fashion' in the JustFashion mod.
	 * @param layer : instance of the layer that should be rendered
	 */
	public static void injectLayerToFashionRendering(LayerRenderer layer){
		extraLayers.add(layer);
	}
	
	public static List<LayerRenderer> getExtraLayers() {
		return extraLayers;
	}
}
