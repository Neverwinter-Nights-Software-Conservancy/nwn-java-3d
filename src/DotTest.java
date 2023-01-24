import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.*;
import javax.vecmath.*;

public class DotTest extends Applet {
  
    private java.net.URL texImage = null;

    private SimpleUniverse u = null;

    public BranchGroup createSceneGraph() {
    // Create the root of the branch graph
    BranchGroup objRoot = new BranchGroup();

    // Create the transform group node and initialize it to the
    // identity.  Enable the TRANSFORM_WRITE capability so that
    // our behavior code can modify it at runtime.  Add it to the
    // root of the subgraph.
    TransformGroup objTrans = new TransformGroup();
    objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    objRoot.addChild(objTrans);

    // Create appearance object for textured cube
    Appearance app = new Appearance();
    
    
    Texture tex = new TextureLoader(texImage, this).getTexture();
    app.setTexture(tex);
    TextureAttributes texAttr = new TextureAttributes();

    
    texAttr.setTextureBlendColor(0,0.71f,0.71f,0);
    texAttr.setTextureMode(TextureAttributes.COMBINE);
    texAttr.setCombineRgbMode(TextureAttributes.COMBINE_DOT3);
    texAttr.setCombineRgbSource(0,TextureAttributes.COMBINE_TEXTURE_COLOR);
    texAttr.setCombineRgbSource(1,TextureAttributes.COMBINE_CONSTANT_COLOR);
    app.setTextureAttributes(texAttr);
    
    
    

    // Create textured cube and add it to the scene graph.
    Box textureCube = new Box(0.4f, 0.4f, 0.4f,
                  Box.GENERATE_TEXTURE_COORDS, app);
    objTrans.addChild(textureCube);

    // Create a new Behavior object that will perform the desired
    // operation on the specified transform object and add it into
    // the scene graph.
    Transform3D yAxis = new Transform3D();
    Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
                    0, 0,
                    4000, 0, 0,
                    0, 0, 0);

    RotationInterpolator rotator =
        new RotationInterpolator(rotationAlpha, objTrans, yAxis,
                     0.0f, (float) Math.PI*2.0f);
    BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
    rotator.setSchedulingBounds(bounds);
    objTrans.addChild(rotator);

        // Have Java 3D perform optimizations on this scene graph.
        objRoot.compile();

    return objRoot;
    }

    public DotTest() {
    }

    public DotTest(java.net.URL url) {
        texImage = url;
    }

    public void init() {
        if (texImage == null) {
        // the path to the image for an applet
        try {
            texImage = new java.net.URL(getCodeBase().toString() +
                        "../images/stone.jpg");
        }
        catch (java.net.MalformedURLException ex) {
            System.out.println(ex.getMessage());
        System.exit(1);
        }
    }
    setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D c = new Canvas3D(config);
    add("Center", c);

    // Create a simple scene and attach it to the virtual universe
    BranchGroup scene = createSceneGraph();
    u = new SimpleUniverse(c);

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
    u.getViewingPlatform().setNominalViewingTransform();

    u.addBranchGraph(scene);
    }

    public void destroy() {
    u.removeAllLocales();
    }

    //
    // The following allows DotTest to be run as an application
    // as well as an applet
    //
    public static void main(String[] args) {
        java.net.URL url = null;
        if (args.length > 0) {
        try {
            url = new java.net.URL("file:" + args[0]);
        }
        catch (java.net.MalformedURLException ex) {
            System.out.println(ex.getMessage());
        System.exit(1);
        }
    }
    else {
        // the path to the image for an application
        try {
            url = DotTest.class.getResource("bump.png");
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
    new MainFrame(new DotTest(url), 256, 256);
    }

}
